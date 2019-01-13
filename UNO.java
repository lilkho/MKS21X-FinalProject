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

  public static void printCards(Terminal terminal, Game game, int x){
    putString(0,3,terminal,"Your Cards:",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    for(int i=0; i<game.getPlayers().get(x).getCards().size(); i++){
      Card card = game.getPlayers().get(x).getCards().get(i);
      terminal.moveCursor(0,5+i);
      determineColor(terminal, card);
      putString(0,5+i,terminal,""+i+": "+card.getValue());
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
      terminal.applyBackgroundColor(Terminal.Color.BLACK);
      terminal.applyForegroundColor(Terminal.Color.DEFAULT);
    }
  }

  public static void printInfo(Terminal terminal, Game game){
    putString(30,0,terminal,"PLAYING: Player "+game.getTurn().getName(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    for (int i=0;i<game.getPlayers().size();i++) {
      putString(30,i+3,terminal,"Player "+game.getPlayers().get(i).toString(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    }
    putString(0,0,terminal,"Top Card:",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    Card topCard = game.getTopCard();
    terminal.moveCursor(10,0);
    determineColor(terminal, topCard);
    putString(10,0,terminal,topCard.getValue());
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
        putString(30,10,terminal,"d to draw card(s)");
        putString(30,11,terminal,"h to hide your cards");
        putString(30,12,terminal,"p to play a card");
        putString(30,13,terminal,"(player) # to get cards");
        putString(30,14,terminal,"escape to exit");
      }
      if(mode == 1){
        putString(30,10,terminal,"space to return to main screen");
      }
      putString(20,9,terminal,"This is mode "+mode);
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
              terminal.applyBackgroundColor(Terminal.Color.BLACK);
              terminal.applyForegroundColor(Terminal.Color.DEFAULT);
            }
            if(key.getCharacter() == 'd'){
              Player playing = game.getTurn();
              game.draw(playing,1);
              //game.setTurn(1);
              terminal.clearScreen();
              printInfo(terminal, game);
              printCards(terminal, game, game.getPlayers().indexOf(playing));
              /*
              mode = 1;
              terminal.clearScreen();
              terminal.applyBackgroundColor(Terminal.Color.BLACK);
              terminal.applyForegroundColor(Terminal.Color.DEFAULT);
              */
            }
            if(key.getCharacter() == 'p'){
              mode = 2;
              terminal.clearScreen();
              terminal.applyBackgroundColor(Terminal.Color.BLACK);
              terminal.applyForegroundColor(Terminal.Color.DEFAULT);
            }
          } catch(IndexOutOfBoundsException e){
            terminal.clearScreen();
            putString(0,0,terminal,"Player does not exist!",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
            terminal.applyBackgroundColor(Terminal.Color.BLACK);
            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
          }
        }

        //to draw cards
        if(mode == 1){
          if(key.getCharacter() == ' '){
            mode = 0;
            terminal.clearScreen();
            terminal.applyBackgroundColor(Terminal.Color.BLACK);
            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
          }
        }

        if (key != null){
            if (key.getKind() == Key.Kind.Escape) {
              terminal.exitPrivateMode();
              running = false;
            }
        }

        //to play cards
        if(mode == 2){
          putString(30,15,terminal,"Choose index of card to play!");
          Player playing = game.getTurn();
          printInfo(terminal, game);
          printCards(terminal, game, game.getPlayers().indexOf(playing));
          if (Character.getNumericValue(key.getCharacter()) < playing.getCards().size()) {
            mode = 0;
            game.play(playing,playing.getCards().get(Character.getNumericValue(key.getCharacter())),playing.getCards().get(Character.getNumericValue(key.getCharacter())).getColor());
            //game.setTurn(1);
            terminal.clearScreen();
            printInfo(terminal, game);
            printCards(terminal, game, game.getPlayers().indexOf(playing));
            putString(30,15,terminal,"chosen card: "+key.getCharacter());
          }
          /*
          Player playing = game.getTurn();
          char c = key.getCharacter();
          int toPlay = Character.getNumericValue(c);
          game.play(playing,playing.getCards().get(0),"");
          */
          if(key.getCharacter() == ' '){
            mode = 0;
            terminal.clearScreen();
            terminal.applyBackgroundColor(Terminal.Color.BLACK);
            terminal.applyForegroundColor(Terminal.Color.DEFAULT);
          }
        }

      }
    }
  }
}
