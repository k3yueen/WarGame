package ui;
import javax.swing.*;

public class GameFrame extends JFrame {
   private GamePanel gamePanel;
   
   //Creates the main game window
   public GameFrame(GameController controller) {
       super("Card Game"); //Sets window title
       setExtendedState(JFrame.MAXIMIZED_BOTH); //Extends the background image to fullscreen
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit when closed
       
       add(gamePanel = new GamePanel(controller)); //Adds game area
       setJMenuBar(new MenuBar(controller)); //Adds menu bar
       setVisible(true); //Shows window
   }
   
   //Gets the game panel
   public GamePanel getGamePanel() { return gamePanel; }
   //Sets the game panel
   public void setGamePanel(GamePanel gamePanel) { this.gamePanel = gamePanel; }
   
   //Prints window info for debugging
   public void printInfo() {
       System.out.println("Game Frame Info:\nTitle: " + getTitle() + "\nSize: " + getSize());
   }
}
