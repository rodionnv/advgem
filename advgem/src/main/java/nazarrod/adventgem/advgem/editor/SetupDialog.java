package nazarrod.adventgem.advgem.editor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import nazarrod.adventgem.advgem.GameData;

public class SetupDialog extends Dialog<Void> {

    private final TextField levelName = new TextField();
    private final TextField playgroundWidth = new TextField();
    private final TextField playgroundHeight = new TextField();
    public SetupDialog(GameData gameData) {

        setTitle("Platformer level setup");
        setHeaderText(null);

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        levelName.setText(String.valueOf(gameData.getLevelName()));
        levelName.setPrefWidth(160);
        playgroundWidth.setText(String.valueOf(gameData.getPlaygroundWidth()));
        playgroundWidth.setPrefWidth(160);
        playgroundHeight.setText(String.valueOf(gameData.getPlaygroundHeight()));
        playgroundHeight.setPrefWidth(160);

        gridPane.add(new Label("Level Name"), 0, 0);
        gridPane.add(levelName, 1, 0);
        gridPane.add(new Label("Playground Width"), 0, 1);
        gridPane.add(playgroundWidth, 1, 1);
        gridPane.add(new Label("Playground Height"), 0, 2);
        gridPane.add(playgroundHeight, 1, 2);

        dialogPane.setContent(gridPane);

        Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> {
            okButtonPressed(gameData);
            event.consume();
        });

        Platform.runLater(levelName::requestFocus);
    }

    private void okButtonPressed(GameData gameData) {
        gameData.setLevelName(levelName.getText());
        gameData.setPlaygroundWidth(parseIntDefault(playgroundWidth.getText(), gameData.getPlaygroundWidth()));
        gameData.setPlaygroundHeight(parseIntDefault(playgroundHeight.getText(), gameData.getPlaygroundHeight()));
    }

    private int parseIntDefault(String str, int defaultValue) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }
}
