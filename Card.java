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
    
    terminal.applyBackgroundColor(Terminal.Color.WHITE);
    terminal.applyForegroundColor(Terminal.Color.BLACK);
  }
}
