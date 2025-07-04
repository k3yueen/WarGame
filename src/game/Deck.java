//Emmanuel Ikechukwu Egede-Uzoma

package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure
import java.io.Serializable; //IMPORT STATEMENT - brings in the Serializable interface for saving objects
import java.util.ArrayList; //IMPORT STATEMENT - brings in ArrayList class for storing lists of objects
import java.util.Collections; //IMPORT STATEMENT - brings in Collections class for utility methods like shuffle
import java.util.List; //IMPORT STATEMENT - brings in List interface for working with lists
import java.util.Random; //IMPORT STATEMENT - brings in Random class for generating random numbers

/**
 *This class represents a deck of playing cards
 *It can create, shuffle, deal, and display cards
 */
//CLASS DECLARATION - creates a new class called "Deck" that can be saved to files (Serializable)
public class Deck implements Serializable {
  //CONSTANT VARIABLE - a special number required for saving objects to files
  private static final long serialVersionUID = 1L;
  //INSTANCE VARIABLE (ATTRIBUTE) - stores a list of Card objects representing the deck
  private List<Card> cards;
  
  /**
   *Constructor: This method creates a full deck of 52 playing cards
   *It adds cards for all 4 suits and 13 values in each suit
   */
  //CONSTRUCTOR METHOD - this creates a new Deck object with all 52 cards
  public Deck() {
    cards = new ArrayList<>(); //OBJECT CREATION - creates a new empty list to store cards
    //ARRAY DECLARATION - creates an array of strings containing all four suits
    String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
    //ENHANCED FOR LOOP - goes through each suit in the suits array
    for (String suit : suits) {
      //REGULAR FOR LOOP - creates numbered cards from 2 to 10 for each suit
      for (int i = 2; i <= 10; i++) {
        cards.add(new Card(i, suit)); //METHOD CALL - adds a new card to the deck
      }
      //METHOD CALLS - adds face cards (Jack, Queen, King) and Ace to the deck
      cards.add(new Card(11, suit)); //OBJECT CREATION - creates Jack card (value 11)
      cards.add(new Card(12, suit)); //OBJECT CREATION - creates Queen card (value 12)
      cards.add(new Card(13, suit)); //OBJECT CREATION - creates King card (value 13)
      cards.add(new Card(14, suit)); //OBJECT CREATION - creates Ace card (value 14, highest)
    }
  }
  
  //GETTER METHOD - returns the current list of cards in the deck
  public List<Card> getCards() {
    return cards; //RETURN STATEMENT - sends back the cards list
  }
  
  //SETTER METHOD - replaces the current list of cards with a new list
  public void setCards(List<Card> cards) {
    this.cards = cards; //ASSIGNMENT - updates this deck's cards list
  }
  
  /**
   *Randomly shuffles the order of cards in the deck
   *This helps to mix up the deck before playing
   */
  //PUBLIC METHOD - shuffles the cards in random order
  public void shuffle() {
    Collections.shuffle(cards, new Random()); //STATIC METHOD CALL - uses Collections class to randomly shuffle the cards
  }
  
  /**
   *Deals (removes and returns) the top card from the deck
   *If the deck is empty, returns null
   */
  //PUBLIC METHOD - removes and returns the top card from the deck
  public Card dealCard() {
    if (!cards.isEmpty()) { //CONDITIONAL STATEMENT - checks if there are cards left in the deck
      return cards.remove(0); //RETURN STATEMENT - removes and returns the card at position 0 (top of deck)
    }
    return null; //RETURN STATEMENT - returns null if no cards left to deal
  }
  
  /**
   *Checks whether there are any cards left in the deck
   *
   *@return true if there are cards remaining, false if the deck is empty
   */
  //PUBLIC METHOD - checks if the deck has any cards remaining
  public boolean hasCards() {
    return !cards.isEmpty(); //RETURN STATEMENT - returns true if cards exist, false if deck is empty
  }
  
  /**
   *Returns how many cards are currently in the deck.
   *
   *@return number of cards left
   */
  //PUBLIC METHOD - returns the number of cards currently in the deck
  public int size() {
    return cards.size(); //RETURN STATEMENT - calls the size() method on the cards list
  }
  
  /**
   *Prints out the details of all the cards in the deck
   *Useful for debugging or checking the deck contents
   */
  //PUBLIC METHOD - prints information about all cards in the deck
  public void printInfo() {
    for (Card card : cards) { //ENHANCED FOR LOOP - goes through each card in the deck
      card.printInfo(); //METHOD CALL - calls each card's printInfo method to display it
    }
  }
}
