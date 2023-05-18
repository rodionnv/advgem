package nazarrod.adventgem.advgem.editor;

import javafx.application.Application;
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
import nazarrod.adventgem.advgem.utils.LevelManager;
import nazarrod.adventgem.advgem.App;

public class Editor extends Application {
    /**
    *  Level Editor Class that allows to create platformer levels
    * */
    GameData gameData = new GameData();
    private Stage stage = null;
    private GraphicController graphicsController = null;

    @Override
    public void start(Stage stage){
        this.stage = stage;
        Button simpleLevelButton = new Button("Create platformer level");
        simpleLevelButton.setPrefWidth(200);
        simpleLevelButton.setOnAction(actionEvent -> buildPlatformerLevel());

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
    private void buildPlatformerLevel(){
        SetupDialog setupDialog = new SetupDialog(gameData);
        setupDialog.showAndWait();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

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
            graphicsController.drawLevel();
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
        exitButton.setOnAction(actionEvent -> start(stage));

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(4);
        buttonBox.getChildren().addAll(new Label("Add new element"),choiceBox,specButton,new Label("Configure inventory"),
                invButton,new Label("Save Level"),saveButton,exitButton);

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, chosen) -> {
            System.out.println(choiceBox.getValue());
            specButton.setDisable(!choiceBox.getValue().equals("Platform"));
        });
        canvas.setOnMouseClicked(mouseEvent -> {
            if(choiceBox.getValue().equals("Platform"))
                platformMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Hero"))
                heroMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Enemy"))
                enemyMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Finish"))
                finishMouseClick(mouseEvent);
        });
        gridPane.add(canvas,0,0);
        gridPane.add(buttonBox,1,0);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private void platformMouseClick(MouseEvent mouseEvent) {
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80;
        boolean f = gameData.addPlatform(x,y);
        if(f){
            graphicsController.drawPlatform(x, y);
            System.err.println("Platform added");
        }
    }

    private void heroMouseClick(MouseEvent mouseEvent){
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80 + 15;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80 + 19;
        int f = gameData.addHero(x,y);
        if(f == 2){
            System.err.println("Hero already exists");
        }
        if(f == 1){
            System.err.println("Hero added");
            graphicsController.drawHero(gameData.getHero());
        }
        if(f == 0){
            System.err.println("Probably collision");
            //TODO Pop-up collision warning
        }
    }

    private void enemyMouseClick(MouseEvent mouseEvent){
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80 + 15;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80 + 19;
        int f = gameData.addEnemy(x,y);
        if(f == 1){
            System.err.println("Enemy added");
            graphicsController.drawEnemies(gameData.getEnemies());
        }
        if(f == 0){
            System.err.println("Probably collision");
            //TODO Pop-up collision warning
        }
    }

    private void finishMouseClick(MouseEvent mouseEvent) {
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80;
        int f = gameData.addFinish(x,y);
        if(f == 1){
            graphicsController.drawFinish(x+30, y+30);
            System.err.println("Finish added");
        }
    }
}
