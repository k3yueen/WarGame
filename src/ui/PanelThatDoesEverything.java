//Roza Antonevici
package ui;

import game.Card; //Imports the Card class from the game package

import javax.swing.*; //Imports all classes from javax.swing for GUI components
import java.awt.*; //Imports all classes from java.awt for graphics and layout
import java.awt.image.BufferedImage; //Imports BufferedImage for image handling
import java.io.File; //Imports File for file handling
import java.io.IOException; //Imports IOException for handling IO exceptions
import javax.imageio.ImageIO; //Imports ImageIO for reading/writing images
import javax.swing.OverlayLayout; //Imports OverlayLayout for layering components
import javax.swing.JPanel; //Imports JPanel as the base container class

//This class represents the main panel in the game window where the game is displayed
public class PanelThatDoesEverything extends JPanel {
  private static final long serialVersionUID = 1L; //Ensures serialization compatibility
  
  private GameControlCenter controller; //Reference to the game controller managing the game logic

  //Labels for player 1's and player 2's card backs and fronts
  private JLabel player1BackCardLabel, player2BackCardLabel;
  private JLabel player1FrontCardLabel, player2FrontCardLabel;

  //Labels for displaying the current scores of player 1 and player 2
  private JLabel player1ScoreLabel, player2ScoreLabel;

  private JLabel resultLabel; //Label for displaying the result text
  private JLabel resultImageLabel; //Label for displaying an image corresponding to the result

  //Labels for displaying player names
  private JLabel player1Label, player2Label;

  private JButton playButton; //Button to trigger playing a round

  private BufferedImage backgroundImage; //Background image for the game panel

  //Constructor to initialize the game panel with a controller
  //@param controller The game controller instance
  public PanelThatDoesEverything(GameControlCenter controller) {
    this.controller = controller; // Store the controller reference

    setLayout(new OverlayLayout(this)); // Use OverlayLayout to stack components on top of each other

    try {
      //Attempt to load the background image from the resources
      backgroundImage = ImageIO.read(getClass().getResourceAsStream("/goldcasino.jpeg"));
    } catch (Exception e) {
      e.printStackTrace(); // Print stack trace if loading fails
    }

    //Create the main container panel with BorderLayout and make it transparent
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setOpaque(false);

    //Create the top panel to hold player info and cards in two columns
    JPanel topPanel = new JPanel(new GridLayout(1, 2));
    topPanel.setOpaque(false);

    //Player 1 Panel
    JPanel player1Panel = new JPanel(new BorderLayout());
    player1Panel.setOpaque(false);

    //Label showing player 1's name or description
    player1Label = createLabel("User Card", 100, 30);
    player1Panel.add(player1Label, BorderLayout.NORTH);

    //Panel holding player 1's cards (back and front) vertically
    JPanel player1CardPanel = new JPanel(new GridLayout(2, 1));
    player1CardPanel.setOpaque(false);

    //Label showing the back of player 1's card with scaled image
    player1BackCardLabel = new JLabel(new ImageIcon(resizeImage("/BackCard.jpg", 80, 120)));
    player1BackCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    player1CardPanel.add(player1BackCardLabel);

    //Label for the front of player 1's card (initially empty)
    player1FrontCardLabel = new JLabel();
    player1FrontCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    player1CardPanel.add(player1FrontCardLabel);

    player1Panel.add(player1CardPanel, BorderLayout.CENTER);

    //Panel and label showing player 1's total score
    JPanel player1ScorePanel = new JPanel();
    player1ScorePanel.setOpaque(false);
    player1ScoreLabel = createLabel("Total score: 0", 160, 25);
    player1ScorePanel.add(player1ScoreLabel);
    player1Panel.add(player1ScorePanel, BorderLayout.SOUTH);

    //Add player 1 panel to the left column of the top panel
    topPanel.add(player1Panel);

    //Player 2 Panel
    JPanel player2Panel = new JPanel(new BorderLayout());
    player2Panel.setOpaque(false);

    //Label showing player 2's name or description
    player2Label = createLabel("Computer Card", 100, 30);
    player2Panel.add(player2Label, BorderLayout.NORTH);

    //Panel holding player 2's cards (back and front) vertically
    JPanel player2CardPanel = new JPanel(new GridLayout(2, 1));
    player2CardPanel.setOpaque(false);

    //Label showing the back of player 2's card with scaled image
    player2BackCardLabel = new JLabel(new ImageIcon(resizeImage("/BackCard.jpg", 80, 120)));
    player2BackCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    player2CardPanel.add(player2BackCardLabel);

    //Label for the front of player 2's card (initially empty)
    player2FrontCardLabel = new JLabel();
    player2FrontCardLabel.setHorizontalAlignment(SwingConstants.CENTER);
    player2CardPanel.add(player2FrontCardLabel);

    player2Panel.add(player2CardPanel, BorderLayout.CENTER);

    // Panel and label showing player 2's total score
    JPanel player2ScorePanel = new JPanel();
    player2ScorePanel.setOpaque(false);
    player2ScoreLabel = createLabel("Total score: 0", 160, 25);
    player2ScorePanel.add(player2ScoreLabel);
    player2Panel.add(player2ScorePanel, BorderLayout.SOUTH);

    //Add player 2 panel to the right column of the top panel
    topPanel.add(player2Panel);

    //Add the top panel (players info/cards) to the center of the main panel
    mainPanel.add(topPanel, BorderLayout.CENTER);

    //Middle Panel for Results 
    JPanel middlePanel = new JPanel(new BorderLayout());
    middlePanel.setOpaque(false);

    //Label to display result messages
    resultLabel = createLabel("", 300, 30);
    resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
    middlePanel.add(resultLabel, BorderLayout.NORTH);

    //Add the middle panel to the north region of main panel
    mainPanel.add(middlePanel, BorderLayout.NORTH);

    //Bottom Panel with Play Button
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
    bottomPanel.setOpaque(false);

    //Panel holding the play button
    JPanel playButtonPanel = new JPanel();
    playButtonPanel.setOpaque(false);

    //Play button with a custom image and no border or content fill for a transparent look
    playButton = new JButton(new ImageIcon(resizeImage("/playbutton.png", 200, 160)));
    playButton.setContentAreaFilled(false);
    playButton.setBorderPainted(false);
    playButton.setBorder(BorderFactory.createEmptyBorder());
    playButton.setOpaque(false);

    //Attach action listener to play a round when clicked
    playButton.addActionListener(e -> controller.playRound());

    playButtonPanel.add(playButton);
    bottomPanel.add(playButtonPanel);

    //Add bottom panel to the south region of main panel
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);

    //Add main panel to this JPanel
    add(mainPanel);

    //Label to display result images such as happy or sad face, centered on the panel
    resultImageLabel = new JLabel();
    resultImageLabel.setAlignmentX(0.5f);
    resultImageLabel.setAlignmentY(0.5f);
    resultImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(resultImageLabel);
  }

  //Helper method to create a styled JLabel with specified text and dimensions
  //@param text The text displayed by the label
  //@param width Preferred width of the label
  //@param height Preferred height of the label
  //@return The created and styled JLabel
  private JLabel createLabel(String text, int width, int height) {
    JLabel label = new JLabel(text, SwingConstants.CENTER); //Center the text horizontally
    label.setForeground(Color.WHITE); //Set text color to white
    label.setBackground(new Color(128, 0, 0)); //Wine color background
    label.setOpaque(true); //Make background visible
    label.setFont(new Font("Arial", Font.BOLD, 14)); //Bold Arial font
    label.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // White border around label.
    //Set fixed size to prevent resizing
    label.setPreferredSize(new Dimension(width, height));
    label.setMinimumSize(new Dimension(width, height));
    label.setMaximumSize(new Dimension(width, height));
    return label;
  }

  //Paints the background image, scaled to fill the entire panel
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g); //Call superclass method to paint other components
    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); //Draw scaled background
    }
  }

  //Updates the game view with the current game state.
  //@param player1Card Card played by player 1 (may be null).
  //@param player2Card Card played by player 2 (may be null).
  //@param result Result text to display.
  //@param player1Score

