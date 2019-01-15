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

  public static void putString(int r, int c,Terminal t, String s){
		t.moveCursor(r,c);
		for(int i = 0; i < s.length();i++){
			t.putCharacter(s.charAt(i));
		}
	}

  public static void putString(int r, int c,Terminal t,
        String s, Terminal.Color forg, Terminal.Color back ){
    t.moveCursor(r,c);
    t.applyBackgroundColor(forg);
    t.applyForegroundColor(Terminal.Color.BLACK);

    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
    t.applyBackgroundColor(Terminal.Color.DEFAULT);
    t.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  public static void reset(Terminal terminal){
    terminal.applyBackgroundColor(Terminal.Color.BLACK);
    terminal.applyForegroundColor(Terminal.Color.DEFAULT);
  }

  public static void printCards(Terminal terminal, Game game, int x){
    putString(0,5,terminal,"Player "+game.getPlayers().get(x).getName()+"'s Cards:",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    for(int i=0; i<game.getPlayers().get(x).getCards().size(); i++){
      Card card = game.getPlayers().get(x).getCards().get(i);
      determineColor(terminal, card);
      putString(0,7+i,terminal,""+i+": "+card.getValue());
    }
    terminal.applyBackgroundColor(Terminal.Color.BLACK);
    terminal.applyForegroundColor(Terminal.Color.DEFAULT);
  }

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

  public static void printInfo(Terminal terminal, Game game){
    putString(0,0,terminal,"TURN: Player "+game.getTurn().getName(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    putString(0,1,terminal,"COMBO: "+game.getCombo(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    putString(25,0,terminal,"PLAYER | #CARDS",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    putString(0,3,terminal,"TOP CARD:",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    Card topCard = game.getTopCard();
    determineColor(terminal, topCard);
    putString(10,3,terminal,topCard.getValue());
    for (int i=0;i<game.getPlayers().size();i++) {
      Player test = game.getPlayers().get(i);
      if(test.getCards().size() == 0){
        putString(50,0,terminal,"Player "+i+" won the game! Congratulations!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
      }
      putString(25,i+2,terminal,test.toString(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    }
  }

  public static void main(String[] args){
    /////////NOT TERMINAL STUFF///////////////
    int p = 0;
    int r = 0;
    try{
      if(args.length==0){
        System.out.println("Welcome to UNO! Enter the number of players to start a game.");
        System.exit(1);
      }
      if(args.length==1){
        p = Integer.parseInt(args[0]);
        if(p<2 || p>4){
          System.out.println("Please enter 2-4 players");
          System.exit(1);
        }
      }
      if(args.length>=2){
        p = Integer.parseInt(args[0]);
        r = Integer.parseInt(args[1]);
        if(p<2 || p>4){
          System.out.println("Please enter 2-4 players");
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
    int y = 0;
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();
    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);
    boolean running = true;
    int mode = 0;

    while(running){
      if(mode == 0){
        //commands
        printInfo(terminal, game);
        reset(terminal);
      //  putString(50,0,terminal,"Rules: "+game.printRules());
        putString(50,3,terminal,"d to draw card(s)");
        putString(50,4,terminal,"h to hide your cards");
        putString(50,5,terminal,"p to play a card");
        putString(50,6,terminal,"(player) # to get cards");
        putString(50,7,terminal,"escape to exit");

      }

      Key key = terminal.readInput();
      if(key != null){
        if(mode == 0){
          try{
            if (key.getKind() == Key.Kind.Escape) {
              terminal.exitPrivateMode();
              running = false;
            }
            if(key.getCharacter() == '0'){
              terminal.clearScreen();
              printInfo(terminal, game);
              printCards(terminal, game, 0);
            }
            if(key.getCharacter() == '1'){
              terminal.clearScreen();
              printInfo(terminal, game);
              printCards(terminal, game, 1);
            }
            if(key.getCharacter() == '2'){
              terminal.clearScreen();
              printInfo(terminal, game);
              printCards(terminal, game, 2);
            }
            if(key.getCharacter() == '3'){
              terminal.clearScreen();
              printInfo(terminal, game);
              printCards(terminal, game, 3);
            }
            if(key.getCharacter() == 'h'){
              terminal.clearScreen();
              printInfo(terminal, game);
              reset(terminal);
            }
            if(key.getCharacter() == 'd'){
              Player playing = game.getTurn();
              if(game.getCombo()!=0){
                game.draw(playing,game.getCombo());
                game.setCombo(0);
              }else{
                game.draw(playing,1);
              }
              terminal.clearScreen();
              printInfo(terminal, game);
              reset(terminal);
            }
            if(key.getCharacter() == 'p'){
              mode = 2;
              terminal.clearScreen();
              reset(terminal);
            }
            if(key.getCharacter() == 'n'){
              game.setTurn(1);
            }
          } catch(IndexOutOfBoundsException e){
            terminal.clearScreen();
            reset(terminal);
            putString(0,0,terminal,"Player does not exist!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
          }
        }

        //to play cards
        if(mode == 2){
          try{
            Player playing = game.getTurn();
            putString(30,15,terminal,"Choose index of card to play!");
            printInfo(terminal, game);
            printCards(terminal, game, game.getPlayers().indexOf(playing));
            int choice = Character.getNumericValue(key.getCharacter());
            if (choice < playing.getCards().size()) {
              mode = 0;
              Card toPlay = playing.getCards().get(choice);
              game.play(playing,toPlay,toPlay.getColor());
              terminal.clearScreen();
              printInfo(terminal, game);
              putString(30,15,terminal,"chosen card: "+key.getCharacter());
              reset(terminal);
            }
          }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Choose index of card.");
          }
        }

      }
    }
  }
}
