package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

/**
 * Platform class that is used to compute all logics and collisions of the game
 */
public class Platform2D implements Serializable {
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    /**
     * @param xPos x position in the game
     * @param yPos y position in the game
     * @param width
     * @param height
     */
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
