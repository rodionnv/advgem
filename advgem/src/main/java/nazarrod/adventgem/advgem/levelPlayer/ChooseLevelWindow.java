package nazarrod.adventgem.advgem.levelPlayer;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
        stage.setScene(new Scene(gridPane));
    }

    private void startLevel(String levelName){
        String levelPath = "./Levels/"+levelName+"/gamedata.dat";
        GameData gameData = LevelManager.loadLevel(levelPath);
        GameWindow gameWindow = new GameWindow(stage,gameData);
        gameWindow.start();
    }

    private Button createButton(String levelName){
        Button button = new Button();
        button.setText(levelName);
        button.setPrefWidth(200);
        button.setOnAction(actionEvent -> startLevel(levelName));
        return button;
    }
}
