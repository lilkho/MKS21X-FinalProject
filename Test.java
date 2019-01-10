public class Test{
  public static void main(String[] args) {
    Game game = new Game(2,0);
    System.out.println("true/false ?="+game.getOrder());
    System.out.println("Player ?="+game.getTurn());
    System.out.println("a lot of players ?="+game.getPlayers());
    System.out.println("some card ?="+game.getTopCard());
    System.out.println("a lot of cards ?="+game.getDeck());
    System.out.println("more cards ?="+game.getDiscard());
    System.out.println("within # of players ?="+game.getIndex());
    System.out.println("empty ?="+game.getRules());
    System.out.println("15 ?="+game.getDiscard().size());
    System.out.println("93 ?="+game.getDeck().size());
    game.draw(game.getPlayers().get(0),93);
    System.out.println("0 ?="+game.getDeck().size());
    game.draw(game.getPlayers().get(0),1);
    System.out.println("0 ?="+game.getDeck().size());
  }
}
