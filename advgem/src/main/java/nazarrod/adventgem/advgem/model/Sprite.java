package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.List;

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
    protected int current_jumps = 0;
    protected boolean falling = false;
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

    public void tryMove(){
        Platform2D newBox = new Platform2D(xPos+getxSpeed(),yPos+getySpeed(),width,height);
        changePos(getxPos()+getxSpeed(), getyPos()+getySpeed());
    }

    public boolean isStanding(List<Platform2D> platforms){
        for(int i = xPos;i <= xPos+width;i++){
            for(Platform2D platform : platforms){
                if(Geometry.checkBelongs(i,yPos+height+1,platform)){current_jumps = 0;return true;}
            }
        }
        return false;
    }

    public void updateFallingState(List<Platform2D> platforms){
        if(isStanding(platforms)){
            if(falling)setySpeed(getySpeed()- yAcc);
            falling = false;
        }
        else{
            if(!falling)setySpeed(getySpeed()+ yAcc);
            falling = true;
        }
    }
}
