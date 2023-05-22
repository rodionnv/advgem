package nazarrod.adventgem.advgem.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Model of the chest that contains items
 */
public class Chest implements Serializable {

    private int xPos;
    private int yPos;
    private List<Item> contents;
    private boolean opened = false;
    private int applesCnt;
    private int bulletsCnt;
    private boolean containsKey;

    private final static Logger logger = Logger.getLogger(Chest.class.getName());
    private static boolean alreadySet = false;
    /**
     * Configures logger for class
     */
    private static void setLogger(){
        if(alreadySet)return;
        alreadySet = true;
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        boolean dirCreated = new File("./Logs/").mkdirs();
        try {
            fh = new FileHandler("./Logs/chest_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    /**
     * @param contents List of items that are in this chest
     * @param xPos x position in the game
     * @param yPos y position in the game
     * @param applesCnt how many apples it contains
     * @param bulletsCnt how many bullets it contains
     * @param containsKey is this chest contains key to the finish
     */
    public Chest(List<Item> contents,int xPos,int yPos,int applesCnt,int bulletsCnt,boolean containsKey) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.contents = contents;
        this.applesCnt = applesCnt;
        this.bulletsCnt = bulletsCnt;
        this.containsKey = containsKey;
    }

    /**
     * Give chest content to the sprite
     * @param hero sprite which will get all the chest contents
     */
    public void giveItems(Hero hero){
        setLogger();
        hero.setApplesCnt(hero.getApplesCnt() + applesCnt);
        hero.setBulletsCnt(hero.getBulletsCnt() + bulletsCnt);
        hero.setHasKey(hero.isHasKey() | containsKey);
        for(Item item : contents){
            if(item.getType() == Item.Type.BOOTS)hero.addBoots(item);
            else hero.addArmor(item);
        }
        opened = true;
        logger.info("Chest "+ this + " was opened");
    }

    /**
     * @return platform instance of this object
     */
    public Platform2D getPlatform(){
        Platform2D platform = new Platform2D(xPos,yPos,50,50);
        return platform;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public List<Item> getContents() {
        return contents;
    }

    public void setContents(List<Item> contents) {
        this.contents = contents;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setApplesCnt(int applesCnt) {
        this.applesCnt = applesCnt;
    }

    public void setBulletsCnt(int bulletsCnt) {
        this.bulletsCnt = bulletsCnt;
    }

    public int getApplesCnt() {
        return applesCnt;
    }

    public int getBulletsCnt() {
        return bulletsCnt;
    }

    public void setContainsKey(boolean containsKey) {
        this.containsKey = containsKey;
    }

    public boolean isContainsKey() {
        return containsKey;
    }

    public void addItem(Item item){
        contents.add(item);
    }
}
