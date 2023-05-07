package nazarrod.adventgem.advgem.model;

import java.io.Serializable;
import java.util.List;

public class Hero implements Serializable {

    private int xPos = 0;
    private int yPos = 0;
    private final int xSpeed = 5;
    private final int ySpeed = 5;
    private final int width = 50;
    private final int height = 60;
    private int hp = 100;

    public Hero(int xPos, int yPos, int hp) {
        this.xPos = xPos;
        this.yPos = yPos;
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

    public void increaseHP(int x){
        setHp(getHp() + x);
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
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

    public boolean isStanding(List<Platform2D> platforms){
        // TODO NOT TESTED YET!!!!
        for(int i = xPos;i <= xPos+width;i++){
            for(Platform2D platform : platforms){
                if(checkBelongs(i,yPos+height+1,platform))return true;
            }
        }
        return false;
    }

    private boolean checkBelongs(int x,int y,Platform2D p){
        int xp1 = p.getX();
        int yp1 = p.getY();
        int xp2 = xp1+p.getWidth();
        int yp2 = yp1+p.getHeight();
        return xp1 <= x && x <= xp2 && yp1 <= y && y <= yp2;
    }
}
