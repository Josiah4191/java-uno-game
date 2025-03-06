package games.cardgames.uno;

import games.Difficulty;
import games.GameState;
import games.cardgames.cardplayers.CardPlayer;
import games.cardgames.uno.unocards.UnoCard;

import java.util.List;

public class UnoGameState extends GameState {

    private List<CardPlayer<UnoCard>> players;
    private Difficulty difficulty;
    private UnoCard lastPlayedCard;


}
