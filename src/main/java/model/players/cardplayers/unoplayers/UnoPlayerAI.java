package model.players.cardplayers.unoplayers;

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.unogame.UnoGameState;
import model.cardgames.unogame.UnoModerator;
import model.players.cardplayers.unoplayers.unobrains.UnoBrain;
import model.players.cardplayers.unoplayers.unobrains.UnoBrainFactory;

import java.util.ArrayList;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
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
        if (!playableCards.isEmpty()) {
            System.out.println("Playable cards: " + playableCards.size());
            return brain.analyze(gameState, playableCards);
        } else {
            return null;
        }
    }

    /*
     It might make sense to define this method in game manager, so that it could work for any player's
     hand of cards.
     OR we need to define it inside the moderator class.
     We can pass the method a player's name, and then check their cards against the last played card.
     Then we can return the list of cards that are playable.
     If the player tries to play a card not in the list of playable cards, then we prevent it.
    */
    private List<UnoCard> getPlayableCards() {
        ArrayList<UnoCard> playableCards = new ArrayList<>();
        UnoModerator moderator = gameState.getModerator();

        for (var card : getPlayerHand()) {
            if (moderator.validateCard(gameState, card)) {
                playableCards.add(card);
            }
        }

        return playableCards;
    }

    public void createBrain() {
        this.brain = UnoBrainFactory.createBrain(gameState.getDifficulty());
    }
}


