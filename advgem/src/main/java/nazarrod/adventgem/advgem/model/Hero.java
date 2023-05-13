package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.utils.Geometry;

import java.time.Duration;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Hero extends Sprite implements Serializable{
    private int xAcc = 3;
    private int yAcc = 4;
    private int jumpSpeed = 8;
    private int current_jumps = 0;
    private boolean falling = false;
    private boolean orientation = true; //True - faced to the right, False - to the left

    public Hero(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp);
    }

    public int getxAcc() {
        return xAcc;
    }

    public void setxAcc(int xAcc) {
        this.xAcc = xAcc;
    }

    public int getyAcc() {
        return yAcc;
    }

    public void setyAcc(int yAcc) {
        this.yAcc = yAcc;
    }

    public int getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(int jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    public boolean getOrientation() {
        return orientation;
    }

    public Platform2D getPlatform() {
        return new Platform2D(getxPos(),getyPos(),getWidth(),getHeight());
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public boolean isStanding(List<Platform2D> platforms){
        for(int i = xPos;i <= xPos+width;i++){
            for(Platform2D platform : platforms){
                if(Geometry.checkBelongs(i,yPos+height+1,platform)){current_jumps = 0;return true;}
            }
        }
        return false;
    }

    public void updateFallingState(List<Platform2D> platforms){
        if(isStanding(platforms)){
            if(falling)setySpeed(getySpeed()- yAcc);
            falling = false;
        }
        else{
            if(!falling)setySpeed(getySpeed()+ yAcc);
            falling = true;
        }
    }

    public void tryMove(){
        Platform2D newBox = new Platform2D(xPos+getxSpeed(),yPos+getySpeed(),width,height);
        changePos(getxPos()+getxSpeed(), getyPos()+getySpeed());
    }

    public void moveRight(){
        setxSpeed(getxAcc());
        orientation = true;
    }
    public void moveLeft(){
        setxSpeed(-getxAcc());
        orientation = false;
    }

    public void jump() {
//        System.out.println(current_jumps);
        current_jumps++;
        if(current_jumps < 2)push(0,-jumpSpeed,true);
    }

    public void push(int xDelta,int yDelta,boolean isJump){
        setxSpeed(getxSpeed()+xDelta);
        setySpeed(getySpeed()+yDelta);
        Timer pushTimer = new Timer();
        pushTimer.schedule(new TimerTask() {
            private Instant startTime = null;
            @Override
            public void run() {
                if (startTime == null)startTime = Instant.now();
                Duration elapsed = Duration.between(startTime, Instant.now());
                if (elapsed.toMillis() >= 300) {
                    setxSpeed(getxSpeed() - xDelta);
                    setySpeed(getySpeed() - yDelta);
                    pushTimer.cancel();
                }
            }
        },0,16);
    }
}
