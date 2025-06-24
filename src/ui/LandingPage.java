package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LandingPage extends javax.swing.JFrame {
  private BufferedImage backgroundImage;
  private BufferedImage newGameBtnImage;
  private BufferedImage loadGameBtnImage;
  private GameControlCenter gameController;

  //Button dimensions and positions (adjusted to be centered under the WAR GAME
  //title)
  private static final int NEW_GAME_BTN_X = 593; // Moved to 593
  private static final int NEW_GAME_BTN_Y = 350; // Under the WAR GAME title
  private static final int LOAD_GAME_BTN_X = 593; // Same X position for alignment
  private static final int LOAD_GAME_BTN_Y = 450; // Below the New Game button
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 80;

  public LandingPage() {
    super(); // Call the super constructor
    loadImages();
    setupFrame();
    createCustomPanel();
  }

  private void loadImages() {
    try {
      String currentDir = System.getProperty("user.dir");
      System.out.println("Current directory: " + currentDir);

      String backgroundPath = currentDir + "/images/LandingPage.png";
      String newGamePath = currentDir + "/images/NewGameBtn.png";
      String loadGamePath = currentDir + "/images/LoadGameBtn.png";

      System.out.println("Looking for background at: " + backgroundPath);
      System.out.println("Looking for new game button at: " + newGamePath);
      System.out.println("Looking for load game button at: " + loadGamePath);

      backgroundImage = ImageIO.read(new File(backgroundPath));
      newGameBtnImage = ImageIO.read(new File(newGamePath));
      loadGameBtnImage = ImageIO.read(new File(loadGamePath));

      System.out.println("All images loaded successfully!");
    } catch (IOException e) {
      System.err.println("Error loading images: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void setupFrame() {
    setTitle("War Card Game - Landing Page");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    if (backgroundImage != null) {
      setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
    } else {
      setSize(800, 600);
    }

    setLocationRelativeTo(null); // Center on screen
  }

  private void createCustomPanel() {
    JPanel customPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        if (backgroundImage != null) {
          g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw buttons
        if (newGameBtnImage != null) {
          g2d.drawImage(newGameBtnImage, NEW_GAME_BTN_X, NEW_GAME_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }

        if (loadGameBtnImage != null) {
          g2d.drawImage(loadGameBtnImage, LOAD_GAME_BTN_X, LOAD_GAME_BTN_Y,
              BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }
      }
    };

    customPanel.setLayout(null); // Use absolute positioning
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //Checking if New Game button was clicked
        if (x >= NEW_GAME_BTN_X && x <= NEW_GAME_BTN_X + BUTTON_WIDTH &&
            y >= NEW_GAME_BTN_Y && y <= NEW_GAME_BTN_Y + BUTTON_HEIGHT) {
          startNewGame();
        }

        //Checking if Load Game button was clicked
        if (x >= LOAD_GAME_BTN_X && x <= LOAD_GAME_BTN_X + BUTTON_WIDTH &&
            y >= LOAD_GAME_BTN_Y && y <= LOAD_GAME_BTN_Y + BUTTON_HEIGHT) {
          loadGame();
        }
      }
    });

    setContentPane(customPanel);
  }

  private void startNewGame() {
    //Hides landing page
    setVisible(false);

    //Starts new game
    gameController = new GameControlCenter();
    gameController.getGameFrame().setVisible(true);
  }

  private void loadGame() {
    //Hides landing page
    setVisible(false);

    //Creates controller and load game
    gameController = new GameControlCenter();
    gameController.openGame();
    gameController.getGameFrame().setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      LandingPage landingPage = new LandingPage();
      landingPage.setVisible(true);
    });
  }
}
