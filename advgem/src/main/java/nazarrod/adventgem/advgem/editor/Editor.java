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
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Chest;
import nazarrod.adventgem.advgem.model.Item;
import nazarrod.adventgem.advgem.view.GraphicController;
import nazarrod.adventgem.advgem.utils.LevelManager;
import nazarrod.adventgem.advgem.App;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Editor extends Application {
    /**
    *  Level Editor Class that allows to create platformer levels
    * */
    GameData gameData = new GameData();
    private Stage stage = null;
    private GraphicController graphicsController = null;
    private final static Logger logger = Logger.getLogger(Editor.class.getName());
    private static boolean alreadySet = false;
    private static void setLogger(){
        if(alreadySet)return;
        alreadySet = true;
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        boolean dirCreated = new File("./Logs/").mkdirs();
        try {
            fh = new FileHandler("./Logs/editor_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

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

    private void buildPlatformerLevel(){
        gameData = new GameData();
        SetupDialog setupDialog = new SetupDialog(gameData);
        setupDialog.showAndWait();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        //Add canvas
        Canvas canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        graphicsController = new GraphicController(canvas,gc,gameData);
        graphicsController.drawLevel();
        //Add control buttons
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Platform","Hero","Enemy","Chest","Finish");
        choiceBox.setValue("Platform");
        choiceBox.setPrefWidth(150);
        Button invButton = new Button("Inventory");
        invButton.setPrefWidth(150);
        invButton.setDisable(true);
        invButton.setOnAction(actionEvent -> {
            InventorySetupDialog inventorySetupDialog = new InventorySetupDialog(gameData,null);
            inventorySetupDialog.showAndWait();
            graphicsController.drawLevel();
        });
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(150);
        saveButton.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Can't save level");
            alert.setHeaderText(null);
            alert.setContentText("There isn't any hero or finish on the level");
            logger.info("User tried to save level without hero or finish");

            alert.showAndWait();
        });
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(150);
        exitButton.setOnAction(actionEvent -> {
            start(stage);
        });

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(4);
        buttonBox.getChildren().addAll(new Label("Choose new element to add"),choiceBox,new Label("Configure inventory"),
                invButton,new Label("Save Level"),saveButton,exitButton);

//        choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, chosen) -> System.out.println(choiceBox.getValue()));
        canvas.setOnMouseClicked(mouseEvent -> {
            if(choiceBox.getValue().equals("Platform"))
                platformMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Hero")) {
                heroMouseClick(mouseEvent);
                if(gameData.getHero() != null)invButton.setDisable(false);
            }
            if(choiceBox.getValue().equals("Enemy"))
                enemyMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Chest"))
                chestMouseClick(mouseEvent);
            if(choiceBox.getValue().equals("Finish"))
                finishMouseClick(mouseEvent);
            if(gameData.getHero() != null &&
                gameData.getFinish() != null){
                saveButton.setOnAction(actionEvent -> {
                    LevelManager.createNewLevel(gameData);
                    start(stage);
                });
            }
        });
        gridPane.add(canvas,0,0);
        gridPane.add(buttonBox,1,0);
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    private void platformMouseClick(MouseEvent mouseEvent) {
        setLogger();
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80;
        boolean f = gameData.addPlatform(x,y);
        if(f){
            graphicsController.drawLevel();
            logger.info("Platform added on "+x+" "+y);
        }
    }

    private void heroMouseClick(MouseEvent mouseEvent){
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80 + 15;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80 + 19;
        int f = gameData.addHero(x,y);
        if(f == 2){
            logger.warning("Hero already exists");
        }
        if(f == 1){
            logger.info("Hero added on "+x+" "+y);
            graphicsController.drawLevel();
        }
        if(f == 0){
            logger.warning("Collision");
        }
    }

    private void enemyMouseClick(MouseEvent mouseEvent){
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80 + 15;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80 + 19;
        int f = gameData.addEnemy(x,y);

        if(f == 1){
            logger.info("Enemy added on "+x+" "+y);
            graphicsController.drawLevel();
        }
        else{
            logger.warning("Collision");
        }
    }
    private void chestMouseClick(MouseEvent mouseEvent){
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80 + 15;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80 + 29;
        Chest chest = new Chest(new ArrayList<Item>(),x,y,0,0,true);
        InventorySetupDialog inventorySetupDialog = new InventorySetupDialog(gameData,chest);
        inventorySetupDialog.showAndWait();
        int f = gameData.addChest(chest);
        if(f == 1) {
            logger.info("Chest added on " + x + " " + y);
            graphicsController.drawLevel();
        }
        else{
            logger.warning("tile is already taken");
        }
    }
    private void finishMouseClick(MouseEvent mouseEvent) {
        int x = (int)mouseEvent.getX()-(int)mouseEvent.getX() % 80;
        int y = (int)mouseEvent.getY()-(int)mouseEvent.getY() % 80;
        int f = gameData.addFinish(x,y);
        if(f == 1){
            graphicsController.drawLevel();
            logger.info("Finish added on "+x+" "+y);
        }
        else{
            logger.warning("Finish already exists or tile is already taken");
        }
    }
}
