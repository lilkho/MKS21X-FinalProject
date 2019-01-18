public class Test{
  public static void main(String[] args) {
  /*  Game game = new Game(2,0);
    System.out.println("true/false ?= "+game.getOrder());
    System.out.println("Player ?= "+game.getTurn());
    System.out.println("a lot of players ?= "+game.getPlayers());
    System.out.println("some card ?= "+game.getTopCard());
    System.out.println("a lot of cards ?= "+game.getDeck());
    System.out.println("more cards ?= "+game.getDiscard());
    System.out.println("within # of players ?= "+game.getIndex());
    System.out.println("empty ?= "+game.getRules());
    System.out.println("15 ?= "+game.getDiscard().size());
    System.out.println("93 ?= "+game.getDeck().size());
    //testing when you draw all of deck
    game.draw(game.getPlayers().get(0),93);
    System.out.println("0 ?= "+game.getDeck().size());
    //when you draw when deck is empty
    game.draw(game.getPlayers().get(0),1);
    System.out.println("107 ?= "+game.getDeck().size());
    //when you draw more than deck size
    game.draw(game.getPlayers().get(0),108);
    System.out.println("0 ?= "+game.getDeck().size());
    System.out.println("7 ?= "+game.getPlayers().get(1).getCards().size());
    game.getPlayers().add(new Player("2",1));
    game.draw(game.getPlayers().get(2),1);
    //say UNO! ?
    System.out.println("2: UNO! ?= "+game.getPlayers().get(2));
    //added card?
    game.getPlayers().get(2).addCard(new Card("BLACK","WILD"));
    System.out.println("BLACKWILD&randomcard ?= "+game.getPlayers().get(2).getCards());
    //can play wild?
    game.play(game.getPlayers().get(2),new Card("BLACK","WILD"),"RED");
    //did color change?
    System.out.println("REDWILD ?= "+game.getTopCard());
    //does topCard get updated?
    game.play(game.getPlayers().get(2),new Card("BLUE","2"),"");
    System.out.println("BLUE2 ?= "+game.getTopCard());
    //invalid color?
    System.out.println("INVALID COLOR ?= ");
    game.play(game.getPlayers().get(2),new Card("BLACK","WILD"),"haha");
    game.play(game.getPlayers().get(2),new Card("BLACK","WILD"),"RED");
    System.out.println("\n\n\n\n\nTHIS IS THE DECK: "+game.getPlayers().get(2).getCards());
    System.out.println("THIS IS THE TOP CARD: "+game.getTopCard());
    System.out.println("TRYING TO PLAY FIRST CARD");
    game.play(game.getPlayers().get(2),game.getPlayers().get(2).getCards().get(0),"");
    System.out.println("NOW THIS IS THE TOP CARD: "+game.getTopCard());
    System.out.println('a'+0);
    System.out.println('b'+0);
    System.out.println((int)'a'+"\n\n\n\n\n");
*/
    Game test = new Game(2,1);
    System.out.println(test.getDeck());
    System.out.println(test.getDiscard());
  //  System.out.println(test.getAllRules());
  //  System.out.println(test.getPlayers().get(0).getCards());
    System.out.println(test.getRules().contains("BOMB CARD"));
  //  System.out.println(test.printPlayers());
  }
}
