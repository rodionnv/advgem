package nazarrod.adventgem.advgem.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Enemy;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Platform2D;

import java.util.List;
import java.util.Objects;
import java.util.Queue;

import static nazarrod.adventgem.advgem.view.GfIMG.*;

/**
 * GraphicController - class that is responsible for visual part
 * contains methods for loading the whole graphic context from GameData and methods for drawing individual components
 */
public class GraphicController {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private GameData gameData;
    private final Image[] numImage = new Image[10];

    public GraphicController(Canvas canvas, GraphicsContext graphicsContext,GameData gameData) {
        this.canvas = canvas;
        this.gc = graphicsContext;
        this.gameData = gameData;

        for (int i = 0; i < numImage.length; i++) {
            String imageName = i + ".png";
            numImage[i] = new Image(imageName);
        }
    }

    public void drawLevel(){
        // Draws only platforms, must load whole level (with heroes and enemies)
        setBackground(Color.CYAN);
        List<Platform2D> platforms = gameData.getPlatforms();
        for(Platform2D platform2D : platforms){
            drawPlatform(platform2D);
        }
        if(gameData.getFinish() != null)drawFinish(gameData.getFinish().getX(),gameData.getFinish().getY());
        if(gameData.getHero() != null)drawHero(gameData.getHero());
        drawBullets(gameData.getBullets());
        drawEnemies(gameData.getEnemies());
        if(gameData.getHero() != null)drawUI(gameData.getLives(),gameData.getHero().getHP());
        gc.restore();
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public void setBackground(Color color){
        gc.setFill(color);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    public void drawPlatform(Platform2D platform){
        drawPlatform(platform.getX(),platform.getY());
    }

    public void drawPlatform(int x,int y){
        gc.drawImage(PLATFORM.img,x,y,80,80);
    }

    public void drawFinish(int x,int y){
        if(gameData.getHero() == null || !gameData.getHero().isHasKey())gc.drawImage(CLOSED_FINISH.img,x-30,y-30,80,80);
        else gc.drawImage(FINISH.img,x-30,y-30,80,80);
    }

    public void drawHero(Hero hero) {
        gc.drawImage(HERO.img,hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
    }

    public void drawBullets(Queue<Bullet>bullets){
        for(Bullet bullet : bullets){
            if(bullet.getShotByHero()){
                if(bullet.isSword())gc.drawImage(SWORD.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
                else gc.drawImage(BLUE_BULLET.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
            }
            else gc.drawImage(RED_BULLET.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
        }
    }

    public void drawEnemies(List<Enemy>enemies){
        for(Enemy enemy : enemies){
            gc.drawImage(ENEMY.img,enemy.getxPos(),enemy.getyPos(),enemy.getWidth(),enemy.getHeight());
        }
    }

    public void drawUI(int lives,int hp){
        for(int i = 0;i < lives;i++)
            gc.drawImage(LIVE.img, i * 50,0,50,50);
        gc.drawImage(HP.img,0,55,50,50);
        if(hp/100 != 0)gc.drawImage(numImage[hp/100],50,55,50,50);
        gc.drawImage(numImage[(hp/10)%10],100,55,50,50);
        gc.drawImage(numImage[hp%10],150,55,50,50);
    }
}
