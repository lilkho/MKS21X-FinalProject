import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private int turnIndex;
  private Player turn;
  private ArrayList<Player> players;
  private Card topCard;
  private ArrayList<Rule> allRules = new ArrayList<Rule>();
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discard = new ArrayList<Card>();
  private ArrayList<Rule> rules = new ArrayList<Rule>();
  private Random randgen = new Random();
  private int index;
  private int combo = 0;

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
    for(int x=0; x<numPlayers; x++){
      Player person = new Player(""+x, 7);
      players.add(person);
      draw(person,7);
    }
    //selects a player to start game
    turnIndex = Math.abs(randgen.nextInt(numPlayers));
    turn = players.get(turnIndex);
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
    boolean play = false;
    //if deck is empty, "shuffle" by making the discard pile the draw pile
    if(deck.size() == 0){
      refresh();
    }
    if(num > deck.size()){
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
    setTurn(1);
  }

  public void refresh(){
    //copies discard to deck
    for(int x=0; x<discard.size(); x++){
      deck.add(discard.get(x));
    }
    discard.clear();
  }

  public void play(Player person, Card toPlay, String color){
    //if card is playable, remove from player's hand, add to discard pile,
    //set it as top card, and set turn to next player
    if(topCard.playable(toPlay)){
      //if card is a skip, turn = 2 indecies after
      if(toPlay.getValue().equals("SKIP")){
        setTurn(1);
      //if card is a reverse, switch order & turn = next player
      }
      if(toPlay.getValue().equals("REVERSE")){
        if(order){
          order = false;
        } else{
          order = true;
        }
      }
      //conditions if a card is +2
      if(toPlay.getValue().equals("+2")){
        combo+=2;
      }
      if(toPlay.getColor().equals("BLACK")){
        if(toPlay.getValue().equals("+4")){
          combo+=4;
        }
        String colors[] = {"RED","BLUE","YELLOW","GREEN"};
        toPlay.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
      } else{
          System.out.println(color+" is an invalid color!");
      }
      person.removeCard(toPlay);
      discard.add(toPlay);
      topCard = toPlay;
      setTurn(1);
    } else {
      System.out.println("Invalid card!");
    }
  }

  public void setTurn(int num){
    if(order){
      //loops around like a circle
      index = (index+num)%players.size();
    }else{
      if(index == 0){
        index = players.size() - num;
      }else if(index == 1 && num == 1){
        index = 0;
      }else if(index == 1 && num == 2){
        index = players.size()-1;
      }else{
        index -= num;
      }
    }
    turn = players.get(index);
  }

  public String toString(){
    /*format:
    Players: [Player 0: 7, Player 1: 7]
    Turn: Player 1
    Top Card: BLUE3
    Combo: 2 (only if not 0)
    Rules: [Perfection] (only if not empty) */
    String res = players+"\nTurn: Player "+players.get(index).getName()+"\nTop Card: "+topCard;
    if(combo!=0){
      res+="\nCombo: "+combo;
    }
    if(!rules.isEmpty()){
      res+="\nRules: "+rules;
    }
    return res;
  }


  public String printPlayers(){
    //format: Player 1: 7
    //Player 2: 7
    String res = "";
    for(int x=0; x<players.size(); x++){
      res+="Player "+players.get(x)+" cards";
      if(x!=players.size()-1){
        res+="\n";
      }
    }
    return res;
  }

  public Card getTopCard(){
    return topCard;
  }

  public ArrayList<Card> getDeck(){
    return deck;
  }

  public ArrayList<Card> getDiscard(){
    return discard;
  }

  public ArrayList<Player> getPlayers(){
    return players;
  }

  public ArrayList<Rule> getRules(){
    return rules;
  }

  public int getIndex(){
    return index;
  }

  public boolean getOrder(){
    return order;
  }

  public Player getTurn(){
    return turn;
  }

  public ArrayList<Rule> getAllRules(){
    return allRules;
  }

  public int getCombo(){
    return combo;
  }

  public void setCombo(int num){
    combo = num;
  }

  public static void main(String[] args) {
    /*Game test = new Game(2, 2);
    System.out.println(deck);
    System.out.println(deck.size());
    System.out.println(discard);
    System.out.println(players);
    System.out.println(test);
    players.get(0).setName("name here");
    printPlayers();*/
  }
}
