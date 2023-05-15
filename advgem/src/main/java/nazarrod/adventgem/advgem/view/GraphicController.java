package nazarrod.adventgem.advgem.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Enemy;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Platform2D;

import java.util.List;
import java.util.Queue;

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
        setBackground(Color.DARKCYAN);
    }

    public void drawLevel(){
        // Draws only platforms, must load whole level (with heroes and enemies)
        setBackground(Color.DARKCYAN);
        List<Platform2D> platforms = gameData.getPlatforms();
        for(Platform2D platform2D : platforms){
            drawPlatform(platform2D);
        }
        drawHero(gameData.getHero());
        drawBullets(gameData.getBullets());
        drawEnemies(gameData.getEnemies());
        gc.restore();
    }

    public void setBackground(Color color){
        gc.setFill(color);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void drawPlatform(int x,int y,int w,int h){
        Rectangle rectangle = new Rectangle(x,y,w,h);
        rectangle.setFill(Color.GRAY);
        gc.save();
        gc.setFill(rectangle.getFill());
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        gc.restore();
    }

    public void drawHero(Hero hero) {
        //All should be like this
        Image image = new Image(hero.getGfName());
        gc.drawImage(image,hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
    }

    public void drawPlatform(Platform2D platform){
        drawPlatform(platform.getX(),platform.getY(),platform.getWidth(),platform.getHeight());
    }

    public void drawBullets(Queue<Bullet>bullets){
        for(Bullet bullet : bullets){
            Image image = new Image("bullet_hero.png");
            gc.drawImage(image,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
        }
    }

    public void drawEnemies(List<Enemy>enemies){
        for(Enemy enemy : enemies){
            Image image = new Image(enemy.getGfName());
            gc.drawImage(image,enemy.getxPos(),enemy.getyPos(),enemy.getWidth(),enemy.getHeight());
        }
    }
}
