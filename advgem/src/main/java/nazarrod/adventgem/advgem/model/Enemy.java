package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Enemy extends Sprite implements Serializable {
    private int hp;

    public Enemy(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp);
        this.xAcc = 3;
        this.yAcc = 4;
    }
}
