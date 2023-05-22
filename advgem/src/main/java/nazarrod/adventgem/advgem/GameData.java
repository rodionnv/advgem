package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.model.*;
import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Math.abs;

public class GameData implements Serializable {

    private final static Logger logger = Logger.getLogger(GameData.class.getName());
    private static boolean alreadySet = false;

    private static void setLogger(){
        if(alreadySet)return;
        alreadySet = true;
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        boolean dirCreated = new File("./Logs/").mkdirs();
        try {
            fh = new FileHandler("./Logs/game_data_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
    private final Random rnd = new Random();
    private String levelName = "NewLevel" + rnd.nextInt(1000000007); // Add random string to avoid name duplication
    private int playgroundWidth = 1920;
    private int PlaygroundHeight = 1080;
    private List<Platform2D> platforms = new ArrayList<>();
    private Platform2D finish;
    private Hero hero = null;
    private int lives = 3;
    private boolean loose = false;
    private boolean win = false;
    private Queue<Bullet> bullets = new LinkedList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Chest> chests = new ArrayList<>();
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

    public boolean addPlatform(int wpos, int hpos){
        setLogger();
        Platform2D platform2D = new Platform2D(wpos,hpos,79,79);
        if(checkIfCollidesWithAnything(platform2D))return false;
        platforms.add(platform2D);
        logger.info(platform2D+" platform added");
        return true;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
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

    public List<Chest> getChests() {
        return chests;
    }

    public int addChest(Chest chest){
        setLogger();
        Platform2D chestPlatform = chest.getPlatform();
        if(checkIfCollidesWithAnything(chestPlatform))return 0;
        chests.add(chest);
        logger.info(chest+" chest added");
        return 1;
    }

    public int addHero(int x, int y){
        setLogger();
        if(hero != null)return 2;
        if(y < 0)return 0;
        Hero hero = new Hero(x,y,100);
        Platform2D heroPlatform = new Platform2D(hero.getxPos(),hero.getyPos(),hero.getWidth(),hero.getHeight());
        if(checkIfCollidesWithAnything(heroPlatform)){
            logger.info(hero+" can't be added because tile is already taken");
            return 0;
        }
        this.hero = hero;
        logger.info(hero+" hero added");
        return 1;
    }

    public int getLives() {
        return lives;
    }

    public boolean isLoose() {
        return loose;
    }

    public boolean isWin() {
        return win;
    }

    public int addEnemy(int x, int y){
        setLogger();
        if(y < 0)return 0;
        Enemy enemy = new Enemy(x,y,100);
        Platform2D enemyPlatform = new Platform2D(enemy.getxPos(),enemy.getyPos(),enemy.getWidth(),enemy.getHeight());
        if(checkIfCollidesWithAnything(enemyPlatform))return 0;
        enemies.add(enemy);
        logger.info(enemy+" enemy added");
        return 1;
    }

    public Platform2D getFinish() {
        return finish;
    }

    public void setFinish(Platform2D finish) {
        this.finish = finish;
    }

    public int addFinish(int x, int y){
        setLogger();
        if(y < 0 || finish != null)return 0;
        Platform2D platform = new Platform2D(x+30,y+30,20,20);
        if(checkIfCollidesWithAnything(platform))return 0;
        finish = platform;
        logger.info(finish+" finish added");
        return 1;
    }

    private long last_enemy_shot = 0;
    public void refreshAll(long now){
        setLogger();
        hero.updateStates(getPlatforms());
        hero.tryMove();
        hero.updJumps(getPlatforms());
        if(hero.isHasKey() && Geometry.checkCollision(hero.getPlatform(),finish)){
            win = true;
            logger.info("Hero reached finish with key, level won");
            return;
        }
        if(hero.outOfLevel(getPlaygroundWidth(),getPlaygroundHeight())){
            lives--;
            logger.info("Hero fell out of level");
            if(lives > 0){
                hero.reincarnate();
                logger.info("Hero reincarnated");
            }
            else{
                loose = true;
                logger.info("Hero lost all it's loves, level lost");
            }
            return;
        }
        boolean enemies_shoot_now = false;
        if(now - last_enemy_shot >= 1000000000) {
            last_enemy_shot = now;
            enemies_shoot_now = true;
        }
        Enemy enemy;
        for (Enemy value : enemies) {
            enemy = value;
            enemy.updateOrientation(getPlatforms());
            enemy.tryMove();
            if(enemies_shoot_now){
                Bullet new_bullet = new Bullet(enemy.getxPos(), enemy.getyPos()+20,20,enemy.getOrientation(),false,false,9999);
                addBullet(new_bullet);
            }
        }
        Iterator<Bullet> bulletIterator = bullets.iterator();
        Bullet bullet;
        while (bulletIterator.hasNext()){
            bullet = bulletIterator.next();
            bullet.move();
            if(Geometry.outOfBounds(bullet.getPlatform(),playgroundWidth,getPlaygroundHeight()) || (abs(bullet.getxPos() - bullet.getxStart()) > bullet.getLiveDist() ) ) {
                bulletIterator.remove();
                logger.info(bullet+" bullet is out of level or it's time has ended");
                continue;
            }

            if(bullet.getShotByHero()) {
                Iterator<Enemy> enemyIterator = enemies.iterator();
                while (enemyIterator.hasNext()) {
                    enemy = enemyIterator.next();
                    if (Geometry.checkCollision(bullet.getPlatform(), enemy.getPlatform())) {
                        enemy.changeHP(-bullet.getDamage());
                        bulletIterator.remove();
                        logger.info(bullet+" hero bullet has collided with enemy");
                        if (enemy.getHP() <= 0) enemyIterator.remove();
                    }
                }
            }
            else{
                if (Geometry.checkCollision(bullet.getPlatform(), hero.getPlatform())) {
                    hero.changeHP((int)(-(bullet.getDamage()/hero.getArmorQ())));
                    bulletIterator.remove();
                    logger.info(bullet+" enemy bullet has collided with hero");
                    if (hero.getHP() <= 0){
                        logger.info(" hero died");
                        lives--;
                        if(lives > 0){
                            hero.reincarnate();
                            logger.info(" hero has more lifes, reincarnate");
                        }
                        else {
                            loose = true;
                            logger.info(" hero don't have anymore lives");
                        }
                        return;
                    }
                }
            }
        }
    }

    private boolean checkIfCollidesWithAnything(Platform2D platform2D){
        for(Platform2D platform : platforms)
            if(Geometry.checkCollision(platform2D,platform))return true;

        if( (finish != null) && Geometry.checkCollision(platform2D,finish) )return true;

        if( (hero != null) && Geometry.checkCollision(platform2D,hero.getPlatform()) )return true;

        for(Enemy enemy : enemies)
            if(Geometry.checkCollision(platform2D,enemy.getPlatform()))return true;
        return false;
    }

}
