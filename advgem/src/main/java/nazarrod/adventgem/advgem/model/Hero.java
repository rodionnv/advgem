package nazarrod.adventgem.advgem.model;

import java.time.Duration;

import java.io.Serializable;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class Hero extends Sprite implements Serializable{

    public Hero(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"plsm.png");
        this.xAcc = 3;
        this.yAcc = 4;
        this.jumpSpeed = 8;
    }

    public void jump() {
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
