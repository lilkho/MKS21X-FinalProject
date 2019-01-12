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
    putString(0,5,terminal,"Your Cards:",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
    for(int i=0; i<game.getPlayers().get(x).getCards().size(); i++){
      Card card = game.getPlayers().get(x).getCards().get(i);
      terminal.moveCursor(0,7+i);
      determineColor(terminal, card);
      putString(0,7+i,terminal,card.getValue());
    }
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

  public static void main(String[] args){
    /////////NOT TERMINAL STUFF///////////////
    int p = 0;
    int r = 0;
    try{
      if(args[0].toUpperCase().equals("HELP")){
        System.out.println("commands and stuff here");
      }
      /*if(args[0].toUpperCase().equals("play")){
        int person = Integer.parseInt(args[1]);
        System.out.println(game+"\n"+game.getPlayers().get(person).getCards());
      }
*/      if(args.length==0){
        System.out.println("Welcome to UNO! Enter 'help' as an argument for commands.");
        throw new IllegalArgumentException("Enter number of players.");
      }
      if(args[0].toUpperCase().equals("START") && args.length==2){
        p = Integer.parseInt(args[1]);
      }
      if(args[0].toUpperCase().equals("START") && args.length>=3){
        p = Integer.parseInt(args[1]);
        r = Integer.parseInt(args[2]);
      }
      if(p<2 || p>4){
        System.out.println("Please enter 2-4 players");
        System.exit(1);
      }
    }catch(IllegalArgumentException e){
      System.out.println("Please enter the following arguments: start #players [#rules]");
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
      putString(20,0,terminal,"This is mode "+mode);
      Key key = terminal.readInput();
      if(key != null){
        //main screen with commands and instructions
        if(mode == 0){
          if (key.getKind() == Key.Kind.Escape) {
            terminal.exitPrivateMode();
            running = false;
          }
          if(key.getCharacter() == ' '){
            mode = 1;
          }
          if(key.getCharacter() == '1'){
            terminal.clearScreen();
            printCards(terminal, game, 0);
          }
          if(key.getCharacter() == '2'){
            terminal.clearScreen();
            printCards(terminal, game, 1);
          }
        }
        //second screen for playing
        if(mode == 1){
          if(key.getCharacter() == '1'){
            printCards(terminal, game, 0);
          }
          if(key.getCharacter() == '2'){
            mode = 3;
          }
          if(key.getCharacter() == '3'){
            mode = 4;
          }
          if(key.getCharacter() == '4'){
            mode = 5;
          }
          if(key.getCharacter() == ' '){
            mode = 0;
          }

          for (int i=0;i<game.getPlayers().size();i++) {
            putString(0,i,terminal,"Player "+game.getPlayers().get(i).toString(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
          }

          Card topCard = game.getTopCard();
          putString(0,3,terminal,"Top Card: "+topCard.getValue());
          for (int i=0;i<topCard.getValue().length();i++) {
            terminal.moveCursor(1+i,3);
            determineColor(terminal, topCard);
          }
        }

        if(mode != 0 && mode != 1){
          if(key.getCharacter() == 'd'){

          }
        }

        }
      }
  }
}
