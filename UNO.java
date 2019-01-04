import java.util.ArrayList;

public class UNO{
  private boolean order;
  private Player turn;
  private ArrayList<String> players;
  private Card card;

  public UNO(int numPlayers, int numRules){
    players = new ArrayList<String>[numPlayers];

  }

  public void draw(int num){
    this.cards.add();
  }

  public void play(Card toPlay){
    card = toPlay;
    if(toPlay.playable()){
      this.cards.remove(toPlay);
    }
  }
}
