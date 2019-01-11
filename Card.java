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

  public void setColor(String col){
    color = col;
  }

  public boolean playable(Card compare){
    if(compare.getColor().equals("BLACK") ||
      this.getColor().equals(compare.getColor()) ||
      this.getValue().equals(compare.getValue())){
      return true;
    }else{
      return false;
    }
  }

  public String toString(){
    return ""+color+value;
  }
}
