package nazarrod.adventgem.advgem.levelPlayer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.view.GraphicController;

public class GameWindow{
    private Stage stage;
    private Canvas canvas = null;
    private GraphicsContext graphicsContext = null;
    private GraphicController graphicsController = null;
    private GameData gameData = null;

    public GameWindow(Stage stage, GameData gameData) {
        this.stage = stage;
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsController = new GraphicController(stage,canvas,graphicsContext,gameData);
    }

    public void start(){
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);
        graphicsController.drawLevel();
        stage.setTitle(gameData.getLevelName());
        stage.setScene(new Scene(gridPane));
        stage.centerOnScreen();
        stage.show();
    }
}
