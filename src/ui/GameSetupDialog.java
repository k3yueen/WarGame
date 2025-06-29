//roza antonevici
package ui;

import javax.swing.*; //swing components
import java.awt.*; //graphics and layout
import java.awt.event.*; //event handling
import java.awt.image.BufferedImage; //image buffer
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO; //image loading

//dialog for selecting game mode
public class GameSetupDialog extends JDialog {
  private static final long serialVersionUID = 1L; //serialization id
  private BufferedImage backgroundImage; //background image
  private BufferedImage doneBtnImage; //done button image
  private BufferedImage cancelBtnImage; //cancel button image
  private JComboBox<String> gameModeCombo; //dropdown for game modes
  private boolean confirmed = false; //flag if user confirmed
  private String gameMode = null; //selected game mode
  private String playerName = null; //name of the player

  //button dimensions and positions
  private static final int DONE_BTN_X = 400;
  private static final int DONE_BTN_Y = 200;
  private static final int CANCEL_BTN_X = 550;
  private static final int CANCEL_BTN_Y = 200;
  private static final int BUTTON_WIDTH = 100;
  private static final int BUTTON_HEIGHT = 40;

  //combo box position
  private static final int COMBO_X = 300;
  private static final int COMBO_Y = 150;
  private static final int COMBO_WIDTH = 200;
  private static final int COMBO_HEIGHT = 30;

  //constructor for game setup dialog
  public GameSetupDialog(javax.swing.JFrame parent, String playerName) {
    super(parent, "Select Game Mode", true); //modal dialog
    this.playerName = playerName;
    loadImages(); //load required images
    setupDialog(); //configure dialog settings
    createCustomPanel(); //set up custom panel
    setupGameModes(); //add game mode options
  }

  //load images from resources
  private void loadImages() {
    try {
      backgroundImage = ImageIO.read(getClass().getResourceAsStream("/GameModeTab.png"));
      doneBtnImage = ImageIO.read(getClass().getResourceAsStream("/DoneBTN.png"));
      cancelBtnImage = ImageIO.read(getClass().getResourceAsStream("/CancelBTN.png"));

      System.out.println("All game mode dialog images loaded successfully from resources!");
    } catch (Exception e) {
      System.err.println("Error loading game mode dialog images from resources: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //configure the dialog window
  private void setupDialog() {
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //close dialog on exit
    setResizable(false); //prevent resizing

    if (backgroundImage != null) {
      setSize(backgroundImage.getWidth(), backgroundImage.getHeight()); //set size based on image
    } else {
      setSize(800, 400); //fallback size
    }

    setLocationRelativeTo(getOwner()); //center the dialog
  }

  //create custom panel with painting and mouse interaction
  private void createCustomPanel() {
    JPanel customPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //draw background
        if (backgroundImage != null) {
          g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
          g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        //draw buttons
        if (doneBtnImage != null) {
          g2d.drawImage(doneBtnImage, DONE_BTN_X, DONE_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }

        if (cancelBtnImage != null) {
          g2d.drawImage(cancelBtnImage, CANCEL_BTN_X, CANCEL_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }
      }
    };

    customPanel.setLayout(null); //use absolute positioning

    //create combo box for game modes
    gameModeCombo = new JComboBox<>();
    gameModeCombo.setBounds(COMBO_X, COMBO_Y, COMBO_WIDTH, COMBO_HEIGHT);
    gameModeCombo.setFont(new Font("Arial", Font.BOLD, 14));
    customPanel.add(gameModeCombo); //add combo box to panel

    //add mouse listener for button clicks
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //check if done button was clicked
        if (x >= DONE_BTN_X && x <= DONE_BTN_X + BUTTON_WIDTH &&
            y >= DONE_BTN_Y && y <= DONE_BTN_Y + BUTTON_HEIGHT) {
          confirmSelection();
        }

        //check if cancel button was clicked
        if (x >= CANCEL_BTN_X && x <= CANCEL_BTN_X + BUTTON_WIDTH &&
            y >= CANCEL_BTN_Y && y <= CANCEL_BTN_Y + BUTTON_HEIGHT) {
          cancelSelection();
        }
      }
    });

    setContentPane(customPanel); //set panel as dialog content
  }

  //add available game modes to combo box
  private void setupGameModes() {
    gameModeCombo.addItem("Select game mode...");
    gameModeCombo.addItem("Computer");
    gameModeCombo.addItem("Another Player");
    gameModeCombo.setSelectedIndex(0); //set default option
  }

  //confirm the selected game mode
  private void confirmSelection() {
    String selected = (String) gameModeCombo.getSelectedItem();
    if (selected != null && !selected.equals("Select game mode...")) {
      gameMode = selected;
      confirmed = true;
      System.out.println("Game mode selected: " + gameMode);
      dispose(); //close dialog
    } else {
      JOptionPane.showMessageDialog(this,
          "Please select a game mode.",
          "No Game Mode Selected",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  //cancel selection and close dialog
  private void cancelSelection() {
    confirmed = false;
    gameMode = null;
    System.out.println("Game mode selection cancelled");
    dispose();
  }

  //return whether the user confirmed
  public boolean isConfirmed() {
    return confirmed;
  }

  //get selected game mode
  public String getGameMode() {
    return gameMode;
  }

  //get player name
  public String getPlayerName() {
    return playerName;
  }
}
