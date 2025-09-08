package com.josiah.uno.controller;

import com.josiah.uno.model.cardgame.card.unocard.UnoCardTheme;
import com.josiah.uno.model.cardgame.card.unocard.UnoEdition;
import com.josiah.uno.model.cardgame.unogame.ClientUnoGameManager;
import com.josiah.uno.model.cardgame.unogame.Difficulty;
import com.josiah.uno.model.image.playerimage.PlayerImage;
import com.josiah.uno.model.image.playerimage.PlayerImageManager;
import com.josiah.uno.multiplayer.client.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;

/*
    The offline controller is connected to the offline view. The offline view is for the user to play the game
    in single player mode. A server/client is created and the user (client) is connected to their own local network
    (server) for playing the game.

     - PlayerImageManager class loads the player images
     - ClientUnoGameManager class sets up the game and creates the local player
     - SceneManager class loads and switches views
     - Client class creates the server and sends messages to server for setting the game and creating local player
 */

public class OfflineController {

    private PlayerImageManager playerImageManager = new PlayerImageManager();
    private ClientUnoGameManager gameManager;
    private SceneManager sceneManager;
    private Client client;
    private Parent settingsPane;

    private UnoEdition selectedEdition;
    private UnoCardTheme selectedTheme;
    private Difficulty selectedDifficulty;
    private PlayerImage selectedAvatar;
    private String selectedName;
    private int numberOfOpponents;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    @FXML
    private AnchorPane centerBox;
    @FXML
    private VBox avatarImageBox;
    @FXML
    private VBox editionBox;
    @FXML
    private StackPane centerPane;
    @FXML
    private GridPane centerGrid;
    @FXML
    private VBox playBtnBox;
    @FXML
    private HBox row2Box;
    @FXML
    private VBox difficultyVBox;
    @FXML
    private HBox difficultyHBox;
    @FXML
    private VBox themeBox;
    @FXML
    private ToggleButton easyBtn;
    @FXML
    private ToggleButton mediumBtn;
    @FXML
    private ToggleButton hardBtn;
    @FXML
    private ToggleButton classicThemeBtn;
    @FXML
    private ToggleButton classicEditionBtn;
    @FXML
    private FlowPane avatarImagePane;
    @FXML
    private Label avatarImageLbl;
    @FXML
    private HBox playerNameBox;
    @FXML
    private Label playerNameLbl;
    @FXML
    private TextField playerNameTextField;
    @FXML
    private Label difficultyLbl;
    @FXML
    private Label editionLbl;
    @FXML
    private Label themeLbl;
    @FXML
    private HBox editionSelection;
    @FXML
    private HBox cardThemeSelection;
    @FXML
    private Button playBtn;
    @FXML
    private ToggleGroup playerImageButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup themeButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup editionButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup difficultyButtonGroup = new ToggleGroup();
    @FXML
    private VBox backBox;
    @FXML
    private Button backBtn;
    @FXML
    private VBox settingsBox;
    @FXML
    private Button settingsBtn;
    @FXML
    private VBox numberOfOpponentsBox;
    @FXML
    private Label numberOfOpponentsLbl;
    @FXML
    SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9, 1);
    @FXML
    private Spinner<Integer> numberOfOpponentsSpinner;
    @FXML
    private VBox separationBox1;
    @FXML
    private VBox separationBox2;

    // This method sets the game manager
    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    // This method gets the sceneManager. It isn't used and may be removed.
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    // This method sets the sceneManager
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // This method runs when the class is loaded and initializes components
    public void initialize() {
        generatePlayerImages();
        initializeMenu();
        initializeToggleGroup();
        initializeSpinner();
    }

    // This method initializes the components for the settings menu and the back button
    public void initializeMenu() {
        backBox.toFront();
        settingsBox.toFront();
    }

    // This method initializes the components for the east, medium, hard, classic theme, and classic edition buttons
    public void initializeToggleGroup() {
        // set toggle group for difficulty buttons
        easyBtn.setToggleGroup(difficultyButtonGroup);
        mediumBtn.setToggleGroup(difficultyButtonGroup);
        hardBtn.setToggleGroup(difficultyButtonGroup);
        // set toggle group for theme button
        classicThemeBtn.setToggleGroup(themeButtonGroup);
        // set toggle group for edition button
        classicEditionBtn.setToggleGroup(editionButtonGroup);
    }

    // This method initializes the spinner component for selecting the number of players
    public void initializeSpinner() {
        numberOfOpponentsSpinner.setValueFactory(spinnerFactory);
    }

    // This method creates the server and client. It is used when the user selects their settings and launches the game
    public void createClient() {
        gameManager = new ClientUnoGameManager(); // create the clientUnoGameManager

        // create Client object.
        client = new Client();
        // set the clientGameManager for the client. The client game manager connects to the game area controller.
        client.setGameManager(gameManager);
        /*
            the Client class implements GameActionListener, so we set the gameManager's gameActionListener to the
            client object so that the client can listen to events that occur in the game manager.
         */
        gameManager.setGameActionListener(client); //
        /*
            Create the server. This method creates a Server object and connects the client to that server port and
            IP address. Basically, it connects to itself, because it is connecting to localhost at port 12345.
            It is used exclusively for offline mode.
        */
        client.createServer();
    }

    // Play audio for click1
    public void playClick1() {
        click1.play();
    }

    // Play audio for click2
    public void playClick2() {
        click2.play();
    }

    // Play audio for confirm1
    public void playConfirm1() {
        confirm1.play();
    }

    // Play audio for error1
    public void playError1() {
        error1.play();
    }

    // This method sets all the images to the buttons that the user can select
    public void generatePlayerImages() {
        /*
            clear the image pane each time this method is called. If this method is called again, like going
            back and forth in the views, then this prevents the avatarImagePane from duplicating the images and
            making a mess
         */
        avatarImagePane.getChildren().clear();

        // loop through all the values in the PlayerImage enum
        for (PlayerImage playerImage : PlayerImage.values()) {
            ToggleButton toggleButton = new ToggleButton(); // create a ToggleButton
            Image image = playerImageManager.getImage(playerImage); // get the image of that playerImage enum
            ImageView imageView = new ImageView(image); // create ImageView with the player image

            imageView.setFitWidth(50); // adjust the image width
            imageView.setFitHeight(50); // adjust the image height

            // set the user data for the button to its image. this is used to get the selected player image.
            toggleButton.setUserData(playerImage);
            toggleButton.setGraphic(imageView); // set the image for the button

            toggleButton.setToggleGroup(playerImageButtonGroup); // set toggle group for the button

            // set the event action for when the button is clicked
            toggleButton.setOnAction(e -> setSelectedAvatar());
            //toggleButton.isSelected(); doesn't do anything useful. may remove.

            // set the sound effect to playClick1 when the user hovers over the toggle button
            toggleButton.setOnMouseEntered(e -> playClick1());

            // add the button to the avatarImagePane
            avatarImagePane.getChildren().add(toggleButton);
        }
    }

    // This method sets the avatar that the user selects
    public void setSelectedAvatar() {
        // get the button that is selected from the playerImageButtonGroup
        ToggleButton selectedAvatarButton = (ToggleButton) playerImageButtonGroup.getSelectedToggle();
        if (selectedAvatarButton != null) { // check if the selectedAvatarButton is null
            playClick2(); // play click2 audio
            // get the user data from the selected button and set the selectedAvatar
            selectedAvatar = (PlayerImage) selectedAvatarButton.getUserData();
            /*
                get the name from the selectedAvatar enum and set the player name text field to the name
                associated with the player image enum
            */
            playerNameTextField.setText(selectedAvatar.getName());

            System.out.println(selectedAvatar); // print the player image to console for debugging
        } else {
            selectedAvatar = null; // if a button is not selected, set the selectedAvatar to null
        }
    }

    // This method sets the theme that the user selects
    public void setSelectedTheme() {
        // get the button that is selected from the themeButtonGroup
        ToggleButton selectedThemeButton = (ToggleButton) themeButtonGroup.getSelectedToggle();
        if (selectedThemeButton != null) { // check if the selectedThemeButton is null
            playClick2(); // play click2 audio
            // Get the user data from the selectedThemeButton which is a String
            String text = (String) selectedThemeButton.getUserData();
            selectedTheme = UnoCardTheme.valueOf(text); // get the UnoCardTheme enum type from the user data String
            System.out.println(selectedTheme); // print the selectedTheme to console for debugging
        } else {
            selectedTheme = null; // if a button is not selected, set the selectedTheme to null
        }
    }

    // This method sets the edition that the user selects
    public void setSelectedEdition() {
        // get the button that is selected from the editionButtonGroup
        ToggleButton selectedEditionButton = (ToggleButton) editionButtonGroup.getSelectedToggle();
        if (selectedEditionButton != null) { // check if the selectedEditionButton is null
            playClick2(); // play click2 audio
            // Get the user data from the selectedEditionButton which is a String
            String text = (String) selectedEditionButton.getUserData();
            selectedEdition = UnoEdition.valueOf(text); // get the UnoEdition enum type from the user data String
            System.out.println(selectedEdition); // print the selectedEdition to the console for debugging
        } else {
            selectedEdition = null; // if a button is not selected, set the selectedTheme to null
        }
    }

    // This method sets the difficulty that the user selects
    public void setSelectedDifficulty() {
        // get the button that is selected from the difficultyButtonGroup
        ToggleButton selectedDifficultyButton = (ToggleButton) difficultyButtonGroup.getSelectedToggle();
        if (selectedDifficultyButton != null) { // check if the selectedDifficultyButton is null
            playClick2(); // play audio click2
            // Get the user data from the selectedDifficultyButton which is a String
            String text = (String) selectedDifficultyButton.getUserData();
            selectedDifficulty = Difficulty.valueOf(text); // get the Difficulty enum type from the user data String
            System.out.println(selectedDifficulty); // print the selectedDifficulty to the console for debugging
        } else {
            selectedDifficulty = null; // if a button is not selected, set the selectedDifficulty to null
        }
    }

    // This method sets the player name
    public void setSelectedName() {
        // get the text from the playerNameTextfield and set it to the selectedName
        selectedName = playerNameTextField.getText();
    }

    // This method sets the number of players
    public void setNumberOfOpponents() {
        // get the value from the numberOfOpponentsSpinner and set it to the numberOfOpponents
        numberOfOpponents = numberOfOpponentsSpinner.getValue();
    }

    // This method returns to the previous menu
    public void goBack() {
        if (client != null) { // check if the client is null
            if (client.getServer() != null) { // check if the client's server is null
                client.getServer().shutDown(); // if the server was created, shut the server down
            }
            client = null; // set the client to null
            gameManager = null; // set the gameGamanager to null
        }

        sceneManager.switchScene("gameSelection"); // use sceneManager to switch to the game selection view
    }

    // This method is used to quit the program. It isn't currently used, but it will be used in the settings menu
    public void quit() {
        Platform.exit();
    }

    // This method is used to open settings menu.
    public void openSettings() {

    }

    // This method assembles the user's selected options and sets up the game with those settings
    public void setupGame() {
        playClick2(); // play audio click2

        createClient(); // create the client and server

        setSelectedName(); // set the selected name
        setNumberOfOpponents(); // set the number of opponents

        // check if any of the settings are null
        if (!(
                selectedEdition == null ||
                        selectedTheme == null ||
                        selectedDifficulty == null ||
                        selectedAvatar == null ||
                        selectedName == null
        )) {

            /*
                the client's gameManager joinGame method sends a message from the client to server for the
                client to join the game with the specified name and player image. the server creates a local
                player with that name and player image and adds it to the list of players.
             */
            gameManager.joinGame(selectedName, selectedAvatar);
            /*
                the client's gameManager setupGame method sends a message from the client to the server to let the
                server know what the settings are for the game to be started. The server begins a new game with the
                specified setting information.
             */
            gameManager.setupGame(selectedEdition, selectedTheme, selectedDifficulty, numberOfOpponents);

            // The sceneManager loads the game area view. The loadGameAreaScene needs the gameManager and client
            sceneManager.loadGameAreaScene(gameManager, client);
            sceneManager.switchScene("gameArea"); // switch to the game area scene

            // Set all of the selected buttons for each group to null incase the user returns to create new game
            playerImageButtonGroup.selectToggle(null);
            themeButtonGroup.selectToggle(null);
            editionButtonGroup.selectToggle(null);
            difficultyButtonGroup.selectToggle(null);

        } else {
            playError1(); // if any of the selected options are null, play error1 audio
        }

    }

    // This method is for setting up the settings, which hasn't been implemented yet
    public void setSettingsPane(Parent settingsPane) {
        this.settingsPane = settingsPane;
    }
}