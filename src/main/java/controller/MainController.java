package controller;

import model.cardgames.unogame.UnoGameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.cardgames.unogame.UnoGameState;
import view.MainView;

public class MainController {

    // game manager
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    // main view
    private MainView mainView;

    // controllers
    private MenuController menuController;
    private OpponentsController opponentsController;
    private GameAreaController gameAreaController;
    private PlayerController playerController;

    // constructor
    public MainController(UnoGameManager gameManager, MainView mainView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.mainView = mainView;

        this.menuController = new MenuController(gameManager, mainView.getMenuView());
        this.opponentsController = new OpponentsController(gameManager, mainView.getOpponentsView());
        this.gameAreaController = new GameAreaController(gameManager, mainView.getGameAreaView());
        this.playerController = new PlayerController(gameManager, mainView.getPlayerView());

        initialize();
    }

    public void initialize() {
        gameManager.initialize();
        initializeGameAreaControls();
        initializeMenuControls();
        initializePlayerControls();
        initializeOpponentsControls();
    }

    public void initializePlayerControls() {
        playerController.setPlayerName();
    }

    public void initializeOpponentsControls() {
        opponentsController.setPlayDirection();
    }

    public void initializeMenuControls() {
        menuController.getMenuView().getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                gameAreaController.initialize();
                gameAreaController.clearLogo();
            }
        });
    }

    public void initializeGameAreaControls() {
        gameAreaController.setLogoImage();
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