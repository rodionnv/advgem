package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Bullet implements Serializable {

    private final String gfName;
    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 8;
    private final int damage;
    private final boolean shotBy;
    private final int width = 24;
    private final int height = 24;

    public Bullet(int xPos, int yPos, int damage, Sprite.ORIENTATION orientation, String gfName, boolean shotBy) {
        //shot_by = 1? - hero's shot; shot_by = 0? enemy
        this.xPos = xPos;
        this.yPos = yPos;
        this.gfName = gfName;
        this.shotBy = shotBy;
        this.damage = damage;
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

    public String getGfName() {
        return gfName;
    }

    public int getDamage() {
        return damage;
    }

    public boolean getShotBy() {
        return shotBy;
    }

    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }

    public void move(){
        setxPos(getxPos()+getxSpeed());
    }
}
