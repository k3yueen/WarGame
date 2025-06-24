package game;

import java.io.*;

/**
 * SaveGame class handles saving and loading the game state to/from a file.
 * It uses Java's built-in Serializable mechanism to serialize the GameEngine instance.
 * Make sure the GameEngine, Player, and Card classes all implement Serializable.
 */
public class SaveGame {

    // Path to the file where we'll save/load the game state
    private final String saveFilePath;

    /**
     * Constructor to initialize the save file path.
     * @param path The path to the file used for saving and loading.
     */
    public SaveGame(String path) {
        this.saveFilePath = path;
    }

    /**
     * Saves the current game state to a file using Java serialization.
     * @param game The GameEngine object to be saved.
     * @throws IOException If something goes wrong during writing.
     */
    public void saveGame(GameEngine game) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(game);  // Write the whole GameEngine object to file
            System.out.println("✅ Game saved successfully to " + saveFilePath);
        }
    }

    /**
     * Loads a previously saved game state from a file.
     * @return A restored GameEngine object.
     * @throws IOException If the file can't be read.
     * @throws ClassNotFoundException If the class definition has changed or is missing.
     */
    public GameEngine loadGame() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            GameEngine loadedGame = (GameEngine) ois.readObject();  // Read and cast the object
            System.out.println("✅ Game loaded successfully from " + saveFilePath);
            return loadedGame;
        }
    }
}
