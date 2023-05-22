package nazarrod.adventgem.advgem;

import javafx.application.Platform;
import nazarrod.adventgem.advgem.editor.Editor;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.levelPlayer.ChooseLevelWindow;
import nazarrod.adventgem.advgem.utils.LevelManager;


/**
 * Main class of the whole App
 */
public class App extends Application {

    private Stage stage;
    private final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getBounds().getHeight();

    /**
     * Start the app
     * @param stage stage to sen scene on
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loginWindow();
    }


    /**
     * Opens winow with main menu
     */
    private void loginWindow(){

        Button loadButton = new Button();
        loadButton.setText("Load latest save");
        loadButton.setPrefWidth(200);
        if(LevelManager.loadLevel("./Saves/gamedata.dat") == null)loadButton.setDisable(true);
        loadButton.setOnAction(actionEvent -> {
            ChooseLevelWindow chooseLevelWindow = new ChooseLevelWindow(stage);
            chooseLevelWindow.startLevel("./Saves/gamedata.dat");
        });

        Button levelButton = new Button();
        levelButton.setText("Levels");
        levelButton.setPrefWidth(200);
        levelButton.setOnAction(actionEvent -> {
            ChooseLevelWindow chooseLevelWindow = new ChooseLevelWindow(stage);
            chooseLevelWindow.start();
        });

        Button NewLevelButton = new Button();
        NewLevelButton.setText("Create new Level");
        NewLevelButton.setPrefWidth(200);
        NewLevelButton.setOnAction(actionEvent -> {
            Editor editor = new Editor();
            editor.start(stage);
        });

        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(actionEvent -> {
            Platform.exit();
        });

        VBox loginMenu = new VBox();
        loginMenu.getChildren().addAll(loadButton,levelButton,NewLevelButton,exitButton);
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