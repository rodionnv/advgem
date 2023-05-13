package nazarrod.adventgem.advgem.levelPlayer;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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

    public GameWindow(Stage stage, GameData gameData) {
        this.stage = stage;
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsController = new GraphicController(stage,canvas,canvas.getGraphicsContext2D(),gameData);
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

        // in nanoseconds
        AnimationTimer gameLoopTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) { // in nanoseconds
                if (now - lastUpdate >= 10_000_000) {
                    gameData.refreshAll();
                    graphicsController.drawLevel();
                    lastUpdate = now;
                }
            }
        };
        gameLoopTimer.start();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case W -> hero.jump();
                case A -> hero.moveLeft();
                case D -> hero.moveRight();
                case SPACE -> {
                    Bullet bullet = new Bullet(hero.getxPos(), hero.getyPos()+20,hero.getOrientation());
                    gameData.addBullet(bullet);
                }
                case E -> {
                    System.out.println("Inventory");
                }
                case R -> {
//                    hero.reincarnate();
                    gameLoopTimer.stop();
                    new ChooseLevelWindow(stage).start();
                }
                case SHIFT -> {
                    System.out.println("Block!");
                }
            }
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case A,D -> hero.setxSpeed(0);
            }
        });
    }
}
