package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Enemy extends Sprite implements Serializable {
    private int hp;

    public Enemy(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"enemy.png");
        this.xAcc = 3;
        this.yAcc = 4;
        this.xSpeed = 3;
    }
    @Override
    public void tryMove() {
        changePos(getxPos() + getxSpeed(), getyPos() + getySpeed());
    }

    public void updateOrientation(List<Platform2D> platforms){
        boolean left = false;
        boolean right = false;
        for(Platform2D platform : platforms) {
            if (Geometry.checkBelongs(xPos, yPos + height + 1, platform))
                left = true;
            if (Geometry.checkBelongs(xPos + width, yPos + height + 1, platform))
                right = true;
            if(left && right)return;
        }
        if(!left){
            if(!getOrientation())setxSpeed(xAcc);
            setOrientation(true);
        }
        if(!right){
            if(getOrientation())setxSpeed(-xAcc);
            setOrientation(false);
        }
    }
}
