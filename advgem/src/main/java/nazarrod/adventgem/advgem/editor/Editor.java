package nazarrod.adventgem.advgem.editor;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.geometry.Platform2D;

import java.util.List;

public class Editor extends Application {
    /*
    *  Level Editor Class that allows to create simple levels,boss levels and chest(?) levels
    * */
    @Override
    public void start(Stage stage){
        Button simpleLevelButton = new Button();
        simpleLevelButton.setText("Create simple level");
        simpleLevelButton.setPrefWidth(200);
        simpleLevelButton.setOnAction(actionEvent -> buildSimpleLevel());


        VBox chooseLevel = new VBox();
        chooseLevel.getChildren().addAll(simpleLevelButton);
        chooseLevel.setSpacing(8);
        chooseLevel.setAlignment(Pos.CENTER);
        stage.setTitle("Create new level");
        stage.setScene(new Scene(chooseLevel,500,500));
        stage.show();
    }

    private void buildSimpleLevel(){
        /*
        * Function builds simple level
        * */
        GameData gameData = new GameData();
        SetupDialog setupDialog = new SetupDialog(gameData);
        setupDialog.showAndWait();
//        gameData.addPlatform(5,5,15,2);
//        List<Platform2D> platforms = gameData.getPlatforms();
//        for(int i = 0;i < platforms.size();i++){
//            Platform2D curPlat = platforms.get(i);
//            System.out.println(curPlat.getWpos()+" "+curPlat.getHpos()+" "+curPlat.getWidth()+" "+curPlat.getHeight());
//        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
