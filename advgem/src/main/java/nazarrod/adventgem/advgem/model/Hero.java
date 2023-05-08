package nazarrod.adventgem.advgem.model;

import java.time.Duration;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Hero implements Serializable {

    private int xPos = 0;
    private int yPos = 0;
    private int xSpeed = 0;
    private int ySpeed = 0;
    private final int width = 50;
    private final int height = 60;
    private int hp = 100;
    private int current_jumps = 0;

    private boolean falling = false;

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

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
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
        for(int i = xPos;i <= xPos+width;i++){
            for(Platform2D platform : platforms){
                if(checkBelongs(i,yPos+height+1,platform)){current_jumps = 0;return true;}
            }
        }
        return false;
    }

    public void updateFallingState(List<Platform2D> platforms){
        if(isStanding(platforms)){
            if(falling)setySpeed(getySpeed()-3);
            falling = false;
        }
        else{
            if(!falling)setySpeed(getySpeed()+3);
            falling = true;
        }
    }

    public void jump() {
        System.out.println(current_jumps);
        current_jumps++;
        if(current_jumps < 2)push(0,-8,true);
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

    private boolean checkBelongs(int x,int y,Platform2D p){
        int xp1 = p.getX();
        int yp1 = p.getY();
        int xp2 = xp1+p.getWidth();
        int yp2 = yp1+p.getHeight();
        return xp1 <= x && x <= xp2 && yp1 <= y && y <= yp2;
    }
}
