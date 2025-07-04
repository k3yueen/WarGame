//Ademola Emmanuel Adegbola

package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure

//CLASS DECLARATION - creates a test class to verify the War game logic works correctly
public class WarGameTest {
  //MAIN METHOD - the entry point where the program starts execution
  public static void main(String[] args) {
    //METHOD CALL - prints test introduction message to console
    System.out.println("Testing WAR logic...");

    //Test 1: Different ranks should NOT show WAR
    //STATIC METHOD CALL - runs test to verify different card ranks don't trigger war
    testDifferentRanks();

    //Test 2: Same ranks should show WAR
    //STATIC METHOD CALL - runs test to verify same card ranks do trigger war
    testSameRanks();

    //METHOD CALL - prints test completion message to console
    System.out.println("WAR logic tests completed!");
  }

  //PRIVATE STATIC METHOD - tests that cards with different ranks don't cause a war
  private static void testDifferentRanks() {
    //METHOD CALL - prints test section header
    System.out.println("\n=== Test 1: Different Ranks ===");
    //OBJECT CREATION - creates a new War card game for testing
    WarCardsGame game = new WarCardsGame();
    //METHOD CALL - shuffles and deals cards to both players
    game.dealCards();

    //Manually set cards with different ranks
    //OBJECT CREATION - creates Ace of Hearts card (highest rank value 14)
    Card card1 = new Card(Card.ACE, Card.HEARTS);
    //OBJECT CREATION - creates 4 of Diamonds card (low rank value 4)
    Card card2 = new Card(Card.FOUR, Card.DIAMONDS);

    //METHOD CALLS - clear both players' hands to start with empty hands
    game.getPlayer1().getHand().clear();
    game.getPlayer2().getHand().clear();
    //METHOD CALLS - add the specific test cards to each player's hand
    game.getPlayer1().addCard(card1);
    game.getPlayer2().addCard(card2);

    //METHOD CALLS - print details about the cards being tested
    System.out.println("Player 1 card: " + card1 + " (rank: " + card1.getRank() + ")");
    System.out.println("Player 2 card: " + card2 + " (rank: " + card2.getRank() + ")");

    //METHOD CALL - execute one round of the game with these specific cards
    game.playRound();
    //METHOD CALL - get the result of the round (should be "p1_wins", not "war")
    String result = game.getRoundResult();
    //METHOD CALL - print the actual result for verification
    System.out.println("Round result: " + result);

    //CONDITIONAL STATEMENT - checks if the result incorrectly shows war
    if (result.equals("war")) {
      //METHOD CALL - prints error message if war occurred with different ranks
      System.out.println("ERROR: WAR shown for different ranks!");
    } else {
      //METHOD CALL - prints success message if no war occurred (correct behavior)
      System.out.println("PASS: No WAR shown for different ranks (correct)");
    }
  }

  //PRIVATE STATIC METHOD - tests that cards with same ranks do cause a war
  private static void testSameRanks() {
    //METHOD CALL - prints test section header
    System.out.println("\n=== Test 2: Same Ranks ===");
    //OBJECT CREATION - creates a new War card game for testing
    WarCardsGame game = new WarCardsGame();
    //METHOD CALL - shuffles and deals cards to both players
    game.dealCards();

    //Manually set cards with same ranks
    //OBJECT CREATION - creates Ace of Hearts card (rank value 14)
    Card card1 = new Card(Card.ACE, Card.HEARTS);
    //OBJECT CREATION - creates Ace of Clubs card (same rank value 14)
    Card card2 = new Card(Card.ACE, Card.CLUBS);

    //METHOD CALLS - clear both players' hands to start with empty hands
    game.getPlayer1().getHand().clear();
    game.getPlayer2().getHand().clear();
    //METHOD CALLS - add the specific test cards to each player's hand
    game.getPlayer1().addCard(card1);
    game.getPlayer2().addCard(card2);

    //METHOD CALLS - print details about the cards being tested
    System.out.println("Player 1 card: " + card1 + " (rank: " + card1.getRank() + ")");
    System.out.println("Player 2 card: " + card2 + " (rank: " + card2.getRank() + ")");

    //METHOD CALL - execute one round of the game with these specific cards
    game.playRound();
    //METHOD CALL - get the result of the round (should be "war")
    String result = game.getRoundResult();
    //METHOD CALL - print the actual result for verification
    System.out.println("Round result: " + result);

    //CONDITIONAL STATEMENT - checks if the result correctly shows war
    if (result.equals("war")) {
      //METHOD CALL - prints success message if war occurred (correct behavior)
      System.out.println("PASS: WAR shown for same ranks (correct)");
    } else {
      //METHOD CALL - prints error message if no war occurred with same ranks
      System.out.println("ERROR: No WAR shown for same ranks!");
    }
  }
}
