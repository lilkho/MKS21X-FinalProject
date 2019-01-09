import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int numCards;

  public Player(String name, int numCards) {
    this.name = name;
    this.numCards = numCards;
  }

  public String getName() {
    return name;
  }

  public int getNumCards() {
    return numCards;
  }

  public void setName(String name){
    this.name = name;
  }

  public ArrayList<Card> getCards() {
    return cards;
  }

  public void removeCard(Card toRemove){
    cards.remove(toRemove);
  }

  public void addCard(Card toAdd){
    cards.add(toAdd);
  }

  public String toString() {
    return name+": "+numCards;
  }
}
