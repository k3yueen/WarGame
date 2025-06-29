package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class PlayerNameDialog extends JDialog {
  private static final long serialVersionUID = 1L;
  private BufferedImage backgroundImage;
  private BufferedImage doneBtnImage;
  private BufferedImage cancelBtnImage;
  private JTextField playerNameField;
  private boolean confirmed = false;
  private String playerName = null;
  private String backgroundImageFile = "/InputTab.png";

  //Button dimensions and positions (moved higher up on InputTab.png)
  private static final int DONE_BTN_X = 300;
  private static final int DONE_BTN_Y = 180;
  private static final int CANCEL_BTN_X = 450;
  private static final int CANCEL_BTN_Y = 180;
  private static final int BUTTON_WIDTH = 100;
  private static final int BUTTON_HEIGHT = 40;

  //Text field position (positioned between title and buttons)
  private static final int TEXT_FIELD_X = 300;
  private static final int TEXT_FIELD_Y = 140;
  private static final int TEXT_FIELD_WIDTH = 200;
  private static final int TEXT_FIELD_HEIGHT = 30;

  public PlayerNameDialog(javax.swing.JFrame parent) {
    super(parent, "Enter Player Name", true);
    loadImages();
    setupDialog();
    createCustomPanel();
  }

  //New constructor to specify background image
  public PlayerNameDialog(javax.swing.JFrame parent, String backgroundImageFile) {
    super(parent, "Enter Player Name", true);
    this.backgroundImageFile = backgroundImageFile;
    loadImages();
    setupDialog();
    createCustomPanel();
  }

  private void loadImages() {
    try {
      backgroundImage = ImageIO.read(getClass().getResourceAsStream(backgroundImageFile));
      doneBtnImage = ImageIO.read(getClass().getResourceAsStream("/DoneBTN.png"));
      cancelBtnImage = ImageIO.read(getClass().getResourceAsStream("/CancelBTN.png"));

      System.out.println("All player name dialog images loaded successfully from resources!");
    } catch (Exception e) {
      System.err.println("Error loading player name dialog images from resources: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private void setupDialog() {
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setResizable(false);

    if (backgroundImage != null) {
      setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
    } else {
      setSize(800, 400);
    }

    setLocationRelativeTo(getOwner());
  }

  private void createCustomPanel() {
    JPanel customPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw background
        if (backgroundImage != null) {
          g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g2d.setColor(Color.LIGHT_GRAY);
          g2d.fillRect(0, 0, getWidth(), getHeight());
        }

        //Draw buttons
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

    customPanel.setLayout(null);

    // Create text field for player name
    playerNameField = new JTextField();
    playerNameField.setBounds(TEXT_FIELD_X, TEXT_FIELD_Y, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
    playerNameField.setFont(new Font("Arial", Font.BOLD, 14));
    customPanel.add(playerNameField);

    //Add mouse detector for button clicks
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //Check if Done button was clicked
        if (x >= DONE_BTN_X && x <= DONE_BTN_X + BUTTON_WIDTH &&
            y >= DONE_BTN_Y && y <= DONE_BTN_Y + BUTTON_HEIGHT) {
          confirmSelection();
        }

        //Checks if Cancel button was clicked
        if (x >= CANCEL_BTN_X && x <= CANCEL_BTN_X + BUTTON_WIDTH &&
            y >= CANCEL_BTN_Y && y <= CANCEL_BTN_Y + BUTTON_HEIGHT) {
          cancelSelection();
        }
      }
    });

    setContentPane(customPanel);
  }

  private void confirmSelection() {
    playerName = playerNameField.getText().trim();
    if (playerName != null && !playerName.isEmpty()) {
      confirmed = true;
      System.out.println("Player name entered: " + playerName);
      dispose();
    } else {
      JOptionPane.showMessageDialog(this,
          "Please enter a player name.",
          "No Name Entered",
          JOptionPane.WARNING_MESSAGE);
    }
  }

  private void cancelSelection() {
    confirmed = false;
    playerName = null;
    System.out.println("Player name input cancelled");
    dispose();
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public String getPlayerName() {
    return playerName;
  }
}
