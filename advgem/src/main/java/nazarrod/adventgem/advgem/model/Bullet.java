package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;

public class Bullet implements Serializable {

    private final String gfName;
    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 8;
    private final int width = 24;
    private final int height = 24;

    public Bullet(int xPos, int yPos,boolean orientation,String gfName) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.gfName = gfName;
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

    public int getxSpeed() {
        return xSpeed;
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

    public void move(){
        setxPos(getxPos()+getxSpeed());
    }
}
