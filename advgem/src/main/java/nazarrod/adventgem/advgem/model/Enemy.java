package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Enemy extends Sprite implements Serializable {
    private int xSpeed = 3;
    private int ySpeed = 0;
    private int hp;

    public Enemy(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp);
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
