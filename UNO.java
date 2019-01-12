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

  public static void main(String[] args){
    /////////NOT TERMINAL STUFF///////////////
    int p = 0;
    int r = 0;
    try{
      if(args[0].toUpperCase().equals("HELP")){
        System.out.println("commands and stuff here");
      }
      if(args[0].toUpperCase().equals("play")){
        int person = Integer.parseInt(args[1]);
        //System.out.println(game+"\n"+game.getPlayers().get(person).getCards());
      }
      if(args.length==0){
        System.out.println("Welcome to UNO! Enter 'help' as an argument for commands");
        throw new IllegalArgumentException("Enter number of players.");
      }
      if(args.length==1){
        p = Integer.parseInt(args[0]);
      }
      if(args.length>=2){
        p = Integer.parseInt(args[0]);
        r = Integer.parseInt(args[1]);
      }
      if(p<2 || p>4){
        System.out.println("Please enter 2-4 players");
        System.exit(1);
      }
    }catch(IllegalArgumentException e){
      System.out.println("Please enter the following arguments: #players [#rules]");
    }
    Game game = new Game(p,r);
    System.out.println(game);

    //setting up terminal
    int x = 0;
    int y = 0;
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();
    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);
    boolean running = true;

    while(running){
      putString(0,5,terminal,"Your Deck: ",Terminal.Color.WHITE,Terminal.Color.DEFAULT);
      for (int i=0;i<game.getPlayers().size();i++) {
        putString(0,i,terminal,"Player "+game.getPlayers().get(i).toString(),Terminal.Color.WHITE,Terminal.Color.DEFAULT);
      }
      for(int i=0; i<game.getPlayers().get(0).getCards().size(); i++){
        Card card = game.getPlayers().get(0).getCards().get(i);
        terminal.moveCursor(0,7+i);
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
        putString(0,7+i,terminal,card.getValue());
      }
      Card topCard = game.getTopCard();
      putString(0,3,terminal,"Top Card: "+topCard.getValue());
      for (int i=0;i<topCard.getValue().length();i++) {
        terminal.moveCursor(1+i,3);
        if (topCard.getColor()=="RED") {terminal.applyBackgroundColor(Terminal.Color.RED);}
        else if (topCard.getColor()=="GREEN") {terminal.applyBackgroundColor(Terminal.Color.GREEN);}
        else if (topCard.getColor()=="BLUE") {terminal.applyBackgroundColor(Terminal.Color.BLUE);}
        else if (topCard.getColor()=="YELLOW") {
          terminal.applyBackgroundColor(Terminal.Color.YELLOW);
          terminal.applyForegroundColor(Terminal.Color.DEFAULT);

        }
        else {
          terminal.applyBackgroundColor(Terminal.Color.WHITE);
          terminal.applyForegroundColor(Terminal.Color.DEFAULT);
        }
        x++;
      }

      terminal.moveCursor(1,6);

      Key key = terminal.readInput();
      if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          running = false;
        }
      }

    }
  }
}
