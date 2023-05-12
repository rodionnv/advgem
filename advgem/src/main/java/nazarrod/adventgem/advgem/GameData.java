package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Platform2D;

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

    private boolean checkIfCollidesWithAnything(Platform2D platform2D){
        for(Platform2D platform : platforms)
            if(checkCollision(platform2D,platform) || checkCollision(platform,platform2D))return true;
        return false;
    }

    private boolean checkBelongs(int x,int y,Platform2D p){
        int xp1 = p.getX();
        int yp1 = p.getY();
        int xp2 = xp1+p.getWidth();
        int yp2 = yp1+p.getHeight();
        return xp1 <= x && x <= xp2 && yp1 <= y && y <= yp2;
    }

    public boolean checkCollision(Platform2D p,Platform2D o){
        int x,y;
        x = p.getX();
        y = p.getY();
        if(checkBelongs(x,y,o))return true;

        x = p.getX();
        y = p.getY()+p.getHeight();
        if(checkBelongs(x,y,o))return true;

        x = p.getX()+p.getWidth();
        y = p.getY();
        if(checkBelongs(x,y,o))return true;

        x = p.getX()+p.getWidth();
        y = p.getY()+p.getHeight();
        if(checkBelongs(x,y,o))return true;

        if( (p.getX() <= o.getX() && o.getX()+o.getWidth() <= p.getX()+p.getWidth()) &&
                (p.getY() <= o.getY() && o.getY()+o.getHeight() <= p.getY()+p.getHeight()) )return true;

        if( (o.getX() <= p.getX() && p.getX()+p.getWidth() <= o.getX()+o.getWidth()) &&
                (o.getY() <= p.getY() && p.getY()+p.getHeight() <= o.getY()+o.getHeight()) )return true;

        if( (p.getX() <= o.getX() && o.getX()+o.getWidth() <= p.getX()+p.getWidth()) &&
                (o.getY() <= p.getY() && p.getY()+p.getHeight() <= o.getY()+o.getHeight()) )return true;

        if( (o.getX() <= p.getX() && p.getX()+p.getWidth() <= o.getX()+o.getWidth()) &&
                (p.getY() <= o.getY() && o.getY()+o.getHeight() <= p.getY()+p.getHeight()) )return true;


        //TODO It is not complete
        return false;
    }
}
