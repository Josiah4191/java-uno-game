package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.unogame.PlayDirection;
import model.database.SimpleUnoDatabase;
import model.images.cardimages.UnoCardClassicImages;
import model.images.cardimages.UnoCardImageManager;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.GameAreaView;

import java.util.Optional;

public class GameAreaController implements GameAreaListener {

    private GameAreaView gameAreaView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public GameAreaController(UnoGameManager gameManager, GameAreaView gameAreaView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.gameAreaView = gameAreaView;
        gameManager.setGameAreaListener(this);
    }

    public void updateGameArea() {
        setDiscardPileImage();
        setDrawPileImage();
        setPlayerCardImage();
        setPlayerCardHandler();
        setPlayDirectionLabel();
        setMainPlayer();
        setOpponentPlayers();
        highlightCurrentPlayerImage();
        setDrawPileCardHandler();
        highlightPlayableCards();
        setPassBtnHandler();
        highlightCurrentSuitColor();
        setSuitColorSelectionHandler();
        setCallUnoHandler();
        setSayUnoBtnHandler();
        showUnoBtn();
        displayCardInformation();
        setMenuBtnHandler();
        setNewGameBtnHandler();
        setSaveGameBtnHandler();
        setLoadGameBtnHandler();
        System.out.println();
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public void announceWinner(UnoPlayer player) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, player.getName() + " has won!\n\nClick 'Ok' to begin a New Game.", ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    gameManager.resetGame();
                }
            }
        });
    }

    public void setDrawPileImage() {
        Label drawPileLbl = gameAreaView.getDrawPileLbl();
        UnoCardImageManager imageManager = gameState.getCardImageManager();

        Image image = imageManager.getImage(UnoCardClassicImages.DECK);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        drawPileLbl.setGraphic(imageView);
    }

    public void highlightCurrentSuitColor() {
        Label discardPileLbl = gameAreaView.getDiscardPileLbl();
        UnoSuit suit = gameState.getCurrentSuit();
        if (!(suit == UnoSuit.WILD)) {
            String color = suit.name();
            String style = String.format("-fx-border-color: %s; -fx-border-width: 3px; -fx-border-radius: 5px;", color);
            discardPileLbl.setStyle(style);
        }
    }

    public void displayCardInformation() {
        var players = gameState.getPlayers();
        int drawPile = gameState.getDrawPile().size();
        int discardPile = gameState.getDiscardPile().size();
        int totalCards = drawPile + discardPile;

        System.out.println("Draw Pile: " + drawPile);
        System.out.println("Discard Pile: " + discardPile);

        for (UnoPlayer player: players) {
            totalCards += player.getPlayerHand().size();
        }

        players.forEach(p -> System.out.println(p.getName() + ": " + p.getPlayerHand().size() + " cards."));

        System.out.println("Total Number of Cards: " + totalCards);
    }

    public void setDiscardPileImage() {
        Label discardPileLbl = gameAreaView.getDiscardPileLbl();
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();

        Image image = imageManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileLbl.setGraphic(imageView);
    }

    public void setPlayerCardImage() {
        UnoPlayer player = gameState.getMainPlayer();
        int numberOfCards = player.getPlayerHand().size();
        gameAreaView.getPlayerCardsBox().getChildren().clear();
        for (int i = 0; i < numberOfCards; i++) {
            Label cardPlaceholder = new Label("");
            UnoCardImageManager imageManager = gameState.getCardImageManager();
            UnoCard card = player.getPlayerHand().get(i);

            Image cardImage = imageManager.getImage(card);
            ImageView imageView = new ImageView(cardImage);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardPlaceholder.setGraphic(imageView);
            cardPlaceholder.setUserData(card);
            gameAreaView.getPlayerCardsBox().getChildren().add(cardPlaceholder);
        }
    }

    public void setDrawPileCardHandler() {
        Label drawPileLbl = getGameAreaView().getDrawPileLbl();
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        currentPlayer.setPassTurn(true);

        drawPileLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if (currentPlayer.equals(mainPlayer)) {
                    if (currentPlayer.isPassTurn()) {
                        showPassBtn();
                        boolean playable = gameManager.playerDrawCard(currentPlayerIndex);

                        setDiscardPileImage();
                        setDrawPileImage();
                        setPlayerCardImage();
                        setMainPlayer();

                        if (playable) {
                            highlightDrawCard();
                            setPlayableDrawCardHandler();
                            showUnoBtn();
                        }

                        currentPlayer.setPassTurn(false);
                    }
                }
            }
        });
    }

    public void setPassBtnHandler() {
        Button passBtn = gameAreaView.getPassBtn();
        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                gameManager.moveToNextPlayer();
                gameManager.runAITurn();
                updateGameArea();
                hidePassBtn();
            }
        });
    }

    public void hidePassBtn() {
        gameAreaView.getPassBtn().setVisible(false);
    }

    public void showPassBtn() {
        gameAreaView.getPassBtn().setVisible(true);
    }

    public void updateGameAreaView() {
        Platform.runLater(this::updateGameArea);
    }

    public void showSuitColorSelection() {
        gameAreaView.getSuitColorSelectionBox().setVisible(true);
    }

    public void hideSuitColorSelection() {
        gameAreaView.getSuitColorSelectionBox().setVisible(false);
    }

    public void setSayUnoBtnHandler() {
        Button unoBtn = gameAreaView.getUnoBtn();

        unoBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                gameState.getMainPlayer().setSayUno(true);
                hideUnoBtn();
            }
        });
    }

    public void showUnoBtn() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        if (mainPlayer.getPlayerHand().size() == 1 && !(mainPlayer.getSayUno())) {
            gameAreaView.getUnoBtn().setVisible(true);
        } else if (mainPlayer.getPlayerHand().size() > 1) {
            mainPlayer.setSayUno(false);
            hideUnoBtn();
        }
    }

    public void hideUnoBtn() {
        gameAreaView.getUnoBtn().setVisible(false);
    }

    public void setSuitColorSelectionHandler() {
        gameAreaView.getRedLbl().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                gameState.setCurrentSuit(UnoSuit.RED);
                hideSuitColorSelection();
                gameManager.moveToNextPlayer();
                gameManager.runAITurn();
                updateGameArea();
            }
        });

        gameAreaView.getGreenLbl().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                gameState.setCurrentSuit(UnoSuit.GREEN);
                hideSuitColorSelection();
                gameManager.runAITurn();
                updateGameArea();
            }
        });

        gameAreaView.getBlueLbl().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                gameState.setCurrentSuit(UnoSuit.BLUE);
                hideSuitColorSelection();
                gameManager.runAITurn();
                updateGameArea();
            }
        });
        gameAreaView.getYellowLbl().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                gameState.setCurrentSuit(UnoSuit.YELLOW);
                hideSuitColorSelection();
                gameManager.runAITurn();
                updateGameArea();
            }
        });
    }

    public void setPlayableDrawCardHandler() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        int lastDrawCardIndex = mainPlayer.getPlayerHand().indexOf(lastDrawCard);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        var cardLbls = gameAreaView.getPlayerCardsBox().getChildren();

        for (var label : cardLbls) {
            UnoCard card = (UnoCard) label.getUserData();
            if (card.equals(lastDrawCard)) {
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        boolean valid = gameManager.playCard(currentPlayerIndex, lastDrawCardIndex);
                        hidePassBtn();
                        updateGameArea();

                        if (valid) {
                            if (card.getSuit() == UnoSuit.WILD) {
                                showSuitColorSelection();
                            } else {
                                gameManager.runAITurn();
                            }
                        }
                    }
                });
            }
        }
    }

    public void setMenuBtnHandler() {
        Button menuBtn = gameAreaView.getMenuBtn();
        menuBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                VBox menuBtnBox = gameAreaView.getMenuBtnBox();
                menuBtnBox.setVisible(menuBtnBox.isVisible() ? false : true);
            }
        });
    }

    public void setPlayerCardHandler() {
        var playerCardLbls = getGameAreaView().getPlayerCardsBox().getChildren();
        for (var label : playerCardLbls) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard) label.getUserData();
                    UnoPlayer mainPlayer = gameState.getMainPlayer();
                    UnoPlayer currentPlayer = gameState.getCurrentPlayer();
                    int currentPlayerIndex = gameState.getPlayers().indexOf(currentPlayer);
                    int cardIndex = currentPlayer.getPlayerHand().indexOf(card);

                    if (currentPlayer.equals(mainPlayer)) {

                        gameAreaView.getUnoBtn().setVisible(true);

                        boolean valid = gameManager.playCard(currentPlayerIndex, cardIndex);
                        updateGameArea();

                        if (valid) {

                            if (mainPlayer.getPlayerHand().isEmpty()) {
                                announceWinner(mainPlayer);
                                return;
                            }

                            if (card.getSuit() == UnoSuit.WILD) {
                                showSuitColorSelection();
                            } else {
                                gameManager.runAITurn();
                            }

                            updateGameArea();

                        }
                    }
                }
            });
        }
    }

    public void setCallUnoHandler() {
        var opponentPlayers = gameAreaView.getOpponentPlayerBox();
        for (var opponent : opponentPlayers.getChildren()) {
            UnoPlayer player = (UnoPlayer) opponent.getUserData();

            opponent.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    boolean checkCallUno = gameManager.checkCallUno(player);
                    if (checkCallUno) {
                        int playerIndex = gameState.getPlayers().indexOf(player);
                        gameManager.applyPenalty(playerIndex, 2);
                        updateGameArea();
                    }
                }
            });
        }
    }

    public void highlightDrawCard() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        var cardLbls = gameAreaView.getPlayerCardsBox().getChildren();

        if (currentPlayer.equals(mainPlayer)) {

            for (var label : cardLbls) {
                if (lastDrawCard.equals(label.getUserData())) {
                    label.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                }
            }
        }
    }

    public void highlightPlayableCards() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var playableCards = mainPlayer.getPlayableCards(gameState);
        var cardLabels = gameAreaView.getPlayerCardsBox().getChildren();

        if (currentPlayer.equals(mainPlayer)) {
            for (UnoCard card : playableCards) {
                for (var label : cardLabels) {
                    if (card.equals(label.getUserData())) {
                        label.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                    }
                }
            }
        }
    }

    public void highlightCurrentPlayerImage() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer mainPlayer = gameState.getMainPlayer();

        if (currentPlayer.equals(mainPlayer)) {
            gameAreaView.getMainPlayerBox().setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        } else {
            gameAreaView.getMainPlayerBox().setStyle("");
        }

        if (!(currentPlayer.equals(mainPlayer))) {

            for (var box : gameAreaView.getOpponentPlayerBox().getChildren()) {

                if (box.getUserData().equals(currentPlayer)) {
                    box.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                } else {
                    box.setStyle("");
                }
            }
        }
    }

    public void setMainPlayer() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        Label mainPlayerNameLbl = gameAreaView.getMainPlayerNameLbl();
        Label mainPlayerImageLbl = gameAreaView.getMainPlayerImageLbl();

        ImageView playerImageView = new ImageView(mainPlayer.getImage());

        playerImageView.setFitHeight(60);
        playerImageView.setFitWidth(60);

        mainPlayerImageLbl.setUserData(mainPlayer);
        mainPlayerImageLbl.setGraphic(playerImageView);

        mainPlayerNameLbl.setText(mainPlayer.getName() + " | " + mainPlayer.getPlayerHand().size());
        mainPlayerNameLbl.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white;");
    }

    public void setOpponentPlayers() {
        var opponentPlayers = gameState.getPlayers().stream()
                .filter((player) -> !(player.equals(gameState.getMainPlayer())))
                .toList();

        gameAreaView.getOpponentPlayerBox().getChildren().clear();
        for (UnoPlayer player : opponentPlayers) {
            VBox playerBox = new VBox();
            playerBox.setAlignment(Pos.CENTER);

            Label playerImage = new Label();
            Label playerNameLbl = new Label(player.getName() + " | " + player.getPlayerHand().size());
            ImageView playerImageView = new ImageView(player.getImage());

            playerNameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

            playerImageView.setFitHeight(60);
            playerImageView.setFitWidth(60);

            playerImage.setGraphic(playerImageView);

            playerBox.setUserData(player);
            playerBox.getChildren().addAll(playerImage, playerNameLbl);
            gameAreaView.getOpponentPlayerBox().getChildren().add(playerBox);
        }
    }

    private void setPlayDirectionLabel() {
        PlayDirection direction = gameState.getDirection();
        gameAreaView.getPlayDirectionLbl().setText(direction.toString());
    }

    public GameAreaView getGameAreaView() {
        return gameAreaView;
    }

    public void setNewGameBtnHandler() {
        Button newGameBtn = gameAreaView.getNewGameBtn();

        newGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                gameManager.resetGame();

            }
        });
    }

    public void setSaveGameBtnHandler() {
        Button saveGameBtn = gameAreaView.getSaveGameBtn();

        saveGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                SimpleUnoDatabase.saveGame(gameState);
            }
        });

    }

    public void setLoadGameBtnHandler() {

        Button loadGameBtn = gameAreaView.getLoadGameBtn();

        loadGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                GameAreaController.this.gameState = SimpleUnoDatabase.loadGame();
                updateGameArea();
            }
        });
    }

}
