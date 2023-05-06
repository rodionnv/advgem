package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.editor.Editor;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.levelPlayer.ChooseLevelWindow;
import nazarrod.adventgem.advgem.levelPlayer.GameWindow;
import nazarrod.adventgem.advgem.utils.LevelManager;

import java.io.File;
import java.util.List;

public class App extends Application {

    private Stage stage;
    private final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loginWindow();
    }

    private void loginWindow(){
        Button levelButton = new Button();
        levelButton.setText("Levels");
        levelButton.setPrefWidth(200);
        levelButton.setOnAction(actionEvent -> {
            ChooseLevelWindow chooseLevelWindow = new ChooseLevelWindow(stage);
            chooseLevelWindow.start();
        });

        Button existProfileButton = new Button();
        existProfileButton.setText("Create new Level");
        existProfileButton.setPrefWidth(200);
        existProfileButton.setOnAction(actionEvent -> {
            Editor editor = new Editor();
            editor.start(stage);
        });

        VBox loginMenu = new VBox();
        loginMenu.getChildren().addAll(levelButton,existProfileButton);
        loginMenu.setSpacing(8);
        loginMenu.setAlignment(Pos.CENTER);
        stage.setTitle("Adventurous GEM");
        stage.setScene(new Scene(loginMenu,screenWidth*0.25,screenHeight*0.6));
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}