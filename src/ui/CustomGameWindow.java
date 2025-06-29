//Roza Antonevici
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//This class creates the game's custom graphical window and handles all user interaction
public class CustomGameWindow extends javax.swing.JFrame {
  //These BufferedImage variables store all the images used in the UI
  private BufferedImage backgroundImage;
  private BufferedImage gameTableImage;
  private BufferedImage menuBarImage;
  private BufferedImage backBtnImage;
  private BufferedImage playBtnImage;
  private BufferedImage pauseBtnImage;
  private BufferedImage aboutBtnImage;

  //Reference to the controller class that manages game logic
  private GameControlCenter controller;

  //Window size constants
  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 800;

  //Menu bar dimensions and position
  private static final int MENU_BAR_X = 0;
  private static final int MENU_BAR_Y = 0;
  private static final int MENU_BAR_WIDTH = 1200;
  private static final int MENU_BAR_HEIGHT = 60;

  //Game table position and size
  private static final int GAME_TABLE_X = 100;
  private static final int GAME_TABLE_Y = 80;
  private static final int GAME_TABLE_WIDTH = 1000;
  private static final int GAME_TABLE_HEIGHT = 600;

  //Position and size for navigation buttons
  private static final int BACK_BTN_X = 50;
  private static final int BACK_BTN_Y = 10;
  private static final int BACK_BTN_WIDTH = 120;
  private static final int BACK_BTN_HEIGHT = 40;

  private static final int PAUSE_BTN_X = BACK_BTN_X + BACK_BTN_WIDTH + 20;
  private static final int PAUSE_BTN_Y = 10;
  private static final int PAUSE_BTN_WIDTH = 120;
  private static final int PAUSE_BTN_HEIGHT = 40;

  private static final int ABOUT_BTN_X = PAUSE_BTN_X + PAUSE_BTN_WIDTH + 20;
  private static final int ABOUT_BTN_Y = 10;
  private static final int ABOUT_BTN_WIDTH = 120;
  private static final int ABOUT_BTN_HEIGHT = 40;

  private static final int PLAY_BTN_X = GAME_TABLE_X + 450;
  private static final int PLAY_BTN_Y = GAME_TABLE_Y + 500;
  private static final int PLAY_BTN_WIDTH = 120;
  private static final int PLAY_BTN_HEIGHT = 40;

  //UI labels for card display and scores
  private JLabel player1CardLabel;
  private JLabel player2CardLabel;
  private JLabel player1BackCardLabel;
  private JLabel player2BackCardLabel;
  private JLabel player1ScoreLabel;
  private JLabel player2ScoreLabel;
  private JLabel warHeaderLabel;
  private JLabel roundResultLabel;

  //Constructor sets up everything once the window is created
  public CustomGameWindow(GameControlCenter controller) {
    this.controller = controller;
    loadImages(); //Loads all image files
    setupWindow(); //Initial window setup
    createCustomPanel(); //Adds the game UI components
  }

  //Loads all required images from resources folder
  private void loadImages() {
    try {
      backgroundImage = ImageIO.read(getClass().getResourceAsStream("/GameBackground.png"));
      gameTableImage = ImageIO.read(getClass().getResourceAsStream("/GameTable.png"));
      menuBarImage = ImageIO.read(getClass().getResourceAsStream("/MenuBar.png"));
      backBtnImage = ImageIO.read(getClass().getResourceAsStream("/BackBTN.png"));
      playBtnImage = ImageIO.read(getClass().getResourceAsStream("/PlayRoundBTN.png"));
      pauseBtnImage = ImageIO.read(getClass().getResourceAsStream("/PauseGameBTN.png"));
      aboutBtnImage = ImageIO.read(getClass().getResourceAsStream("/AboutBTN.png"));
      System.out.println("All custom game window images loaded successfully from resources!");
    } catch (Exception e) {
      System.err.println("Error loading custom game window images: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //Sets up basic window properties like size and title
  private void setupWindow() {
    setTitle("War Card Game - Custom Design");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setLocationRelativeTo(null); //Center the window on screen
  }

  //Creates the main game panel and adds all game components to it
  private void createCustomPanel() {
    JPanel customPanel = new JPanel() {
      //Override paintComponent to draw all graphics manually
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw background image
        if (backgroundImage != null) {
          g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        //Draw top menu bar
        if (menuBarImage != null) {
          g2d.drawImage(menuBarImage, MENU_BAR_X, MENU_BAR_Y, MENU_BAR_WIDTH, MENU_BAR_HEIGHT, null);
        }

        //Draw game table
        if (gameTableImage != null) {
          g2d.drawImage(gameTableImage, GAME_TABLE_X, GAME_TABLE_Y, GAME_TABLE_WIDTH, GAME_TABLE_HEIGHT, null);
        }

        //Draw each game control button with fallback if missing
        drawButton(g2d, backBtnImage, BACK_BTN_X, BACK_BTN_Y, BACK_BTN_WIDTH, BACK_BTN_HEIGHT, "BACK", Color.RED);
        drawButton(g2d, pauseBtnImage, PAUSE_BTN_X, PAUSE_BTN_Y, PAUSE_BTN_WIDTH, PAUSE_BTN_HEIGHT, "PAUSE", Color.ORANGE);
        drawButton(g2d, aboutBtnImage, ABOUT_BTN_X, ABOUT_BTN_Y, ABOUT_BTN_WIDTH, ABOUT_BTN_HEIGHT, "ABOUT", Color.BLUE);
        drawButton(g2d, playBtnImage, PLAY_BTN_X, PLAY_BTN_Y, PLAY_BTN_WIDTH, PLAY_BTN_HEIGHT, "PLAY", Color.GREEN);
      }

      //Draw a button or a colored fallback if image is unavailable
      private void drawButton(Graphics2D g2d, BufferedImage img, int x, int y, int w, int h, String text, Color fallbackColor) {
        if (img != null) {
          g2d.drawImage(img, x, y, w, h, null);
        } else {
          g2d.setColor(fallbackColor);
          g2d.fillRect(x, y, w, h);
          g2d.setColor(Color.WHITE);
          g2d.setFont(new Font("Arial", Font.BOLD, 12));
          g2d.drawString(text, x + 10, y + 25);
        }
      }
    };

    customPanel.setLayout(null); //Use absolute positioning
    setupGameComponents(customPanel); //Add UI components
    setContentPane(customPanel); //Attach panel to window
  }

  //Add cards, scores, labels, and mouse click handlers
  private void setupGameComponents(JPanel panel) {
    //Label that says "WAR!" when a tie occurs
    warHeaderLabel = new JLabel("");
    warHeaderLabel.setForeground(Color.RED);
    warHeaderLabel.setFont(new Font("Arial", Font.BOLD, 48));
    warHeaderLabel.setBounds(GAME_TABLE_X, GAME_TABLE_Y + 280, GAME_TABLE_WIDTH, 50);
    warHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
    panel.add(warHeaderLabel);
    panel.setComponentZOrder(warHeaderLabel, 0); //Show on top

    //Player 1 name label
    JLabel player1Label = new JLabel("Player: " + controller.getPlayer1Name());
    player1Label.setForeground(Color.WHITE);
    player1Label.setFont(new Font("Arial", Font.BOLD, 18));
    player1Label.setBounds(GAME_TABLE_X + 50, GAME_TABLE_Y + 90, 200, 30);
    panel.add(player1Label);

    //Player 2 name label
    JLabel player2Label = new JLabel("Opponent: " + controller.getPlayer2Name());
    player2Label.setForeground(Color.WHITE);
    player2Label.setFont(new Font("Arial", Font.BOLD, 18));
    player2Label.setBounds(GAME_TABLE_X + 750, GAME_TABLE_Y + 90, 200, 30);
    panel.add(player2Label);

    //Back of deck for each player
    player1BackCardLabel = new JLabel();
    player1BackCardLabel.setBounds(GAME_TABLE_X + 50, GAME_TABLE_Y + 150, 80, 120);
    panel.add(player1BackCardLabel);

    player2BackCardLabel = new JLabel();
    player2BackCardLabel.setBounds(GAME_TABLE_X + 600, GAME_TABLE_Y + 150, 80, 120);
    panel.add(player2BackCardLabel);

    //Cards currently played face-up
    player1CardLabel = new JLabel();
    player1CardLabel.setBounds(GAME_TABLE_X + 200, GAME_TABLE_Y + 150, 80, 120);
    panel.add(player1CardLabel);

    player2CardLabel = new JLabel();
    player2CardLabel.setBounds(GAME_TABLE_X + 750, GAME_TABLE_Y + 150, 80, 120);
    panel.add(player2CardLabel);

    //Player scores (card counts)
    player1ScoreLabel = new JLabel("Cards: 0");
    player1ScoreLabel.setForeground(Color.YELLOW);
    player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
    player1ScoreLabel.setBounds(GAME_TABLE_X + 50, GAME_TABLE_Y + 350, 200, 30);
    panel.add(player1ScoreLabel);

    player2ScoreLabel = new JLabel("Cards: 0");
    player2ScoreLabel.setForeground(Color.YELLOW);
    player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 22));
    player2ScoreLabel.setBounds(GAME_TABLE_X + 750, GAME_TABLE_Y + 350, 200, 30);
    panel.add(player2ScoreLabel);

    //Round result (who won or if there was a war)
    roundResultLabel = new JLabel("", SwingConstants.CENTER);
    roundResultLabel.setForeground(Color.YELLOW);
    roundResultLabel.setFont(new Font("Arial", Font.BOLD, 24));
    roundResultLabel.setBounds(GAME_TABLE_X, GAME_TABLE_Y + 280, GAME_TABLE_WIDTH, 30);
    panel.add(roundResultLabel);

    //Mouse listener to handle button clicks
    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (isWithinBounds(x, y, BACK_BTN_X, BACK_BTN_Y, BACK_BTN_WIDTH, BACK_BTN_HEIGHT)) {
          controller.goBackToLanding();
        }

        if (isWithinBounds(x, y, PAUSE_BTN_X, PAUSE_BTN_Y, PAUSE_BTN_WIDTH, PAUSE_BTN_HEIGHT)) {
          controller.saveGameToDefaultFile();
          JOptionPane.showMessageDialog(CustomGameWindow.this, "Game paused and saved!", "Paused", JOptionPane.INFORMATION_MESSAGE);
          controller.goBackToLanding();
        }

        if (isWithinBounds(x, y, ABOUT_BTN_X, ABOUT_BTN_Y, ABOUT_BTN_WIDTH, ABOUT_BTN_HEIGHT)) {
          showAboutPopup();
        }

        if (isWithinBounds(x, y, PLAY_BTN_X, PLAY_BTN_Y, PLAY_BTN_WIDTH, PLAY_BTN_HEIGHT)) {
          controller.playRound();
        }
      }
    });

    updateCardDisplay(); //Show current game state
  }

  //Shows an "About" dialog with game version or an image
  private void showAboutPopup() {
    try {
      BufferedImage aboutImage = ImageIO.read(getClass().getResourceAsStream("/About.png"));
      ImageIcon aboutIcon = new ImageIcon(aboutImage);
      JDialog aboutDialog = new JDialog(this, "About", true);
      aboutDialog.getContentPane().add(new JLabel(aboutIcon));
      aboutDialog.pack();
      aboutDialog.setLocationRelativeTo(this);
      aboutDialog.setVisible(true);
    } catch (IOException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this,
        "<html><center>War Card Game<br>Version 1.0</center></html>",
        "About", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  //Checks if mouse click occurred inside button area
  private boolean isWithinBounds(int x, int y, int startX, int startY, int width, int height) {
    return x >= startX && x <= startX + width && y >= startY && y <= startY + height;
  }

  //Updates card icons, scores, and messages after each round
  private void updateCardDisplay() {
    warHeaderLabel.setText("");

    game.WarCardsGame game = controller.getGame();
    game.Card p1Card = game.getPlayer1Card();
    game.Card p2Card = game.getPlayer2Card();

    player1BackCardLabel.setIcon(getCardIcon("BackOfCard"));
    player2BackCardLabel.setIcon(getCardIcon("BackOfCard"));

    if (game.getPlayer1().getCardCount() == 0 || p1Card == null) {
      player1CardLabel.setIcon(null);
    } else {
      player1CardLabel.setIcon(getCardIcon(getCardImageName(p1Card)));
    }

    if (game.getPlayer2().getCardCount() == 0 || p2Card == null) {
      player2CardLabel.setIcon(null);
    } else {
      player2CardLabel.setIcon(getCardIcon(getCardImageName(p2Card)));
    }

    player1ScoreLabel.setText("Cards: " + game.getPlayer1().getCardCount());
    player2ScoreLabel.setText("Cards: " + game.getPlayer2().getCardCount());

    String result = game.getRoundResult();
    if (game.isGameOver()) {
      warHeaderLabel.setText("");
      roundResultLabel.setText("");
      return;
    }

    switch (result) {
      case "war":
        warHeaderLabel.setText("WAR!");
        roundResultLabel.setText("");
        break;
      case "p1_wins":
        roundResultLabel.setText(controller.getPlayer1Name() + " won the round!");
        break;
      case "p2_wins":
        roundResultLabel.setText(controller.getPlayer2Name() + " won the round!");
        break;
      default:
        roundResultLabel.setText("");
        break;
    }
  }

  //Returns image file name based on card rank and suit
  private String getCardImageName(game.Card card) {
    if (card == null) return null;
    int rank = card.getRank();
    String suit = card.getSuit();

    if (rank == 0) return "JokerCard1";
    if (rank == 14) return "AceOf" + suit;
    if (rank == 11) return suit + "Jack";
    if (rank == 12) return suit + "Queen";
    if (rank == 13) return suit + "King";
    return rank + suit.substring(0, 1); //For numbered cards like "7H"
  }

  //Loads and scales a card image nicely
  private ImageIcon getCardIcon(String imageName) {
    if (imageName == null || imageName.isEmpty()) return null;
    try {
      BufferedImage cardImage = ImageIO.read(getClass().getResourceAsStream("/" + imageName + ".png"));
      Image scaledImage = cardImage.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
      return new ImageIcon(scaledImage);
    } catch (IOException e) {
      System.err.println("Could not load card image: " + imageName + ".png");
      return null;
    }
  }

  //Public method to update game display when game state changes
  public void refreshGameDisplay() {
    updateCardDisplay();
  }
}
