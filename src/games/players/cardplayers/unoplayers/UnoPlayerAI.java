package games.players.cardplayers.unoplayers;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.unogame.UnoGameState;
import games.cardgames.unogame.UnoModerator;
import games.cardgames.unogame.UnoRules;
import games.players.cardplayers.unoplayers.unobrains.UnoBrain;
import games.players.cardplayers.unoplayers.unobrains.UnoBrainFactory;

import java.util.ArrayList;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends UnoPlayer. This class represents a computer player in an UNO game.
*/

public class UnoPlayerAI extends UnoPlayer {
    /*
    The UnoBrain object processes the gameState information and makes a decision for which card to play
     */
    private UnoBrain brain;
    private UnoGameState gameState;

    /*
    The UnoPlayerAI constructor receives an UnoGameState object.
    The UnoGameState object owns the information about the state of the game.
    An AI player has an UnoBrain object.
    The createBrain method is called.
    The AI player will need information about the game to process and make a decision.
     */
    public UnoPlayerAI(UnoGameState gameState) {
        this.gameState = gameState;
        createBrain();
    }

    /*
    The playCard() method returns an UnoCard.
    The playCard(int index) method is defined in CardPlayer.
    The brain analyze() method returns an integer to select which card to return.
     */
    @Override
    public UnoCard playCard(int cardIndex) {
        var playableCards = getPlayableCards();
        cardIndex = brain.analyze(gameState, playableCards);
        return super.playCard(cardIndex);
    }

    private List<UnoCard> getPlayableCards() {
        ArrayList<UnoCard> playableCards = new ArrayList<>();
        UnoModerator moderator = gameState.getModerator();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();
        UnoRules rules = gameState.getRules();

        for (var card : getPlayerHand()) {
            if (moderator.validatePlay(rules, card, lastPlayedCard)) {
                playableCards.add(card);
            }
        }

        return playableCards;
    }

    public void createBrain() {
        this.brain = UnoBrainFactory.createBrain(gameState.getDifficulty());
    }
}


