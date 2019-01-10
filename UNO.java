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
    Game game = new Game(Integer.parseInt(args[0]),Integer.parseInt(args[1]));

    int x=0;
    int y=0;
    boolean running = true;
    long lastTime =  System.currentTimeMillis();
    long timer = 3000000;
    long timePassed = 0;

    Terminal terminal = TerminalFacade.createTextTerminal();
    terminal.enterPrivateMode();

    TerminalSize size = terminal.getTerminalSize();
    terminal.setCursorVisible(false);

<<<<<<< HEAD
    for (int i=0;i<game.getPlayers().size();i++) {
      putString(0,i,terminal,"Player "+game.getPlayers().get(i).toString());
    }


    while(running) {
      terminal.moveCursor(x,y);
=======
    boolean running = true;
    long lastTime =  System.currentTimeMillis();
    long timer = 10*60*1000;
    //long timePassed = 0;

    game.getPlayers().get(0).setName("random");
    putString(0,0,terminal,game.printPlayers());

    while(running && (timePassed)!=0){
>>>>>>> ef0aa955beddf6734b1cd37dceae63ffe7c3a0e8
      lastTime = System.currentTimeMillis();
      //timePassed = timer - lastTime;
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
