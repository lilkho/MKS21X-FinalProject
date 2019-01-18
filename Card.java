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

  public String getType(Card check){
    String s = check.getValue();
    if(s.equals("0") || s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") ||
    s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9")){
      return "ACTION";
    }else{
      return "NUMBER";
    }
  }

  public boolean playable(Card compare){
    //to prevent other cards to be placed on +2 or +4 bc they share the same color
    /*if(this.getValue().equals("+2") && !compare.getValue().equals("+2") ||
      this.getValue().equals("+4") && !compare.getValue().equals("+4")){
        return false;
    }*/
      //black can be placed on any card, same color or value is playable
    if (this.getColor().equals("BLACK")) {return true;}
    return compare.getColor().equals("BLACK") ||
      this.getColor().equals(compare.getColor()) ||
      this.getValue().equals(compare.getValue());
  }

  public String toString(){
    return ""+color+value;
  }
}
