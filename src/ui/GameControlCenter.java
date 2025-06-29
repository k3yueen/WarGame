//Roza Antonevici
package ui;

import game.*; //imports all classes from the game package
import javax.swing.JFrame;
import javax.swing.JOptionPane; //imports JOptionPane for dialog boxes

//this class acts as the controller for the game, managing the interaction between the game logic and the user interface
public class GameControlCenter {
  private WarCardsGame game; //game instance
  private JFrame gameFrame; //main game window
  private String player1Name; //name of player 1
  private String player2Name; //name of player 2
  private SaveGame saveGameHandler; //handler for saving the game state
  private LoadGame loadGameHandler; //handler for loading the game state

  //constructor to initialize the game controller
  public GameControlCenter() {
    saveGameHandler = new SaveGame(); //initialize save handler
    loadGameHandler = new LoadGame(); //initialize load handler
    game = new WarCardsGame(); //create new game instance
    game.dealCards(); //deal cards
    updateView(); //update the game view
  }

  //constructor to initialize the game controller with player names
  public GameControlCenter(String player1Name, String player2Name) {
    saveGameHandler = new SaveGame(); //initialize save handler
    loadGameHandler = new LoadGame(); //initialize load handler
    this.player1Name = player1Name; //set player 1 name
    this.player2Name = player2Name; //set player 2 name
    game = new WarCardsGame(); //create new game instance
    game.getPlayer1().setName(player1Name); //set name in game logic
    game.getPlayer2().setName(player2Name);
    game.dealCards(); //deal cards
    updateView(); //update the game view
  }

  //getters and setters
  public WarCardsGame getGame() {
    return game; //return game instance
  }

  public void setGame(WarCardsGame game) {
    this.game = game; //set game instance
  }

  public JFrame getGameFrame() {
    return gameFrame; //return game frame
  }

  public void setGameFrame(JFrame gameFrame) {
    this.gameFrame = gameFrame; //set game frame
  }

  public String getPlayer1Name() {
    return player1Name; //return player 1 name
  }

  public void setPlayer1Name(String player1Name) {
    this.player1Name = player1Name; //set player 1 name
  }

  public String getPlayer2Name() {
    return player2Name; //return player 2 name
  }

  public void setPlayer2Name(String player2Name) {
    this.player2Name = player2Name; //set player 2 name
  }

  //prompt for player names
  private void getPlayerNames() {
    player1Name = JOptionPane.showInputDialog("Enter Player 1 name:"); //input for player 1
    String[] options = { "Play against another player", "Play against the computer" }; //game mode options
    int choice = JOptionPane.showOptionDialog(null, "Choose game mode", "Game Mode",
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
        null, options, options[0]); //game mode dialog
    if (choice == 0) {
      player2Name = JOptionPane.showInputDialog("Enter Player 2 name:"); //input for player 2
    } else {
      player2Name = "Computer"; //set player 2 as computer
    }
  }

  //play a round of the game
  public void playRound() {
    if (!game.isGameOver()) { //check if game is ongoing
      game.playRound(); //play a round
      updateView(); //update view
      if (gameFrame != null && gameFrame instanceof ui.CustomGameWindow) {
        ((ui.CustomGameWindow) gameFrame).refreshGameDisplay(); //refresh GUI
      }
      if (game.isGameOver()) {
        JOptionPane.showMessageDialog(null, game.getResultMessage()); //show game result
      }
    }
  }

  //update the view
  public void updateView() {
    if (gameFrame != null && gameFrame instanceof ui.JFrame) {
      ui.JFrame customFrame = (ui.JFrame) gameFrame;
      customFrame.getGamePanel().updateGameView(
          game.getPlayer1Card(),
          game.getPlayer2Card(),
          getResultText(),
          game.getPlayer1().getScore(),
          game.getPlayer2().getScore(),
          player1Name,
          player2Name);
    }
  }

  //get result message for the round
  private String getResultText() {
    if (game.getPlayer1Card() != null && game.getPlayer2Card() != null) {
      if (game.getPlayer1Card().getRank() > game.getPlayer2Card().getRank()) {
        return player1Name + " wins this round!";
      } else if (game.getPlayer1Card().getRank() < game.getPlayer2Card().getRank()) {
        return player2Name + " wins this round!";
      } else {
        return "It's a tie!";
      }
    }
    return "";
  }

  //start a new game
  public void newGame() {
    getPlayerNames(); //prompt for names
    game.reset(); //reset game logic
    updateView(); //refresh view
  }

  //save game
  public void saveGame() {
    saveGameHandler.saveGame(game); //use handler to save game
  }

  //open a saved game
  public void openGame() {
    WarCardsGame loadedGame = loadGameHandler.loadGame(); //load saved game
    if (loadedGame != null) {
      game = loadedGame; //set loaded game
      updateView(); //update view
    }
  }

  //go back to landing screen
  public void goBackToLanding() {
    if (gameFrame != null) {
      gameFrame.dispose(); //close current frame
    }
    LandingPage landingPage = new LandingPage(); //open landing page
    landingPage.setVisible(true);
  }

  //print controller info
  public void printInfo() {
    System.out.println("Game Controller Info:");
    game.printInfo(); //print game info
  }

  //create the game frame
  public void createGameFrame() {
    gameFrame = new ui.JFrame(this); //create custom frame
  }

  //save game to default file
  public void saveGameToDefaultFile() {
    try {
      java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream("paused_game.dat"));
      oos.writeObject(game); //write game to file
      oos.close();
      System.out.println("Game saved to paused_game.dat");
    } catch (Exception e) {
      System.err.println("Error saving game: " + e.getMessage());
      e.printStackTrace();
    }
  }

  //load game from default file
  public void loadGameFromDefaultFile() {
    try {
      java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream("paused_game.dat"));
      WarCardsGame loadedGame = (WarCardsGame) ois.readObject(); //read game from file
      ois.close();
      if (loadedGame != null) {
        game = loadedGame;
        if (game.getPlayer1() != null) this.player1Name = game.getPlayer1().getName();
        if (game.getPlayer2() != null) this.player2Name = game.getPlayer2().getName();
        updateView(); //update view
        if (gameFrame != null && gameFrame instanceof ui.CustomGameWindow) {
          ((ui.CustomGameWindow) gameFrame).refreshGameDisplay();
        }
        System.out.println("Game loaded from paused_game.dat");
      }
    } catch (Exception e) {
      System.err.println("Error loading game: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

