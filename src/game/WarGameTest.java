//Ademola Emmanuel Adegbola
package game;

public class WarGameTest {
  public static void main(String[] args) {
    System.out.println("Testing WAR logic...");

    //Test 1: Different ranks should NOT show WAR
    testDifferentRanks();

    //Test 2: Same ranks should show WAR
    testSameRanks();

    System.out.println("WAR logic tests completed!");
  }

  private static void testDifferentRanks() {
    System.out.println("\n=== Test 1: Different Ranks ===");
    WarCardsGame game = new WarCardsGame();
    game.dealCards();

    //Manually set cards with different ranks
    Card card1 = new Card(Card.ACE, Card.HEARTS); //Ace of Hearts (rank 14)
    Card card2 = new Card(Card.FOUR, Card.DIAMONDS); //4 of Diamonds (rank 4)

    game.getPlayer1().getHand().clear();
    game.getPlayer2().getHand().clear();
    game.getPlayer1().addCard(card1);
    game.getPlayer2().addCard(card2);

    System.out.println("Player 1 card: " + card1 + " (rank: " + card1.getRank() + ")");
    System.out.println("Player 2 card: " + card2 + " (rank: " + card2.getRank() + ")");

    game.playRound();
    String result = game.getRoundResult();
    System.out.println("Round result: " + result);

    if (result.equals("war")) {
      System.out.println("ERROR: WAR shown for different ranks!");
    } else {
      System.out.println("PASS: No WAR shown for different ranks (correct)");
    }
  }

  private static void testSameRanks() {
    System.out.println("\n=== Test 2: Same Ranks ===");
    WarCardsGame game = new WarCardsGame();
    game.dealCards();

    //Manually set cards with same ranks
    Card card1 = new Card(Card.ACE, Card.HEARTS); //Ace of Hearts (rank 14)
    Card card2 = new Card(Card.ACE, Card.CLUBS); //Ace of Clubs (rank 14)

    game.getPlayer1().getHand().clear();
    game.getPlayer2().getHand().clear();
    game.getPlayer1().addCard(card1);
    game.getPlayer2().addCard(card2);

    System.out.println("Player 1 card: " + card1 + " (rank: " + card1.getRank() + ")");
    System.out.println("Player 2 card: " + card2 + " (rank: " + card2.getRank() + ")");

    game.playRound();
    String result = game.getRoundResult();
    System.out.println("Round result: " + result);

    if (result.equals("war")) {
      System.out.println("PASS: WAR shown for same ranks (correct)");
    } else {
      System.out.println("ERROR: No WAR shown for same ranks!");
    }
  }
}
