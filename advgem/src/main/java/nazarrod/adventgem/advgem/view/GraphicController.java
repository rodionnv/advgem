package nazarrod.adventgem.advgem.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Platform2D;

import java.util.List;

/**
 * GraphicController - class that is responsible for visual part
 * contains methods for loading the whole graphic context from GameData and methods for drawing individual components
 */
public class GraphicController {
    Stage stage;
    Canvas canvas;
    GraphicsContext gc;
    GameData gameData;

    public GraphicController(Stage stage, Canvas canvas, GraphicsContext graphicsContext,GameData gameData) {
        this.stage = stage;
        this.canvas = canvas;
        this.gc = graphicsContext;
        this.gameData = gameData;
    }

    public void drawLevel(){
        // Draws only platforms, must load whole level (with heroes and enemies)
        setBackground(Color.DARKCYAN);
        List<Platform2D> platforms = gameData.getPlatforms();
        for(Platform2D platform2D : platforms){
            drawPlatform(platform2D);
        }
        drawHero(gameData.getHero());
    }

    public void setBackground(Color color){
        gc.setFill(color);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void drawPlatform(int x,int y,int w,int h){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        Image image = new Image("platform.png");
        ImagePattern imagePattern = new ImagePattern(image);
        rectangle.setFill(imagePattern);
        gc.save();
        gc.setFill(rectangle.getFill());
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.restore();
    }

    public void drawHero(Hero hero) {
        Rectangle rectangle = new Rectangle(hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
//        Image image = new Image("platform.png");
//        ImagePattern imagePattern = new ImagePattern(image);
//        rectangle.setFill(imagePattern);
        rectangle.setFill(Color.RED);
        gc.save();
        gc.setFill(rectangle.getFill());
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.restore();
    }

    public void drawPlatform(Platform2D platform){
        drawPlatform(platform.getX(),platform.getY(),platform.getWidth(),platform.getHeight());
    }
}
