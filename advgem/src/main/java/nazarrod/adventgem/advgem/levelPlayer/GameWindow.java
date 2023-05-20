package nazarrod.adventgem.advgem.levelPlayer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.utils.LevelManager;
import nazarrod.adventgem.advgem.view.GraphicController;

public class GameWindow{
    private final Stage stage;
    private final Canvas canvas;
    private final GraphicController graphicsController;
    private GameData gameData;
    private boolean aKeyPressed = false;
    private boolean dKeyPressed = false;
    private boolean wPressedConstantly = false;

    public GameWindow(GameData gameData) {
        this.stage = new Stage();
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsController = new GraphicController(canvas,canvas.getGraphicsContext2D(),gameData);
    }

    public void start(){
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);
        graphicsController.drawLevel();
        stage.setTitle(gameData.getLevelName());
        stage.setScene(new Scene(gridPane));
        stage.centerOnScreen();
        stage.show();

        // in nanoseconds
        AnimationTimer gameLoopTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) { // in nanoseconds
                if (now - lastUpdate >= 10_000_000) {
                    gameData.refreshAll(now);
                    graphicsController.drawLevel();
                    lastUpdate = now;
                    if(gameData.isWin()){
                        stop();
                        winLevel();
                    }
                    if(gameData.isLoose()){
                        stop();
                        looseLevel();
                    }
                }
            }
        };
        gameLoopTimer.start();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case W -> {
                    if(!wPressedConstantly)gameData.getHero().jump();
                    wPressedConstantly = true;
                }
                case A -> {
                    gameData.getHero().moveLeft();
                    aKeyPressed = true;
                    if(!dKeyPressed)
                        gameData.getHero().moveLeft();
                }
                case D -> {
                    gameData.getHero().moveRight();
                    dKeyPressed = true;
                    if(!aKeyPressed)
                        gameData.getHero().moveRight();
                }
                case SPACE -> {
                    Bullet bullet;
                    if(gameData.getHero().getWeapon() == Hero.Weapon.BULLET) {
                        bullet = new Bullet(gameData.getHero().getxPos(), gameData.getHero().getyPos() + 20, 20, gameData.getHero().getOrientation(), false, true, 9999);
                    }
                    else{
                        bullet = new Bullet(gameData.getHero().getxPos(), gameData.getHero().getyPos() + 20, 20, gameData.getHero().getOrientation(), true, true, 70);
                    }
                    gameData.addBullet(bullet);
                }
                case E -> {
                    aKeyPressed = false;
                    dKeyPressed = false;
                    gameData.getHero().setxSpeed(0);
                    gameData.getHero().setArmorQ(5);
                    gameData.getHero().setSpeedB(5);
                    gameData.getHero().setWeapon(Hero.Weapon.BULLET);
//                    openInventory(gameLoopTimer);
                }
                case P,ESCAPE -> pauseLevel(gameLoopTimer);
                case SHIFT -> {
                    System.out.println("Block!");
                }
            }
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case W -> {
                    wPressedConstantly = false;
                }
                case A -> {
                    aKeyPressed = false;
                    if(!dKeyPressed)gameData.getHero().setxSpeed(0);
                    else gameData.getHero().moveRight();
                }
                case D -> {
                    dKeyPressed = false;
                    if(!aKeyPressed)gameData.getHero().setxSpeed(0);
                    else gameData.getHero().moveLeft();
                }
            }
        });
    }

    public void openInventory(AnimationTimer gameLoopTimer) {
        gameLoopTimer.stop();
        Stage invStage = new Stage();

        invStage.centerOnScreen();
        invStage.initModality(Modality.APPLICATION_MODAL);
        invStage.setOnCloseRequest(windowEvent -> gameLoopTimer.start());
        invStage.show();
    }

    public void winLevel(){
        looseLevel();
    }

    public void looseLevel(){
        Stage looseStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Button restartButton = new Button("Restart");
        restartButton.setPrefWidth(200);
        restartButton.setOnAction(actionEvent -> {
            looseStage.close();
            new ChooseLevelWindow(stage).startLevel(gameData.getLevelName());
        });
        Button exitLevelButton = new Button("Exit level");
        exitLevelButton.setPrefWidth(200);
        exitLevelButton.setOnAction(actionEvent -> {
            looseStage.close();
            new ChooseLevelWindow(stage).start();
        });

        vBox.getChildren().addAll(restartButton,exitLevelButton);
        looseStage.setScene(new Scene(vBox));
        looseStage.centerOnScreen();
        looseStage.initModality(Modality.APPLICATION_MODAL);
        looseStage.setOnCloseRequest(windowEvent -> {});
        looseStage.show();
    }

    public void pauseLevel(AnimationTimer gameLoopTimer){
        gameLoopTimer.stop();
        Stage pauseStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Button continueButton = new Button("Continue");
        continueButton.setPrefWidth(200);
        continueButton.setOnAction(actionEvent -> {
            pauseStage.close();
            gameLoopTimer.start();
        });
        Button restartButton = new Button("Restart");
        restartButton.setPrefWidth(200);
        restartButton.setOnAction(actionEvent -> {
            pauseStage.close();
            new ChooseLevelWindow(stage).startLevel(gameData.getLevelName());
        });
        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(200);
        if(LevelManager.loadLevel("./Saves/gamedata.dat") == null)loadButton.setDisable(true);
        loadButton.setOnAction(actionEvent -> {
            gameData = LevelManager.loadLevel("./Saves/gamedata.dat");
            graphicsController.setGameData(gameData);
            pauseStage.close();
            gameLoopTimer.start();
        });
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(200);
        saveButton.setOnAction(actionEvent -> {
            aKeyPressed = false;
            dKeyPressed = false;
            gameData.getHero().setxSpeed(0);
            LevelManager.makeNewSave(gameData);
            loadButton.setDisable(false);
        });
        Button exitLevelButton = new Button("Exit level");
        exitLevelButton.setPrefWidth(200);
        exitLevelButton.setOnAction(actionEvent -> {
            pauseStage.close();
            new ChooseLevelWindow(stage).start();
        });
        Button saveExitButton = new Button("Save and Exit game");
        saveExitButton.setPrefWidth(200);
        saveExitButton.setOnAction(actionEvent -> {
            pauseStage.close();
            LevelManager.makeNewSave(gameData);
            Platform.exit();
        });
        Button exitButton = new Button("Exit game");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(actionEvent -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(continueButton,restartButton,saveButton,loadButton,exitLevelButton,saveExitButton,exitButton);
        pauseStage.setScene(new Scene(vBox));
        pauseStage.centerOnScreen();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.setOnCloseRequest(windowEvent -> gameLoopTimer.start());
        pauseStage.show();
    }
}
