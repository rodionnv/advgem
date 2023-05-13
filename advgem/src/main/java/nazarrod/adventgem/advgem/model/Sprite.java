package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Sprite implements Serializable {
    protected int xPos;
    protected int yPos;
    protected final int startXPos;
    protected final int startYPos;
    protected final int width = 50;
    protected final int height = 60;
    protected int xSpeed = 0;
    protected int ySpeed = 0;
    protected int hp;

    public Sprite(int xPos, int yPos, int hp) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.startXPos = xPos;
        this.startYPos = yPos;
        this.hp = hp;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void changeHP(int x){
        setHp(getHp() + x);
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void changePos(int x, int y){
        setxPos(x);
        setyPos(y);
    }

    public void reincarnate(){
        setxPos(startXPos);
        setyPos(startYPos);
    }
}
