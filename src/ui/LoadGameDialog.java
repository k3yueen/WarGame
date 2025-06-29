//Roza Antonevici
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

//Dialog for selecting a saved game to load
public class LoadGameDialog extends JDialog {
  private static final long serialVersionUID = 1L;//Serialization ID

  private BufferedImage backgroundImage;//Background image for dialog
  private BufferedImage doneBtnImage;//Image for Done button
  private BufferedImage cancelBtnImage;//Image for Cancel button

  private JComboBox<String> savedGamesComboBox;//Dropdown to select saved games
  private JButton doneButton;//Confirm selection button
  private JButton cancelButton;//Cancel button

  private boolean confirmed = false;//Flag if user confirmed selection
  private String selectedGame = null;//The selected saved game filename

  //Constants for button position and size
  private static final int DONE_BTN_X = 615;
  private static final int DONE_BTN_Y = 320;
  private static final int CANCEL_BTN_X = 390;
  private static final int CANCEL_BTN_Y = 320;
  private static final int BUTTON_WIDTH = 150;
  private static final int BUTTON_HEIGHT = 80;

  private static final String DEFAULT_SELECT_OPTION = "Select a saved game...";//Default combo box entry

  //Constructor to create dialog with parent frame
  public LoadGameDialog(JFrame parent) {
    super(parent, "Load Saved Game", true);//Modal dialog with title

    loadImages();//Load background and button images

    setSize(backgroundImage != null ? backgroundImage.getWidth() : 800,
            backgroundImage != null ? backgroundImage.getHeight() : 400);//Set dialog size

    setLocationRelativeTo(parent);//Center dialog on parent

    setupUI();//Setup UI components and layout
  }

  //Load images from resources
  private void loadImages() {
    try {
      //Load background and buttons from resource paths
      backgroundImage = ImageIO.read(getClass().getResourceAsStream("/ChooseGame.png"));
      doneBtnImage = ImageIO.read(getClass().getResourceAsStream("/DoneBTN.png"));
      cancelBtnImage = ImageIO.read(getClass().getResourceAsStream("/CancelBTN.png"));
    } catch (IOException e) {
      System.err.println("Error loading dialog images:"+e.getMessage());
      e.printStackTrace();
    }
  }

  //Setup UI components and add to dialog
  private void setupUI() {
    JPanel customPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw background image or fallback color
        if (backgroundImage != null) {
          g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g.setColor(Color.GRAY);
          g.fillRect(0, 0, getWidth(), getHeight());
        }
      }
    };

    customPanel.setLayout(null);//Use absolute positioning

    //Load saved game list into combo box
    ArrayList<String> savedGamesList = loadSavedGamesFromFile();
    savedGamesComboBox = new JComboBox<>();
    savedGamesComboBox.setBounds(360, 210, 390, 50);
    savedGamesComboBox.addItem(DEFAULT_SELECT_OPTION);//Add default selection option
    for (String game : savedGamesList) {
      savedGamesComboBox.addItem(game);//Add saved games to combo box
    }
    savedGamesComboBox.setFont(new Font("Arial", Font.PLAIN, 20));//Set font for readability

    customPanel.add(savedGamesComboBox);//Add combo box to panel

    //Add mouse listener to detect clicks on custom drawn buttons
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //Check if Done button area was clicked
        if (x >= DONE_BTN_X && x <= DONE_BTN_X + BUTTON_WIDTH &&
            y >= DONE_BTN_Y && y <= DONE_BTN_Y + BUTTON_HEIGHT) {
          onDoneClicked();
        }

        //Check if Cancel button area was clicked
        if (x >= CANCEL_BTN_X && x <= CANCEL_BTN_X + BUTTON_WIDTH &&
            y >= CANCEL_BTN_Y && y <= CANCEL_BTN_Y + BUTTON_HEIGHT) {
          onCancelClicked();
        }
      }

      //Paint Done and Cancel buttons on panel
      @Override
      public void mouseEntered(MouseEvent e) {
        customPanel.repaint();
      }
    });

    //Override paint to draw buttons
    customPanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Draw background or fallback color
        if (backgroundImage != null) {
          g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
          g.setColor(Color.GRAY);
          g.fillRect(0, 0, getWidth(), getHeight());
        }

        //Draw Done button image
        if (doneBtnImage != null) {
          g.drawImage(doneBtnImage, DONE_BTN_X, DONE_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }

        //Draw Cancel button image
        if (cancelBtnImage != null) {
          g.drawImage(cancelBtnImage, CANCEL_BTN_X, CANCEL_BTN_Y, BUTTON_WIDTH, BUTTON_HEIGHT, null);
        }
      }
    };

    //Add combo box again since we replaced the panel
    customPanel.setLayout(null);
    customPanel.add(savedGamesComboBox);

    //Add the mouse listener again
    customPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (x >= DONE_BTN_X && x <= DONE_BTN_X + BUTTON_WIDTH &&
            y >= DONE_BTN_Y && y <= DONE_BTN_Y + BUTTON_HEIGHT) {
          onDoneClicked();
        }

        if (x >= CANCEL_BTN_X && x <= CANCEL_BTN_X + BUTTON_WIDTH &&
            y >= CANCEL_BTN_Y && y <= CANCEL_BTN_Y + BUTTON_HEIGHT) {
          onCancelClicked();
        }
      }
    });

    setContentPane(customPanel);//Set panel as dialog content
  }

  //Load saved game filenames from "saved_games.txt" or fallback list
  private ArrayList<String> loadSavedGamesFromFile() {
    ArrayList<String> savedGamesList = new ArrayList<>();

    try {
      //Get current directory and file path
      String currentDir = System.getProperty("user.dir");
      File savedGamesFile = new File(currentDir+"/saved_games.txt");

      //Read file if it exists
      if (savedGamesFile.exists()) {
        BufferedReader br = new BufferedReader(new FileReader(savedGamesFile));
        String line;
        while ((line = br.readLine()) != null) {
          if (!line.trim().isEmpty()) {
            savedGamesList.add(line.trim());
          }
        }
        br.close();
      } else {
        //If file not found, add some default example saved games
        savedGamesList.add("saved_game1.dat");
        savedGamesList.add("saved_game2.dat");
        savedGamesList.add("saved_game3.dat");
      }
    } catch (IOException e) {
      System.err.println("Error loading saved games file:"+e.getMessage());
      e.printStackTrace();
    }

    return savedGamesList;
  }

  //Called when Done button is clicked
  private void onDoneClicked() {
    String selected = (String) savedGamesComboBox.getSelectedItem();
    if (selected == null || selected.equals(DEFAULT_SELECT_OPTION)) {
      JOptionPane.showMessageDialog(this, "Please select a saved game to load.", "No Selection",
                                    JOptionPane.WARNING_MESSAGE);
      return;//Do not close dialog if no valid selection
    }
    selectedGame = selected;//Store selected game filename
    confirmed = true;//Mark as confirmed
    setVisible(false);//Hide dialog
  }

  //Called when Cancel button is clicked
  private void onCancelClicked() {
    confirmed = false;//Mark as not confirmed
    selectedGame = null;//Clear selection
    setVisible(false);//Hide dialog
  }

  //Returns true if user confirmed a valid selection
  public boolean isConfirmed() {
    return confirmed;
  }

  //Returns the selected saved game filename
  public String getSelectedGame() {
    return selectedGame;
  }
}
