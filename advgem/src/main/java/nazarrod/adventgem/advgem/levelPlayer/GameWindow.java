package nazarrod.adventgem.advgem.levelPlayer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.view.GraphicController;

public class GameWindow{
    private Stage stage;
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
                if (now - lastUpdate >= 10_000_000) { // I dont know what value to put here yet
                    graphicsController.drawLevel();
//                    System.out.println(hero.getxSpeed()+" "+hero.getySpeed());
                    hero.updateFallingState(gameData.getPlatforms());
                    hero.changePos(hero.getxPos()+hero.getxSpeed(), hero.getyPos()+hero.getySpeed());
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
                        System.out.println("W pressed"+" "+hero.isStanding(gameData.getPlatforms()));
                        hero.jump();
                    }
                    case A -> {
                        System.out.println("A pressed"+" "+hero.isStanding(gameData.getPlatforms()));
                        hero.setxSpeed(-1);
                    }
                    case S -> {
                        System.out.println("S pressed"+" "+hero.isStanding(gameData.getPlatforms()));
//                        hero.setySpeed(hero.getySpeed()+1);
                    }
                    case D -> {
                        System.out.println("D pressed"+" "+hero.isStanding(gameData.getPlatforms()));
                        hero.setxSpeed(+1);
                    }
                    case SPACE -> {
                        System.out.println("Fire!");
                    }
                    case E -> {
                        System.out.println("Inventory");
                    }
                }
            }
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                switch (code) {
                    case W -> {
                        System.out.println("W relesed"+" "+hero.isStanding(gameData.getPlatforms()));
//                        hero.setySpeed(hero.getySpeed()+1);
                    }
                    case A -> {
                        System.out.println("A relesed"+" "+hero.isStanding(gameData.getPlatforms()));
                        hero.setxSpeed(0);
                    }
                    case S -> {
                        System.out.println("S relesed"+" "+hero.isStanding(gameData.getPlatforms()));
//                        hero.setySpeed(hero.getySpeed()-1);
                    }
                    case D -> {
                        System.out.println("D relesed"+" "+hero.isStanding(gameData.getPlatforms()));
                        hero.setxSpeed(0);
                    }
                }
            }
        });
    }
}
