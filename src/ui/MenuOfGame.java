//Roza Antonevici
package ui;

import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

//This class creates the menu bar with game options like new, save, open, exit, and about
public class MenuOfGame extends JMenuBar {

  //Constructor that sets up the menu bar and links actions to the game controller
  // @param controller The game controller handling game logic.
  public MenuOfGame(GameControlCenter controller) {
    JMenu menu = new JMenu("File");//Create "File" menu

    JMenuItem newGame = new JMenuItem("New Game");//Menu item for starting a new game
    newGame.addActionListener(e -> controller.newGame());//Start new game on click

    JMenuItem saveGame = new JMenuItem("Save Game");//Menu item for saving current game
    saveGame.addActionListener(e -> controller.saveGame());//Save game on click

    JMenuItem openGame = new JMenuItem("Open Game");//Menu item to open a saved game
    openGame.addActionListener(e -> controller.openGame());//Open saved game on click

    JMenuItem exit = new JMenuItem("Exit");//Menu item to exit application
    exit.addActionListener(e -> System.exit(0));//Exit program on click

    JMenuItem about = new JMenuItem("About");//Menu item to show about dialog
    about.addActionListener(e -> showAboutDialog());//Show about dialog on click

    menu.add(newGame);//Add new game item to menu
    menu.add(saveGame);//Add save game item to menu
    menu.add(openGame);//Add open game item to menu
    menu.add(exit);//Add exit item to menu
    menu.add(about);//Add about item to menu

    add(menu);//Add the "File" menu to the menu bar
  }

  //Show a dialog with developer info and an image if available
  private void showAboutDialog() {
    System.out.println("About button clicked!");//Log click event
    JDialog aboutDialog = new JDialog();//Create dialog window
    aboutDialog.setTitle("About");//Set dialog title

    try {
      String imagePath = "warGame/images/About.png";//Relative path to about image
      BufferedImage aboutImage = ImageIO.read(new File(imagePath));//Load image
      JLabel imageLabel = new JLabel(new ImageIcon(aboutImage));//Wrap image in label
      aboutDialog.add(imageLabel);//Add image label to dialog
      aboutDialog.setSize(aboutImage.getWidth(), aboutImage.getHeight());//Set dialog size to image size
    } catch (IOException e) {
      //If image load fails, show text instead
      aboutDialog.setLayout(new GridLayout(2, 1));//Layout with two rows
      JLabel titleLabel = new JLabel("Could not load About.png", SwingConstants.CENTER);//Error message label
      titleLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));//Set font style
      aboutDialog.add(titleLabel);//Add error message label
      JLabel nameLabel = new JLabel("Developer: Sooou", SwingConstants.CENTER);//Developer name label
      aboutDialog.add(nameLabel);//Add developer label
      aboutDialog.setSize(400, 300);//Set dialog size for text fallback
      System.err.println("Could not load About.png: " + e.getMessage());//Log error to console
    }

    aboutDialog.setLocationRelativeTo(null);//Center dialog on screen
    aboutDialog.setResizable(false);//Prevent resizing
    aboutDialog.setVisible(true);//Show dialog
  }

  //Print menu bar info to the console.
  public void printInfo() {
    System.out.println("Menu Bar Info:");//Header message
    for (int i = 0; i < getMenuCount(); i++) {//Loop through all menus
      System.out.println("Menu: " + getMenu(i).getText());//Print each menu title
    }
  }
}

