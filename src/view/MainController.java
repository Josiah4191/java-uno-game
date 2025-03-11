package view;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.PlayDirection;
import games.cardgames.unogame.UnoGameManager;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainController {
    private MenuView menuView = new MenuView();
    private OpponentsView opponentsView = new OpponentsView();
    private GameAreaView gameAreaView = new GameAreaView();
    private ControlsView controlsView = new ControlsView();
    private MainView mainView = new MainView(menuView, opponentsView, gameAreaView, controlsView);


    UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);


    public MainController(Stage stage) {
        // Set up scene and stage
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("UNO JavaFX UI - Dark Mode");
        stage.show();

        menuView.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                var gameState = manager.getGameState();
                manager.startNewGame();
                gameAreaView.displayPlayerCardBox(gameState);
                gameAreaView.displayDrawPileImage(gameState);
                gameAreaView.displayDiscardPileImage(gameState);
                gameAreaView.setCurrentPlayer(gameState);
                controlsView.setPlayerName(gameState);
                opponentsView.setOpponentNames(gameState);
                opponentsView.setPlayDirection(gameState);
                menuView.getNewGameButton().setDisable(true);
                setActionsToButtons();
            }
        });
    }

    public void setActionsToButtons() {
        var labels = gameAreaView.getPlayerCardsBox().getChildren();
        for (var label: labels) {
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard)label.getUserData();
                    int currentPlayerIndex = manager.getCurrentPlayerIndex();
                    var players = manager.getPlayers();

                    if (currentPlayerIndex == 0) {
                        playCard(card, (Label) label);
                        manager.moveToNextPlayer();
                    } else {
                        computerPlayCard();
                    }

                }
            });
        }
    }

    public void computerPlayCard() {
        var service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(new Runnable() {
            public void run() {

                if (manager.getCurrentPlayerIndex() == 0) {
                    service.shutdown();
                }

                playCard();
                manager.moveToNextPlayer();
            }
        }, 5, 5, TimeUnit.SECONDS);

    }

    public void playCard() {
        var players = manager.getPlayers()
                .stream()
                .filter(e -> e != manager.getPlayer(0))
                .toList();
        for (var player: players) {
            int index = players.indexOf(player);
            manager.playCard(index, 0);
        }
    }

    public void playCard(UnoCard card, Label label) {
        // get the card that they clicked
        // get players
        var players = manager.getPlayers();
        var player = manager.getCurrentPlayer();
        var playerIndex = players.indexOf(player);
        var cards = player.getPlayerHand();
        var cardIndex = cards.indexOf(card);
        UnoCard card1 = manager.playCard(playerIndex, cardIndex);
        gameAreaView.displayDiscardPileImage(manager.getGameState());
        gameAreaView.getPlayerCardsBox().getChildren().remove(label);
    }















}

