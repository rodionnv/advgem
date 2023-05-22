package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;


/**
 * Movable object with health and abilities to shoot
 */
public class Sprite implements Serializable {
    private final static Logger logger = Logger.getLogger(Sprite.class.getName());
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
            fh = new FileHandler("./Logs/sprite_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    /**
     * Direction to move
     */
    public enum ORIENTATION{
        LEFT,RIGHT
    }
    protected final String gfName;
    protected int xPos;
    protected int yPos;
    protected final int startXPos;
    protected final int startYPos;
    protected final int width = 50;
    protected final int height = 60;
    protected int xSpeed = 0;
    protected int ySpeed = 0;
    protected int xAcc = 0;
    protected int yAcc = 0;
    protected int jumpSpeed = 0;
    protected int HP;
    private int speedB = 0;
    private double armorQ = 1;
    protected int startHP;
    public int current_jumps = 0;
    protected boolean falling = false;
    protected boolean hittingTop = false;
    protected boolean hittingLetf = false;
    protected boolean hittingRight = false;
    private ORIENTATION orientation = ORIENTATION.RIGHT;

    /**
     * @param xPos x position in the game
     * @param yPos x position in the game
     * @param HP health points
     * @param gfName name of the graphic file
     */
    public Sprite(int xPos, int yPos, int HP,String gfName) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.startXPos = xPos;
        this.startYPos = yPos;
        this.HP = HP;
        this.startHP = HP;
        this.gfName = gfName;
    }

    public String getGfName() {
        return gfName;
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

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void changeHP(int x){
        setHP(getHP() + x);
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public int getxAcc() {
        return xAcc;
    }

    public void setxAcc(int xAcc) {
        this.xAcc = xAcc;
    }

    public int getyAcc() {
        return yAcc;
    }

    public void setyAcc(int yAcc) {
        this.yAcc = yAcc;
    }

    public int getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }
    public int getSpeedB() {
        return speedB;
    }
    public void setSpeedB(int speedB) {
        this.speedB = speedB;
    }

    public double getArmorQ() {
        return armorQ;
    }

    public void setArmorQ(double armorQ) {
        this.armorQ = armorQ;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ORIENTATION getOrientation() {
        return orientation;
    }

    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
    }

    public void changePos(int x, int y){
        setxPos(x);
        setyPos(y);
    }

    public void moveRight(){
        setxSpeed(getxAcc()+speedB);
        setOrientation(ORIENTATION.RIGHT);
    }
    public void moveLeft(){
        setxSpeed(-(getxAcc()+speedB));
        setOrientation(ORIENTATION.LEFT);
    }

    public void reincarnate(){
        setxPos(startXPos);
        setyPos(startYPos);
        setHP(startHP);
    }


    /**
     * @return Platform2D instance of this Sprite
     */
    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }


    /**
     * Calls an update ot the current sprite state and moves if it is possible
     */
    public void tryMove() {
        int dX = getxSpeed();
        int dY = getySpeed();
        if (!falling) dY = min(0, getySpeed());
        if (hittingTop)dY = max(0, getySpeed());
        if (hittingLetf) dX = max(0, getxSpeed());
        if (hittingRight) dX = min(0, getxSpeed());

        changePos(getxPos() + dX, getyPos() + dY);
    }


    /**
     * Updates sprite state (standing, facing the wall or hitting head)
     * @param platforms platform list on the level
     */
    public void updateStates(List<Platform2D> platforms) {
        updateFallingState(platforms);
        if(!falling)drop(platforms);
        updateHittingLeftState(platforms);
        updateHittingRightState(platforms);
        updateHittingTopState(platforms);
    }

    public boolean isStanding(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos,yPos+height+yAcc,platform)){return true;}
            if(Geometry.checkBelongs(xPos+width,yPos+height+yAcc,platform)){return true;}
        }
        return false;
    }

    /**
     * Updates counter of the consequtive jumps when hero is standing
     * @param platforms list of the platforms on the level
     */
    public void updJumps(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos,yPos+height+yAcc,platform))current_jumps = 0;
            if(Geometry.checkBelongs(xPos+width,yPos+height+yAcc,platform))current_jumps = 0;
        }
    }


    /**
     * Updates falling state
     * @param platforms list of the platforms on the level
     */
    private void updateFallingState(List<Platform2D> platforms){
        if(isStanding(platforms)){
            if(falling)setySpeed(getySpeed() - yAcc);
            falling = false;
        }
        else{
            if(!falling)setySpeed(getySpeed() + yAcc);
            falling = true;
        }
    }

    /**
     * Updates hitting wall on the left state
     * @param platforms list of the platforms on the level
     */
    public void updateHittingLeftState(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos-(xAcc+speedB),yPos,platform)){hittingLetf = true;return;}
            if(Geometry.checkBelongs(xPos-(xAcc+speedB),yPos+height,platform)){hittingLetf = true;return;}
        }
        hittingLetf = false;
    }

    /**
     * Updates hitting wall on the right state
     * @param platforms list of the platforms on the level
     */
    public void updateHittingRightState(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos+width+(xAcc+speedB),yPos,platform)){hittingRight = true;return;}
            if(Geometry.checkBelongs(xPos+width+(xAcc+speedB),yPos+height,platform)){hittingRight = true;return;}
        }
        hittingRight = false;
    }

    /**
     * Updates hitting wall on the top state
     * @param platforms list of the platforms on the level
     */
    public void updateHittingTopState(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos,yPos-max(yAcc,abs(ySpeed)),platform)){hittingTop = true;return;}
            if(Geometry.checkBelongs(xPos+width,yPos-max(yAcc,abs(ySpeed)),platform)){hittingTop = true;return;}
        }
        hittingTop = false;
    }

    /**
     * Puts sprite to the ground if it is floating in the air
     * @param platforms list of the platforms on the level
     */
    private void drop(List<Platform2D> platforms){
        boolean ok = true;
        while(ok) {
            for (Platform2D platform : platforms) {
                if (Geometry.checkBelongs(xPos, yPos + height + 1, platform) ||
                        Geometry.checkBelongs(xPos + width, yPos + height + 1, platform)) {
                    ok = false;
                }
            }
            if(ok)changePos(xPos,yPos+1);
        }
    }


    /**
     * Checks if hero is out of bounds
     * @param x width of the allowed area
     * @param y height of the allowrd area
     * @return
     */
    public boolean outOfLevel(int x,int y){
        setLogger();
        if(yPos < 0)return false;
        if(Geometry.outOfBounds(getPlatform(),x,y)) {
            logger.info("Sprite " + this + " fell out of level");
            return true;
        }
        return false;
    }

}
