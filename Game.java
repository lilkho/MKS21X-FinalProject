import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private static ArrayList<Player> players;
  private static Card topCard;
  private static ArrayList<Card> deck = new ArrayList<Card>();
  private static ArrayList<Card> discard = new ArrayList<Card>();
  private ArrayList<Rule> rules = new ArrayList<Rule>();
  private Random randgen = new Random();
  private int index;

  public Game(int numPlayers, int numRules){
    //clears discard pile, sets the deck, chooses top card randomly
    discard.clear();
    setDeck();
    topCard = deck.get(Math.abs(randgen.nextInt(deck.size())));
    //bc the card is chosen, remove from deck && add to "discard" (already used) deck
    deck.remove(topCard);
    discard.add(topCard);
    //sets up game with numPlayers and 7 cards each
    players = new ArrayList<Player>(numPlayers);
    //makes a Player for each player && everyone draws 7 Cards
    //separate setName function?
    for(int x=0; x<numPlayers; x++){
      Player person = new Player(""+x, 7);
      players.add(person);
      draw(person,7);
    }
    index = Math.abs(randgen.nextInt(numPlayers));
  }

  public void setDeck(){
    String[] colors = {"BLUE","RED","YELLOW","GREEN"};
    //1-9 has 4 colors each + a duplicate, 0 does not have a duplicate
    for (int i=0;i<10;i++) {
      if(i==0){
        deck.add(new Card("RED",""+i));
        deck.add(new Card("BLUE",""+i));
        deck.add(new Card("YELLOW",""+i));
        deck.add(new Card("GREEN",""+i));
      }else{
        for(int x=0; x<2; x++){
          deck.add(new Card("RED",""+i));
          deck.add(new Card("BLUE",""+i));
          deck.add(new Card("YELLOW",""+i));
          deck.add(new Card("GREEN",""+i));
        }
      }
    }
    //reverse, skip, and +2 has 4 colors each + a duplicate
    for (int i=0;i<4;i++) {
      for(int x=0; x<2; x++){
        deck.add(new Card(colors[i],"REVERSE"));
        deck.add(new Card(colors[i],"SKIP"));
        deck.add(new Card(colors[i],"+2"));
      }
    }
    //4 wilds and 4 +4's
    for (int i=0;i<4;i++) {
      deck.add(new Card("BLACK","+4"));
      deck.add(new Card("BLACK","WILD"));
    }
  }


  public void draw(Player person, int num){
    //if deck is empty, "shuffle" by making the discard pile the draw pile
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
    //conditions if a card is +2
    //how does player choose to draw or to play +2?
    //keep track of combo?
    if(order == true && toPlay.getValue().equals("+2")){
      turn = players.get(index+1);
    }else{
      turn = players.get(index-1);
    }
    if(toPlay.playable(toPlay)){
      person.removeCard(toPlay);
      discard.add(toPlay);
    }
  }

  public void setTurn(Player person){
    turn = person;
  }

  public static void main(String[] args) {
    Game test = new Game(2, 2);
    System.out.println(deck);
    System.out.println(deck.size());
    System.out.println(discard);
  }
}
