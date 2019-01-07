import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private ArrayList<Player> players;
  private Card card;
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discard = new ArrayList<Card>();
  private Random randgen;
  private int index;
  

  public Game(int numPlayers, int numRules){
    players = new ArrayList<Player>(numPlayers);
    for(int x=0; x<numPlayers; x++){

    }
    index = Math.abs(randgen.nextInt(numPlayers));
  }

  public void draw(Player person, int num){
    if(deck.size()==0){
      refresh();
    }
    for(int x=0; x<num; x++){
      //chooses a random card from the deck and adds it to the player's hand
      Card toAdd = deck.get(Math.abs(randgen.nextInt(deck.size())));
      person.addCard(toAdd);
      //removes card from deck and adds it to "discard" pile
      discard.add(toAdd);
      deck.remove(toAdd);
    }
  }

  public void refresh(){
    for(int x=0; x<discard.size(); x++){
      deck.set(x, discard.get(x));
    }
    discard.clear();
  }

  public void play(Player person, Card toPlay){
    if(order == true && toPlay.getValue().equals("SKIP")){
      turn = players.get(index+1);
    }else{
      turn = players.get(index-1);
    }
    if(order == true && toPlay.getValue().equals("REVERSE")){
      order = false;
      turn = players.get(index-1);
    }else{
      order = true;
      turn = players.get(index+1);
    }
    if(toPlay.playable(toPlay)){
      person.removeCard(toPlay);
    }
  }

  public void setTurn(Player person){
    turn = person;
  }
}
