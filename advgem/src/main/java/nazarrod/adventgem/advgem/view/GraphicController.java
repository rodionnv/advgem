package nazarrod.adventgem.advgem.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.*;

import java.util.List;
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

    /**
     * @param canvas canvas to draw on
     * @param graphicsContext graphicsContext of the canvas
     * @param gameData game data
     */
    public GraphicController(Canvas canvas, GraphicsContext graphicsContext,GameData gameData) {
        this.canvas = canvas;
        this.gc = graphicsContext;
        this.gameData = gameData;

        for (int i = 0; i < numImage.length; i++) {
            String imageName = i + ".png";
            numImage[i] = new Image(imageName);
        }
    }

    /**
     * Draw all components of the level
     */
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
        drawChests(gameData.getChests());
        if(gameData.getHero() != null)drawUI();
        gc.restore();
    }

    /**
     * set data of the current level
     * @param gameData game data
     */
    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }


    /**
     * Fill background
     * @param color Color og the background
     */
    public void setBackground(Color color){
        gc.setFill(color);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    /**
     * Draw platform on the canvas
     * @param platform
     */
    public void drawPlatform(Platform2D platform){
        drawPlatform(platform.getX(),platform.getY());
    }

    /**
     * Draw platform on the canvas
     * @param x
     * @param y
     */
    public void drawPlatform(int x,int y){
        gc.drawImage(PLATFORM.img,x,y,80,80);
    }

    /**
     * Draw finish on the canvas
     * @param x
     * @param y
     */
    public void drawFinish(int x,int y){
        if(gameData.getHero() == null || !gameData.getHero().isHasKey())gc.drawImage(CLOSED_FINISH.img,x-30,y-30,80,80);
        else gc.drawImage(FINISH.img,x-30,y-30,80,80);
    }

    /**
     * Draw hero on the canvas
     * @param hero
     */
    public void drawHero(Hero hero) {
        gc.drawImage(HERO.img,hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
    }

    /**
     * Draws all bullets on the canvas
     * @param bullets list of the active bullets
     */
    public void drawBullets(Queue<Bullet>bullets){
        for(Bullet bullet : bullets){
            if(bullet.getShotByHero()){
                if(bullet.isSword())gc.drawImage(SWORD.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
                else gc.drawImage(BLUE_BULLET.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
            }
            else gc.drawImage(RED_BULLET.img,bullet.getxPos(),bullet.getyPos(),bullet.getWidth(),bullet.getHeight());
        }
    }

    /**
     * Draws all enemies on the canvas
     * @param enemies list of the alive enemies
     */
    public void drawEnemies(List<Enemy>enemies){
        for(Enemy enemy : enemies){
            gc.drawImage(ENEMY.img,enemy.getxPos(),enemy.getyPos(),enemy.getWidth(),enemy.getHeight());
        }
    }


    /**
     * Draws all chests on the canvas
     * @param chests List of the closed chests
     */
    public void drawChests(List<Chest>chests){
        for(Chest chest : chests){
            gc.drawImage(CHEST.img,chest.getxPos(),chest.getyPos(),50,50);
        }
    }


    /**
     * Draw UI info about hero
     */
    public void drawUI(){
        for(int i = 0;i < gameData.getLives();i++)
            gc.drawImage(LIVE.img, i * 50,0,50,50);
        gc.drawImage(HP.img,0,55,50,50);
        if(gameData.getHero().getHP()/100 != 0)gc.drawImage(numImage[gameData.getHero().getHP()/100],50,55,50,50);
        gc.drawImage(numImage[(gameData.getHero().getHP()/10)%10],100,55,50,50);
        gc.drawImage(numImage[gameData.getHero().getHP()%10],150,55,50,50);

        gc.drawImage(BLUE_BULLET.img,250,0,50,50);
        if(gameData.getHero().getBulletsCnt()/100 != 0)gc.drawImage(numImage[gameData.getHero().getBulletsCnt()/100],300,0,50,50);
        gc.drawImage(numImage[(gameData.getHero().getBulletsCnt()/10)%10],350,0,50,50);
        gc.drawImage(numImage[gameData.getHero().getBulletsCnt()%10],400,0,50,50);

        gc.drawImage(APPLE.img,250,55,50,50);
        if(gameData.getHero().getApplesCnt()/100 != 0)gc.drawImage(numImage[gameData.getHero().getApplesCnt()/100],300,55,50,50);
        gc.drawImage(numImage[(gameData.getHero().getApplesCnt()/10)%10],350,55,50,50);
        gc.drawImage(numImage[gameData.getHero().getApplesCnt()%10],400,55,50,50);
    }
}
