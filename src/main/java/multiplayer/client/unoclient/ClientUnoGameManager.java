package multiplayer.client.unoclient;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.Difficulty;
import model.cardgame.unogame.PlayDirection;
import model.cardgame.unogame.UnoGameState;
import model.cardgame.unogame.UnoRules;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;

public class ClientUnoGameManager {

    private UnoGameState gameState;

    /*
    private UnoRules rules = new UnoClassicRules();
    private UnoHumanPlayer mainPlayer = new UnoHumanPlayer(1);
    private List<UnoPlayer> players = new ArrayList<>();
    private Map<Integer, Integer> playerIdToIndex = new HashMap<>();
    private UnoModerator moderator = new UnoModerator();
    private PlayDirection direction = PlayDirection.FORWARD;
    private Difficulty difficulty = Difficulty.EASY;
    private UnoSuit currentSuit;
    private UnoEdition edition;
    private transient UnoCardImageManager cardImageManager = new UnoCardImageManager();
    private transient PlayerImageManager playerImageManager= new PlayerImageManager();
    private UnoCardMachine machine = new UnoCardMachine();
    private int currentPlayerIndex;
    private int stackPenalty = 0;
     */

    public ClientUnoGameManager() {
    }

    public void initialize(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public void setLocalPlayer(UnoPlayer player) {
        gameState.setLocalPlayer(player);
    }

    public String changeName(String newName) {
        ChangePlayerName changeName = new ChangePlayerName(newName);
        return changeName.toJson();
    }

    public String changePlayerImage(PlayerImage playerImage) {
        ChangePlayerImage changeImage = new ChangePlayerImage(playerImage);
        return changeImage.toJson();
    }

    public String playCard(int cardIndex) {
        PlayCard playCard = new PlayCard(cardIndex);
        return playCard.toJson();
    }

    public String sayUno() {
        SayUno sayUno = new SayUno(true);
        return sayUno.toJson();
    }

    public String callUno(int playerIndex) {
        CallUno callUno = new CallUno(playerIndex);
        return callUno.toJson();
    }

    public String drawCard() {
        DrawCard drawCard = new DrawCard();
        return drawCard.toJson();
    }

    public String passTurn() {
        PassTurn passTurn = new PassTurn(true);
        return passTurn.toJson();
    }

    public void updateRules(UnoRules rules) {
        gameState.setRules(rules);
    }

    public void updatePlayerCardNumber(int playerIndex, int cardNumber) {
        var map = gameState.getPlayerIndexToCardNumber();
        map.put(playerIndex, cardNumber);
    }

    public void updatePlayerName(int playerIndex, String name) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setName(name);
    }

    public void updatePlayerImage(int playerIndex, PlayerImage playerImage) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setImage(playerImage);
    }

    public void updateCurrentPlayerIndex(int playerIndex) {
        gameState.setCurrentPlayerIndex(playerIndex);
    }

    public void updateNextPlayerIndex(int nextPlayerIndex) {
        gameState.setNextPlayerIndex(nextPlayerIndex);
    }

    public void updateLastPlayedCard(UnoCard card) {
        gameState.setLastPlayedCard(card);
    }

    public void updatePlayDirection(PlayDirection playDirection) {
        gameState.setPlayDirection(playDirection);
    }

    public void updateEdition(UnoEdition edition) {
        gameState.setEdition(edition);
    }

    public void updateCurrentSuit(UnoSuit currentSuit) {
        gameState.setCurrentSuit(currentSuit);
    }

    public void updateDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    public void updateStackPenalty(int stackPenalty) {
        gameState.setStackPenalty(stackPenalty);
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public UnoGameState getGameState() {
        return gameState;
    }




}
