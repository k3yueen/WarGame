package ui;

//import javax.swing.JFrame; //removed to avoid conflict
import javax.swing.JPanel;
import ui.MenuOfGame;
import ui.PanelThatDoesEverything;

//this class creates the main game window
public class JFrame extends javax.swing.JFrame {
  private static final long serialVersionUID = 1L; //serialization id
  private GameControlCenter controller; //reference to game controller
  private PanelThatDoesEverything gamePanel; //main game panel

  //constructor to initialize the game frame with a game panel and menu bar
  public JFrame(GameControlCenter controller) {
    this.controller = controller; //store controller reference
    setTitle("Card Game"); //set window title
    setExtendedState(JFrame.MAXIMIZED_BOTH); //set window to full screen
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit app on close

    gamePanel = new PanelThatDoesEverything(controller); //initialize game panel
    setContentPane(gamePanel); //set main content pane

    setJMenuBar(new MenuOfGame(controller)); //set menu bar
    setVisible(true); //make frame visible
  }

  //getter for game panel
  public PanelThatDoesEverything getGamePanel() {
    return gamePanel; //return game panel
  }

  //setter for game panel
  public void setGamePanel(PanelThatDoesEverything gamePanel) {
    this.gamePanel = gamePanel; //set new game panel
  }

  //prints information about the game frame
  public void printInfo() {
    System.out.println("Game Frame Info:"); //print heading
    System.out.println("Title: " + getTitle()); //print window title
    System.out.println("Size: " + getSize()); //print window size
  }
}

