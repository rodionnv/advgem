package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Platform2D implements Serializable {
    /*
    * 2D Platform
    * wpos,hpos - coords of the bottom left corner
     */
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Platform2D(int xPos, int yPos, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return xPos;
    }

    public void setX(int wpos) {
        this.xPos = wpos;
    }

    public int getY() {
        return yPos;
    }

    public void setY(int hpos) {
        this.yPos = hpos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
