import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int numCards;

  /**
  * Constructor for making a player
  * @param name name of player
  */
  public Player(String name) {
    this.name = name;
  }

  /**
  * Prints player info (name, number of cards)
  * @return String of player info
  */
  public String toString() {
    String res = "Player "+name+": ";
    if(cards.size()==1){
      res+="UNO!";
    }else{
      res+=cards.size();
    }
    return res;
  }

  /**
  * Sets name of player to given name
  * @param name name to be set
  */
  public void setName(String name){
    this.name = name;
  }

  /**
  * Removes card from player's deck
  * @param toRemove card to remove
  */
  public void removeCard(Card toRemove){
    cards.remove(toRemove);
  }

  /**
  * Adds card to player's deck
  * @param toAdd card to add
  */
  public void addCard(Card toAdd){
    cards.add(toAdd);
  }

  //GET METHODS//

  public String getName() {
    return name;
  }

  public int getNumCards() {
    return cards.size();
  }

  public ArrayList<Card> getCards() {
    return cards;
  }
}
