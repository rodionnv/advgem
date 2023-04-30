package nazarrod.adventgem.advgem.editor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import nazarrod.adventgem.advgem.GameData;

public class PlatformSetupDialog extends Dialog<Void> {

    private final TextField xpos = new TextField();
    private final TextField ypos = new TextField();
    private final TextField width = new TextField();
    private final TextField height = new TextField();
    public PlatformSetupDialog(GameData gameData) {

        setTitle("Set platform manually");
        setHeaderText(null);

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        xpos.setText("X");
        xpos.setPrefWidth(160);
        ypos.setText("Y");
        ypos.setPrefWidth(160);
        width.setText("300");
        width.setPrefWidth(160);
        height.setText("40");
        height.setPrefWidth(160);


        gridPane.add(new Label("X"), 0, 0);
        gridPane.add(xpos, 0, 1);
        gridPane.add(new Label("Y"), 1, 0);
        gridPane.add(ypos, 1, 1);
        gridPane.add(new Label("Width"), 2, 0);
        gridPane.add(width, 2, 1);
        gridPane.add(new Label("Height"), 3, 0);
        gridPane.add(height, 3, 1);

        dialogPane.setContent(gridPane);

        Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> {
            gameData.addPlatform(parseIntDefault(xpos.getText(),0),
                    parseIntDefault(ypos.getText(),0),
                    parseIntDefault(width.getText(),300),
                    parseIntDefault(height.getText(),40));
            event.consume();
        });

        Platform.runLater(() -> xpos.requestFocus());
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
