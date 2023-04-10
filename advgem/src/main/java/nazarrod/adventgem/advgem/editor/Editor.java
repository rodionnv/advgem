package nazarrod.adventgem.advgem.editor;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.utils.LevelManager;

import java.util.List;

public class Editor extends Application {
    /*
    *  Level Editor Class that allows to create simple levels,boss levels and chest(?) levels
    * */
    private final LevelManager levelManager = new LevelManager();
    @Override
    public void start(Stage stage){
        Button simpleLevelButton = new Button();
        simpleLevelButton.setText("Create platformer level");
        simpleLevelButton.setPrefWidth(200);
        simpleLevelButton.setOnAction(actionEvent -> buildPlatformerLevel(stage));


        VBox chooseLevel = new VBox();
        chooseLevel.getChildren().addAll(simpleLevelButton);
        chooseLevel.setSpacing(8);
        chooseLevel.setAlignment(Pos.CENTER);
        stage.setTitle("Create new level");
        stage.setScene(new Scene(chooseLevel,500,500));
        stage.show();
    }

    private void buildPlatformerLevel(Stage stage){
        /*
        * Function builds simple level
        * */
        GameData gameData = new GameData();
        SetupDialog setupDialog = new SetupDialog(gameData);
        setupDialog.showAndWait();

        GridPane gridPane = new GridPane();
        Canvas canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        gridPane.getChildren().add(canvas);
        gridPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        levelManager.createNewLevel(gameData);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
