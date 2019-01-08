import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private static ArrayList<Player> players;
  private Card topCard = new Card("","");
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discard = new ArrayList<Card>();
  private Random randgen;
  private int index;


  public Game(int numPlayers, int numRules){
    //clears discard pile, chooses top card randomly
    discard.clear();
    setDeck();
    topCard = deck.get(Math.abs(randgen.nextInt(deck.size())));
    deck.remove(topCard);
    discard.add(topCard);
    //sets up game with numPlayers and 7 cards each
    players = new ArrayList<Player>();
    for(int x=0; x<numPlayers; x++){
      Player person = new Player(""+x, 7);
      players.set(x, person);
      draw(person,7);
    }
    index = Math.abs(randgen.nextInt(numPlayers));
  }

  public void setDeck(){
    deck.add(new Card("RED","0"));
    deck.add(new Card("RED", "1"));
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
    //conditions if card is a skip
    if(order == true && toPlay.getValue().equals("SKIP")){
      turn = players.get(index+1);
    }else{
      turn = players.get(index-1);
    }
    //conditions if card is a reverse
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

  public static void main(String[] args) {
    Game test = new Game(2, 2);
    System.out.println(players.get(0).getCards());
  }
}
