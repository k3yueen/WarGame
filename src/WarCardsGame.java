package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *This class runs the entire War card game. It handles everything that happens during the game
 *War is a simple card game where players flip cards and whoever has the higher card wins both cards
 *If cards are equal, there's a "war" where players play additional cards until someone wins
 */
public class WarGame implements Serializable {
    
    //This number is required for saving/loading the game to files. Don't worry about it
    private static final long serialVersionUID = 1L;
    
    //These are the main components of our game
    private Deck deck;              //The deck of 52 cards we're playing with
    private Player player1;         //The human player
    private Player player2;         //The computer player
    private Card player1Card;       //The card Player 1 just played this round
    private Card player2Card;       //The card Player 2 just played this round
    private boolean gameOver;       //True when the game is finished, false when still playing
    private String resultMessage;   //The message we show when the game ends (who won)

    /**
     *This is the constructor. It runs automatically when you create a new WarGame.
     *It sets up a new game with a fresh deck and 2 players in our case the user and 
     */
    public WarGame() {
        deck = new Deck();                    //Create a new deck of 52 cards
        player1 = new Player("Player 1");    //Create the human player
        player2 = new Player("Computer");     //Create the computer player
        gameOver = false;                     //Game just started, so it's not over yet
        resultMessage = "";                   //No result message yet since game isn't over
    }

    //These are getter and setter methods. They let other classes read and change these values
    
    public Deck getDeck() {
        return deck;  //Returns the current deck
    }

    public void setDeck(Deck deck) {
        this.deck = deck;  //Sets a new deck
    }

    public Player getPlayer1() {
        return player1;  //Returns player 1
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;  //Sets a new player 1
    }

    public Player getPlayer2() {
        return player2;  //Returns player 2
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;  //Sets a new player 2
    }

    public Card getPlayer1Card() {
        return player1Card;  //Returns the card player 1 just played
    }

    public void setPlayer1Card(Card player1Card) {
        this.player1Card = player1Card;  //Sets player 1's current card
    }

    public Card getPlayer2Card() {
        return player2Card;  //Returns the card player 2 just played
    }

    public void setPlayer2Card(Card player2Card) {
        this.player2Card = player2Card;  //Sets player 2's current card
    }

    public boolean isGameOver() {
        return gameOver;  //Returns true if game is over and false if still playing
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;  //Sets whether the game is over or not
    }

    public String getResultMessage() {
        return resultMessage;  //Returns the message about who won
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;  //Sets the message about who won
    }

    /**
     *This method starts the game by shuffling the deck and giving cards to both players
     *It splits the 52 cards evenly meaning 26 cards to each player
     */
    public void dealCards() {
        deck.shuffle();  //Mixing up the cards randomly
        
        //Keeps dealing cards until the deck is empty
        while (deck.hasCards()) {
            player1.addCard(deck.dealCard());  //Gives 1 card to player 1
            
            //Checks if there are still cards left before giving to player 2
            if (deck.hasCards()) {
                player2.addCard(deck.dealCard());  //Gives one card to player 2
            }
        }
    }

    /**
     *This method plays 1 round of the game
     *Both players play one card and whoever has the higher card wins both cards
      If the cards are equal, it triggers a war
     */
    public void playRound() {
        //Dont play if the game is already over or if a player has no cards
        if (gameOver || !player1.hasCards() || !player2.hasCards()) {
            return;  //Exit this method early
        }

        //Each player plays their top card
        player1Card = player1.playCard();  //Player 1 plays a card
        player2Card = player2.playCard();  //Player 2 plays a card

        //Makes sure both players actually had cards to play
        if (player1Card != null && player2Card != null) {
            //Creates a list to hold all the cards being fought over
            List<Card> warCards = new ArrayList<>();
            warCards.add(player1Card);  //Adds player 1's card to the pile
            warCards.add(player2Card);  //Adds player 2's card to the pile

            //Compares the card ranks to see who wins
            if (player1Card.getRank() > player2Card.getRank()) {
                //Player 1 wins! Give them all the cards and points
                player1.incrementScoreWithCards(warCards);
            } else if (player1Card.getRank() < player2Card.getRank()) {
                //Player 2 wins! Give them all the cards and points
                player2.incrementScoreWithCards(warCards);
            } else {
                //The cards are equal! This triggers a war
                handleWar(warCards);
            }
        }

        //After the round checks if the game is over
        checkGameOver();
    }

    /**
     *This method handles a war situation when both players play cards of equal rank
     *In a war each player puts down 2 cards facedown, then 1 card face up
     *The face up cards are compared, and the winner takes all the cards
     *If those cards are also equal the war continues
     */
    private void handleWar(List<Card> warCards) {
        //Keep doing wars until someone wins
        while (true) {
            //Checks if players have enough cards to continue the war
            //A war requires 3 cards (2 face-down + 1 face-up)
            if (player1.getCardCount() < 4 || player2.getCardCount() < 4) {
                
                //If player 1 doesnt have enough cards, player 2 wins
                if (player1.getCardCount() < 4) {
                    player2.incrementScoreWithCards(warCards);  //Gives all war cards to player 2
                    gameOver = true;                            //Ends the game
                    resultMessage = "Computer wins :(";        //Prints the losing message
                    return;  //Exit this method
                }
                
                //If player 2 doesnt have enough cards, player 1 wins
                if (player2.getCardCount() < 4) {
                    player1.incrementScoreWithCards(warCards);  //Gives all war cards to player 1
                    gameOver = true;                            //Ends the game
                    resultMessage = "War ended. Congratulations! " + player1.getName();  //Sets the winning message
                    return;  //Exit this method
                }
            }

            //Both players have enough cards, so continue the war
            //Each player puts down 2 cards facedown (these cards dont matter for comparison)
            for (int i = 0; i < 2; i++) {
                warCards.add(player1.playCard());  //Player 1 puts down a facedown card
                warCards.add(player2.playCard());  //Player 2 puts down a facedown card
            }

            //Each player puts down 1 card face up (these cards will be compared)
            player1Card = player1.playCard();  //Player 1's face-up card
            player2Card = player2.playCard();  //Player 2's face-up card
            warCards.add(player1Card);         //Add player 1's face-up card to the war pile
            warCards.add(player2Card);         //Add player 2's face-up card to the war pile

            //Compare the face-up cards to see who wins the war
            if (player1Card.getRank() > player2Card.getRank()) {
                //Player 1 wins the war so give them all the cards from this war
                player1.incrementScoreWithCards(warCards);
                break;  //Exit the war loop
            } else if (player1Card.getRank() < player2Card.getRank()) {
                //Player 2 wins the war so give them all the cards from this war
                player2.incrementScoreWithCards(warCards);
                break;  //Exit the war loop
            }
            //If the face-up cards are also equal, the while loop continues for another war
        }

        //After the war is resolved, check if the game is over
        checkGameOver();
    }

    /**
     *This method checks if the game should end
     *The game ends when one player runs out of cards
     *It also sets the appropriate winning/losing message
     */
    private void checkGameOver() {
        //If player 1 has no cards left they lose
        if (!player1.hasCards()) {
            gameOver = true;                    //Marks the game as finished
            resultMessage = "Computer wins :("; //Sets the losing message
        } 
        //If player 2 has no cards left they lose so player 1 wins
        else if (!player2.hasCards()) {
            gameOver = true;                    //Marks the game as finished
            resultMessage = "War ended. Congratulations! " + player1.getName();  //Sets the winning message
        }
    }

    /**
     *This method resets the game back to the beginning
     *It creates a new deck, new players, and deals the cards again
     *Use this when you want to start a completely new game
     */
    public void reset() {
        deck = new Deck();                    //Creates a fresh deck of 52 cards
        player1 = new Player("Player 1");     //Creates a new player 1
        player2 = new Player("Computer");     //Creates a new player 2 (computer)
        player1Card = null;                   //Clears player 1's current card
        player2Card = null;                   //Clear player 2's current card
        gameOver = false;                     //Marks the game as not over
        resultMessage = "";                   //Clears any previous result message
        dealCards();                          //Shuffles and deal cards to start the new game
    }

    /**
     *This method prints information about the current game state to the console
     *It shows if the game is over, who won, and details about both players
     *This is mainly used for debugging or testing purposes
     */
    public void printInfo() {
        System.out.println("Game Over: " + gameOver);      //Print whether the game is finished
        System.out.println("Result: " + resultMessage);    //Print who won the game
        player1.printInfo();                               //Print player 1's information
        player2.printInfo();                               //Print player 2's information
    }
}
