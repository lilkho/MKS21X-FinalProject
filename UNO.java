//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;

public class UNO{
  public static int count = 0; //to count how many times a player has drawn a
  public static boolean win = false; //to keep track of if sudden death effect is activated

  /**
  * Puts a string on the terminal at a given location
  * @param r starting row to put the string
  * @param c starting column to put the string
  * @param t terminal to put the string
  * @param s String to put
  */
  public static void putString(int r, int c,Terminal t, String s){
		t.moveCursor(r,c);
		for(int i = 0; i < s.length();i++){
			t.putCharacter(s.charAt(i));
		}
    reset(t);
	}

  /**
  * Puts a string on the terminal at a given location
  * @param r starting row to put the string
  * @param c starting column to put the string
  * @param t terminal to put the string
  * @param s String to put
  * @param forg foreground color of the string
  * @param back background color of the string
  */
  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(Terminal.Color.BLACK);

    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
    reset(t);
  }

  /**
  * Resets the terminal's colors
  * @param terminal terminal to reset
  */
  public static void reset(Terminal terminal){
    terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
    terminal.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  /**
  * Prints the cards of the player
  * @param terminal terminal to put the cards
  * @param game game being played
  * @param num index of player
  */
  public static void printCards(Terminal terminal, Game game, int num){
    Terminal.Color x = Terminal.Color.WHITE;
    Terminal.Color y = Terminal.Color.DEFAULT;
    //if sudden death effect, game info will have a magenta background
    if(Game.getSudden()){
      x = Terminal.Color.MAGENTA;
      y = Terminal.Color.WHITE;
    }
    putString(0,5,terminal,"Player "+game.getPlayers().get(num).getName()+"'s Cards:",x,y);
    for(int i=0; i<game.getPlayers().get(num).getCards().size(); i++){
      Card card = game.getPlayers().get(num).getCards().get(i);
      determineColor(terminal, card);
      putString(1,7+i,terminal,""+card.getValue());
    }
    reset(terminal);
  }

  /**
  * Determines the color of a card
  * @param terminal terminal to apply the color
  * @param card card to check
  */
  public static void determineColor(Terminal terminal, Card card){
    if (card.getColor()=="RED") {
      terminal.applyBackgroundColor(Terminal.Color.RED);
      terminal.applyForegroundColor(Terminal.Color.DEFAULT);
    }else if (card.getColor()=="GREEN") {
      terminal.applyBackgroundColor(Terminal.Color.GREEN);
      terminal.applyForegroundColor(Terminal.Color.BLACK);
    }else if (card.getColor()=="BLUE") {
      terminal.applyBackgroundColor(Terminal.Color.BLUE);
      terminal.applyForegroundColor(Terminal.Color.DEFAULT);
    }else if (card.getColor()=="YELLOW"){
      terminal.applyBackgroundColor(Terminal.Color.YELLOW);
      terminal.applyForegroundColor(Terminal.Color.BLACK);
    }else{
      reset(terminal);
    }
  }

  /**
  * Prints the game info, including turn, combo, players, top card, rules, and commands
  * @param terminal terminal to put the game info
  * @param game game to print info of
  */
  public static void printInfo(Terminal terminal, Game game){
    try{
      //default colors of game info
      Terminal.Color x = Terminal.Color.WHITE;
      Terminal.Color y = Terminal.Color.DEFAULT;
      //if sudden death effect, game info will have a magenta background
      if(Game.getSudden()){
        x = Terminal.Color.MAGENTA;
        y = Terminal.Color.WHITE;
      }
      putString(0,0,terminal,"TURN: Player "+game.getTurn().getName(),x,y);
      putString(0,1,terminal,"COMBO: "+game.getCombo(),x,y);
      putString(25,0,terminal,"PLAYER | #CARDS",x,y);
      putString(0,3,terminal,"TOP CARD:",x,y);
      if(game.getRules().size()>10){
        putString(50,11,terminal,"Rules: "+game.printRules1(),x,y);
        putString(50,12,terminal,game.printRules2(),x,y);
      }else{
        putString(50,11,terminal,"Rules: "+game.getRules(),x,y);
      }

      for(int c=0; c<game.getRules().size(); c++){
        putString(50,13+c,terminal,game.getRuleInfo().get(c).toString());
      }
      Card topCard = game.getTopCard();
      determineColor(terminal, topCard);
      putString(10,3,terminal,topCard.getValue());
      for (int i=0;i<game.getPlayers().size();i++) {
        Player person = game.getPlayers().get(i);
        String temp = ": ";
        //win message
        if(person.getCards().size() == 0 || game.getPlayers().size() == 1){
          win = true;
          putString(0,0,terminal,"Player "+i+" won the game! Congratulations!",x,y);
        }
        //if camouflage rule, # of cards will be displayed as "?" instead
        if(game.getRules().contains("CAMOUFLAGE")){
          putString(25,i+2,terminal,person.getName()+temp+"?",x,y);
        }else{
          putString(25,i+2,terminal,person.toString(),x,y);
        }
      }
      /*
      putString(50,2,terminal,"d to draw card(s)");
      putString(50,3,terminal,"h to hide your cards");
      putString(50,4,terminal,"n to pass turn");
      putString(50,5,terminal,"p to play a card");
      putString(50,6,terminal,"arrow up/down to move cursor");
      putString(50,7,terminal,"space to play the card");
      putString(50,8,terminal,"player # to get cards");
      putString(50,9,terminal,"escape to exit");
      */
    }catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Player does not exist!");
    }
  }

  public static void main(String[] args){
    /////////NOT TERMINAL STUFF///////////////
    int p = 0;
    int r = 0;
    try{
      if(args.length==0){
        System.out.println("Welcome to UNO! Enter the number of players and/or rules to start a game.");
        System.exit(1);
      }
      if(args.length==1){
        p = Integer.parseInt(args[0]);
        if(p<2 || p>10){
          System.out.println("Please enter 2-10 players");
          System.exit(1);
        }
      }
      if(args.length>=2){
        p = Integer.parseInt(args[0]);
        r = Integer.parseInt(args[1]);
        if(p<2 || p>10){
          System.out.println("Please enter 2-10 players");
          System.exit(1);
        }
        if(r<0 || r>17){
          System.out.println("Please enter 0-17 rules");
          System.exit(1);
        }
      }
    }catch(IllegalArgumentException e){
      System.out.println("Please enter the following arguments: #players [#rules]");
      System.exit(1);
    }
    Game game = new Game(p,r);

    //setting up terminal
    int x = 0;
    int y = 7;
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();
    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);
    boolean running = true;
    int mode = 0;

    while(running){
      if(win){
        //exits terminal if someone wins
        terminal.exitPrivateMode();
        running = false;
      }

      if(mode == 0){
        //commands
        printInfo(terminal, game);
        reset(terminal);
        putString(50,2,terminal,"d to draw card(s)");
        putString(50,3,terminal,"h to hide your cards");
        putString(50,4,terminal,"p to play a card");
        putString(50,5,terminal,"escape to exit game");
      }

      Key key = terminal.readInput();
      if(key != null){
        //DEFAULT GAME MODE
        if(mode == 0){
          if (key.getKind() == Key.Kind.Escape) {
            terminal.exitPrivateMode();
            running = false;
          }
          //seeing a certain player's cards, and only can choose based on how many players there are.
          if(Character.getNumericValue(key.getCharacter()) < game.getPlayers().size() &&
            Character.getNumericValue(key.getCharacter()) >= 0 &&
            Character.getNumericValue(key.getCharacter()) == Integer.parseInt(game.getTurn().getName())){
            terminal.clearScreen();
            printInfo(terminal, game);
            printCards(terminal, game, Character.getNumericValue(key.getCharacter()));
          }
          //to hide cards
          if(key.getCharacter() == 'h'){
            terminal.clearScreen();
            printInfo(terminal, game);
            reset(terminal);
          }
          //to play cards
          if(key.getCharacter() == 'p'){
            mode = 1;
            terminal.clearScreen();
            reset(terminal);
          }
          // to draw cards
          if(key.getCharacter() == 'd'){
            mode = 2;
            terminal.clearScreen();
            reset(terminal);
          }
        }
      }

      //to play cards
      if(mode == 1){
        //commands
        putString(50,2,terminal,"arrow up/down to move cursor");
        putString(50,3,terminal,"enter to play the card");
        //print player's cards
        Player playing = game.getTurn();
        printInfo(terminal, game);
        printCards(terminal, game, game.getPlayers().indexOf(playing));
        //cursor
        terminal.moveCursor(x,y);
  			terminal.applyBackgroundColor(Terminal.Color.WHITE);
  			terminal.applyForegroundColor(Terminal.Color.BLACK);
  			terminal.putCharacter(' ');
  			terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
  			terminal.applyForegroundColor(Terminal.Color.DEFAULT);
        if (key!=null) {
          //player is limited going above the cards
          if (key.getKind() == Key.Kind.ArrowUp) {
            if (y>7) {
              terminal.moveCursor(x,y);
      			  terminal.putCharacter(' ');
              y--;
            }
          }
          //player is limited going below how many cards there are
    			if (key.getKind() == Key.Kind.ArrowDown) {
            if (y<playing.getCards().size()+6) {
              terminal.moveCursor(x,y);
              terminal.putCharacter(' ');
    				  y++;
            }
          }
          //play card
          if (key.getKind()==Key.Kind.Enter) {
            if (y-7<playing.getCards().size()) {
              Card toPlay = playing.getCards().get(y-7);
              if(game.getTopCard().playable(toPlay) || count==1){
                mode = 0;
                count = 0;
              }else{
                mode = 2;
              }
              game.play(playing,toPlay,toPlay.getColor());
              terminal.clearScreen();
              printInfo(terminal, game);
              reset(terminal);
            }
          }
        }
      }

      //to draw cards
      if(mode == 2){
        Player playing = game.getTurn();
        putString(50,2,terminal,"n to pass turn");
        putString(50,3,terminal,"p to play a card");
        putString(50,4,terminal,"player # to get cards");
        if(key!=null){
          //pass turn
          if(key.getCharacter() == 'n'){
            terminal.clearScreen();
            mode = 0;
            count = 0;
            game.setTurn(1);
            printInfo(terminal, game);
            putString(50,0,terminal,"Player "+game.getTurn().getName()+" passed!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
          }
          //play card
          if(key.getCharacter() == 'p'){
            mode = 1;
          }
          //seeing a certain player's cards, and only can choose based on how many players there are.
          if(Character.getNumericValue(key.getCharacter()) < game.getPlayers().size() &&
            Character.getNumericValue(key.getCharacter()) >= 0 &&
            Character.getNumericValue(key.getCharacter()) == Integer.parseInt(game.getTurn().getName())){
            terminal.clearScreen();
            printInfo(terminal, game);
            printCards(terminal, game, Character.getNumericValue(key.getCharacter()));
          }
        }
        //draws the combo number of cards
        if(game.getCombo()!=0){
          game.draw(playing,game.getCombo());
          //to display correct message
          game.setTurn(-1);
          putString(50,0,terminal,"Player "+playing.getName()+" drew "+game.getCombo()+" cards!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
          game.setTurn(1);
          game.setCombo(0);
          //prevents next player from drawing 1 card bc count = 0
          mode = 0;
        }else{
          if(count==0){
            //draws 1 card bc havent drawn before
            game.draw(playing,1);
            //prevents player from drawing again
            count++;
            putString(50,0,terminal,"Player "+playing.getName()+" drew 1 card!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
            mode = 0;
          }
        }
        printInfo(terminal, game);
      }

    }
  }
}
