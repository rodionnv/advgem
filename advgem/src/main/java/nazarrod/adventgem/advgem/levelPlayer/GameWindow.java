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
import nazarrod.adventgem.advgem.view.GraphicController;

public class GameWindow{
    private final Stage stage;
    private final Canvas canvas;
    private final GraphicController graphicsController;
    private final GameData gameData;
    private final Hero hero;
    private boolean aKeyPressed = false;
    private boolean dKeyPressed = false;
    private boolean wPressedConstantly = false;

    public GameWindow(GameData gameData) {
        this.stage = new Stage();
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsController = new GraphicController(canvas,canvas.getGraphicsContext2D(),gameData);
        this.hero = gameData.getHero();
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
                    if(!wPressedConstantly)hero.jump();
                    wPressedConstantly = true;
                }
                case A -> {
                    hero.moveLeft();
                    aKeyPressed = true;
                    if(!dKeyPressed)
                        hero.moveLeft();
                }
                case D -> {
                    hero.moveRight();
                    dKeyPressed = true;
                    if(!aKeyPressed)
                        hero.moveRight();
                }
                case SPACE -> {
                    Bullet bullet = new Bullet(hero.getxPos(), hero.getyPos()+20,20,hero.getOrientation(), "bullet_blue.png",true);
                    gameData.addBullet(bullet);
                }
                case E -> {
                    aKeyPressed = false;
                    dKeyPressed = false;
                    hero.setxSpeed(0);
                    openInventory(gameLoopTimer);
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
                    if(!dKeyPressed)hero.setxSpeed(0);
                    else hero.moveRight();
                }
                case D -> {
                    dKeyPressed = false;
                    if(!aKeyPressed)hero.setxSpeed(0);
                    else hero.moveLeft();
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
        Button exitLevelButton = new Button("Exit level");
        exitLevelButton.setPrefWidth(200);
        exitLevelButton.setOnAction(actionEvent -> {
            pauseStage.close();
            new ChooseLevelWindow(stage).start();
        });
        Button exitButton = new Button("Exit game");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(actionEvent -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(continueButton,restartButton,exitLevelButton,exitButton);
        pauseStage.setScene(new Scene(vBox));
        pauseStage.centerOnScreen();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.setOnCloseRequest(windowEvent -> gameLoopTimer.start());
        pauseStage.show();
    }
}
