//Emmanuel Ikechukwu Egede-Uzoma
package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *This class represents a deck of playing cards
 *It can create, shuffle, deal, and display cards
 */
public class Deck implements Serializable {

  private static final long serialVersionUID = 1L;

  //List to store all the cards in the deck
  private List<Card> cards;

  /**
   *Constructor: This method creates a full deck of 52 playing cards
   *It adds cards for all 4 suits and 13 values in each suit
   */
  public Deck() {
    cards = new ArrayList<>();

    //All possible suits in a standard deck
    String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

    //Loop through each suit
    for (String suit : suits) {
      //Adds number cards (2 to 10)
      for (int i = 2; i <= 10; i++) {
        cards.add(new Card(i, suit));
      }

      //Adds face cards (Jack, Queen, King) and Ace
      cards.add(new Card(11, suit)); // Jack
      cards.add(new Card(12, suit)); // Queen
      cards.add(new Card(13, suit)); // King
      cards.add(new Card(14, suit)); // Ace (treated as the highest card here)
    }
  }

  //Getter method to return the current list of cards
  public List<Card> getCards() {
    return cards;
  }

  //Setter method to replace the current list of cards
  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  /**
   *Randomly shuffles the order of cards in the deck
   *This helps to mix up the deck before playing
   */
  public void shuffle() {
    Collections.shuffle(cards, new Random());
  }

  /**
   *Deals (removes and returns) the top card from the deck
   *If the deck is empty, returns null
   */
  public Card dealCard() {
    if (!cards.isEmpty()) {
      return cards.remove(0); //Removes the card at the top (index 0)
    }
    return null; //No cards left to deal
  }

  /**
   *Checks whether there are any cards left in the deck
   *
   *@return true if there are cards remaining, false if the deck is empty
   */
  public boolean hasCards() {
    return !cards.isEmpty();
  }

  /**
   *Returns how many cards are currently in the deck.
   *
   *@return number of cards left
   */
  public int size() {
    return cards.size();
  }

  /**
   *Prints out the details of all the cards in the deck
   *Useful for debugging or checking the deck contents
   */
  public void printInfo() {
    for (Card card : cards) {
      card.printInfo(); //Assumes Card class has a method to print itself
    }
  }
}

