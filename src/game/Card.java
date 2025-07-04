//Yuhan Zhang
package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure

//CLASS DECLARATION - creates a new class called "Card" that can be saved to files (Serializable)
public class Card implements java.io.Serializable {
    //CONSTANT VARIABLE - a special number required for saving objects to files
    private static final long serialVersionUID = 1L;

    //INSTANCE VARIABLES (ATTRIBUTES) - these store data for each individual card object
    private int rank;      //VARIABLE - stores the card's number value (2-14)
    private String suit;   //VARIABLE - stores the card's suit name (Hearts, Diamonds, etc.)

    //CONSTANT VARIABLES - these never change and represent card rank values
    public static final int TWO = 2;     //CONSTANT - represents the number 2 card
    public static final int THREE = 3;   //CONSTANT - represents the number 3 card
    public static final int FOUR = 4;    //CONSTANT - represents the number 4 card
    public static final int FIVE = 5;    //CONSTANT - represents the number 5 card
    public static final int SIX = 6;     //CONSTANT - represents the number 6 card
    public static final int SEVEN = 7;   //CONSTANT - represents the number 7 card
    public static final int EIGHT = 8;   //CONSTANT - represents the number 8 card
    public static final int NINE = 9;    //CONSTANT - represents the number 9 card
    public static final int TEN = 10;    //CONSTANT - represents the number 10 card
    public static final int JACK = 11;   //CONSTANT - represents the Jack card (value 11)
    public static final int QUEEN = 12;  //CONSTANT - represents the Queen card (value 12)
    public static final int KING = 13;   //CONSTANT - represents the King card (value 13)
    public static final int ACE = 14;    //CONSTANT - represents the Ace card (value 14)

    //CONSTANT VARIABLES - these never change and represent the four card suits
    public static final String HEARTS = "Hearts";     //CONSTANT STRING - represents Hearts suit
    public static final String DIAMONDS = "Diamonds"; //CONSTANT STRING - represents Diamonds suit
    public static final String SPADES = "Spades";     //CONSTANT STRING - represents Spades suit
    public static final String CLUBS = "Clubs";       //CONSTANT STRING - represents Clubs suit

    //CONSTRUCTOR METHOD - this creates a new Card object with given rank and suit
    public Card(int rank, String suit) {
        this.rank = rank;   //ASSIGNMENT - sets this card's rank to the provided value
        this.suit = suit;   //ASSIGNMENT - sets this card's suit to the provided value
    }

    //GETTER METHODS - these allow other code to read the card's information

    //GETTER METHOD - returns the card's rank (number value)
    public int getRank() {
        return rank;        //RETURN STATEMENT - sends back the rank value
    }

    //GETTER METHOD - returns the card's suit (Hearts, Diamonds, etc.)
    public String getSuit() {
        return suit;        //RETURN STATEMENT - sends back the suit string
    }

    //SETTER METHODS - these allow other code to change the card's information

    //SETTER METHOD - changes the card's rank to a new value
    public void setRank(int rank) {
        this.rank = rank;   //ASSIGNMENT - updates this card's rank
    }

    //SETTER METHOD - changes the card's suit to a new value
    public void setSuit(String suit) {
        this.suit = suit;   //ASSIGNMENT - updates this card's suit
    }

    //PRIVATE HELPER METHOD - converts the number rank into a readable word/string
    private String getRankString() {
        switch (rank) {                    //SWITCH STATEMENT - checks the rank value
            case TWO:                      //CASE - if rank equals 2
                return "2";                //RETURN STATEMENT - send back the string "2"
            case THREE:                    //CASE - if rank equals 3
                return "3";                //RETURN STATEMENT - send back the string "3"
            case FOUR:                     //CASE - if rank equals 4
                return "4";                //RETURN STATEMENT - send back the string "4"
            case FIVE:                     //CASE - if rank equals 5
                return "5";                //RETURN STATEMENT - send back the string "5"
            case SIX:                      //CASE - if rank equals 6
                return "6";                //RETURN STATEMENT - send back the string "6"
            case SEVEN:                    //CASE - if rank equals 7
                return "7";                //RETURN STATEMENT - send back the string "7"
            case EIGHT:                    //CASE - if rank equals 8
                return "8";                //RETURN STATEMENT - send back the string "8"
            case NINE:                     //CASE - if rank equals 9
                return "9";                //RETURN STATEMENT - send back the string "9"
            case TEN:                      //CASE - if rank equals 10
                return "10";               //RETURN STATEMENT - send back the string "10"
            case JACK:                     //CASE - if rank equals 11 (Jack)
                return "Jack";             //RETURN STATEMENT - send back the string "Jack"
            case QUEEN:                    //CASE - if rank equals 12 (Queen)
                return "Queen";            //RETURN STATEMENT - send back the string "Queen"
            case KING:                     //CASE - if rank equals 13 (King)
                return "King";             //RETURN STATEMENT - send back the string "King"
            case ACE:                      //CASE - if rank equals 14 (Ace)
                return "Ace";              //RETURN STATEMENT - send back the string "Ace"
            default:                       //DEFAULT CASE - if rank is none of the above
                return String.valueOf(rank); //RETURN STATEMENT - convert number to string
        }
    }

    //OVERRIDE METHOD - replaces Java's default toString method with our custom version
    @Override
    public String toString() {
        //RETURN STATEMENT - combines rank string + " of " + suit (example: "Ace of Hearts")
        return getRankString() + " of " + suit;
    }

    //PUBLIC METHOD - prints the card information to the console/screen
    public void printInfo() {
        //METHOD CALL - calls System.out.println to display the card's toString result
        System.out.println(this.toString());
    }
}
