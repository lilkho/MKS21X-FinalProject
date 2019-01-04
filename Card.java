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

public class Card{
  private String color;
  private String value;

  public Card(String col, String val){
    color = col;
    value = val;
  }

  public String getColor(){
    return color;
  }

  public String getValue(){
    return value;
  }

  public boolean playable(Card compare){
    if(this.getColor().equals(compare.getColor()) ||
      this.getValue().equals(compare.getValue())){
      return true;
    }else{
      return false;
    }
  }

  public String toString(){
    Terminal terminal = TerminalFacade.createTextTerminal();
		terminal.enterPrivateMode();

    boolean running = true;

    while(running){
      Key key = terminal.readInput();
      if (key != null)
      {

        if (key.getKind() == Key.Kind.Escape) {

          terminal.exitPrivateMode();
          running = false;
        }
      }
      terminal.applyBackgroundColor(Terminal.Color.WHITE);
      terminal.applyForegroundColor(Terminal.Color.BLACK);
    }
  }
}
