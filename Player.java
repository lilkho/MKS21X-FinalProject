import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Card> cards;
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

  public ArrayList<Card> getCards() {
    return cards;
  }

  public String toString() {
    return name+": "+numCards;
  }
}
