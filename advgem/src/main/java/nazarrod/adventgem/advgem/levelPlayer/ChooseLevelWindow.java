package nazarrod.adventgem.advgem.levelPlayer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.App;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.utils.LevelManager;

import java.util.List;

public class ChooseLevelWindow {
    private Stage stage;

    public ChooseLevelWindow(Stage stage) {
        this.stage = stage;
    }

    public void start(){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        Button levelButton = null;
        List<String> list = LevelManager.getLevels();
        int button_num = 0;
        for (String filename : list){
            System.out.println(filename);
            levelButton = createButton(filename);
            gridPane.add(levelButton,0,++button_num);
        }
        Button backButton = new Button("Back");
        backButton.setPrefWidth(200);
        backButton.setOnAction(actionEvent -> new App().start(stage));
        gridPane.add(backButton,0,++button_num);
        stage.setScene(new Scene(gridPane));
        stage.centerOnScreen();
    }

    public void startLevel(String levelPath){
        GameData gameData = LevelManager.loadLevel(levelPath);
        GameWindow gameWindow = new GameWindow(gameData);
        stage.hide();
        gameWindow.start();
    }

    private Button createButton(String levelName){
        Button button = new Button();
        button.setText(levelName);
        button.setPrefWidth(200);
        button.setOnAction(actionEvent -> startLevel("./Levels/"+levelName+"/gamedata.dat"));
        return button;
    }
}
