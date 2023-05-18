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
    private Stage stage;
    private Canvas canvas;
    private GraphicsContext gc;
    private GameData gameData;
    private final Image platformImage = new Image("platform.png");
    private final Image finishImage = new Image("finish.png");
    private final Image heroImage = new Image("hero.png");
    private final Image enemyImage = new Image("enemy.png");
    private final Image redBulletImage = new Image("bullet_red.png");
    private final Image blueBulletImage = new Image("bullet_blue.png");

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
        drawFinish(gameData.getFinish().getX(),gameData.getFinish().getY());
        drawHero(gameData.getHero());
        drawBullets(gameData.getBullets());
        drawEnemies(gameData.getEnemies());
        gc.restore();
    }

    public void setBackground(Color color){
        gc.setFill(color);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void drawPlatform(Platform2D platform){
        drawPlatform(platform.getX(),platform.getY());
    }

    public void drawPlatform(int x,int y){
        gc.drawImage(platformImage,x,y,80,80);
    }

    public void drawFinish(int x,int y){
        gc.drawImage(finishImage,x-30,y-30,80,80);
    }

    public void drawHero(Hero hero) {
        gc.drawImage(heroImage,hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
    }

    public void drawBullets(Queue<Bullet>bullets){
        for(Bullet bullet : bullets){
            if(bullet.getShotBy())gc.drawImage(blueBulletImage,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
            else gc.drawImage(redBulletImage,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
        }
    }

    public void drawEnemies(List<Enemy>enemies){
        for(Enemy enemy : enemies){
            gc.drawImage(enemyImage,enemy.getxPos(),enemy.getyPos(),enemy.getWidth(),enemy.getHeight());
        }
    }
}
