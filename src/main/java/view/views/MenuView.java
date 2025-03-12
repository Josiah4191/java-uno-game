package view.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;

public class MenuView {
    private VBox menuBox = new VBox(10);
    private Button newGameButton = new Button("New Game");
    private Button saveGameButton = new Button("Save Game");
    private Button loadGameButton = new Button("Load Game");
    private Button rulesButton = new Button("Rules");
    private Button scoreHistoryButton = new Button("Score History");
    private VBox buttonContainer = new VBox(5, newGameButton, saveGameButton, loadGameButton, rulesButton, scoreHistoryButton);
    private TitledPane menuPane = new TitledPane("Menu", buttonContainer);

    public MenuView() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        menuBox = new VBox(10);
        menuBox.setPadding(new Insets(5));
        menuPane.setExpanded(true);

        menuBox.getChildren().add(spacer);
        menuBox.getChildren().add(menuPane);
    }

    public VBox getView() {
        return menuBox;
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Button getSaveGameButton() {
        return saveGameButton;
    }

    public Button getLoadGameButton() {
        return loadGameButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getScoreHistoryButton() {
        return scoreHistoryButton;
    }



}


