package view.controllers;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.views.*;

public class MainController {

    // game manager
    private UnoGameManager gameManager = new UnoGameManager(UnoEdition.CLASSIC, Difficulty.EASY);

    // game state
    private UnoGameState gameState = gameManager.getGameState();

    // controllers
    private MenuController menuController = new MenuController(gameManager, gameState);
    private OpponentsController opponentsController = new OpponentsController(gameManager, gameState);
    private GameAreaController gameAreaController = new GameAreaController(gameManager, gameState);
    private ControlsController controlsController = new ControlsController(gameManager, gameState);

    // constructor
    public MainController() {
        initialize();
    }

    public void initialize() {
        gameManager.initialize();
        menuController.getMenuView().getNewGameButton().setOnAction(e -> gameAreaController.initialize());
    }

    // run
    public void run(Stage primaryStage) {
        MainView mainView = new MainView(
                menuController.getMenuView(),
                opponentsController.getOpponentsView(),
                gameAreaController.getGameAreaView(),
                controlsController.getControlsView());
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNO");
        primaryStage.show();
    }


}

/*
    // this method belongs to main controller
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

    // this method belongs to main controller but might also need to use game controller methods for updating
    // this method is for computer players
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


    // this method is for human players (main player)
    // it belongs in main controller but might need to use game controller methods for updating
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

        /*
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
         */