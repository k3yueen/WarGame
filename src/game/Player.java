import java.util.LinkedList;
import java.util.Queue;
import java.io.Serializabale;

public class Player {
    private String name;
    private int score;
    private Queue<Card> hand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new LinkedList<>();
    }

    //  Add the new card to the bottom of the deck
    public void addCard(Card card) {
        hand.add(card);
    }

    // Draw a card from the top of the deck to play
    public Card playCard() {
        return hand.poll();
    }

    // Check if the player still has cards to play
    public boolean hasCards() {
        return !hand.isEmpty();
    }

    // Increment the player's score
    public void incrementScore() {
        score++;
    }

    // Add multiple winning cards to the bottom of the deck
    public void addCardsToBottom(Queue<Card> wonCards) {
        hand.addAll(wonCards);
    }

    // Other helper methods
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getCardCount() {
        return hand.size();
    }
}

