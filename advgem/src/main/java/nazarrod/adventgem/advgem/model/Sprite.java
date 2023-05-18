package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Sprite implements Serializable {
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
    protected int startHP;
    public int current_jumps = 0;
    protected boolean falling = false;
    protected boolean hittingHead = false;
    protected boolean hittingLetf = false;
    protected boolean hittingRight = false;
    private boolean orientation = true; //True - faced to the right, False - to the left

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
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public void changePos(int x, int y){
        setxPos(x);
        setyPos(y);
    }

    public void moveRight(){ //TODO Make it better so sprite doesn't stop when A&D pressed at the same time
        setxSpeed(getxAcc());
        setOrientation(true);
    }
    public void moveLeft(){
        setxSpeed(-getxAcc());
        setOrientation(false);
    }

    public void reincarnate(){
        setxPos(startXPos);
        setyPos(startYPos);
        setHP(startHP);
    }

    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }

    public void tryMove(List<Platform2D> platforms) {
        Platform2D newBox = new Platform2D(xPos + getxSpeed(), yPos + getySpeed(), width, height);
        int dX = getxSpeed();
        int dY = getySpeed();
        if (!falling) dY = min(0, getySpeed());
        if (hittingLetf) dX = max(0, getxSpeed());
        if (hittingRight) dX = min(0, getxSpeed());

        changePos(getxPos() + dX, getyPos() + getySpeed());
    }

    public void updateStates(List<Platform2D> platforms) {
        updateFallingState(platforms);
        updateHittingLeftState(platforms);
        updateHittingRightState(platforms);
        //updateHittingHeadState(platforms); //TODO !!!
    }

    public boolean isStanding(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos,yPos+height+yAcc,platform)){current_jumps = 0;return true;}
            if(Geometry.checkBelongs(xPos+width,yPos+height+yAcc,platform)){current_jumps = 0;return true;}
        }
        return false;
    }

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

    public void updateHittingLeftState(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos-xAcc,yPos,platform)){hittingLetf = true;return;}
            if(Geometry.checkBelongs(xPos-xAcc,yPos+height,platform)){hittingLetf = true;return;}
        }
        hittingLetf = false;
    }

    public void updateHittingRightState(List<Platform2D> platforms){
        for(Platform2D platform : platforms){
            if(Geometry.checkBelongs(xPos+width+xAcc,yPos,platform)){hittingRight = true;return;}
            if(Geometry.checkBelongs(xPos+width+xAcc,yPos+height,platform)){hittingRight = true;return;}
        }
        hittingRight = false;
    }

}
