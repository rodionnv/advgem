package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Hero implements Serializable {

    private int xpos = 0;
    private int ypos = 0;
    private final int width = 40;
    private final int height = 60;
    private int hp = 100;

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
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

    public void changePos(int x,int y){
        setXpos(x);
        setYpos(y);
    }
}
