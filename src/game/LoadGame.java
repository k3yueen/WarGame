//Sema Khaled Murshed Ibrahim

package game; //PACKAGE DECLARATION - tells Java where this class belongs in the project structure
import java.io.FileInputStream; //IMPORT STATEMENT - brings in FileInputStream class for reading files
import java.io.ObjectInputStream; //IMPORT STATEMENT - brings in ObjectInputStream class for reading saved objects
import javax.swing.JFileChooser; //IMPORT STATEMENT - brings in JFileChooser class for file selection dialog
import javax.swing.JOptionPane; //IMPORT STATEMENT - brings in JOptionPane class for showing message boxes

/**
 *Handles loading the game state from a file
 */
//CLASS DECLARATION - creates a new class called "LoadGame" that handles loading saved games
public class LoadGame {
  /**
   *Loads the game state from a file selected by the user
   *@returns the loaded game state
   */
  //PUBLIC METHOD - loads a saved game from a file chosen by the user
  public WarCardsGame loadGame() {
    //OBJECT CREATION - creates a new file chooser dialog for selecting files
    JFileChooser fileChooser = new JFileChooser();
    //METHOD CALL - sets the title that appears at the top of the file chooser window
    fileChooser.setDialogTitle("Select a saved game file");
    //METHOD CALL - shows the file chooser dialog and stores the user's choice
    int userSelection = fileChooser.showOpenDialog(null);
    //CONDITIONAL STATEMENT - checks if the user clicked "Open" (approved the file selection)
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      //TRY-WITH-RESOURCES STATEMENT - automatically closes file streams when done
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()))) {
        //OBJECT CREATION - creates ObjectInputStream to read the saved game object from file
        //TYPE CASTING - converts the loaded object back to WarCardsGame type
        WarCardsGame game = (WarCardsGame) ois.readObject();
        //METHOD CALL - shows a success message to the user
        JOptionPane.showMessageDialog(null, "Game loaded successfully!");
        //RETURN STATEMENT - sends back the loaded game object
        return game;
      } catch (Exception e) { //CATCH BLOCK - handles any errors that occur during loading
        //METHOD CALL - shows an error message to the user with the specific error details
        JOptionPane.showMessageDialog(null, "Error loading game: " + e.getMessage());
        //METHOD CALL - prints the full error details to the console for debugging
        e.printStackTrace();
        //RETURN STATEMENT - returns null to indicate loading failed
        return null;
      }
    }
    //RETURN STATEMENT - returns null if user canceled the file selection
    return null;
  }
  
  /**
   *Prints information about the load game
   */
  //PUBLIC METHOD - prints basic information about the LoadGame class
  public void printInfo() {
    //METHOD CALL - prints a message to the console
    System.out.println("Load Game Info:");
  }
}
