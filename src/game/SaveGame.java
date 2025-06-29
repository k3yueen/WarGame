//Ademola Emmanuel Adegbola
package game;

import java.io.FileOutputStream;       //Used to write raw bytes to a file
import java.io.ObjectOutputStream;     //Used to write Java objects to a file (serialization)
import javax.swing.JFileChooser;       //GUI component that lets the user pick a file location
import javax.swing.JOptionPane;        //GUI component to show messages to the user

/**
 *This class is responsible for saving the current game state to a file
 *It uses Java's built-in file dialogs and object serialization
 */
public class SaveGame {

  /**
   *Saves the current game state (WarCardsGame object) to a file
   *It asks the user to choose a file location using a file dialog
   *
   *@param game The current game object that needs to be saved
   */
  public void saveGame(WarCardsGame game) {
    //Create a file chooser dialog to let the user pick where to save the game
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Specify a file to save"); /Title shown on the dialog

    //Show the dialog and store the user's choice (Approve/Cancel)
    int userSelection = fileChooser.showSaveDialog(null);

    //If the user clicked "Save" or "OK" (not Cancel)
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      try (
        //Create an ObjectOutputStream to write the game object to the selected file
        ObjectOutputStream oos = new ObjectOutputStream(
          new FileOutputStream(fileChooser.getSelectedFile()))
      ) {
        oos.writeObject(game);  //Actually save (serialize) the game object to the file

        //Inform the user that the game was saved successfully
        JOptionPane.showMessageDialog(null, "Game saved successfully!");
      } catch (Exception e) {
        //If an error occurs during saving, show an error message and print the error
        JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
        e.printStackTrace();  //Print the error details to the console (for debugging)
      }
    }
    //If the user clicked Cancel, the method just ends quietly (no saving happens)
  }

  /**
   *Prints basic information about this class to the console.
   *(This method could be expanded for more detailed logging if needed.)
   */
  public void printInfo() {
    System.out.println("Save Game Info:");
  }
}
