import java.util.ArrayList;
import java.util.Random;

public class Game{
  private boolean order;
  private Player turn;
  private ArrayList<Player> players;
  private Card topCard;
  private ArrayList<Rule> allRules = new ArrayList<Rule>(); //all rules possible
  private ArrayList<Card> deck = new ArrayList<Card>();
  private ArrayList<Card> discard = new ArrayList<Card>();
  private ArrayList<String> rules = new ArrayList<String>(); //rule names only
  private ArrayList<Rule> ruleInfo = new ArrayList<Rule>(); //rule name+description
  private Random randgen = new Random();
  private int index; //index of current player
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
      //selects random rule from all rules possible and makes sure contradicting rules won't appear in the same game
      for(int y=0; y<numRules; y++){
        Rule toAdd = allRules.get(Math.abs(randgen.nextInt(allRules.size())));
        //if "mess" is added, all other rules are cleared
        if(toAdd.getName().equals("MESS")){
          y=numRules;
          rules.clear();
          ruleInfo.clear();
          ruleInfo.add(toAdd);
          String name = toAdd.getName();
          rules.add(name);
          allRules.remove(toAdd);
        //if trying to add "no action" to a game with already action rules, it cannot be added
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
        //cannot add action rule if "no action" is already in game
        }else if(rules.contains("NO ACTION") && toAdd.getType(toAdd).equals("ACTION")){
          allRules.remove(toAdd);
        //cannot add super combo if "no combo" is in game
        }else if(rules.contains("NO COMBO") && toAdd.getName().equals("SUPER COMBO")){
          allRules.remove(toAdd);
        //cannot add no combo or no action if "super combo" is in game
        }else if(rules.contains("SUPER COMBO") && (toAdd.getName().equals("NO COMBO") || toAdd.getName().equals("NO ACTION"))){
          allRules.remove(toAdd);
        //otherwise everything can be added
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
    play(new Player(""),test,test.getColor());
    //selects a player to "start" game
    turn = players.get(Math.abs(randgen.nextInt(numPlayers)));
    //prevents first player from playing again
    setTurn(1);
  }

  /**
  * Sets the deck for the game based on the rules in the game
  */
  public void setDeck(){
    //sets up deck based on what rules are present in game
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
    }
    if(rules.contains("SUPER COMBO") || rules.contains("MESS")){
      for(int x=0; x<4; x++){
        deck.add(new Card(colors[x],"+0"));
      }
    }
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
  }

  /**
  * Sets the turn given how many people are skipped
  * @param num the number of people to skip
  */
  public void setTurn(int num){
    try{
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
    }catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Game over!");
    }
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
    //cannot draw if sudden death is activated
    if(sudden){
      players.remove(person);
    }
    for(int x=0; x<num; x++){
      //chooses a random card from the deck and adds it to the player's hand
      Card toAdd = deck.get(Math.abs(randgen.nextInt(deck.size())));
      //if player draws bomb, player is eliminated
      if(rules.contains("BOMB CARD") && toAdd.getValue().equals("BOMB")){
        players.remove(person);
      }
      person.addCard(toAdd);
      //removes card from deck and adds it to "discard" pile
      discard.add(toAdd);
      deck.remove(toAdd);
    }
    //overload eliminates players with over 10 cards
    if(rules.contains("OVERLOAD") && person.getCards().size()>10){
      players.remove(person);
      setTurn(1);
    }
    //if there is a combo, you must draw & no turn
    if(num!=1){
      setTurn(1);
    }
  }

  /**
  * Player plays a card and sets the turn to the next player
  * @param person Player playing the card
  * @param toPlay Card being played
  * @param color color of card if playing a wild or +4
  */
  public void play(Player person, Card toPlay, String color){
    try{
      //this is for the "first player" that is later removed
      if(topCard == null){
        topCard = toPlay;
      }
      //plays cards according to rules
      //super combo: if there is a combo, any + card can be placed
      if(rules.contains("SUPER COMBO") && combo!=0 &&
        (toPlay.getValue().equals("+4") ||
        toPlay.getValue().equals("+2") ||
        toPlay.getValue().equals("+0"))){
        combo+=Integer.parseInt(toPlay.getValue());
        person.removeCard(toPlay);
        discard.add(toPlay);
        topCard = toPlay;
        setTurn(1);
      //if the player's last card is action && rule is clean finish, player is forced to draw
      }else if(person.getCards().size() == 1 && rules.contains("CLEAN FINISH") &&
          (toPlay.getType(toPlay).equals("ACTION"))){
          draw(person,1);
      //if rule is no combo and there is a "combo," player must draw combo # of cards
      }else if(rules.contains("NO COMBO") && combo!=0 &&
        (topCard.getValue().equals("+2") ||
        topCard.getValue().equals("+4")) ||
        topCard.getValue().equals("+0")){
        draw(person,combo);
        setCombo(0);
      //if ink card is playable, next player's cards all turn the color of ink card
      }else if(toPlay.getValue().equals("INK")){
        if(combo == 0 && (topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue()))){
          String c = toPlay.getColor();
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
          for(int x=0; x<turn.getCards().size(); x++){
            Card check = turn.getCards().get(x);
            if(!check.getColor().equals("BLACK")){
              check.setColor(c);
            }
          }
        }
      //if equality is playable, everyone draws/discards until 3 cards
      }else if(toPlay.getValue().equals("EQUALITY")){
        if(combo == 0 && (topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue()))){
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
      //if rain is playable, everyone else draws 1 card
      }else if(toPlay.getValue().equals("RAIN")){
        if(combo == 0 && (topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue()))){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
          for(int x=0; x<players.size() && x!=index; x++){
            draw(players.get(x),1);
          }
        }
      //if there is no combo, clone can be played
      //wild & +4 choose a color, other cards are copied
      }else if(toPlay.getValue().equals("CLONE")){
        if(combo==0){
          if(topCard.getValue().equals("WILD")){
            String colors[] = {"RED","BLUE","YELLOW","GREEN"};
            topCard.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
            person.removeCard(toPlay);
            discard.add(toPlay);
            setTurn(1);
          }else if(topCard.getValue().equals("+2") ||
            topCard.getValue().equals("+4") ||
            topCard.getValue().equals("+0")){
            combo+=Integer.parseInt(topCard.getValue());
            if(topCard.getValue().equals("+4")){
              String colors[] = {"RED","BLUE","YELLOW","GREEN"};
              topCard.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
            }
            person.removeCard(toPlay);
            discard.add(toPlay);
            setTurn(1);
          }else{
            person.removeCard(toPlay);
            discard.add(toPlay);
            setTurn(1);
          }
        }
      //if justice is playable, count # of players with fewer cards and discard that number of cards
      }else if(toPlay.getValue().equals("JUSTICE")){
        if(combo == 0 && (topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue()))){
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
      //if magnet is playable, discard every card of that color
      }else if(toPlay.getValue().equals("MAGNET")){
        if(combo == 0 && (topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue()))){
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
      //if thunder is playable, select 2 random players and draw 1-5 cards
      }else if(toPlay.getValue().equals("THUNDER") && combo==0){
        if(topCard.getColor().equals(toPlay.getColor()) ||
          topCard.getValue().equals(toPlay.getValue())){
          //same player cannot be chosen twice
          ArrayList<Player> test = new ArrayList<Player>();
          for(int i=0; i<players.size(); i++){
            test.add(players.get(i));
          }
          for(int x=0; x<2; x++){
            Player chosen = test.get(Math.abs(randgen.nextInt(players.size())));
            test.remove(chosen);
            int num = Math.abs(randgen.nextInt(5))+1;
            draw(chosen,num);
          }
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(1);
        }
      //otherwise play by normal UNO rules
      }else if(topCard.playable(toPlay)){
        effectCheck(person,toPlay,color);
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
  public void effectCheck(Player person, Card toPlay, String color) {
    //if there's a combo, card to play and card on top must match value
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
      //if card is a skip, turn = 2 indices after
      //player size = 2, reverse works like a skip
      if(toPlay.getValue().equals("SKIP") || toPlay.getValue().equals("REVERSE")){
        setTurn(1);
      }
      //if +2, add 2 to combo
      if(toPlay.getValue().equals("+2")){
        combo+=2;
      }
      //if playing sudden death, sudden death is activated and game info turns magenta
      if(toPlay.getValue().equals("SUDDEN DEATH")){
        sudden = true;
      }
      //if perfection is in rule, check card value and # of cards
      //if match, player can play again
      if(rules.contains("PERFECTION")){
        String size = person.getCards().size()+"";
        if(size.equals(toPlay.getValue())){
          person.removeCard(toPlay);
          discard.add(toPlay);
          topCard = toPlay;
          setTurn(-1);
        }
      }
      if(toPlay.getColor().equals("BLACK")){
        //add 4 to combo if +4
        if(toPlay.getValue().equals("+4")){
          combo+=4;
        }
        //wild & +4 choose a color
        String colors[] = {"RED","BLUE","YELLOW","GREEN"};
        toPlay.setColor(colors[Math.abs(randgen.nextInt(colors.length))]);
      }
      //remove card from player's deck, add to discard, and set it as top card
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
  * Prints rules in arraylist-like format, but separated into sets of 10
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
  * Prints rules in arraylist-like format, but separated into sets of 10
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

  public ArrayList<String> getRules(){
    return rules;
  }

  public ArrayList<Player> getPlayers(){
    return players;
  }

  public ArrayList<Rule> getRuleInfo(){
    return ruleInfo;
  }

  public boolean getOrder(){
    return order;
  }

  public Player getTurn(){
    return turn;
  }

  public int getCombo(){
    return combo;
  }

  public static boolean getSudden(){
    return sudden;
  }

  public static void main(String[] args) {
  }
}
