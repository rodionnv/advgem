package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.editor.Editor;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Math.max;

public class Hero extends Sprite implements Serializable{

    public enum Weapon{
        SWORD,BULLET;
    }

    private boolean hasKey = false;
    private int applesCnt;
    private int bulletsCnt;
    private Weapon weapon = Weapon.SWORD;
    protected Item boots = null;
    protected Item armor = null;
    private List<Item> bootsList = new ArrayList<>();
    private List<Item> armorList = new ArrayList<>();

    private final static Logger logger = Logger.getLogger(Hero.class.getName());
    private static boolean alreadySet = false;
    private static void setLogger(){
        if(alreadySet)return;
        alreadySet = true;
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        FileHandler fh;
        boolean dirCreated = new File("./Logs/").mkdirs();
        try {
            fh = new FileHandler("./Logs/hero_logs.txt");
        }catch (IOException e){
            return;
        }
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }

    public Hero(int xPos, int yPos, int hp) {
        super(xPos, yPos, hp,"hero.png");
        this.xAcc = 4;
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
    public Item getBoots() {
        return boots;
    }

    public void setBoots(Item boots) {
        this.boots = boots;
    }

    public Item getArmor() {
        return armor;
    }

    public void setArmor(Item armor) {
        this.armor = armor;
    }

    public List<Item> getBootsList() {
        return bootsList;
    }

    public void setBootsList(List<Item> bootsList) {
        this.bootsList = bootsList;
    }

    public void addBoots(Item boots){
        bootsList.add(boots);
    }

    public List<Item> getArmorList() {
        return armorList;
    }

    public void setArmorList(List<Item> armorList) {
        this.armorList = armorList;
    }

    public void addArmor(Item armor){
        armorList.add(armor);
    }

    public int getApplesCnt() {
        return applesCnt;
    }

    public void setApplesCnt(int applesCnt) {
        this.applesCnt = applesCnt;
    }

    public int getBulletsCnt() {
        return bulletsCnt;
    }

    public void setBulletsCnt(int bulletsCnt) {
        this.bulletsCnt = bulletsCnt;
    }

    public void equip(Item item){
        setLogger();
        if(item.isEquipped())return;
        if((item.getType() == Item.Type.BOOTS) && (this.getBoots() != null))
            unequip(this.getBoots());
        if((item.getType() == Item.Type.ARMOR) && (this.getArmor() != null))
            unequip(this.getArmor());
        setSpeedB(item.getSpeedB());
        setArmorQ(item.getdArmorQ());
        setHP(getHP()+item.getdHP());
        if((item.getType() == Item.Type.BOOTS))setBoots(item);
        else setArmor(item);
        item.setEquipped(true);
        logger.info("Hero equipped "+item);
    }

    public void unequip(Item item){
        item.setEquipped(false);
        setSpeedB(0);
        setArmorQ(1);
        if(item.getHpBonusType() == Item.HpBonusType.ONLY_WHEN_EQUIPPED)setHP(max(1,getHP()-item.getdHP()));
        if(item.getType() == Item.Type.BOOTS)setBoots(null);
        if(item.getType() == Item.Type.ARMOR)setArmor(null);
        logger.info("Hero unequipped "+item);
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
