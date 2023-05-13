package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Platform2D;
import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.*;

public class GameData implements Serializable {
    private final Random rnd = new Random();
    private String levelName = "NewLevel" + rnd.nextInt(1000000007); // Add random string to avoid name duplication
    private int playgroundWidth = 1920;
    private int PlaygroundHeight = 1080;
    private List<Platform2D> platforms = new ArrayList<>();
    private Hero hero = null;
    private Queue<Bullet> bullets = new LinkedList<Bullet>();

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getPlaygroundWidth() {
        return playgroundWidth;
    }

    public void setPlaygroundWidth(int playgroundWidth) {
        this.playgroundWidth = playgroundWidth;
    }

    public int getPlaygroundHeight() {
        return PlaygroundHeight;
    }

    public void setPlaygroundHeight(int playgroundHeight) {
        PlaygroundHeight = playgroundHeight;
    }

    public List<Platform2D> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform2D> platforms) {
        this.platforms = platforms;
    }

    public boolean addPlatform(int wpos, int hpos, int width, int height){
        Platform2D platform2D = new Platform2D(wpos,hpos,width,height);
        if(checkIfCollidesWithAnything(platform2D))return false;
        platforms.add(platform2D);
        return true;
    }

    public Queue<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Queue<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Hero getHero() {
        return hero;
    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }

    public int addHero(int x, int y){
        if(hero != null)return 2;
        if(y < 0)return 0;
        Hero hero = new Hero(x,y,100);
        Platform2D heroPlatform = new Platform2D(hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
        if(checkIfCollidesWithAnything(heroPlatform))return 0;
        this.hero = hero;
        return 1;
    }

    public void refreshAll(){
        hero.updateFallingState(getPlatforms());
        hero.tryMove();
        for(Bullet bullet : bullets){
            bullet.move();
        }
    }

    private boolean checkIfCollidesWithAnything(Platform2D platform2D){
        for(Platform2D platform : platforms)
            if(Geometry.checkCollision(platform2D,platform) || Geometry.checkCollision(platform,platform2D))return true;
        return false;
    }
}
