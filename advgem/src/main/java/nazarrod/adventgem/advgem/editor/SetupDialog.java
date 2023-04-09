package nazarrod.adventgem.advgem.editor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import nazarrod.adventgem.advgem.GameData;

public class SetupDialog extends Dialog<Void> {

    private final TextField tfPlaygroundWidth = new TextField();
    private final TextField tfPlaygroundHeight = new TextField();
    public SetupDialog(GameData gameData) {

        setTitle("Tower Defense Setup");
        setHeaderText(null); // if this text is set, the dialog looks really ugly!

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        tfPlaygroundWidth.setText(String.valueOf(gameData.getPlaygroundWidth()));
        tfPlaygroundWidth.setPrefWidth(160);
        tfPlaygroundHeight.setText(String.valueOf(gameData.getPlaygroundHeight()));
        tfPlaygroundHeight.setPrefWidth(160);

        grid.add(new Label("Playground Width"), 0, 0);
        grid.add(tfPlaygroundWidth, 1, 0);
        grid.add(new Label("Playground Height"), 0, 1);
        grid.add(tfPlaygroundHeight, 1, 1);

        dialogPane.setContent(grid);

        Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> {
            okButtonPressed(gameData);
            event.consume();
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfPlaygroundWidth.requestFocus();
            }
        });
    }

    private void okButtonPressed(GameData gameData) {
        gameData.setPlaygroundWidth(parseUnsignedIntDefault(tfPlaygroundWidth.getText(), gameData.getPlaygroundWidth()));
        gameData.setPlaygroundHeight(parseUnsignedIntDefault(tfPlaygroundHeight.getText(), gameData.getPlaygroundHeight()));
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
