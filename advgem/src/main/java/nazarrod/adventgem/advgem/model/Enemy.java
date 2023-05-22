package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.List;

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
            if(left && right)break;
        }
        for(Platform2D platform : platforms) {
            if (Geometry.checkBelongs(xPos+getWidth()+1, yPos, platform)) {
                left = true;
                right = false;
                break;
            }
            if (Geometry.checkBelongs(xPos-1, yPos, platform)) {
                right = true;
                left = false;
                break;
            }
        }
        if(!left){
            if(getOrientation() == ORIENTATION.LEFT)setxSpeed(xAcc);
            setOrientation(ORIENTATION.RIGHT);
        }
        if(!right){
            if(getOrientation() == ORIENTATION.RIGHT)setxSpeed(-xAcc);
            setOrientation(ORIENTATION.LEFT);
        }
    }
}
