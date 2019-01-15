import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private int turnIndex;
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
    if(numRules!=0){
      setRules();
      for(int y=0; y<numRules; y++){
        Rule toAdd = allRules.get(Math.abs(randgen.nextInt(allRules.size())));
        rules.add(toAdd);
        allRules.remove(toAdd);
      }
    }
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
    if(!contains("NO ACTION")){
      for (int i=0;i<4;i++) {
        for(int x=0; x<2; x++){
          deck.add(new Card(colors[i],"REVERSE"));
          deck.add(new Card(colors[i],"SKIP"));
          deck.add(new Card(colors[i],"+2"));
        }
      }
    }
    //4 wilds and 4 +4's
    for (int i=0;i<4;i++) {
      deck.add(new Card("BLACK","+4"));
      if(!contains("NO ACTION")){
        deck.add(new Card("BLACK","WILD"));
      }
    }
  }

  public void setRules(){
    allRules.add(new Rule("There are only numerical and wild cards in the deck.","NO ACTION"));
    /*allRules.add(new Rule("NO COMBO","You cannot block combos."));
    allRules.add(new Rule("CLEAN FINISH","You can only win if your last card is a numerical card."));
    allRules.add(new Rule("PERFECTION","If you play a card whose numerical value is equal to the number of cards in your hand, you can play again."));
    allRules.add(new Rule("SUPER COMBO","You can block a combo with any + card"));
    allRules.add(new Rule("CAMOUFLAGE","You cannot see anyone’s number of cards until they only have 1 card left."));
    allRules.add(new Rule("SUDDEN DEATH CARD","You are eliminated if you are unable to play a card."));
    allRules.add(new Rule("BOMB CARD","You are eliminated if you draw this card."));
    allRules.add(new Rule("INK CARD","When you play this card, every colored card on the next player’s hand turns the color of your ink card."));
  */}

  public boolean contains(String name){
    for(int x=0; x<rules.size(); x++){
      String test = rules.get(x).getName();
      if(name.equals(test)){
        return true;
      }
    }
    return false;
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
    if(num!=1){
      setTurn(1);
    }
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
      if(combo != 0 && !topCard.getValue().equals(toPlay.getValue())){
        System.out.println("Invalid card!");
      }else{
        //if card is a reverse, switch order & turn = next player
        if(players.size() > 2 && toPlay.getValue().equals("REVERSE")){
          if(order){
            order = false;
          } else{
            order = true;
          }
        }
        //if card is a skip, turn = 2 indecies after
        //player size = 2, reverse works like a skip
        if(toPlay.getValue().equals("SKIP") || toPlay.getValue().equals("REVERSE")){
          setTurn(1);
        }
        //if +2, add 2 to combo
        if(toPlay.getValue().equals("+2")){
          combo+=2;
        }
        if(toPlay.getColor().equals("BLACK")){
          //add 4 to combo if +4
          if(toPlay.getValue().equals("+4")){
            combo+=4;
          }
          //wild & +4 choose a color
          String colors[] = {"RED","BLUE","YELLOW","GREEN"};
          toPlay.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
        } else{
            System.out.println(color+" is an invalid color!");
        }
        person.removeCard(toPlay);
        discard.add(toPlay);
        topCard = toPlay;
        setTurn(1);
      }
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
      res+="\nRules: ";
      for(int x=0; x<rules.size(); x++){
        res+=rules.get(x).getName();
      }
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

  public String printRules(){
    String res = "";
    for(int x=0; x<rules.size(); x++){
      res+=rules.get(x).getName();
      if(x!=rules.size()-1){
        res+=", ";
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
