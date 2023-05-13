package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Bullet implements Serializable {
    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 8;
    private final int width = 24;
    private final int height = 24;

    public Bullet(int xPos, int yPos,boolean orientation) {
        this.xPos = xPos;
        this.yPos = yPos;
        if(!orientation)xSpeed *= -1;
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

    public int getxSpeed() {
        return xSpeed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move(){
        setxPos(getxPos()+getxSpeed());
        System.out.println(this+" "+xPos);
    }
}
