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

        setTitle("Tower Defense Setup");
        setHeaderText(null); // if this text is set, the dialog looks really ugly!

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        levelName.setText(String.valueOf(gameData.getLevelname()));
        levelName.setPrefWidth(160);
        playgroundWidth.setText(String.valueOf(gameData.getPlaygroundWidth()));
        playgroundWidth.setPrefWidth(160);
        playgroundHeight.setText(String.valueOf(gameData.getPlaygroundHeight()));
        playgroundHeight.setPrefWidth(160);

        grid.add(new Label("Level Name"), 0, 0);
        grid.add(levelName, 1, 0);
        grid.add(new Label("Playground Width"), 0, 1);
        grid.add(playgroundWidth, 1, 1);
        grid.add(new Label("Playground Height"), 0, 2);
        grid.add(playgroundHeight, 1, 2);

        dialogPane.setContent(grid);

        Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> {
            okButtonPressed(gameData);
            event.consume();
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playgroundWidth.requestFocus();
            }
        });
    }

    private void okButtonPressed(GameData gameData) {
        gameData.setLevelname(levelName.getText());
        gameData.setPlaygroundWidth(parseUnsignedIntDefault(playgroundWidth.getText(), gameData.getPlaygroundWidth()));
        gameData.setPlaygroundHeight(parseUnsignedIntDefault(playgroundHeight.getText(), gameData.getPlaygroundHeight()));
    }

    private int parseUnsignedIntDefault(String str, int defaultValue) {
        int result;
        try {
            result = Integer.parseUnsignedInt(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }
}
