//Ademola Emmanuel Adegbola
package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure
import java.io.FileOutputStream; //IMPORT STATEMENT - brings in FileOutputStream class for writing raw bytes to a file
import java.io.ObjectOutputStream; //IMPORT STATEMENT - brings in ObjectOutputStream class for writing Java objects to a file (serialization)
import javax.swing.JFileChooser; //IMPORT STATEMENT - brings in JFileChooser class for GUI file selection dialog
import javax.swing.JOptionPane; //IMPORT STATEMENT - brings in JOptionPane class for showing message boxes to users

/**
 *This class is responsible for saving the current game state to a file
 *It uses Java's built-in file dialogs and object serialization
 */
//CLASS DECLARATION - creates a new class called "SaveGame" that handles saving games to files
public class SaveGame {
  /**
   *Saves the current game state (WarCardsGame object) to a file
   *It asks the user to choose a file location using a file dialog
   *
   *@param game The current game object that needs to be saved
   */
  //PUBLIC METHOD - saves the current game state to a file chosen by the user
  public void saveGame(WarCardsGame game) {
    //OBJECT CREATION - creates a new file chooser dialog for selecting save location
    JFileChooser fileChooser = new JFileChooser();
    //METHOD CALL - sets the title that appears at the top of the file chooser dialog
    fileChooser.setDialogTitle("Specify a file to save");
    //METHOD CALL - shows the save dialog and stores the user's choice (Save/Cancel)
    int userSelection = fileChooser.showSaveDialog(null);
    //CONDITIONAL STATEMENT - checks if the user clicked "Save" or "OK" (not Cancel)
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      //TRY-WITH-RESOURCES STATEMENT - automatically closes file streams when done
      try (
        //OBJECT CREATION - creates ObjectOutputStream to write the game object to the selected file
        ObjectOutputStream oos = new ObjectOutputStream(
          new FileOutputStream(fileChooser.getSelectedFile()))
      ) {
        //METHOD CALL - actually saves (serializes) the game object to the file
        oos.writeObject(game);
        //METHOD CALL - shows a success message to the user
        JOptionPane.showMessageDialog(null, "Game saved successfully!");
      } catch (Exception e) { //CATCH BLOCK - handles any errors that occur during saving
        //METHOD CALL - shows an error message to the user with the specific error details
        JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
        //METHOD CALL - prints the full error details to the console for debugging
        e.printStackTrace();
      }
    }
    //IMPLICIT RETURN - if the user clicked Cancel, the method just ends quietly (no saving happens)
  }
  
  /**
   *Prints basic information about this class to the console.
   *(This method could be expanded for more detailed logging if needed.)
   */
  //PUBLIC METHOD - prints basic information about the SaveGame class
  public void printInfo() {
    //METHOD CALL - prints a message to the console
    System.out.println("Save Game Info:");
  }
}
