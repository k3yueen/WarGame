//Yuhan Zhang
package game;

public class Card implements java.io.Serializable {
  private int rank;
  private String suit;

  //Constants defining card rank and suit
  public static final int TWO = 2;
  public static final int THREE = 3;
  public static final int FOUR = 4;
  public static final int FIVE = 5;
  public static final int SIX = 6;
  public static final int SEVEN = 7;
  public static final int EIGHT = 8;
  public static final int NINE = 9;
  public static final int TEN = 10;
  public static final int JACK = 11;
  public static final int QUEEN = 12;
  public static final int KING = 13;
  public static final int ACE = 14;

  public static final String HEARTS = "Hearts";
  public static final String DIAMONDS = "Diamonds";
  public static final String SPADES = "Spades";
  public static final String CLUBS = "Clubs";

  //Constructor
  public Card(int rank, String suit) {
    this.rank = rank;
    this.suit = suit;
  }

  //Getters
  public int getRank() {
    return rank;
  }

  public String getSuit() {
    return suit;
  }

  //Setters
  public void setRank(int rank) {
    this.rank = rank;
  }

  public void setSuit(String suit) {
    this.suit = suit;
  }

  //Converts the rank to a string representation
  private String getRankString() {
    switch (rank) {
      case TWO:
        return "2";
      case THREE:
        return "3";
      case FOUR:
        return "4";
      case FIVE:
        return "5";
      case SIX:
        return "6";
      case SEVEN:
        return "7";
      case EIGHT:
        return "8";
      case NINE:
        return "9";
      case TEN:
        return "10";
      case JACK:
        return "Jack";
      case QUEEN:
        return "Queen";
      case KING:
        return "King";
      case ACE:
        return "Ace";
      default:
        return String.valueOf(rank);
    }
  }

  //Returns card information in a readable format
  @Override
  public String toString() {
    return getRankString() + " of " + suit;
  }

  //Displays card information
  public void printInfo() {
    System.out.println(this.toString());
  }
}
