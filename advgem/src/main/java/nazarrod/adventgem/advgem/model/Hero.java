package nazarrod.adventgem.advgem.model;

import java.time.Duration;

import java.io.Serializable;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.min;

public class Hero extends Sprite implements Serializable{

    public enum Weapon{
        SWORD,BULLET;
    }

    private boolean hasKey = false;
    private Weapon weapon = Weapon.SWORD;

    public Hero(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"hero.png");
        this.xAcc = 3;
        this.yAcc = 8;
        this.jumpSpeed = 16;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void jump() {
        current_jumps++;
        if(current_jumps == 1)push(-jumpSpeed,true);
        if(current_jumps == 2)push(-12,true);
    }

    public void push(int yDelta,boolean isJump){
        setySpeed(getySpeed()+yDelta);
        Timer pushTimer = new Timer();
        pushTimer.schedule(new TimerTask() {
            private Instant startTime = null;
            @Override
            public void run() {
                if (startTime == null)startTime = Instant.now();
                Duration elapsed = Duration.between(startTime, Instant.now());
                if (elapsed.toMillis() >= 300) {
                    setySpeed(getySpeed() - yDelta);
                    pushTimer.cancel();
                }
            }
        },0,16);
    }
}
