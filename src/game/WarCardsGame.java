//Roza Antonevici
package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Manages the overall game logic, including dealing cards, playing rounds,
 *handling wars, and checking game-over conditions.
 */
public class WarCardsGame implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Deck deck;
  private Player player1;
  private Player player2;
  private Card player1Card;
  private Card player2Card;
  private boolean gameOver;
  private String resultMessage;
  private String roundResult = "";
  private int roundsPlayed = 0;
  private static final int MAX_ROUNDS = 100;
  private boolean pendingWar = false;
  private List<Card> pendingWarCards = new ArrayList<>();

  /**
   *Constructor to initialize the game with a deck and two players.
   */
  public WarCardsGame() {
    deck = new Deck();
    player1 = new Player("Player 1");
    player2 = new Player("Computer");
    gameOver = false;
    resultMessage = "";
  }

  public Deck getDeck() {
    return deck;
  }

  public void setDeck(Deck deck) {
    this.deck = deck;
  }

  public Player getPlayer1() {
    return player1;
  }

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public Card getPlayer1Card() {
    return player1Card;
  }

  public void setPlayer1Card(Card player1Card) {
    this.player1Card = player1Card;
  }

  public Card getPlayer2Card() {
    return player2Card;
  }

  public void setPlayer2Card(Card player2Card) {
    this.player2Card = player2Card;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
  }

  public String getResultMessage() {
    return resultMessage;
  }

  public String getRoundResult() {
    return roundResult;
  }

  public void setResultMessage(String resultMessage) {
    this.resultMessage = resultMessage;
  }

  /**
   *Shuffles and deals the cards to the players.
   */
  public void dealCards() {
    deck.shuffle();
    System.out.println("Deck shuffled. Dealing cards...");
    int count = 0;
    while (deck.hasCards()) {
      Card c1 = deck.dealCard();
      player1.addCard(c1);
      count++;
      if (deck.hasCards()) {
        Card c2 = deck.dealCard();
        player2.addCard(c2);
        count++;
      }
    }
    System.out.println("Cards dealt. Player 1: " + player1.getCardCount() + ", Player 2: " + player2.getCardCount());
    //Debug: Check for duplicate cards between players
    java.util.Set<String> player1Cards = new java.util.HashSet<>();
    java.util.Set<String> player2Cards = new java.util.HashSet<>();
    for (Card c : player1.getHand())
      player1Cards.add(c.toString());
    for (Card c : player2.getHand())
      player2Cards.add(c.toString());
    for (String card : player1Cards) {
      if (player2Cards.contains(card)) {
        System.out.println("[DEBUG] Duplicate card found in both hands: " + card);
      }
    }
  }

  /**
   *Plays a single round of the game.
   */
  public void playRound() {
    if (gameOver)
      return;
    roundsPlayed++;
    //Resets round result at the beginning of each round
    roundResult = "";

    if (gameOver || !player1.hasCards() || !player2.hasCards()) {
      roundResult = "";
      return;
    }

    if (pendingWar) {
      //Resolves the pending war
      handleWar(pendingWarCards);
      pendingWar = false;
      pendingWarCards = new ArrayList<>();
      checkGameOver();
      return;
    }

    player1Card = player1.playCard();
    player2Card = player2.playCard();

    if (player1Card != null && player2Card != null) {
      System.out.println("Player 1 card: " + player1Card + " (rank=" + player1Card.getRank() + ", suit="
          + player1Card.getSuit() + ")");
      System.out.println("Player 2 card: " + player2Card + " (rank=" + player2Card.getRank() + ", suit="
          + player2Card.getSuit() + ")");
      System.out.println("Comparing: " + player1Card + " (rank=" + player1Card.getRank() + ") vs " + player2Card
          + " (rank=" + player2Card.getRank() + ")");
      List<Card> warCards = new ArrayList<>();
      warCards.add(player1Card);
      warCards.add(player2Card);

      if (player1Card.getRank() > player2Card.getRank()) {
        System.out.println("Result: Player 1 wins round");
        player1.incrementScoreWithCards(warCards);
        roundResult = "p1_wins";
      } else if (player1Card.getRank() < player2Card.getRank()) {
        System.out.println("Result: Player 2 wins round");
        player2.incrementScoreWithCards(warCards);
        roundResult = "p2_wins";
      } else {
        System.out.println("Result: WAR!");
        roundResult = "war";
        pendingWar = true;
        pendingWarCards = warCards;
        //Do NOT resolve the war yet; wait for next playRound()
        return;
      }
      System.out.println("After comparison, roundResult = " + roundResult);
    }

    checkGameOver();
  }

  /**
   *Handles the logic for a "war" when two cards of equal rank are played
   * 
   *@param warCards The list of cards involved in the war
   */
  private void handleWar(List<Card> warCards) {
    while (true) {
      int p1Count = player1.getCardCount();
      int p2Count = player2.getCardCount();
      //If either player is out of cards, the other wins
      if (p1Count == 0) {
        player2.incrementScoreWithCards(warCards);
        gameOver = true;
        roundResult = "p2_wins"; // Set round result when war ends
        if (player2.getName().equals("Computer")) {
          resultMessage = "Computer wins :(";
        } else {
          resultMessage = "Match ended, Congratulations " + player2.getName();
        }
        //Sets face up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        return;
      }
      if (p2Count == 0) {
        player1.incrementScoreWithCards(warCards);
        gameOver = true;
        roundResult = "p1_wins"; // Set round result when war ends
        resultMessage = "Match ended, Congratulations " + player1.getName();
        //Set face up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        return;
      }
      //Each player puts up to 2 cards face down (or as many as they have minus 1)
      int p1FaceDown = Math.max(0, Math.min(2, p1Count - 1));
      int p2FaceDown = Math.max(0, Math.min(2, p2Count - 1));
      for (int i = 0; i < p1FaceDown; i++) {
        warCards.add(player1.playCard());
      }
      for (int i = 0; i < p2FaceDown; i++) {
        warCards.add(player2.playCard());
      }
      //Each player puts their last card face up
      player1Card = player1.playCard();
      player2Card = player2.playCard();
      warCards.add(player1Card);
      warCards.add(player2Card);
      //If either player is out of cards after playing, the other wins
      if (player1Card == null) {
        player2.incrementScoreWithCards(warCards);
        gameOver = true;
        roundResult = "p2_wins"; // Set round result when war ends
        if (player2.getName().equals("Computer")) {
          resultMessage = "Computer wins :(";
        } else {
          resultMessage = "Match ended, Congratulations " + player2.getName();
        }
        //Sets face up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        return;
      }
      if (player2Card == null) {
        player1.incrementScoreWithCards(warCards);
        gameOver = true;
        roundResult = "p1_wins"; // Set round result when war ends
        resultMessage = "Match ended, Congratulations " + player1.getName();
        //Sets face-up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        return;
      }
      if (player1Card.getRank() > player2Card.getRank()) {
        player1.incrementScoreWithCards(warCards);
        roundResult = "p1_wins"; //Sets round result when war ends
        //Sets face up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        break;
      } else if (player1Card.getRank() < player2Card.getRank()) {
        player2.incrementScoreWithCards(warCards);
        roundResult = "p2_wins"; //Sets round result when war ends
        //Sets face up cards to last played
        if (warCards.size() >= 2) {
          player1Card = warCards.get(warCards.size() - 2);
          player2Card = warCards.get(warCards.size() - 1);
        }
        break;
      }
      //If tie, repeat war with remaining cards (roundResult remains "war")
    }
    checkGameOver();
  }

  /**
   *Checks if the game is over and sets the result message accordingly
   */
  private void checkGameOver() {
    if (roundsPlayed >= MAX_ROUNDS) {
      gameOver = true;
      int p1 = player1.getCardCount();
      int p2 = player2.getCardCount();
      if (p1 > p2) {
        resultMessage = player1.getName() + " wins by card count after 100 rounds!";
      } else if (p2 > p1) {
        resultMessage = player2.getName() + " wins by card count after 100 rounds!";
      } else {
        resultMessage = "It's a draw after 100 rounds!";
      }
      roundResult = "";
      return;
    }
    if (!player1.hasCards()) {
      gameOver = true;
      if (player2.getName().equals("Computer")) {
        resultMessage = "Computer won the game :(";
      } else {
        resultMessage = "Match ended, Congratulations " + player2.getName() + "!";
      }
      roundResult = "";
    } else if (!player2.hasCards()) {
      gameOver = true;
      resultMessage = "Match ended, Congratulations " + player1.getName() + "!";
      roundResult = "";
    }
  }

  /**
   *Resets the game with a new deck and players
   */
  public void reset() {
    deck = new Deck();
    player1 = new Player("Player 1");
    player2 = new Player("Computer");
    player1Card = null;
    player2Card = null;
    gameOver = false;
    resultMessage = "";
    roundResult = "";
    dealCards();
  }

  /**
   *Prints information about the game.
   */
  public void printInfo() {
    System.out.println("Game Over: " + gameOver);
    System.out.println("Result: " + resultMessage);
    player1.printInfo();
    player2.printInfo();
  }
}
