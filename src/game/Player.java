//Tianshuo Xie

package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure
import java.io.Serializable; //IMPORT STATEMENT - brings in Serializable interface for saving objects
import java.util.LinkedList; //IMPORT STATEMENT - brings in LinkedList class for efficient queue operations
import java.util.List; //IMPORT STATEMENT - brings in List interface for working with collections
import java.util.Queue; //IMPORT STATEMENT - brings in Queue interface for first-in-first-out operations

/**
 * Represents a player in the game, managing their hand of cards and score.
 */
//CLASS DECLARATION - creates a new class called "Player" that can be saved to files (Serializable)
public class Player implements Serializable {
  /**
   * 
   */
  //CONSTANT VARIABLE - a special number required for saving objects to files
  private static final long serialVersionUID = 1L;
  //INSTANCE VARIABLES (ATTRIBUTES) - these store data for each individual player object
  private Queue<Card> hand; //VARIABLE - stores the player's cards in a queue (first-in-first-out order)
  private int score; //VARIABLE - stores the player's current score as an integer
  private String name; //VARIABLE - stores the player's name as a string
  
  /**
   * Constructor to initialize the player with a name.
   * 
   * @param name The name of the player.
   */
  //CONSTRUCTOR METHOD - creates a new Player object with given name
  public Player(String name) {
    this.name = name; //ASSIGNMENT - sets this player's name to the provided value
    this.score = 0; //ASSIGNMENT - starts the player's score at zero
    hand = new LinkedList<>(); //OBJECT CREATION - creates a new empty LinkedList to store cards
  }
  
  //GETTER METHOD - returns the player's hand of cards
  public Queue<Card> getHand() {
    return hand; //RETURN STATEMENT - sends back the hand queue
  }
  
  //SETTER METHOD - replaces the player's hand with a new set of cards
  public void setHand(Queue<Card> hand) {
    this.hand = hand; //ASSIGNMENT - updates this player's hand
  }
  
  //GETTER METHOD - returns the player's current score
  public int getScore() {
    return score; //RETURN STATEMENT - sends back the score value
  }
  
  //SETTER METHOD - changes the player's score to a new value
  public void setScore(int score) {
    this.score = score; //ASSIGNMENT - updates this player's score
  }
  
  //GETTER METHOD - returns the player's name
  public String getName() {
    return name; //RETURN STATEMENT - sends back the name string
  }
  
  //SETTER METHOD - changes the player's name to a new value
  public void setName(String name) {
    this.name = name; //ASSIGNMENT - updates this player's name
  }
  
  /**
   * Adds a card to the player's hand.
   * 
   * @param card The card to add.
   */
  //PUBLIC METHOD - adds a single card to the player's hand
  public void addCard(Card card) {
    if (card != null) { //CONDITIONAL STATEMENT - checks if the card is not null (valid)
      hand.add(card); //METHOD CALL - adds the card to the end of the hand queue
    }
  }
  
  /**
   * Plays a card from the player's hand.
   * 
   * @return The played card.
   */
  //PUBLIC METHOD - removes and returns the first card from the player's hand
  public Card playCard() {
    return hand.poll(); //RETURN STATEMENT - removes and returns the first card in the queue
  }
  
  /**
   * Increments the player's score by a specified value.
   * 
   * @param value The value to add to the score.
   */
  //PUBLIC METHOD - adds points to the player's score
  public void incrementScore(int value) {
    score += value; //ARITHMETIC OPERATION - adds the value to the current score
  }
  
  /**
   * Checks if the player has cards remaining.
   * 
   * @return true if the player has cards, false otherwise.
   */
  //PUBLIC METHOD - checks if the player has any cards left in their hand
  public boolean hasCards() {
    return !hand.isEmpty(); //RETURN STATEMENT - returns true if hand is not empty, false if empty
  }
  
  /**
   * Returns the number of cards in the player's hand.
   * 
   * @return The number of cards.
   */
  //PUBLIC METHOD - returns how many cards the player currently has
  public int getCardCount() {
    return hand.size(); //RETURN STATEMENT - calls the size() method on the hand queue
  }
  
  /**
   * Adds a list of cards to the bottom of the player's hand.
   * 
   * @param cards The list of cards to add.
   */
  //PUBLIC METHOD - adds multiple cards to the player's hand at once
  public void addCardsToBottom(List<Card> cards) {
    hand.addAll(cards); //METHOD CALL - adds all cards from the list to the hand queue
  }
  
  /**
   * Increments the player's score based on the ranks of a list of cards.
   * 
   * @param cards The list of cards.
   */
  //PUBLIC METHOD - adds points based on card values and adds cards to hand
  public void incrementScoreWithCards(List<Card> cards) {
    for (Card card : cards) { //ENHANCED FOR LOOP - goes through each card in the list
      incrementScore(card.getRank()); //METHOD CALL - adds the card's rank value to the score
    }
    addCardsToBottom(cards); //METHOD CALL - adds all the cards to the player's hand
  }
  
  /**
   * Prints information about the player.
   */
  //PUBLIC METHOD - displays all information about the player
  public void printInfo() {
    System.out.println("Player: " + name); //METHOD CALL - prints the player's name
    System.out.println("Score: " + score); //METHOD CALL - prints the player's score
    System.out.println("Cards: "); //METHOD CALL - prints a header for the cards list
    for (Card card : hand) { //ENHANCED FOR LOOP - goes through each card in the hand
      card.printInfo(); //METHOD CALL - prints each card's information
    }
  }
}
