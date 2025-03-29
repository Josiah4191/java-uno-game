package multiplayer.client.clientmessage;

import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.unogame.Difficulty;

public class SetupGameAction extends GameAction {
    private UnoEdition edition;
    private UnoCardTheme theme;
    private Difficulty difficulty;
    private int numberOfOpponents;

    public SetupGameAction(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents) {
        this.edition = edition;
        this.theme = theme;
        this.difficulty = difficulty;
        this.numberOfOpponents = numberOfOpponents;
        setType(GameActionType.SETUP_GAME);
    }

    public UnoEdition getEdition() {
        return edition;
    }

    public void setEdition(UnoEdition edition) {
        this.edition = edition;
    }

    public UnoCardTheme getTheme() {
        return theme;
    }

    public void setTheme(UnoCardTheme theme) {
        this.theme = theme;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }

    public void setNumberOfOpponents(int numberOfOpponents) {
        this.numberOfOpponents = numberOfOpponents;
    }
}
