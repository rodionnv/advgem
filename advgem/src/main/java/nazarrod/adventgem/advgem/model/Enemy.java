package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;

public class Enemy extends Sprite implements Serializable {
    private int hp;

    public Enemy(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"enemy.png");
        this.xAcc = 3;
        this.yAcc = 4;
    }

//    public void updateOrientation(){
//        if(getOrientation()){
//            if(!Geometry.checkCollision())
//        }
//    }
}
