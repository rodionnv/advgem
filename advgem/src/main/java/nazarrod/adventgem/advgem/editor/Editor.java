package nazarrod.adventgem.advgem.editor;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.view.GraphicController;
import nazarrod.adventgem.advgem.model.Platform2D;
import nazarrod.adventgem.advgem.utils.LevelManager;
import nazarrod.adventgem.advgem.App;

import java.util.List;

public class Editor extends Application {
    /**
    *  Level Editor Class that allows to create platformer levels
    * */
    GameData gameData = new GameData();
    private int prevX = -1;
    private int prevY = -1;
    private GraphicController graphicsController = null;

    @Override
    public void start(Stage stage){
        Button simpleLevelButton = new Button("Create platformer level");
        simpleLevelButton.setPrefWidth(200);
        simpleLevelButton.setOnAction(actionEvent -> buildPlatformerLevel(stage));

        Button mainMenuButton = new Button("Main menu");
        mainMenuButton.setPrefWidth(200);
        mainMenuButton.setOnAction(actionEvent -> new App().start(stage));


        VBox chooseLevel = new VBox();
        chooseLevel.getChildren().addAll(simpleLevelButton,mainMenuButton);
        chooseLevel.setSpacing(8);
        chooseLevel.setAlignment(Pos.CENTER);
        stage.setTitle("Create new level");
        stage.setScene(new Scene(chooseLevel,500,500));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Function builds Platform level
     * */
    private void buildPlatformerLevel(Stage stage){
        SetupDialog setupDialog = new SetupDialog(gameData);
        setupDialog.showAndWait();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);

        //Add canvas
        Canvas canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        graphicsController = new GraphicController(stage,canvas,gc,gameData);
        graphicsController.setBackground(Color.DARKCYAN);
        //Add control buttons
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Platform","Hero","Enemy","Finish");
        choiceBox.setValue("Platform");
        Button specButton = new Button("Set manually");
        specButton.setOnAction(actionEvent -> {
            PlatformSetupDialog platformSetupDialog = new PlatformSetupDialog(gameData);
            platformSetupDialog.showAndWait();
        });
        specButton.setPrefWidth(150);
        specButton.setDisable(false);
        choiceBox.setPrefWidth(150);
        Button invButton = new Button("Inventory");
        invButton.setPrefWidth(150);
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(150);
        saveButton.setOnAction(actionEvent -> {
            LevelManager.createNewLevel(gameData);
            start(stage);
        });
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(actionEvent -> {
            start(stage);
        });

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(4);
        buttonBox.getChildren().addAll(new Label("Add new element"),choiceBox,specButton,new Label("Configure inventory"),
                invButton,new Label("Save Level"),saveButton,exitButton);

        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String chosen) {
                System.out.println(choiceBox.getValue());
                specButton.setDisable(!choiceBox.getValue().equals("Platform"));
            }
        });
        canvas.setOnMouseClicked(mouseEvent -> {
            System.err.println("Mouse pressed on " + (int)mouseEvent.getX() + " " + (int)mouseEvent.getY());
            if(choiceBox.getValue().equals("Platform"))
                platformMouseClick(gc, mouseEvent);
        });
        gridPane.add(canvas,0,0);
        gridPane.add(buttonBox,1,0);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private void platformMouseClick(GraphicsContext gc,MouseEvent mouseEvent) {
        int x = (int)mouseEvent.getX();
        int y = (int)mouseEvent.getY();
        if(prevX == -1){
            prevX = x;
            prevY = y;
        }
        else{
            if(prevX > x){
                int t = prevX;
                prevX = x;
                x = t;
            }
            if(prevY > y){
                int t = prevY;
                prevY = y;
                y = t;
            }
            graphicsController.drawPlatform(prevX,prevY,x-prevX,y-prevY);
            gameData.addPlatform(prevX,prevY,x-prevX,y-prevY);
            List<Platform2D> platforms = gameData.getPlatforms();
            for(Platform2D platform2D : platforms){
                System.out.println(platform2D.getWpos() + " " + platform2D.getHpos() + " " + platform2D.getWidth() + " " + platform2D.getHeight() + " ");
            }
            prevX = -1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
