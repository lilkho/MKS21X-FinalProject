import java.util.ArrayList;
import java.util.Random;

public class UNO{
  private boolean order;
  private Player turn;
  private ArrayList<Player> players;
  private Card card;
  private ArrayList<Card> deck = new ArrayList<Card>();
  private Random randgen;

  public UNO(int numPlayers, int numRules){
    players = new ArrayList<Player>(numPlayers);
    
  }

  public void draw(Player person, int num){
    for(int x=0; x<num; x++){
      Card toAdd = deck.get(Math.abs(randgen.nextInt(deck.size())));
      person.addCard(toAdd);
    }
  }

  public void play(Player person, Card toPlay){
    if(toPlay.playable(toPlay)){
      person.removeCard(toPlay);
    }
  }
  
  public void setTurn(Player person){
    turn = person;
  }
}
