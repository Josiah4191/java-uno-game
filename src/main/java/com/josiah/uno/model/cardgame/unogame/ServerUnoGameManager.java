package com.josiah.uno.model.cardgame.unogame;

import com.josiah.uno.model.cardgame.card.unocard.UnoCard;
import com.josiah.uno.model.cardgame.card.unocard.UnoCardTheme;
import com.josiah.uno.model.cardgame.card.unocard.UnoEdition;
import com.josiah.uno.model.cardgame.card.unocard.UnoSuit;
import com.josiah.uno.model.image.playerimage.PlayerImage;
import com.josiah.uno.model.player.cardplayer.unoplayer.UnoHumanPlayer;
import com.josiah.uno.model.player.cardplayer.unoplayer.UnoPlayer;
import com.josiah.uno.model.player.cardplayer.unoplayer.UnoPlayerAI;
import com.josiah.uno.multiplayer.server.servermessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    The ServerUnoGameManager is the class that handles all the gameplay logic. It owns the UnoGameState and has methods
    for running the game.

    The Server class owns and uses the ServerUnoGameManager to control the game. The true state of the game is owned
    by this game manager class. The actual changes and control of the game happens in this class. The ClientUnoGameManager
    only sends messages to the Server and makes requests, and then the Server will use this ServerUnoGameManager to actually
    try to make changes. Then the Server will send a message back to the client informing them what happened and if any
    updates need to take place on their end, so that the game state on the client side can mirror the server's game state.

    Instance Variables:
        - UnoGameState
            - The UnoGameState holds all the information about the game
        - GameEventListener
            - The GameAreaListener is a reference to the Server. The Server object listens to events that occur in this game manager,
            like when a message needs to be sent to a client, the game manager can use the Server here to send a message to the client.
 */

public class ServerUnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameEventListener gameEventListener;
    private Thread turnCycleThread;

    public UnoGameState getGameState() {
        return gameState;
    }

    // Get the game state
    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    // Set the game event listener
    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    /*
        This method sets up the game. The arguments are needed in order to start the game, and these are what the
        user will select in a menu. When the user selects all the settings and begins the game, a message is sent
        from their ClientUnoGameManager to the Server, and the Server will read that message, get all the
        setup information (e.g, edition, theme ...) and then the Server will use its ServerUnoGameManager to call
        this method.
     */
    public void setupGame(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents, int playerID) {
        // set the game settings
        gameState.setTheme(theme);
        gameState.setEdition(edition);
        gameState.setDifficulty(difficulty);
        gameState.getCardMachine().createMachine(gameState.getEdition());

        // get local players and ai players
        var localPlayers = gameState.getPlayerIdToPlayer().values();
        var aiPlayers = createAIPlayer(numberOfOpponents);

        // add the local players and ai players to the game
        gameState.getPlayers().addAll(localPlayers);
        gameState.getPlayers().addAll(aiPlayers);

        // draw a card from the draw pile and add it to the discard pile
        UnoCard lastPlayedCard = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToDiscardPile(lastPlayedCard);

        // set and get the current suit
        gameState.setCurrentSuit(lastPlayedCard.getSuit());
        UnoSuit currentSuit = gameState.getCurrentSuit();

        // deal 7 cards to each player
        dealCards(7, gameState.getPlayers());

        // get the local player's cards
        UnoPlayer localPlayer = gameState.getPlayerFromPlayerID(playerID);
        List<UnoCard> localPlayerCards = localPlayer.getPlayerHand();

        // get the list of all players
        var players = gameState.getPlayers();

        // play the first card that is added to discard pile
        playFirstCard(lastPlayedCard);

        // start the turn cycle
        continueTurnCycle();

        // send the setup game event to the clients
        SetupGameEvent setupGameEvent = new SetupGameEvent(theme, edition, difficulty, currentSuit, lastPlayedCard, players, localPlayerCards);
        gameEventListener.sendEventMessageToAll(setupGameEvent);
    }

    /*
        This method adds a local (client/human) player to the game.
     */
    public void addLocalPlayer(String playerName, int playerID, PlayerImage playerImage) {
        UnoHumanPlayer player = new UnoHumanPlayer(playerID); // create human player
        player.setImage(playerImage); // set player image
        player.setName(playerName); // set player name

        // add the player to the map. the map is used to map each player to their ID
        gameState.getPlayerIdToPlayer().put(playerID, player);

        // send add local player event to clients that the player was added
        AddLocalPlayerEvent addLocalPlayerEvent = new AddLocalPlayerEvent(player);
        gameEventListener.sendEventMessage(addLocalPlayerEvent, playerID);
    }

    /*
        This method is used for debugging. It prints information to the console about the deck,
        player cards, draw pile, and discard pile. It makes sure that there are always 108 cards and that
        cards are transferred to the correct piles.
     */
    public void printDeckInformation() {
        int deckSize = gameState.getDeck().size(); // get number of cards in deck
        int discardPileSize = gameState.getDiscardPile().size(); // get number of cards in discard pile
        int drawPileSize = gameState.getDrawPile().size(); // get number of cards in draw pile
        int totalPileSize = deckSize + discardPileSize + drawPileSize; // get the total number of cards in all piles

        // print card pile sizes to console
        System.out.println();
        System.out.println("Total number of cards in deck: " + deckSize);
        System.out.println("Total number of cards in draw pile: " + drawPileSize);
        System.out.println("Total number of cards in discard pile: " + discardPileSize);

        // loop through all the players in the game
        for (UnoPlayer player : gameState.getPlayers()) {
            int totalCards = player.getPlayerHand().size(); // get number of cards in the player's hand
            // print the player name and their total number of cards in hand
            System.out.println("Player: " + player.getName() + " | " + "Total number of cards: " + totalCards);
            // add the draw pile, discard pile, deck cards, and all the player cards together
            totalPileSize += totalCards;
        }
        // print total number of cards in all the piles and the player's hand
        // there should always be 108 cards total
        System.out.println("Total number of cards: " + totalPileSize);
        System.out.println();
    }

    /*
        This method draws a card from the draw pile for a player. It is used for all players, including humans and
        AI players.

        It receives a player index from the list of player.
     */
    public void playerDrawCardFromDrawPile(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the UnoPlayer object from the player index
        // the UnoCardMachine to draw card from draw pile and return the UnoCard
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        // the UnoModerator validates the card to see if the drawn card is playable
        boolean cardIsPlayable = gameState.getModerator().validateCard(gameState, card);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex(); // get the current player index

        addCardToPlayer(playerIndex, card); // add the drawn card to the player

        // check if the card is playable
        if (cardIsPlayable) { // if playable
            gameState.setPlayableCards(List.of(card)); // reset the playable cards and add this card
        } else { // if not playable
            gameState.setPlayableCards(List.of()); // reset playable cards and do not add this card
        }

        // check if the UnoPlayer is AI
        if (player.isAI()) { // if the player is AI
            currentPlayerIndex = moveToNextPlayer(1); // move the current player index to the next player
        }

        int totalCardsRemaining = player.getTotalCardsRemaining(); // get total cards remaining

        var playableCards = gameState.getPlayableCards(); // get the playable cards to send to client
        // send a SetPlayableCardEvent message to the clients to update them what the playable cards are
        if (!(player.isAI())) {
            gameEventListener.sendEventMessage(new SetPlayableCardEvent(playableCards), player.getPlayerID());
        }

        // send a CardDrawnEvent message to the client to tell them that a card was drawn, the player that drew the card,
        // how many cards that player now has, and what the new current player index is
        CardDrawnEvent cardDrawnEvent = new CardDrawnEvent(playerIndex, card, totalCardsRemaining, currentPlayerIndex);
        gameEventListener.sendEventMessageToAll(cardDrawnEvent);
        /*
        the sayUno method will send a SaidUnoEvent message to the client to tell them that a player needs to set their
        sayUno boolean status to false. this is being sent because they drew a card, so if they happened to have 1
        card remaining, they must now have more than 1 card remaining, and they would have to sayUno again if they
        are reduced to 1 card.
         */
        sayUno(playerIndex, false);
    }

    /*
        The callUno method is called when a player calls UNO for someone that has 1 card remaining. This method
        receives a player index to check call UNO against the player at that index in the list of players. If
        it turns out that the player being checked did not say UNO and has 1 card remaining, the applyPenalty() method
        will add 2 cards to that player and send an apply penalty event message to the clients for them to update
        that player's card information. A say uno event is also sent so that the player's say UNO status is set to false.

        If it turns out that the player being checked did say UNO and has 1 card remaining, or that they just don't have 1
        card remaining, a NoOpEvent (no operation) will be sent to the other clients that it was invalid, and nothing
        happened.
     */
    public void callUno(int playerIndex) {
        // the UnoModerator checks call uno against the player, which returns true or false
        boolean checkCallUno = gameState.getModerator().checkCallUno(gameState, playerIndex);

        // check if a penalty should be applied
        if (checkCallUno) { // if true
            applyPenalty(playerIndex, 2); // apply the penalty to player and send event message
            sayUno(playerIndex, false); // send say uno event message
        } else { // if false
            // send NoOpEvent message that the UNO call was invalid
            gameEventListener.sendEventMessageToAll(new NoOpEvent(NoOpEventType.INVALID_CALL_UNO));
        }
    }

    /*
        This method is used for a player to play a card. It receives a player index and the card index for which card
        the player selected from their hand of cards
     */
    public void playCard(int playerIndex, int cardIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the UnoPlayer from the list of players using index
        // check to make sure that the player is actually the current player before playing the card
        if (!(playerIndex == gameState.getCurrentPlayerIndex())) { // if the player is not the current player
            // if player is not current player, send NoOpEvent because the turn is invalid.
            // this message is sent only to the player that tries to play, and does not get sent to all clients
            gameEventListener.sendEventMessage(new NoOpEvent(NoOpEventType.INVALID_TURN), player.getPlayerID());
            return;
        }

        UnoCard card = player.getCard(cardIndex); // get the UnoCard from the player's hand of cards using the card index
        var playableCards = gameState.getPlayableCards(); // get the list of playable cards from the game state

        // check if the card the player wants to play is in the list of playable cards
        if (playableCards.contains(card)) { // if it is in the list of playable cards
            UnoCard lastPlayedCard = player.playCard(cardIndex); // use the player object to play the card, which returns the UnoCard
            gameState.setLastPlayedCard(lastPlayedCard); // update the last played card
            int currentPlayerIndex = processCardPlayed(card); // process the card and update the current player index
            UnoSuit currentSuit = gameState.getCurrentSuit(); // update the current suit
            int totalCardsRemaining = player.getTotalCardsRemaining(); // get the total cards remaining for the player
            // get the play direction (forward/reverse) to send to client (in case it was changed in the processCardPlayed method)
            PlayDirection playDirection = gameState.getPlayDirection();

            // send CardPlayedEvent message to the clients for them to update the discard pile, current player, the player who played the card,
            // how many cards that player has remaining, the new current suit, and the new play direction if any changes have been made
            CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(playerIndex, cardIndex, currentPlayerIndex, totalCardsRemaining, lastPlayedCard, currentSuit, playDirection);
            gameEventListener.sendEventMessageToAll(cardPlayedEvent);
        } else { // if the card is not in the list of playable cards, the player can't play the card
            // send NoOpEvent message to client that the card isn't playable
            gameEventListener.sendEventMessageToAll(new NoOpEvent(NoOpEventType.INVALID_CARD));
        }

        // check if the player is AI
        if (!(player.isAI())) { // if the UnoPlayer is not AI
            // check if the card that was drawn is a Wild card
            if (!(card.getSuit() == UnoSuit.WILD)) { // if it is not a wild card
                continueTurnCycle(); // continue the turn cycle
            }
        }

        // check if the player has won the game
        if (checkWinner(player)) { // if the player now has 0 cards remaining
            // send AnnounceWinnerEvent message to all clients
            gameEventListener.sendEventMessageToAll(new AnnounceWinnerEvent(playerIndex));
        }
    }

    /*
        This method is used for the AI to play a card.
     */
    public void aiPlayCard(UnoPlayerAI player) {
        aiCallUno(); // the AI players will call UNO on all the local (human/client) players at the start of their turn
        UnoCard card = player.selectCard(); // use the UnoPlayerAI to select a card from their hand
        int cardIndex = player.getPlayerHand().indexOf(card); // get the card index of their selected card
        int playerIndex = gameState.getPlayerIndex(player); // get the player index for the UnoPlayerAI

        // check to see if the selected card is null. If it's null, then they didn't have a playable card to select
        if (card == null) { // if null (they don't have a playable card)
            playerDrawCardFromDrawPile(playerIndex); // make that AI player draw a card from the draw pile
        } else { // if the card is not null
            playCard(playerIndex, cardIndex); // play the card for that AI player
        }
    }

    /*
        This method is used to reset the say UNO boolean for a player to true or false. If a player says UNO,
        then this method will update their say UNO status. Then it will send a SaidUnoEvent message the clients
        for them to update that player's say UNO status.
     */
    public void sayUno(int playerIndex, boolean sayUno) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the UnoPlayer from the player index
        player.sayUno(sayUno); // update the player's sayUno status
        boolean newSayUno = player.getSayUno(); // get the updated sayUno status from the player

        // send SaidUnoEvent message to all clients
        SaidUnoEvent saidUnoEvent = new SaidUnoEvent(playerIndex, newSayUno);
        gameEventListener.sendEventMessageToAll(saidUnoEvent);
    }

    /*
        This method is used when a player passes their turn. It's called when a player clicks the
        pass turn button after they drew a card but either the card is not playable, or the player
        chose not to play the card. A TurnPassedEvent message is sent to the clients for them to update
        the current player index.
     */
    public void passTurn() {
        int currentPlayerIndex = moveToNextPlayer(1); // move to the next player index
        // send TurnPassedEvent message
        TurnPassedEvent turnPassedEvent = new TurnPassedEvent(currentPlayerIndex);
        gameEventListener.sendEventMessageToAll(turnPassedEvent);
        continueTurnCycle(); // continue the turn cycle
    }

    /*
        This method updates the player's image. It receives the player index for the player to update and
        the image to update to. It sends an ImageChangedEvent to the clients for them to update that player's image
     */
    public void updatePlayerImage(int playerIndex, PlayerImage image) {
        gameState.getPlayer(playerIndex).setImage(image); // get the UnoPlayer and set their image

        // send ImageChangedEvent message
        ImageChangedEvent imageChangedEvent = new ImageChangedEvent(playerIndex, image);
        gameEventListener.sendEventMessageToAll(imageChangedEvent);
    }

    /*
    This method updates the player's name. It receives the player index for the player to update and
    the name to update to. It sends an NameChangedEvent to the clients for them to update that player's name
 */
    public void updatePlayerName(int playerIndex, String name) {
        gameState.getPlayer(playerIndex).setName(name); // get the UnoPlayer and set their name

        // send NameChangedEvent message
        NameChangedEvent nameChangedEvent = new NameChangedEvent(playerIndex, name);
        gameEventListener.sendEventMessageToAll(nameChangedEvent);
    }

    /*
        This method will apply a penalty to a player, which will draw cards from the draw pile and add
        those cards to the player's hand. It receives a player index to apply the penalty to the player at that index,
        and the cardPenalty is the number of cards that the player should have to draw.
     */
    public void applyPenalty(int playerIndex, int cardPenalty) {
        /*
            create an empty ArrayList to hold the drawn UnoCard objects
            the client needs to know which cards were drawn if the client (local player) is the one that is drawing
            the cards. otherwise, the client will update the player's total number of cards, but doesn't need to know
            about the actual UnoCard objects.
         */
        List<UnoCard> cardsDrawn = new ArrayList<>();

        // create for loop for the number of card penalties
        for (int i = 0; i < cardPenalty; i++) {
            UnoCard card = gameState.getCardMachine().drawCardFromDrawPile(); // draw a card from the draw pile
            addCardToPlayer(playerIndex, card); // add that UnoCard to the player's hand of cards
            cardsDrawn.add(card); // add the cards drawn to the list
        }

        // get the updated total number of cards remaining from the player
        int totalCardsRemaining = gameState.getPlayer(playerIndex).getPlayerHand().size();

        // send ApplyPenaltyEvent message to the clients for them to update that player's cards
        ApplyPenaltyEvent applyPenaltyEvent = new ApplyPenaltyEvent(playerIndex, cardsDrawn, totalCardsRemaining);
        gameEventListener.sendEventMessageToAll(applyPenaltyEvent);
    }

    /*
        This method changes the current suit and then sends a SuitChangedEvent message to the clients for them
        to update their game state. It receives an UnoSuit enum type for an argument.

        This method is called after a player has played a Wild card, because the player needs to select a new suit,
        and then the suit will be changed.
     */

    public void changeSuit(UnoSuit suit) {
        gameState.setCurrentSuit(suit); // set the suit
        // send SuitChangedEvent message to clients
        SuitChangedEvent suitChangedEvent = new SuitChangedEvent(suit);
        gameEventListener.sendEventMessageToAll(suitChangedEvent);

        /*
            check if the new suit is Wild
            - if the suit is wild, then the turn cycle should not continue, because a new suit will need to be chosen
            - if the suit is not wild, then the turn cycle should continue
         */
        if (!(suit == UnoSuit.WILD)) { // if the suit is NOT Wild
            continueTurnCycle(); // continue the turn cycle
        }
    }

    /*
        This method is for the AI players to call UNO on human players
     */
    public void aiCallUno() {
        var humanPlayers = getLocalPlayers(); // get a list of all the human/local players
        // loop through the list of human players
        for (UnoPlayer player : humanPlayers) {
            int playerIndex = gameState.getPlayerIndex(player); // get the player index for that player
            callUno(playerIndex); // call UNO on that player using their index
        }
    }

    /*
        This method is used to update the playable cards. At the start of every turn, the playable cards are updated
        and based on the current player's hand of cards. The UnoModerator is used to determine which cards in that player's
        hand of cards is valid to play, and then those cards are stored in the playableCards list.
     */
    public void updatePlayableCards(UnoPlayer player) {
        List<UnoCard> cards = player.getPlayerHand(); // get the player's cards
        List<UnoCard> playableCards = new ArrayList<>(); // create empty playableCards list

        /*
            loop through each card in the player's list of cards, and for each card, check if that card is valid
            using the UnoModerator. if it is valid, then add that card to the playableCards list
         */
        cards.forEach(card -> {
            boolean valid = gameState.getModerator().validateCard(gameState, card); // validate the card
            if (valid) { // if the card is valid
                playableCards.add(card); // add the card to the playableCards list
            }
        });

        gameState.setPlayableCards(playableCards); // update the playable cards
    }

    /*
        This method continues the turn cycle. It is used to keep the game moving to the next player.
     */
    public void continueTurnCycle() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer(); // get the current player
        updatePlayableCards(currentPlayer); // update the playable cards for that player

        // check if the current player is an AI player
        if (currentPlayer.isAI()) { // if the current player is AI
            // start a new thread for the AI player to take a turn
            turnCycleThread = new Thread(new Runnable() {
                public void run() {

                    try {
                        Thread.sleep(4000); // make this thread sleep for 4 seconds to simulate the AI thinking
                    } catch (InterruptedException e) {
                        // handle the exception and print a statement to console when this thread is interrupted
                        // this thread will be interrupted when the game shuts down, so this should print to the console
                        System.out.println("From Server Uno GameManager: Closing thread " + turnCycleThread.getName());
                        System.out.flush();
                    }

                    UnoPlayerAI player = (UnoPlayerAI) currentPlayer; // cast the UnoPlayer to UnoPlayerAI

                    // call listener interface method to send to server
                    // called aiCardPlayed(GameEvent event)
                    aiPlayCard(player); // play the card for the AI player
                    continueTurnCycle(); // continue the turn cycle
                }
            }, "[Continue Turn Cycle Thread]"); // name of the thread
            turnCycleThread.start(); // start the thread
        } else { // if the current layer is NOT AI
            var playableCards = gameState.getPlayableCards(); // get the playable cards
            /*
                send the playable cards for the current player to the client using their player ID
                if they are the current player
             */
            gameEventListener.sendEventMessage(new SetPlayableCardEvent(playableCards), currentPlayer.getPlayerID());
        }
    }

    /*
        This method is used to interrupt the turnCycleThread. It is used when shutting down the program. This way,
        the thread for the AI player won't continue running in the background when the program shuts down. It is called
        in the shutDown() method inside the Server class.
     */
    public void cancelTurnCycle() {
        turnCycleThread.interrupt();
    }

    /*
        This method creates AI players for the game and adds them to the list of players. It receives an integer
        for the number of AI players that should be created. It also returns the list of aiPlayers
     */
    private List<UnoPlayer> createAIPlayer(int numberOfPlayers) {
        List<UnoPlayer> aiPlayers = new ArrayList<>(); // create empty list of UnoPlayers

        // create a loop for the number of players to be added
        for (int i = 0; i < numberOfPlayers; i++) {
            UnoPlayerAI player = new UnoPlayerAI(gameState); // create a new UnoPlayerAI
            gameState.addPlayer(player); // add the player to the list of players
        }

        return aiPlayers; // return the list of aiPlayers
    }

    /*
        This method deals cards to all the players in the list of players. It receives an integer for the number of
        cards that each player should be dealt. The UnoCardMachine is used to deal the cards and the cards are drawn
        from the draw pile and added to the player's hand of cards.
     */
    private void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.getCardMachine().dealCards(numberOfCards, players);
    }

    /*
        This method adds a card to the player's hand. It receives a player index for which player should have a card
        added, and it receives the UnoCard that should be added to their list of cards.
     */
    private void addCardToPlayer(int playerIndex, UnoCard card) {
        var player = gameState.getPlayer(playerIndex); // get the player using the player index
        player.addCard(card); // add the card to the player's hand of cards
    }

    /*
        This method adds a card to the discard pile. It receives an UnoCard which should be added to the list of cards
        in the discard pile.
     */
    private void addCardToDiscardPile(UnoCard card) {
        gameState.getCardMachine().addCardToDiscardPile(card); // use UnoCardMachine to add the card to the discard pile
        gameState.setLastPlayedCard(card); // update the last played card

        // check if the card is a Wild card
        if (!(card.getSuit() == UnoSuit.WILD)) { // if the card is not a Wild card
            gameState.setCurrentSuit(card.getSuit()); // update the current suit to the suit of the last played card
        }

    }

    /*
        This method is used to validate a card. This method still uses the UnoModerator to do the validation, but
        it isn't currently being used because the UnoModerator is being used directly.
     */
    private boolean validateCard(UnoCard card) {
        UnoModerator moderator = gameState.getModerator();
        return moderator.validateCard(gameState, card);
    }

    /*
        This method checks if a player has won the game. It receives an UnoPlayer and then checks to see if
        their list of cards is empty.
     */
    private boolean checkWinner(UnoPlayer player) {
        boolean win = false; // create a boolean flag for the win condition

        // check if the player list of cards is empty
        if (player.getPlayerHand().isEmpty()) { // if it is empty
            win = true; // set win to true
        }

        return win; // return the win condition
    }

    /*
        This method is supposed to apply the first played card at the start of the game to the first player, if it
        is an action card. This method has not been implemented yet.
     */
    public void playFirstCard(UnoCard card) {
    }

    /*
        This method processes a card that has been validated and played. It receives an UnoCard object.
        This method returns an integer which represents the new current player index.

        - First, the card is added to the discard pile.
        - Second, this method will check to see who the next player is.
        - Third, this method switches on the UnoCard object's value (e.g. number, action, skip, draw two)

        each case statement takes the appropriate action based on the value of the card that was played.
            e.g.
                - UnoValue.REVERSE - reverse the play direction
                - UnoValue.SKIP - skips the next player
                - UnoValue.DRAW_TWO - apply penalty to the next player and skip them
     */
    private int processCardPlayed(UnoCard card) {
        addCardToDiscardPile(card); // add the card to the discard pile

        int nextPlayerIndex = getNextPlayerIndex(1); // get the next player index

        // switch on the value of the UnoCard
        switch (card.getValue()) {
            case REVERSE: // if the value is REVERSE
                reversePlayDirection(); // reverse the play direction
                int numberOfPlayers = gameState.getPlayers().size(); // get the number of players in the game

                // check if the number of players is equal to 2 players
                if (numberOfPlayers == 2) { // if it is equal to 2 players
                    return moveToNextPlayer(2); // skip the next player and return the new player index
                } else { // if it is greater than 2 players
                    return moveToNextPlayer(1); // move to the next player and return the new player index
                }
            case SKIP: // if the value is SKIP
                return moveToNextPlayer(2); // skip the next player and return the new player index
            case DRAW_TWO: // if the value is DRAW TWO
                applyPenalty(nextPlayerIndex, 2); // apply a penalty to the next player index
                return moveToNextPlayer(2); // skip the next player and return the new player index
            case WILD_DRAW_FOUR:
                applyPenalty(nextPlayerIndex, 4); // apply a penalty to the next player index
                return moveToNextPlayer(2); // skip the next player and return the new player index
            case DRAW_TW0_STACK:
                gameState.addStackPenalty(2); // update the stack penalty (for different rule sets)
                return moveToNextPlayer(1); // move to next player and return the new player index
            case WILD_DRAW_FOUR_STACK:
                gameState.addStackPenalty(4); // update the stack penalty (for different rule sets)
                return moveToNextPlayer(1); // move to next player and return the new player index
            default:
                return moveToNextPlayer(1); // move to the next player and return the new player index
        }
    }

    /*
        This method filters through the list of players and returns a list of only the AI players.
     */
    private List<UnoPlayer> getAIPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(UnoPlayer::isAI)
                .toList();
    }

    /*
        This method filters through the list of players and returns a list of only the local/human players
     */
    private List<UnoPlayer> getLocalPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(player -> !(player.isAI()))
                .toList();
    }

    /*
        This method reverses the play direction (forward/reverse)
     */
    private void reversePlayDirection() {
        PlayDirection direction = gameState.getPlayDirection(); // get the current play direction
        // set the direction equal to the opposite of the current play direction
        direction = (direction == PlayDirection.FORWARD) ? PlayDirection.REVERSE : PlayDirection.FORWARD;
        gameState.setPlayDirection(direction); // update the play direction
    }

    /*
        This method is used to get the next player index
     */
    private int getNextPlayerIndex(int numberToSkipAhead) {
        int currentPlayerIndex = gameState.getCurrentPlayerIndex(); // get the current player index
        int numberOfPlayers = gameState.getPlayers().size(); // get the total number of players

        // check what the direction of play is
        if (gameState.getPlayDirection().isForward()) { // if the direction is forward
            currentPlayerIndex += numberToSkipAhead; // increment the current player index
        } else { // if the direction is reverse
            currentPlayerIndex -= numberToSkipAhead; // decrement the current player index
        }

        /*
            The mod operator is used here to prevent the new current player index from going off the end of the
            list of players. If the current player index were to increment or decrement unevenly or incremented too much,
            then the player index would exceed (or be outside) the bounds of the list of players.

            The mod operator prevents this from happening by wrapping it around based on the number of players.

            For example, if there are 5 players, and the current player index is 4, that means it is player 5's turn.
            If we need to skip to the next player, without the mod operator, it will put the current player index at 5,
            which can throw an index out of bounds exception.

            But if you take 5 (the new current player index) % 5 (the number of players), it equals 0, which would be
            the next player index if you were to wrap around to the front of the list. This also works for any
            number of players. If the current player is index 4, and there are 5 players, and you need to skip the next
            player, then the current player index will be updated to 6, and 6 (new player index) % 5 (total number of players)
            is 1, which would be the next player index because index 0 is skipped.

            The if/else statement checks if the current player index is positive or negative, because it works slightly
            different for negative numbers. If the number is negative, you have to add the number of players to the
            current player index to make it positive, or else the index won't be turn out positive.
         */
        // check if the current player index is greater than 0
        if (currentPlayerIndex >= 0) { // if the current player index is positive
            currentPlayerIndex = currentPlayerIndex % numberOfPlayers; // use mod operator to get player index
        } else { // if the current player index is negative
            // add the number of players to the current player index before using mod operator
            currentPlayerIndex = (currentPlayerIndex + numberOfPlayers) % numberOfPlayers;
        }

        return currentPlayerIndex; // return the new current player index
    }

    /*
        This method is used to move the current player index to the next player. It receives a number for how many
        players it should skip. The number 1 will move to the next player. The number 2 will skip the next player and
        set the player index to the player after them.
     */
    private int moveToNextPlayer(int numberToSkipAhead) {
        int nextPlayerIndex = getNextPlayerIndex(numberToSkipAhead); // get the next player index
        gameState.setCurrentPlayerIndex(nextPlayerIndex); // update the current player to the new player index
        return nextPlayerIndex; // return the updated current player index
    }

    /*
        This method is used to get the actual UnoPlayer object for the next player
     */
    private UnoPlayer getNextPlayer() {
        int nextPlayerPosition = getNextPlayerIndex(1); // get the next player position
        return gameState.getPlayer(nextPlayerPosition); // get the player using the player index and return the UnoPlayer
    }

    /*
        This method is used to randomly select the first player in the UNO game
     */
    private void selectFirstPlayer() {
        Random random = new Random(); // create Random object for randomly generating a number
        var players = gameState.getPlayers(); // get the list of players
        UnoPlayer player = players.get(random.nextInt(players.size())); // use Random object to select an index from the list of players
        int index = players.indexOf(player); // get the index of the player that was selected
        gameState.setCurrentPlayerIndex(index); // update the current player index
    }

}
