package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.io.Serializable;
import java.util.List;

/**
 * Enemy class extends Sprite with another move methods
 */
public class Enemy extends Sprite implements Serializable {
    private int hp;

    /**
     * @param xPos x starting position in the game
     * @param yPos y starting position in the game
     * @param hp health points
     */
    public Enemy(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"enemy.png");
        this.xAcc = 3;
        this.yAcc = 4;
        this.xSpeed = 3;
    }

    /**
     * Move enemy with it's speed
     */
    @Override
    public void tryMove() {
        changePos(getxPos() + getxSpeed(), getyPos() + getySpeed());
    }


    /**
     * Updates direction in which enemy moves
     * @param platforms list of platforms in the level
     */
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
