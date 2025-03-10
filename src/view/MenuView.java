package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class MenuView {
    private VBox menuBox;

    public MenuView() {
        menuBox = new VBox(10);
        menuBox.setPadding(new Insets(5));

        Button newGameButton = new Button("New Game");
        Button saveGameButton = new Button("Save Game");
        Button loadGameButton = new Button("Load Game");
        Button rulesButton = new Button("Rules");
        Button scoreHistoryButton = new Button("Score History");

        VBox buttonContainer = new VBox(5, newGameButton, saveGameButton, loadGameButton, rulesButton, scoreHistoryButton);
        TitledPane menuPane = new TitledPane("Menu", buttonContainer);
        menuPane.setExpanded(true);

        menuBox.getChildren().add(menuPane);
    }

    public VBox getView() {
        return menuBox;
    }
}


