package nazarrod.adventgem.advgem.levelPlayer;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.view.GraphicController;

import java.util.LinkedList;
import java.util.Queue;

public class GameWindow{
    private final Stage stage;
    private Canvas canvas = null;
    private GraphicsContext graphicsContext = null;
    private GraphicController graphicsController = null;
    private GameData gameData = null;
    private AnimationTimer gameLoopTimer;

    public GameWindow(Stage stage, GameData gameData) {
        this.stage = stage;
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsController = new GraphicController(stage,canvas,graphicsContext,gameData);
    }

    public void start(){
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);
        graphicsController.drawLevel();
        stage.setTitle(gameData.getLevelName());
        stage.setScene(new Scene(gridPane));
        stage.centerOnScreen();
        stage.show();
        Hero hero = gameData.getHero();
        gameLoopTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) { // in nanoseconds
                if (now - lastUpdate >= 10_000_000) {
                    graphicsController.drawLevel();
//                    System.out.println(hero.getxSpeed()+" "+hero.getySpeed());
                    hero.updateFallingState(gameData.getPlatforms());
                    hero.tryMove();
                    lastUpdate = now;
                }
            }
        };
        gameLoopTimer.start();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                switch (code) {
                    case W -> {
                        hero.jump();
                    }
                    case A -> {
                        hero.moveLeft();
                    }
                    case D -> {
                        hero.moveRight();
                    }
                    case SPACE -> {
                        Bullet bullet = new Bullet(hero.getxPos(), hero.getyPos(),hero.getOrientation());
                        gameData.addBullet(bullet);
                    }
                    case E -> {
                        System.out.println("Inventory");
                    }
                    case SHIFT -> {
                        System.out.println("Block!");
                    }
                }
            }
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                switch (code) {
                    case A,D -> {
                        hero.setxSpeed(0);
                    }
                }
            }
        });
    }
}
