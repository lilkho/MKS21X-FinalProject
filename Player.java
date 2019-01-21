import java.util.ArrayList;
import java.util.Random;

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

  /**
  * Remove n random card(s) from the player's deck
  * @param num number of cards to remove
  */
  public void remove(int num){
    Random randgen = new Random();
    for(int i=0; i<num; i++){
      int rand = Math.abs(randgen.nextInt(cards.size()));
      cards.remove(rand);
    }
  }

  //GET METHODS//

  public String getName() {
    return name;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }
}
