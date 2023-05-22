package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

/**
 * Model of the bullet. Bullet is an object that flies and damages sprites
 */
public class Bullet implements Serializable {

    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 8;
    private final int xStart;
    private final int liveDist;
    private final int damage;
    private final boolean isSword;
    private final boolean shotByHero;
    private final int width = 24;
    private final int height = 24;

    /**
     * @param xPos starting x position
     * @param yPos starting y position
     * @param damage how many damage it gives
     * @param orientation direction to fly
     * @param isSword is it a sword hit
     * @param shotByHero is it shot by hero or an enemy
     * @param liveDist how much time it has to live in the game
     */
    public Bullet(int xPos, int yPos, int damage, Sprite.ORIENTATION orientation, boolean isSword, boolean shotByHero,int liveDist) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isSword = isSword;
        this.shotByHero = shotByHero;
        this.damage = damage;
        this.xStart = xPos;
        this.liveDist = liveDist;
        if(orientation == Sprite.ORIENTATION.LEFT)xSpeed *= -1;
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

    public boolean isSword() {
        return isSword;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getShotByHero() {
        return shotByHero;
    }

    public int getxStart() {
        return xStart;
    }

    public int getLiveDist() {
        return liveDist;
    }

    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }

    /**
     * Move horizontally
     */
    public void move(){
        setxPos(getxPos()+getxSpeed());
    }
}
