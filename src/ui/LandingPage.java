//Roza Antonevici
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class LandingPage extends javax.swing.JFrame {
  private static final long serialVersionUID = 1L; //serialization ID
  private BufferedImage backgroundImage; //background image
  private BufferedImage newGameBtnImage; //new game button image
  private BufferedImage loadGameBtnImage; //load game button image
  private GameControlCenter gameController; //controller reference

  //button dimensions and positions
  private static final int NEW_GAME_BTN_X = 593; //x-position of new game button
  private static final int NEW_GAME_BTN_Y = 350; //y-position of new game button
  private static final int LOAD_GAME_BTN_X = 593; //x-position of load game button
  private static final int LOAD_GAME_BTN_Y = 450; //y-position of load game button
  private static final int BUTTON_WIDTH = 200; //width of buttons
  private static final int BUTTON_HEIGHT = 80; //height of buttons

  //constructor
  public LandingPage() {
    super(); //call parent constructor
    System.out.println("LandingPage constructor started");
    loadImages(); //load UI images
    System.out.println("Images loaded, setting up frame");
    setupFrame(); //set frame properties
    System.out.println("Frame setup complete, creating custom panel");
    createCustomPanel(); //add UI panel
    System.out.println("LandingPage constructor completed");
  }

  //load UI images
  private void loadImages() {
    try {
      System.out.println("[DEBUG] Attempting to load images from classpath root");

      InputStream backgroundStream = getClass().getResourceAsStream("/LandingPage.png");
      InputStream newGameStream = getClass().getResourceAsStream("/NewGameBtn.png");
      InputStream loadGameStream = getClass().getResourceAsStream("/LoadGameBtn.png");

      //check if any image stream is null
      if (backgroundStream == null) {
        System.err.println("[ERROR] LandingPage.png not found in classpath root!");
      }
      if (newGameStream == null) {
        System.err.println("[ERROR] NewGameBtn.png not found in classpath root!");
      }
      if (loadGameStream == null) {
        System.err.println("[ERROR] LoadGameBtn.png not found in classpath root!");
      }

      //throw exception if any image is missing
      if (backgroundStream == null || newGameStream == null || loadGameStream == null) {
        throw new IOException("One or more image resources not found. Check build path and classpath.");
      }

      backgroundImage = ImageIO.read(backgroundStream); //read background image
      newGameBtnImage = ImageIO.read(newGameStream); //read new game button image
      loadGameBtnImage = ImageIO.read(loadGameStream); //read load game button image

      System.out.println("All images loaded successfully from classpath root!");
    } catch (IOException e) {
      System.err.println("Error loading images from resources: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //setup frame properties
  private void setupFrame() {
    setTitle("War Card Game - Landing Page"); //set window title
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app on exit
    setResizable(false); //disable resizing

    if (backgroundImage != null) {
      setSize(backgroundImage.getWidth(), backgroundImage.getHeight()); //set size from background image
    } else {
      setSize(800, 600); //default size
    }

    setLocationRelativeTo(null); //center window on screen
  }

  //create and set custom panel
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
          g2d.setColor(Color.BLACK);
          g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        //draw New Game button
        if (newGameBtnImage != null) {
          g2d.drawImage(newGameBtnImage, NEW_GAME_BTN_X, NEW_GAME_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }

        //draw Load Game button
        if (loadGameBtnImage != null) {
          g2d.drawImage(loadGameBtnImage, LOAD_GAME_BTN_X, LOAD_GAME_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }
      }
    };

    customPanel.setLayout(null); //absolute layout

    //add mouse listener for button clicks
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //check if New Game button was clicked
        if (x >= NEW_GAME_BTN_X && x <= NEW_GAME_BTN_X + BUTTON_WIDTH &&
            y >= NEW_GAME_BTN_Y && y <= NEW_GAME_BTN_Y + BUTTON_HEIGHT) {
          startNewGame();
        }

        //check if Load Game button was clicked
        if (x >= LOAD_GAME_BTN_X && x <= LOAD_GAME_BTN_X + BUTTON_WIDTH &&
            y >= LOAD_GAME_BTN_Y && y <= LOAD_GAME_BTN_Y + BUTTON_HEIGHT) {
          loadGame();
        }
      }
    });

    setContentPane(customPanel); //set custom panel as content pane
  }

  //start a new game
  private void startNewGame() {
    PlayerNameDialog nameDialog = new PlayerNameDialog(this); //prompt player 1
    nameDialog.setVisible(true);

    if (nameDialog.isConfirmed()) {
      String playerName = nameDialog.getPlayerName(); //get player 1 name
      System.out.println("Player name entered: " + playerName);

      GameSetupDialog modeDialog = new GameSetupDialog(this, playerName); //prompt for game mode
      modeDialog.setVisible(true);

      if (modeDialog.isConfirmed()) {
        String gameMode = modeDialog.getGameMode(); //get game mode

        String player2Name; //name for player 2
        if (gameMode.equals("Computer")) {
          player2Name = "Computer";
        } else {
          PlayerNameDialog player2Dialog = new PlayerNameDialog(this, "/InputTab2.png"); //prompt player 2
          player2Dialog.setVisible(true);
          if (player2Dialog.isConfirmed()) {
            player2Name = player2Dialog.getPlayerName();
          } else {
            setVisible(true); //show landing page again if canceled
            return;
          }
        }

        setVisible(false); //hide landing page

        gameController = new GameControlCenter(playerName, player2Name); //initialize controller
        CustomGameWindow customGameWindow = new CustomGameWindow(gameController); //start game window
        gameController.setGameFrame(customGameWindow);
        customGameWindow.setVisible(true); //show game window

        System.out.println("Starting new game with player: " + playerName);
        System.out.println("Game mode: " + gameMode);
      }
    }
  }

  //load paused game from file
  private void loadGame() {
    try {
      ui.GameControlCenter controller = new ui.GameControlCenter(); //new controller
      controller.loadGameFromDefaultFile(); //load saved game
      CustomGameWindow customGameWindow = new CustomGameWindow(controller);
      controller.setGameFrame(customGameWindow);
      setVisible(false); //hide landing page
      customGameWindow.setVisible(true); //show resumed game
      System.out.println("Resumed paused game from paused_game.dat");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "No paused game found or failed to load.", "Load Game",
          JOptionPane.ERROR_MESSAGE);
      System.err.println("Failed to load paused game: " + e.getMessage());
    }
  }

  //main method
  public static void main(String[] args) {
    System.out.println("Main method started");
    SwingUtilities.invokeLater(() -> {
      System.out.println("Creating LandingPage instance");
      LandingPage landingPage = new LandingPage();
      System.out.println("Setting window visible");
      landingPage.setVisible(true);
      System.out.println("Window should now be visible");
      System.out.println("Window size: " + landingPage.getSize());
      System.out.println("Window location: " + landingPage.getLocation());
      System.out.println("Window is visible: " + landingPage.isVisible());
      System.out.println("Application started successfully. Close the window to exit.");
    });
    System.out.println("Main method completed");
  }
}
