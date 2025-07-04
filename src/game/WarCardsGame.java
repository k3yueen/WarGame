//Roza Antonevici
package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure

import java.io.Serializable; //IMPORT STATEMENT - brings in Serializable interface for saving objects
import java.util.ArrayList; //IMPORT STATEMENT - brings in ArrayList class for storing lists of objects
import java.util.List; //IMPORT STATEMENT - brings in List interface for working with collections

/**
 * Manages the overall game logic, including dealing cards, playing rounds,
 * handling wars, and checking game-over conditions.
 */
//CLASS DECLARATION - creates the main game class that controls the entire War card game
public class WarCardsGame implements Serializable {
  /**
   * 
   */
  //CONSTANT VARIABLE - a special number required for saving objects to files
  private static final long serialVersionUID = 1L;
  //INSTANCE VARIABLES (ATTRIBUTES) - these store the game's current state
  private Deck deck; //VARIABLE - stores the deck of cards used in the game
  private Player player1; //VARIABLE - stores the first player object
  private Player player2; //VARIABLE - stores the second player object (computer)
  private Card player1Card; //VARIABLE - stores the current card played by player 1
  private Card player2Card; //VARIABLE - stores the current card played by player 2
  private boolean gameOver; //VARIABLE - tracks whether the game has ended
  private String resultMessage; //VARIABLE - stores the final game result message
  private String roundResult = ""; //VARIABLE - stores the result of the current round
  private int roundsPlayed = 0; //VARIABLE - counts how many rounds have been played
  private static final int MAX_ROUNDS = 100; //CONSTANT - maximum number of rounds before game ends
  private boolean pendingWar = false; //VARIABLE - tracks if a war is waiting to be resolved
  private List<Card> pendingWarCards = new ArrayList<>(); //VARIABLE - stores cards involved in pending war

  /**
   * Constructor to initialize the game with a deck and two players.
   */
  //CONSTRUCTOR METHOD - creates a new War card game with default setup
  public WarCardsGame() {
    deck = new Deck(); //OBJECT CREATION - creates a new deck of 52 cards
    player1 = new Player("Player 1"); //OBJECT CREATION - creates first player with name "Player 1"
    player2 = new Player("Computer"); //OBJECT CREATION - creates second player with name "Computer"
    gameOver = false; //ASSIGNMENT - sets game as not finished
    resultMessage = ""; //ASSIGNMENT - initializes result message as empty
  }

  //GETTER METHODS - these allow other code to read the game's information
  
  //GETTER METHOD - returns the game's deck
  public Deck getDeck() {
    return deck; //RETURN STATEMENT - sends back the deck object
  }

  //SETTER METHOD - changes the game's deck to a new one
  public void setDeck(Deck deck) {
    this.deck = deck; //ASSIGNMENT - updates this game's deck
  }

  //GETTER METHOD - returns player 1 object
  public Player getPlayer1() {
    return player1; //RETURN STATEMENT - sends back player1 object
  }

  //SETTER METHOD - changes player 1 to a new player
  public void setPlayer1(Player player1) {
    this.player1 = player1; //ASSIGNMENT - updates this game's player1
  }

  //GETTER METHOD - returns player 2 object
  public Player getPlayer2() {
    return player2; //RETURN STATEMENT - sends back player2 object
  }

  //SETTER METHOD - changes player 2 to a new player
  public void setPlayer2(Player player2) {
    this.player2 = player2; //ASSIGNMENT - updates this game's player2
  }

  //GETTER METHOD - returns player 1's current card
  public Card getPlayer1Card() {
    return player1Card; //RETURN STATEMENT - sends back player1's card
  }

  //SETTER METHOD - sets player 1's current card
  public void setPlayer1Card(Card player1Card) {
    this.player1Card = player1Card; //ASSIGNMENT - updates player1's current card
  }

  //GETTER METHOD - returns player 2's current card
  public Card getPlayer2Card() {
    return player2Card; //RETURN STATEMENT - sends back player2's card
  }

  //SETTER METHOD - sets player 2's current card
  public void setPlayer2Card(Card player2Card) {
    this.player2Card = player2Card; //ASSIGNMENT - updates player2's current card
  }

  //GETTER METHOD - checks if the game has ended
  public boolean isGameOver() {
    return gameOver; //RETURN STATEMENT - sends back true if game is finished, false if still playing
  }

  //SETTER METHOD - sets whether the game is over
  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver; //ASSIGNMENT - updates the game's finished status
  }

  //GETTER METHOD - returns the final result message
  public String getResultMessage() {
    return resultMessage; //RETURN STATEMENT - sends back the result message string
  }

  //GETTER METHOD - returns the current round's result
  public String getRoundResult() {
    return roundResult; //RETURN STATEMENT - sends back the round result string
  }

  //SETTER METHOD - sets the final result message
  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage; //ASSIGNMENT - updates the result message
  }

  /**
   * Shuffles and deals the cards to the players.
   */
  //PUBLIC METHOD - shuffles deck and distributes cards equally between players
  public void dealCards() {
    deck.shuffle(); //METHOD CALL - randomly mixes up the order of cards in the deck
    System.out.println("Deck shuffled. Dealing cards..."); //METHOD CALL - prints status message
    while (deck.hasCards()) { //WHILE LOOP - continues until all cards are dealt
      Card c1 = deck.dealCard(); //METHOD CALL - removes top card from deck for player 1
      player1.addCard(c1); //METHOD CALL - adds card to player 1's hand
      if (deck.hasCards()) { //CONDITIONAL STATEMENT - checks if deck still has cards
        Card c2 = deck.dealCard(); //METHOD CALL - removes next card from deck for player 2
        player2.addCard(c2); //METHOD CALL - adds card to player 2's hand
      }
    }
    System.out.println("Cards dealt. Player 1: " + player1.getCardCount() + ", Player 2: " + player2.getCardCount()); //METHOD CALL - prints card counts
    // Debug: Check for duplicate cards between players
    java.util.Set<String> player1Cards = new java.util.HashSet<>(); //OBJECT CREATION - creates set to track player 1's cards
    java.util.Set<String> player2Cards = new java.util.HashSet<>(); //OBJECT CREATION - creates set to track player 2's cards
    for (Card c : player1.getHand()) //ENHANCED FOR LOOP - goes through each card in player 1's hand
      player1Cards.add(c.toString()); //METHOD CALL - adds card string representation to set
    for (Card c : player2.getHand()) //ENHANCED FOR LOOP - goes through each card in player 2's hand
      player2Cards.add(c.toString()); //METHOD CALL - adds card string representation to set
    for (String card : player1Cards) { //ENHANCED FOR LOOP - checks each card in player 1's set
      if (player2Cards.contains(card)) { //CONDITIONAL STATEMENT - checks if player 2 has same card
        System.out.println("[DEBUG] Duplicate card found in both hands: " + card); //METHOD CALL - prints error message
      }
    }
  }

  /**
   * Plays a single round of the game.
   */
  //PUBLIC METHOD - executes one round of the War card game
  public void playRound() {
    if (gameOver) //CONDITIONAL STATEMENT - exits if game is already finished
      return;
    roundsPlayed++; //INCREMENT OPERATION - increases round counter by 1
    // Reset round result at the beginning of each round
    roundResult = ""; //ASSIGNMENT - clears previous round result

    if (gameOver || !player1.hasCards() || !player2.hasCards()) { //CONDITIONAL STATEMENT - checks for game end conditions
      roundResult = ""; //ASSIGNMENT - clears round result
      return; //RETURN STATEMENT - exits method early
    }

    if (pendingWar) { //CONDITIONAL STATEMENT - checks if there's a war waiting to be resolved
      // Resolve the pending war
      handleWar(pendingWarCards); //METHOD CALL - resolves the waiting war
      pendingWar = false; //ASSIGNMENT - marks war as resolved
      pendingWarCards = new ArrayList<>(); //OBJECT CREATION - creates new empty list for war cards
      checkGameOver(); //METHOD CALL - checks if game ended after war
      return; //RETURN STATEMENT - exits method
    }

    player1Card = player1.playCard(); //METHOD CALL - player 1 plays their top card
    player2Card = player2.playCard(); //METHOD CALL - player 2 plays their top card

    if (player1Card != null && player2Card != null) { //CONDITIONAL STATEMENT - checks if both players had cards to play
      System.out.println("Player 1 card: " + player1Card + " (rank=" + player1Card.getRank() + ", suit="
          + player1Card.getSuit() + ")"); //METHOD CALL - prints player 1's card details
      System.out.println("Player 2 card: " + player2Card + " (rank=" + player2Card.getRank() + ", suit="
          + player2Card.getSuit() + ")"); //METHOD CALL - prints player 2's card details
      System.out.println("Comparing: " + player1Card + " (rank=" + player1Card.getRank() + ") vs " + player2Card
          + " (rank=" + player2Card.getRank() + ")"); //METHOD CALL - prints comparison details
      List<Card> warCards = new ArrayList<>(); //OBJECT CREATION - creates list to store cards involved in this round
      warCards.add(player1Card); //METHOD CALL - adds player 1's card to the pot
      warCards.add(player2Card); //METHOD CALL - adds player 2's card to the pot

      if (player1Card.getRank() > player2Card.getRank()) { //CONDITIONAL STATEMENT - checks if player 1's card is higher
        System.out.println("Result: Player 1 wins round"); //METHOD CALL - prints round result
        player1.incrementScoreWithCards(warCards); //METHOD CALL - gives player 1 the points and cards
        roundResult = "p1_wins"; //ASSIGNMENT - sets round result for UI
      } else if (player1Card.getRank() < player2Card.getRank()) { //CONDITIONAL STATEMENT - checks if player 2's card is higher
        System.out.println("Result: Player 2 wins round"); //METHOD CALL - prints round result
        player2.incrementScoreWithCards(warCards); //METHOD CALL - gives player 2 the points and cards
        roundResult = "p2_wins"; //ASSIGNMENT - sets round result for UI
      } else { //ELSE CLAUSE - handles when both cards have same rank (tie)
        System.out.println("Result: WAR!"); //METHOD CALL - prints war announcement
        roundResult = "war"; //ASSIGNMENT - sets round result to indicate war
        pendingWar = true; //ASSIGNMENT - marks that a war needs to be resolved
        pendingWarCards = warCards; //ASSIGNMENT - stores cards for the war
        // Do NOT resolve the war yet; wait for next playRound()
        return; //RETURN STATEMENT - exits to let UI handle war display
      }
      System.out.println("After comparison, roundResult = " + roundResult); //METHOD CALL - prints debug info
    }

    checkGameOver(); //METHOD CALL - checks if game ended after this round
  }

  /**
   * Handles the logic for a "war" when two cards of equal rank are played.
   * 
   * @param warCards The list of cards involved in the war.
   */
  //PRIVATE METHOD - handles the complex logic when players tie (war scenario)
  private void handleWar(List<Card> warCards) {
    while (true) { //INFINITE LOOP - continues until war is resolved or game ends
      int p1Count = player1.getCardCount(); //METHOD CALL - gets player 1's remaining card count
      int p2Count = player2.getCardCount(); //METHOD CALL - gets player 2's remaining card count
      // If either player is out of cards, the other wins
      if (p1Count == 0) { //CONDITIONAL STATEMENT - checks if player 1 has no cards left
        player2.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 2
        gameOver = true; //ASSIGNMENT - ends the game
        roundResult = "p2_wins"; //ASSIGNMENT - sets round result when war ends
        if (player2.getName().equals("Computer")) { //CONDITIONAL STATEMENT - checks if player 2 is computer
          resultMessage = "Computer wins :("; //ASSIGNMENT - sets computer win message
        } else {
          resultMessage = "Match ended, Congratulations " + player2.getName(); //STRING CONCATENATION - creates player win message
        }
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        return; //RETURN STATEMENT - exits the war resolution
      }
      if (p2Count == 0) { //CONDITIONAL STATEMENT - checks if player 2 has no cards left
        player1.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 1
        gameOver = true; //ASSIGNMENT - ends the game
        roundResult = "p1_wins"; //ASSIGNMENT - sets round result when war ends
        resultMessage = "Match ended, Congratulations " + player1.getName(); //STRING CONCATENATION - creates player win message
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        return; //RETURN STATEMENT - exits the war resolution
      }
      // Each player puts up to 2 cards face down (or as many as they have minus 1)
      int p1FaceDown = Math.max(0, Math.min(2, p1Count - 1)); //MATH OPERATION - calculates how many face-down cards player 1 can play
      int p2FaceDown = Math.max(0, Math.min(2, p2Count - 1)); //MATH OPERATION - calculates how many face-down cards player 2 can play
      for (int i = 0; i < p1FaceDown; i++) { //FOR LOOP - plays player 1's face-down cards
        warCards.add(player1.playCard()); //METHOD CALL - adds player 1's card to war pot
      }
      for (int i = 0; i < p2FaceDown; i++) { //FOR LOOP - plays player 2's face-down cards
        warCards.add(player2.playCard()); //METHOD CALL - adds player 2's card to war pot
      }
      // Each player puts their last card face up
      player1Card = player1.playCard(); //METHOD CALL - player 1 plays face-up card
      player2Card = player2.playCard(); //METHOD CALL - player 2 plays face-up card
      warCards.add(player1Card); //METHOD CALL - adds player 1's face-up card to war pot
      warCards.add(player2Card); //METHOD CALL - adds player 2's face-up card to war pot
      // If either player is out of cards after playing, the other wins
      if (player1Card == null) { //CONDITIONAL STATEMENT - checks if player 1 ran out of cards
        player2.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 2
        gameOver = true; //ASSIGNMENT - ends the game
        roundResult = "p2_wins"; //ASSIGNMENT - sets round result when war ends
        if (player2.getName().equals("Computer")) { //CONDITIONAL STATEMENT - checks if player 2 is computer
          resultMessage = "Computer wins :("; //ASSIGNMENT - sets computer win message
        } else {
          resultMessage = "Match ended, Congratulations " + player2.getName(); //STRING CONCATENATION - creates player win message
        }
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        return; //RETURN STATEMENT - exits the war resolution
      }
      if (player2Card == null) { //CONDITIONAL STATEMENT - checks if player 2 ran out of cards
        player1.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 1
        gameOver = true; //ASSIGNMENT - ends the game
        roundResult = "p1_wins"; //ASSIGNMENT - sets round result when war ends
        resultMessage = "Match ended, Congratulations " + player1.getName(); //STRING CONCATENATION - creates player win message
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        return; //RETURN STATEMENT - exits the war resolution
      }
      if (player1Card.getRank() > player2Card.getRank()) { //CONDITIONAL STATEMENT - checks if player 1 wins the war
        player1.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 1
        roundResult = "p1_wins"; //ASSIGNMENT - sets round result when war ends
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        break; //BREAK STATEMENT - exits the war loop
      } else if (player1Card.getRank() < player2Card.getRank()) { //CONDITIONAL STATEMENT - checks if player 2 wins the war
        player2.incrementScoreWithCards(warCards); //METHOD CALL - gives all war cards to player 2
        roundResult = "p2_wins"; //ASSIGNMENT - sets round result when war ends
        // Set face-up cards to last played
        if (warCards.size() >= 2) { //CONDITIONAL STATEMENT - checks if there are enough cards to display
          player1Card = warCards.get(warCards.size() - 2); //METHOD CALL - gets second-to-last card for display
          player2Card = warCards.get(warCards.size() - 1); //METHOD CALL - gets last card for display
        }
        break; //BREAK STATEMENT - exits the war loop
      }
      // If tie, repeat war with remaining cards (roundResult remains "war")
    }
    checkGameOver(); //METHOD CALL - checks if game ended after war
  }

  /**
   * Checks if the game is over and sets the result message accordingly.
   */
  //PRIVATE METHOD - determines if the game should end and sets appropriate messages
  private void checkGameOver() {
    if (roundsPlayed >= MAX_ROUNDS) { //CONDITIONAL STATEMENT - checks if maximum rounds reached
      gameOver = true; //ASSIGNMENT - ends the game
      int p1 = player1.getCardCount(); //METHOD CALL - gets player 1's final card count
      int p2 = player2.getCardCount(); //METHOD CALL - gets player 2's final card count
      if (p1 > p2) { //CONDITIONAL STATEMENT - checks if player 1 has more cards
        resultMessage = player1.getName() + " wins by card count after 100 rounds!"; //STRING CONCATENATION - creates win message
      } else if (p2 > p1) { //CONDITIONAL STATEMENT - checks if player 2 has more cards
        resultMessage = player2.getName() + " wins by card count after 100 rounds!"; //STRING CONCATENATION - creates win message
      } else {
        resultMessage = "It's a draw after 100 rounds!"; //ASSIGNMENT - sets tie message
      }
      roundResult = ""; //ASSIGNMENT - clears round result
      return; //RETURN STATEMENT - exits method
    }
    if (!player1.hasCards()) { //CONDITIONAL STATEMENT - checks if player 1 is out of cards
      gameOver = true; //ASSIGNMENT - ends the game
      if (player2.getName().equals("Computer")) { //CONDITIONAL STATEMENT - checks if player 2 is computer
        resultMessage = "Computer won the game :("; //ASSIGNMENT - sets computer win message
      } else {
        resultMessage = "Match ended, Congratulations " + player2.getName() + "!"; //STRING CONCATENATION - creates player win message
      }
      roundResult = ""; //ASSIGNMENT - clears round result
    } else if (!player2.hasCards()) { //CONDITIONAL STATEMENT - checks if player 2 is out of cards
      gameOver = true; //ASSIGNMENT - ends the game
      resultMessage = "Match ended, Congratulations " + player1.getName() + "!"; //STRING CONCATENATION - creates player win message
      roundResult = ""; //ASSIGNMENT - clears round result
    }
  }

  /**
   * Resets the game with a new deck and players.
   */
  //PUBLIC METHOD - restarts the game with fresh deck and players
  public void reset() {
    deck = new Deck(); //OBJECT CREATION - creates new deck of 52 cards
    player1 = new Player("Player 1"); //OBJECT CREATION - creates new player 1
    player2 = new Player("Computer"); //OBJECT CREATION - creates new computer player
    player1Card = null; //ASSIGNMENT - clears player 1's current card
    player2Card = null; //ASSIGNMENT - clears player 2's current card
    gameOver = false; //ASSIGNMENT - sets game as active
    resultMessage = ""; //ASSIGNMENT - clears result message
    roundResult = ""; //ASSIGNMENT - clears round result
    dealCards(); //METHOD CALL - shuffles and deals cards to start new game
  }

  /**
   * Prints information about the game.
   */
  //PUBLIC METHOD - displays current game state information
  public void printInfo() {
    System.out.println("Game Over: " + gameOver); //METHOD CALL - prints whether game is finished
    System.out.println("Result: " + resultMessage); //METHOD CALL - prints final result message
    player1.printInfo(); //METHOD CALL - prints player 1's information
    player2.printInfo(); //METHOD CALL - prints player 2's information
  }
}
