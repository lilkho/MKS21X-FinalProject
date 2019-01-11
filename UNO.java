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

  public static void main(String[] args){
    /////////NOT TERMINAL STUFF///////////////
    int p = 2;
    int r = 0;
    try{
      if(args.length==0){
        throw new IllegalArgumentException("Enter number of players.");
      }
      if(args.length!=0){
        p = Integer.parseInt(args[0]);
        r = Integer.parseInt(args[1]);
        if(p<2 || p>4){
          System.out.println("Please enter 2-4 players");
          System.exit(1);
        }
      }
    }catch(IllegalArgumentException e){
      System.out.println("Please enter the following arguments: #players [#rules]");
    }
    Game game = new Game(p,r);
    //putString(0,0,terminal,game.printPlayers());
    //setting up terminal
    int x = 0;
    int y = 0;
    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();
    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

    boolean running = true;

    while(running){
      x=1;y=4;
      Card topCard = game.getTopCard();

      terminal.moveCursor(1,4);

      for (int i=0;i<topCard.getValue().length();i++) {
        terminal.moveCursor(1+i,4);

        if (topCard.getColor()=="RED") {terminal.applyBackgroundColor(Terminal.Color.RED);}
        else if (topCard.getColor()=="GREEN") {terminal.applyBackgroundColor(Terminal.Color.GREEN);}
        else if (topCard.getColor()=="BLUE") {terminal.applyBackgroundColor(Terminal.Color.BLUE);}
        else if (topCard.getColor()=="YELLOW") {
          terminal.applyBackgroundColor(Terminal.Color.YELLOW);
          terminal.applyForegroundColor(Terminal.Color.BLACK);
        }
        else {
          terminal.applyBackgroundColor(Terminal.Color.WHITE);
          terminal.applyForegroundColor(Terminal.Color.BLACK);
        }
        terminal.putCharacter(topCard.getValue().charAt(i));
        terminal.applySGR(Terminal.SGR.RESET_ALL);
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

      for (int i=0;i<game.getPlayers().size();i++) {
        putString(1,i+1,terminal,"Player "+game.getPlayers().get(i).toString());
      }
    }




  }
}
