package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Enemy implements Serializable {

    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 3;
    private int ySpeed = 0;
    private final int width = 50;
    private final int height = 60;
    private int hp = 100;

    public Enemy(int xPos, int yPos, int hp) {
        this.xPos = xPos;
        this.yPos = yPos;
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

    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }

    public void tryMove(){
        Platform2D newBox = new Platform2D(xPos+getxSpeed(),yPos+getySpeed(),width,height);
        changePos(getxPos()+getxSpeed(), getyPos()+getySpeed());
    }
    public void changePos(int x,int y){
        setxPos(x);
        setyPos(y);
    }
}
