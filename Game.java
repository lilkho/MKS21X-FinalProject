import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private ArrayList<Player> players;
  private Card topCard;
  private ArrayList<Rule> allRules = new ArrayList<Rule>();
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discard = new ArrayList<Card>();
  private ArrayList<String> rules = new ArrayList<String>();
  private ArrayList<Rule> ruleInfo = new ArrayList<Rule>();
  private Random randgen = new Random();
  private int index;
  private int combo = 0;
  private static boolean sudden = false;

  /**
  * Constructor to start an UNO game
  * @param numPlayers number of players in the game
  * @param numRules number of rules in the game
  */
  public Game(int numPlayers, int numRules){
    //clears discard pile, sets the deck, chooses top card randomly
    discard.clear();
    deck.clear();
    if(numRules!=0){
      setRules();
      for(int y=0; y<numRules; y++){
        Rule toAdd = allRules.get(Math.abs(randgen.nextInt(allRules.size())));
        if(toAdd.getName().equals("MESS")){
          y=numRules;
          rules.clear();
          ruleInfo.clear();
          ruleInfo.add(toAdd);
          String name = toAdd.getName();
          rules.add(name);
          allRules.remove(toAdd);
        }else if(toAdd.getName().equals("NO ACTION")){
          boolean checked = false;
          for(int i=ruleInfo.size()-1; i>0; i--){
            Rule r = ruleInfo.get(i);
            if(r.getType(r).equals("ACTION") || r.getName().equals("SUPER COMBO")){
              allRules.remove(toAdd);
              i=0;
            }

          }
          checked = true;
          if(checked){
            allRules.remove(toAdd);
          }
        }else if(rules.contains("NO ACTION") && toAdd.getType(toAdd).equals("ACTION")){
          allRules.remove(toAdd);
        }else if(rules.contains("NO COMBO") && toAdd.getName().equals("SUPER COMBO")){
          allRules.remove(toAdd);
        }else if(rules.contains("SUPER COMBO") && (toAdd.getName().equals("NO COMBO") || toAdd.getName().equals("NO ACTION"))){
          allRules.remove(toAdd);
        }else{
          ruleInfo.add(toAdd);
          String name = toAdd.getName();
          rules.add(name);
          allRules.remove(toAdd);
        }
      }
    }
    setDeck();
    players = new ArrayList<Player>(numPlayers+1);
    //makes a Player for each player && everyone draws 7 Cards
    for(int x=0; x<numPlayers; x++){
      Player person = new Player(""+x);
      players.add(person);
      draw(person,7);
    }
    //this is needed bc if the first card chosen is an action card,
    //the action must be carried out
    Card test = deck.get(Math.abs(randgen.nextInt(deck.size())));
    play(new Player(""),test);
    //selects a player to "start" game
    turn = players.get(Math.abs(randgen.nextInt(numPlayers)));
    setTurn(1);
  }

  /**
  * Sets the deck for the game based on the rules in the game
  */
  public void setDeck(){
    String[] colors = {"BLUE","RED","YELLOW","GREEN"};
    //1-9 has 4 colors each + a duplicate, 0 does not have a duplicate
    if(!rules.contains("MESS")){
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
    }
    //reverse, skip, and +2 has 4 colors each + a duplicate
    if(!rules.contains("NO ACTION")){
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
      deck.add(new Card("BLACK","WILD"));
      if(!rules.contains("NO ACTION")){
        deck.add(new Card("BLACK","+4"));
      }
    }
    if(rules.contains("BOMB CARD") || rules.contains("MESS")){
      deck.add(new Card("BLACK","BOMB"));
    }
    if(rules.contains("INK CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"INK"));
      }
    }
    if(rules.contains("SUDDEN DEATH CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"SUDDEN DEATH"));
      }
    }
    if(rules.contains("EQUALITY CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"EQUALITY"));
      }
    }
    if(rules.contains("RAIN CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"RAIN"));
      }
    }
    if(rules.contains("CLONE CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card("BLACK","CLONE"));
      }
    }
    if(rules.contains("MAGNET CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"MAGNET"));
      }
    }
    if(rules.contains("JUSTICE CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"JUSTICE"));
      }
    }
    if(rules.contains("THUNDER CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"THUNDER"));
      }
    }/*
    if(rules.contains("MYSTERIOUS CARD") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card("BLACK","MYSTERIOUS"));
      }
    }*/
  }

  /**
  * Sets the rules in the games
  */
  public void setRules(){

    allRules.add(new Rule("NO ACTION","There are only numerical and wild cards in the deck."));
    allRules.add(new Rule("CAMOUFLAGE","You cannot see anyone’s number of cards until they only have 1 card left."));
    allRules.add(new Rule("PERFECTION","If you play a card whose numerical value is equal to the number of cards in your hand, you can play again."));
    allRules.add(new Rule("NO COMBO","You cannot block combos."));
    allRules.add(new Rule("CLEAN FINISH","You can only win if your last card is a numerical card."));
    allRules.add(new Rule("BOMB CARD","You are eliminated if you draw this card."));
    allRules.add(new Rule("SUPER COMBO","You can block a combo with any + card"));
    allRules.add(new Rule("SUDDEN DEATH CARD","You are eliminated if you are unable to play a card."));
    allRules.add(new Rule("INK CARD","When you play this card, every colored card on the next player’s hand turns the color of your ink card."));
    allRules.add(new Rule("MESS","The deck consists only of action cards."));
    allRules.add(new Rule("EQUALITY CARD","When you play this card, every player either draws or discards cards until everyone has 3 cards."));
    allRules.add(new Rule("OVERLOAD","Players with more than 10 cards get eliminated."));
    allRules.add(new Rule("RAIN CARD","When you play this card, every other player draws 1 card."));
    allRules.add(new Rule("CLONE CARD","The card activates the same effect as the previous card."));
    allRules.add(new Rule("JUSTICE CARD","When you play this card, discard 1 card for each player with fewer cards than you."));
    allRules.add(new Rule("THUNDER CARD","When you play this card, two random players draw 1-5 cards."));
    allRules.add(new Rule("MAGNET CARD","When you play this card, discard every card that has your magnet's color."));
//EVERYTHING UP TO THIS POINT WORKS//
/*
    allRules.add(new Rule("HELL","add description!"));
    allRules.add(new Rule("GIFT CARD","add description!"));
    allRules.add(new Rule("STACKING","add description!!!"));
    allRules.add(new Rule("MYSTERIOUS CARD","add description!"));
*/
    }

    /**
    * Sets the turn given how many people are skipped
    * @param num the number of people to skip
    */
  public void setTurn(int num){
    if(num == 0){
      index = index;
    }else if(num == -1){
      index= index-1%players.size();
    }else if(order){
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

  /**
  * Refreshes the deck if it is empty
  */
  public void refresh(){
    //copies discard to deck
    for(int x=0; x<discard.size(); x++){
      deck.add(discard.get(x));
    }
    discard.clear();
  }

  /**
  * Player draws a number of cards and sets the turn to the next player
  * @param person Player drawing
  * @param num number of cards being drawn
  */
  public void draw(Player person, int num){
    boolean play = false;
    //if deck is empty, "shuffle" by making the discard pile the draw pile
    if(deck.size() == 0 || num > deck.size()){
      refresh();
    }
    if(sudden){
      players.remove(person);
    }
    for(int x=0; x<num; x++){
      //chooses a random card from the deck and adds it to the player's hand
      Card toAdd = deck.get(Math.abs(randgen.nextInt(deck.size())));
      if(rules.contains("BOMB CARD") && toAdd.getValue().equals("BOMB")){
        players.remove(person);
      }
      person.addCard(toAdd);
      //removes card from deck and adds it to "discard" pile
      discard.add(toAdd);
      deck.remove(toAdd);
    }
    if(rules.contains("OVERLOAD") && person.getCards().size()>10){
      players.remove(person);
      setTurn(1);
    }
    if(num!=1){
      setTurn(1);
    }
  }

  public void play(Player person, String color){
    int i = person.getCards().indexOf(new Card("BLACK","WILD"));
    Card toPlay = person.getCards().get(i);
    person.removeCard(toPlay);
    discard.add(toPlay);
    toPlay.setColor(color);
    topCard = toPlay;
    setTurn(1);
  }

  public void play(Player person, String color, boolean add){
    combo+=4;
    int i = person.getCards().indexOf(new Card("BLACK","+4"));
    Card toPlay = person.getCards().get(i);
    person.removeCard(toPlay);
    discard.add(toPlay);
    toPlay.setColor(color);
    topCard = toPlay;
    setTurn(1);
  }
  /**
  * Player plays a card and sets the turn to the next player
  * @param person Player playing the card
  * @param toPlay Card being played
  * @param color color of card if playing a wild or +4
  */
  public void play(Player person, Card toPlay){
    try{
      if(topCard == null){
        topCard = toPlay;
      }
      //if card is playable, remove from player's hand, add to discard pile,
      //set it as top card, and set turn to next player
      if(rules.contains("SUPER COMBO") &&
        (topCard.getValue().equals("+2") || topCard.getValue().equals("+4")) &&
        (topCard.getValue().equals("+2") || topCard.getValue().equals("+4"))){
        combo+=Integer.parseInt(toPlay.getValue());
        person.removeCard(toPlay);
        discard.add(toPlay);
        topCard = toPlay;
        setTurn(1);
      }else if(person.getCards().size() == 1 && rules.contains("CLEAN FINISH") &&
          (toPlay.getValue().equals("+2") || toPlay.getValue().equals("+4") ||
          toPlay.getValue().equals("WILD") || toPlay.getValue().equals("REVERSE") ||
          toPlay.getValue().equals("SKIP"))){
          draw(person,1);
      }else if(rules.contains("NO COMBO") && combo!=0 && (topCard.getValue().equals("+2") ||
        topCard.getValue().equals("+4"))){
        draw(person,Integer.parseInt(topCard.getValue()));
        setCombo(0);
      }else if(toPlay.getValue().equals("INK")){
        String c = toPlay.getColor();
        setTurn(1);
        for(int x=0; x<turn.getCards().size(); x++){
          Card check = turn.getCards().get(x);
          if(!check.getColor().equals("BLACK")){
            check.setColor(c);
          }
        }
      }else if(toPlay.getValue().equals("EQUALITY")){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
          for(int x=0; x<players.size(); x++){
            Player check = players.get(x);
            int size = check.getCards().size();
            if(size<3){
              draw(check,3-size);
            }
            if(size>3){
              check.remove(size-3);
            }
          }
        }
      }else if(toPlay.getValue().equals("RAIN")){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
          for(int x=0; x<players.size() && x!=index; x++){
            draw(players.get(x),1);
          }
        }
      }else if(toPlay.getValue().equals("CLONE")){
        if(combo!=0){
          if(topCard.getValue().equals("WILD")){
            String colors[] = {"RED","BLUE","YELLOW","GREEN"};
            topCard.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
            person.removeCard(toPlay);
            discard.add(toPlay);
            setTurn(1);
          }else if(topCard.getValue().equals("+2") || topCard.getValue().equals("+4")){
            if(combo==0){
              combo+=Integer.parseInt(topCard.getValue());
              person.removeCard(toPlay);
              discard.add(toPlay);
              setTurn(1);
            }
          }else{
            person.removeCard(toPlay);
            discard.add(toPlay);
            setTurn(1);
          }
        }
      }else if(toPlay.getValue().equals("JUSTICE")){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          int count = 0;
          int test = turn.getCards().size();
          for(int x=0; x<players.size(); x++){
            if(players.get(x).getCards().size() < test){
              count++;
            }
          }
          turn.remove(count);
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
        }
      }else if(toPlay.getValue().equals("MAGNET")){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          String c = toPlay.getColor();
          for(int x=turn.getCards().size()-1; x>=0; x--){
            if(turn.getCards().get(x).getColor().equals(c)){
              turn.getCards().remove(x);
            }
          }
          setTurn(1);
        }
      }else if(toPlay.getValue().equals("THUNDER")){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          for(int x=0; x<2; x++){
            Player chosen = players.get(Math.abs(randgen.nextInt(players.size())));
            int num = Math.abs(randgen.nextInt(5))+1;
            draw(chosen,num);
          }
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
        }
      }else if(topCard.playable(toPlay)){
        effectCheck(person,toPlay);
      }
    }catch(NumberFormatException e){
      System.out.println("Invalid card!");
    }
  }

  /**
  * Checks the effects of action cards (skip, reverse, +2) based on the rules of UNO!
  * @param person Player playing the card
  * @param toPlay Card being played
  * @param color color of card if playing a wild or +4
  */
  public void effectCheck(Player person, Card toPlay) {
    /*  }else if(rules.contains("STACKING") && !(toPlay.getValue().equals("+2") || toPlay.getValue().equals("+4") ||
      toPlay.getValue().equals("WILD") || toPlay.getValue().equals("SKIP") ||
      toPlay.getValue().equals("REVERSE")) && checkStack(person, toPlay) == true){
        person.removeCard(toPlay);
        discard.add(toPlay);
        topCard = toPlay;
        setTurn(-1); */
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
      if(toPlay.getValue().equals("SUDDEN DEATH")){
        sudden = true;
      }
      if(rules.contains("PERFECTION")){
        String size = person.getCards().size()+"";
        if(size.equals(toPlay.getValue())){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(-1);
        }
      }
      person.removeCard(toPlay);
      discard.add(toPlay);
      topCard = toPlay;
      setTurn(1);
    }
  }

  /**
  * Sets the combo to a certain number
  * @param num number that the combo should be set to
  */
  public void setCombo(int num){
    combo = num;
  }

  //not really working
  public boolean checkStack(Player person, Card toPlay){
    for(int x=0; x<person.getCards().size(); x++){
      if(toPlay.getValue().equals(person.getCards().get(x).getValue())){
        return true;
      }
    }
    return false;
  }

  /**
  * Prints game info (players, turn, top card, combo, rules)
  * @return String of game info
  */
  public String toString(){
    /*format:
    Players: [Player 0: 7, Player 1: 7]
    Turn: Player 1
    Top Card: BLUE3
    Combo: 2
    Rules: [Perfection] (only if not empty) */
    //String res = players+"\nTurn: Player "+players.get(index).getName()+"\nTop Card: "+topCard;
    String res = "\nCombo: "+combo;
    if(!rules.isEmpty()){
      res+="\nRules: ";
      for(int x=0; x<rules.size(); x++){
        res+=rules.get(x);
        if(x!=rules.size()-1){
          res+=", ";
        }
      }
    }
    return res;
  }

  /**
  * Prints players info (name, #cards)
  * @return String of players info
  */
  public String printPlayers(){
    //format: Player 1: 7
    //Player 2: 7
    String res = "";
    for(int x=0; x<players.size(); x++){
      res+="Player "+players.get(x).getName()+": ";
      if(rules.contains("CAMOUFLAGE")){
        res+="?";
      }else{
        res+=players.get(x).getCards().size();
      }
      if(x!=players.size()-1){
        res+="\n";
      }
    }
    return res;
  }

  /**
  * Prints rules in arraylist-like format, but separated into intervals of 10
  * @return String of rules
  */
  public String printRules1(){
    String res = "[";
    for(int i=0; i<10; i++){
      res+=rules.get(i)+", ";
    }
    return res;
  }

  /**
  * Prints rules in arraylist-like format, but separated into intervals of 10
  * @return String of rules
  */
  public String printRules2(){
    String res = "";
    for(int i=10; i<rules.size(); i++){
      res+=rules.get(i);
      if(i!=rules.size()-1){
        res+=", ";
      }
    }
    return res+"]";
  }

  ///GET METHODS///
  public Card getTopCard(){
    return topCard;
  }

  public ArrayList<Card> getDeck(){
    return deck;
  }

  public ArrayList<String> getRules(){
    return rules;
  }

  public ArrayList<Card> getDiscard(){
    return discard;
  }

  public ArrayList<Player> getPlayers(){
    return players;
  }

  public ArrayList<Rule> getRuleInfo(){
    return ruleInfo;
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

  public static boolean getSudden(){
    return sudden;
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
