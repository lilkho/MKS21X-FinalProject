public class Card{
  private String color;
  private String value;

  /**
  * Constructor for card
  * @param col color of card
  * @param val value of card
  */
  public Card(String col, String val){
    color = col;
    value = val;
  }

  /**
  * Sets color of card to given color
  * @param col color to be set to
  */
  public void setColor(String col){
    color = col;
  }

  /**
  * Returns the type of a card
  * @param check card to check type of
  * @return "NUMBER" if the card is a number,
            "ACTION" otherwise
  */
  public String getType(Card check){
    String s = check.getValue();
    if(s.equals("0") || s.equals("1") || s.equals("2") || s.equals("3") || s.equals("4") || s.equals("5") ||
    s.equals("6") || s.equals("7") || s.equals("8") || s.equals("9")){
      return "NUMBER";
    }else{
      return "ACTION";
    }
  }

  /**
  * Returns if the card is playable
  * @param compare card to be compared against the top card
  * @return true if the card is playable,
  *         false otherwise
  */
  public boolean playable(Card compare){
    //black can be placed on any card, same color or value is playable
    if (this.getColor().equals("BLACK")) {return true;}
    return compare.getColor().equals("BLACK") ||
      this.getColor().equals(compare.getColor()) ||
      this.getValue().equals(compare.getValue());
  }

  /**
  * Returns card info
  * @return card's color and value
  */
  public String toString(){
    return ""+color+value;
  }

  //GET METHODS//
  public String getColor(){
    return color;
  }

  public String getValue(){
    return value;
  }
}
