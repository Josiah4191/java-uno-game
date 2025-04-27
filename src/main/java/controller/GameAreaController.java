package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.PlayDirection;
import model.image.cardimage.UnoCardImageManager;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.cardgame.unogame.ClientUnoGameManager;
import model.cardgame.unogame.UnoGameState;
import multiplayer.client.Client;
import multiplayer.server.servermessage.*;

import java.util.Optional;

/*

This GameAreaController class connects the ClientUnoGameManager class to the GameAreaView.
    - The ClientUnoGameManager is the client side game manager which is used to send messages to the server and update the
    client's game.
    - The GameAreaView is the main screen where gameplay takes place.

Instance variables:
    - Client
        - This controller stores a reference to the Client object that uses this controller. Currently, it is used
        to shut down all of the threads that the client and server are running.
    - SceneManager
        - The SceneManager class holds all the scenes in the program. It's used to launch the different UI views
        and it can load them as well.
    - ClientUnoGameManager
        - The client game manager is a lightweight game manager class that is used to send JSON messages to the server,
        and the client game manager also has methods to update the client's side of the game to mirror the server, which is
        where the actual changes take place.
    - UnoGameState
        - The ClientUnoGameManager owns it's version of the UnoGameState. The UnoGameState has all the information about the
        state of the game.
    - drawCard boolean
        - This flag is used for when a player draws a card. If the player draws a card, then this will be set to true, and
        the button will be disabled. It's used to prevent the user from drawing multiple cards back to back.
    - AudioClips
        - Used for different sound effects
    - UI
        - Used for user interaction and viewing the game.
            - containers (AnchorPane, StackPane, VBox ...)
            - buttons
            - labels
 */

public class GameAreaController implements GameAreaListener {

    private Client client;
    private SceneManager sceneManager;
    private ClientUnoGameManager gameManager;
    private UnoGameState gameState;
    private boolean drawCard = false;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/double_click.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());
    private AudioClip BGMusic = new AudioClip(getClass().getResource("/audio/Evening_harmony.mp3").toString());

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private StackPane settingsOuterStackPane;
    @FXML
    private StackPane settingsInnerStackPane;
    @FXML
    private VBox settingsVbox;
    @FXML
    private Label settingsLbl;
    @FXML
    private GridPane settingsGridPane;
    @FXML
    private VBox settingsBtnVbox;
    @FXML
    private Button settingsBtn;
    @FXML
    private StackPane centerStackPane;
    @FXML
    private GridPane centerGridPane;
    @FXML
    private HBox playDirectionHbox = new HBox();
    @FXML
    private Label playDirectionLbl;
    @FXML
    private FlowPane opponentPlayerFlowPane;
    @FXML
    private HBox pilesHbox;
    @FXML
    private Button drawPileBtn;
    @FXML
    private Button discardPileBtn;
    @FXML
    private FlowPane playerCardsFlowPane;
    @FXML
    private StackPane playerControlsStackPane;
    @FXML
    private VBox localPlayerVbox;
    @FXML
    private Label localPlayerNameLbl;
    @FXML
    private Label localPlayerImageLbl;
    @FXML
    private Label localPlayerCardNumberLbl;
    @FXML
    private FlowPane suitColorSelectionFlowPane;
    @FXML
    private Label yellowCardLbl;
    @FXML
    private Label redCardLbl;
    @FXML
    private Label blueCardLbl;
    @FXML
    private Label greenCardLbl;
    @FXML
    private HBox passTurnHbox;
    @FXML
    private Button passTurnBtn;
    @FXML
    private HBox controlsHBox;
    @FXML
    private Button sayUnoBtn;
    @FXML
    private Button rulesBtn;
    @FXML
    private Button interfaceBtn;
    @FXML
    private Button keybindingsBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button saveGameBtn;
    @FXML
    private Button loadGameBtn;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button returnBtn;

    public void initialize() {
        settingsBtnVbox.toFront();
        playerControlsStackPane.toFront();
        initializeBtns();
        hideSuitColorSelection();
        setRootAnchorPaneKeybind();
    }

    public void initializeBtns() {
        setSayUnoBtnHandler();
        setPassTurnBtnHandler();
        setSuitColorSelectionHandler();
        setQuitBtnHandler();
        setSettingsBtnHandler();
        setMainMenuBtnHandler();
        setNewGameBtnHandler();
        setReturnBtnHandler();
        setRulesBtnHandler();
        disableBtn(sayUnoBtn);
        disableBtn(passTurnBtn);

    }

    //Disables a button and sets the opacity to 0.5 (less visible)
    public void disableBtn(Button button) {
        button.setDisable(true);
        button.setStyle("-fx-opacity: 0.5;");
    }

    //Enables a button and sets the opacity to 1 (more visible)
    public void enableBtn(Button button) {
        button.setDisable(false);
        button.setStyle("-fx-opacity: 1;");
    }

    // Set the game manager
    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    // Set the game state and update the game area view to reflect the current state of the game
    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        updateAll();
    }

    // Set the scene manager
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // Get the scene manager (not currently used)
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    // Set the client
    public void setClient(Client client) {
        this.client = client;
    }

    // Play click 1
    public void playClick1() {
        click1.play();
    }

    // Play click 2
    public void playClick2() {
        click2.play();
    }

    // Play error 1
    public void playError1() {
        error1.play();
    }

    // Play confirm 1
    public void playConfirm1() {
        confirm1.play();
    }

    // Play background music
    public void playBGMusic() {
        BGMusic.play();
    }

    /*
        This method calls all the methods which read the client's game state and displays images
        and information about the game's state.
     */
    public void updateAll() {
        updateDiscardPileImage(); // show an image of the discard pile card
        updateLocalPlayerCards(); // show the local player's cards
        updateLocalPlayer(); // show the local player name/portrait/number of cards
        updateLocalPlayerCardHandler(); // add event handler to the local player's cards
        updateOpponentPlayers(); // show the opponent player names/portraits/number of cards
        updatePlayDirection(); // show the current play direction (forward, reverse)
        highlightCurrentPlayer(); // highlight the current player
        highlightCurrentSuitColor(); // highlight the current suit (color around the discard pile)
        updateDrawPileCardHandler(); // add event handler to the draw pile card (for drawing cards)
        showUnoBtn(); // enable the uno button (if the player has 1 card and hasn't said UNO, it will enable the button)
    }

    /*
        This method is used by the Client class to update the game area view.

        When the client receives a message from the server (as a GameEvent object), the client will:
            - check the type of the event object
            - get the information from the event object
            - use the ClientUnoGameManager to update the client's game
            - pass the event to this method so that the proper part of the UI can be updated to reflect the changes

        This method can, and might also be, used to display information to the console, among other things.
     */
    public void updateGameView(GameEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                GameEventType type = event.getType();

                switch (type) {
                    case GameEventType.CARD_DRAWN, GameEventType.TURN_PASSED, GameEventType.CARD_PLAYED,
                         GameEventType.ANNOUNCE_WINNER, GameEventType.APPLY_PENALTY, GameEventType.SUIT_CHANGED:
                        updateAll();
                        break;
                    case GameEventType.NAME_CHANGED:
                        updateLocalPlayerCards();
                        updateOpponentPlayers();
                        break;
                    case GameEventType.IMAGE_CHANGED:
                        updateLocalPlayer();
                        updateOpponentPlayers();
                        break;
                    case GameEventType.SAID_UNO:
                        System.out.println("Controller updating SAY UNO");
                        break;
                    case GameEventType.NO_OP:
                        NoOpEvent noOpEvent = (NoOpEvent) event;
                        switch (noOpEvent.getEventType()) {
                            case NoOpEventType.INVALID_CARD, NoOpEventType.INVALID_TURN:
                                //playError1();
                                System.out.println("Game Area Controller received NoOpEvent. Not playing Error1");
                                break;
                            case NoOpEventType.INVALID_CALL_UNO:
                                //playError1();
                                break;
                        }
                        break;
                    default:
                        System.out.println("Controller: UNKNOWN EVENT OCCURRED");
                        break;
                }
            }
        });
    }

    //This method gets the last played card and displays it's image for the discard pile
    public void updateDiscardPileImage() {
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();

        Image image = imageManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileBtn.setGraphic(imageView);
    }

    // This method shows the image for each card in the local player's cards
    public void updateLocalPlayerCards() {
        UnoPlayer localPlayer = gameState.getLocalPlayer(); // get the local player
        int numberOfCards = localPlayer.getPlayerHand().size(); // get the number of cards
        playerCardsFlowPane.getChildren().clear(); // clear the container that stores the buttons for the cards
        for (int i = 0; i < numberOfCards; i++) {
            Button cardBtn = new Button(""); // create a button
            UnoCardImageManager imageManager = gameState.getCardImageManager(); // get the image manager
            UnoCard card = localPlayer.getPlayerHand().get(i); // get the card from the player's hand

            Image cardImage = imageManager.getImage(card); // get the image of that card
            ImageView imageView = new ImageView(cardImage); // create ImageView object

            imageView.setFitHeight(60); // adjust the height
            imageView.setFitWidth(60); // adjust the width

            cardBtn.setGraphic(imageView); // set the image for the button to the image of the card
            cardBtn.setUserData(card); // set the user data of this button object to the card object

            /*
                If the local player is not the current player, then all the cards will be dim (opacity 0.5)
                If the local player is the current player, then the cards won't be dim (opacity 1)
                This method also allows the user to hover over the cards to view full opacity
             */
            if (!(localPlayer.equals(gameState.getCurrentPlayer()))) {
                cardBtn.setStyle("-fx-opacity: 0.5;");
                cardBtn.setOnMouseEntered(e -> cardBtn.setStyle("-fx-opacity: 1;"));
                cardBtn.setOnMouseExited(e -> cardBtn.setStyle("-fx-opacity: 0.5;"));
            }

            playerCardsFlowPane.getChildren().add(cardBtn); // add the button object to the container
        }
    }

    // This method gets the local player and sets the portrait image, name, and number of cards
    public void updateLocalPlayer() {
        UnoPlayer localPlayer = gameState.getLocalPlayer(); // get the local player
        Image playerImage = gameState.getPlayerImageManager().getImage(localPlayer.getImage()); // get the player's image
        ImageView playerImageView = new ImageView(playerImage); // create an ImageView object

        playerImageView.setFitHeight(60); // adjust the image height
        playerImageView.setFitWidth(60); // adjust the image width

        localPlayerImageLbl.setUserData(localPlayer); // set the user data of the image label to the local player object
        localPlayerImageLbl.setGraphic(playerImageView); // set the local player image label to the player's portrait image
        localPlayerNameLbl.setText(localPlayer.getName()); // set the local player name label to the player's name

        // Set the text of the local player card number label to the number of cards the player has
        localPlayerCardNumberLbl.setText(String.valueOf(localPlayer.getPlayerHand().size()));

        // If the player has 1 card, change the color of the text to green, otherwise, make it white.
        if (localPlayer.getPlayerHand().size() == 1) {
            localPlayerCardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: mediumspringgreen;");
        } else {
            localPlayerCardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: antiquewhite;");
        }
    }

    // This method gets the opponent players and sets the portrait image, name, and number of cards
    public void updateOpponentPlayers() {
        /*
            Get a list of all the players in the game
            Filter the list to exclude the local player
         */
        var opponentPlayers = gameState.getPlayers()
                .stream()
                .filter(player -> !player.equals(gameState.getLocalPlayer()))
                .toList();

        opponentPlayerFlowPane.getChildren().clear(); // clear the container

        // Loop through the list of opponent players
        for (UnoPlayer player : opponentPlayers) {
            VBox opponentBox = new VBox(); // create a container box for the player's information
            opponentBox.setAlignment(Pos.CENTER); // center the children

            /*
                The opponent player images have an opacity of 0.5 if it is not the current player. This is
                to make it clear that it is not their turn.

                But the user can mouse over the individual opponent players for clarity
             */
            if (!player.equals(gameState.getCurrentPlayer())) {
                opponentBox.setOnMouseEntered(e -> opponentBox.setStyle("-fx-opacity: 1;"));
            }

            // This will re-highlight the current player when mouse exited
            opponentBox.setOnMouseExited(e -> highlightOpponentPlayer());

            Label cardNumberLbl = new Label(); // create a label for the player's card number
            int playerIndex = gameState.getPlayers().indexOf(player); // get the player's index
            int totalCardsRemaining = gameState.getPlayerIndexToCardNumber().get(playerIndex); // get the player's card number
            cardNumberLbl.setText(String.valueOf(totalCardsRemaining)); // set the card number label to the player's card number

            /*
                If the player has 1 card remaining, this will make the color of text yellow
                Otherwise, the color is white
             */
            if (totalCardsRemaining == 1) {
                cardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: yellow;");
            } else {
                cardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: antiquewhite;");
            }

            Label playerImageLbl = new Label(); // create a label for the player's image
            Label playerNameLbl = new Label(player.getName()); // create a label for the player's name

            Image playerImage = gameState.getPlayerImageManager().getImage(player.getImage()); // get the image of the player
            ImageView playerImageView = new ImageView(playerImage); // create an ImageView
            playerNameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: antiquewhite;"); // style the player's name label

            playerImageView.setFitHeight(60); // adjust the image height
            playerImageView.setFitWidth(60); // adjust the image width

            playerImageLbl.setGraphic(playerImageView); // set the image to the player name label
            playerImageLbl.setPrefWidth(60); // adjust the prefWidth for the image label

            opponentBox.setUserData(player); // set the user data for the player's container box to the player
            opponentBox.getChildren().addAll(cardNumberLbl, playerImageLbl, playerNameLbl); // add the card number, image, and name to the container
            opponentPlayerFlowPane.getChildren().add(opponentBox); // add the player's container to the container for all opponents

            setOpponentPlayerCallUnoHandler(); // set the event handler for clicking the player's images (for calling UNO)
        }
    }

    // This method will rotate the image of the play direction (forward, reverse)
    public void updatePlayDirection() {
        PlayDirection direction = gameState.getPlayDirection();
        if (direction == PlayDirection.REVERSE) {
            playDirectionLbl.setRotate(180); // 180 rotation shows a reverse arrow
        } else {
            playDirectionLbl.setRotate(0); // 0 rotation shows a forward arrow
        }
    }

    // This method calls the highlight methods to highlight the current player
    public void highlightCurrentPlayer() {
        highlightLocalPlayer(); // highlights the local player if the local player is current player
        highlightOpponentPlayer(); // highlights the opponent player if the opponent player is the current player
    }

    /*
        This method highlights the local player if the local player is the current player

        This is separate from the highlightOpponentPlayer method because the local player UI elements
        are not part of the opponent player container
     */
    public void highlightLocalPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer(); // get the current player
        UnoPlayer localPlayer = gameState.getLocalPlayer(); // get the local player

        if (currentPlayer.equals(localPlayer)) { // check if the current player is the local player
            localPlayerVbox.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                    "-fx-opacity: 1"); // if true, add white background highlight and change opacity to 1
        } else {
            localPlayerVbox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);");
        }
    }

    // This method highlights the opponent player if the opponent player is the current player
    public void highlightOpponentPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer(); // get the current player

        // Loop through the list of player containers in the opponent container flow pane
        for (var box : opponentPlayerFlowPane.getChildren()) {

            // The user data of the player container box was set that player object
            if (box.getUserData().equals(currentPlayer)) { // check if the player is equal to the current player
                box.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                        "-fx-opacity: 1"); // if true, add white background highlight and change opacity to 1
            } else {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);" +
                        "-fx-opacity: 0.5"); // if false, remove the white background, change opacity to 0.5
            }
        }
    }

    //This method gets the current suit color and adds a border for that color to the discard pile image
    public void highlightCurrentSuitColor() {
        UnoSuit suit = gameState.getCurrentSuit(); // get the current suit
        String color = suit.getColor(); // get the color of the suit
        String style = String.format("-fx-effect: dropshadow(three-pass-box, %s, 20, 0.5, 0, 0);", color); // create a String of the style with the color
        discardPileBtn.setStyle(style); // add the style to the discard pile button
    }

    /*
    public void highlightPlayableCards() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var playableCards = localPlayer.getPlayableCards(gameState);

        if (localPlayer.equals(currentPlayer)) {
            playerCardsFlowPane.getChildren().forEach(cardBtn -> {

                UnoCard card = (UnoCard) cardBtn.getUserData();

                if (playableCards.contains(card)) {
                    cardBtn.setStyle("-fx-opacity: 1;");
                } else {
                    cardBtn.setStyle("-fx-opacity: 0.5;");
                }
            });
        }
    }
     */

    // This method will enable the UNO button for the player to click if the player has 1 card and hasn't said UNO
    public void showUnoBtn() {
        UnoPlayer localPlayer = gameState.getLocalPlayer(); // get the local player
        if (localPlayer.getPlayerHand().size() == 1 && !(localPlayer.getSayUno())) { // check if cards = 1 and they didn't say UNO
            playConfirm1(); // play confirm 1 sound effect
            enableBtn(sayUnoBtn); // enable the button
        } else {
            disableBtn(sayUnoBtn); // disable the button if the total cards is not 1 and/or the player has said UNO
        }
    }

    /*
        (incomplete)
        This method will announce the winner and prompt the user to take an action
        This method was used for testing purposes, but it is not currently being used
        More comments will be added when this method is reworked
     */
    public void announceWinner(UnoPlayer player) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, player.getName() + " has won!\n\nClick 'Ok' to begin a New Game.", ButtonType.OK);
                alert.setHeaderText("");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                }
            }
        });
    }

    // This method will show the cards for selecting a new color after a wild card has been played by the local player
    public void showSuitColorSelection() {
        suitColorSelectionFlowPane.setManaged(true); // set managed to true
        suitColorSelectionFlowPane.setVisible(true); // set the visibility to true
        suitColorSelectionFlowPane.toFront(); // bring the container to the front so they can be clicked
    }

    // This method hides the suit color selection cards (Previously, it was setting managed to false as well, but does not do so now)
    public void hideSuitColorSelection() {
        suitColorSelectionFlowPane.setVisible(false);
    }

    // This method sets the event handlers to the suit color cards when selecting a new color after a wild card is played
    public void setSuitColorSelectionHandler() {
        // Loop through the four suit cards (red, green yellow, blue)
        suitColorSelectionFlowPane.getChildren().forEach(suitLbl -> {
            String suitString = (String) suitLbl.getUserData(); // get the name of the color as a String from the label's user data
            UnoSuit suit = UnoSuit.valueOf(suitString); // convert the string to an UnoSuit enum

            suitLbl.setOnMouseClicked(e -> {
                gameManager.changeSuitColor(suit); // use the client game manager to change the suit color that the user selects
                hideSuitColorSelection(); // hide the suit color selection cards when the user selected a new color
            });
        });
    }

    // This method sets the event handlers for the local player's cards
    public void updateLocalPlayerCardHandler() {

        var playableCards = gameState.getPlayableCards(); // get the playable cards from the game state

        // Loop through the player card container that stores the card buttons
        playerCardsFlowPane.getChildren().forEach(cardBtn -> {
            UnoCard card = (UnoCard) cardBtn.getUserData(); // get the card object from the card button user data
            // Get the card index of that card from the player's hand of cards
            int cardIndex = gameManager.getLocalPlayer().getPlayerHand().indexOf(card);

            // Check if the current card is contained in the list of playable cards.
            if (playableCards.contains(card)) {
                cardBtn.setOnMouseClicked(e -> { // if the card exists in the playable cards, set on mouse clicked
                    disableBtn(passTurnBtn); // reset (disable) the pass turn button
                    drawCard = false; // set draw card to false because the user is playing this card

                    gameManager.playCard(cardIndex); // use the game manager to play the card if the user clicks this card object

                    // Check if the card that was played is a wild card
                    if (card.getSuit() == UnoSuit.WILD) {
                        gameManager.changeSuitColor(UnoSuit.WILD); // change the current suit to wild (so any card can be played)
                        showSuitColorSelection(); // show the suit color selection so the user can select a new color
                    } else {
                        hideSuitColorSelection(); // hide the suit color selection (this is probably not needed)
                    }
                });

            } else {
                // If the card doesn't exist in the playable cards, play error message when clicked
                cardBtn.setOnMouseClicked(e -> playError1());
            }

        });
    }

    // This method sets the event handler for the draw pile button
    public void updateDrawPileCardHandler() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer(); // get the current player
        UnoPlayer localPlayer = gameState.getLocalPlayer(); // get the local player

        // Set on mouse clicked event for user to draw a card
        drawPileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (currentPlayer.equals(localPlayer) && (!drawCard)) { // if the local player is current player and draw card is false
                    gameManager.drawCard(); // use game manager to draw the card
                    enableBtn(passTurnBtn); // enable the pass turn button incase the player needs to pass
                    drawCard = true; // set draw card to true so that a card can't be drawn again
                } else {
                    // If it isn't the player's turn or they already drew a card, play an error message
                    playError1();
                }
            }
        });
    }


    // This method sets the event handler for the pass turn button
    public void setPassTurnBtnHandler() {
        passTurnBtn.setOnMouseClicked(e -> {
            gameManager.passTurn(); // use game manager to pass the turn
            disableBtn(passTurnBtn); // disable the pass turn button
            drawCard = false; // set the draw card flag to false so that the player can draw card again on next turn
        });
    }

    // This method sets the event handler for the say UNO button
    public void setSayUnoBtnHandler() {
        sayUnoBtn.setOnMouseClicked(e -> {
            gameManager.sayUno(); // use game manager to say uno for the local player
            disableBtn(sayUnoBtn); // disable the say uno button
        });
    }

    // This method sets the event handler for the settings button
    public void setSettingsBtnHandler() {
        settingsBtn.setOnMouseClicked(e -> {
            settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible())); // show the settings pane
            settingsOuterStackPane.toFront(); // bring the pane to the front
        });
    }

    // This method will set the event handler for the rules button in the settings menu
    public void setRulesBtnHandler() {
    }

    // This method sets keybindings for the settings button
    public void setRootAnchorPaneKeybind() {
        // The root anchor pane is the one that sets the keybind
        rootAnchorPane.setOnKeyPressed(e -> {
            KeyCode code = e.getCode(); // get the keycode that the user presses
            if (code == KeyCode.ESCAPE) { // if the keycode is the escape key
                settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible())); // show the settings menu pane
                settingsOuterStackPane.toFront(); // bring the settings menu pane to the front
                /*
                if (rulesInnerStackPane.isVisible()) {
                    rulesInnerStackPane.setVisible(false);
                    rulesInnerStackPane.toFront();
                } else {
                    settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
                    settingsOuterStackPane.toFront();
                }
                 */
            } else if (code == KeyCode.DIGIT1) { // if the key pressed is the number 1
                gameManager.sayUno(); // say uno for the player
                disableBtn(sayUnoBtn); // disable the say uno button
            } else if (code == KeyCode.DIGIT2) { // if the key pressed is the number 2
                gameManager.passTurn(); // pass the turn
                disableBtn(passTurnBtn); // disable the pass turn button
            }
        });
    }

    // This method uses the client object to close down the server and client threads
    public void quit() {
        client.getServer().shutDown();
        System.out.println("From Game Area Controller: Server shutting down"); // print shut down message
        System.out.flush();
        Platform.exit(); // exit the JavaFX application
    }

    // This method sets the event handler for the quit button
    public void setQuitBtnHandler() {
        exitBtn.setOnMouseClicked(se -> {
            quit(); // call the quit method
        });
    }

    // This method sets the event handler for the main menu button
    public void setMainMenuBtnHandler() {
        mainMenuBtn.setOnMouseClicked(e -> {
            client.getServer().shutDown(); // shut down the server and client threads
            System.out.println("From Game Area Controller: Server shutting down"); // print shut down message
            System.out.flush();

            sceneManager.switchScene("gameSelection"); // switch scenes to the game selection view
            sceneManager.removeScene("gameArea"); // remove this game area scene from the scene manager
        });
    }

    // This method sets the event handler for the new game button
    public void setNewGameBtnHandler() {
        newGameBtn.setOnMouseClicked(e -> {
            client.getServer().shutDown(); // shut down the server and client threads
            System.out.println("From Game Area Controller: Server shutting down"); // print shut down message
            System.out.flush();

            sceneManager.switchScene("offline"); // switch scenes to the offline selection view
            sceneManager.removeScene("gameArea"); // remove this game area scene from the scene manager
        });
    }

    // This method sets the event handler for the return button, which returns to the game
    public void setReturnBtnHandler() {
        returnBtn.setOnMouseClicked(e -> {
            // Toggles the settings menu pane
            settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
            settingsOuterStackPane.toFront();
        });
    }

    // This method sets the event handler for the user to click the opponent player images, which calls UNO on that player.
    public void setOpponentPlayerCallUnoHandler() {
        opponentPlayerFlowPane.getChildren().forEach(opponentBox -> {
            UnoPlayer opponentPlayer = (UnoPlayer) opponentBox.getUserData(); // get the player from the player container that is clicked
            opponentBox.setOnMouseClicked(e -> { // set the on mouse clicked for that player container
                int playerIndex = gameState.getPlayerIndex(opponentPlayer); // get the player index of the player
                gameManager.callUno(playerIndex); // use game manager to call UNO on that player
            });
        });
    }
}