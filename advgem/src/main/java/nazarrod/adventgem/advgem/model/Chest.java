package nazarrod.adventgem.advgem.model;

import java.io.Serializable;
import java.util.List;

public class Chest implements Serializable {

    private int xPos;
    private int yPos;
    private List<Item> contents;
    private boolean opened = false;
    private final int applesCnt;
    private final int bulletsCnt;
    private final boolean containsKey;

    public Chest(List<Item> contents,int xPos,int yPos,int applesCnt,int bulletsCnt,boolean containsKey) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.contents = contents;
        this.applesCnt = applesCnt;
        this.bulletsCnt = bulletsCnt;
        this.containsKey = containsKey;
    }

    public void giveItems(Hero hero){
        hero.setApplesCnt(hero.getApplesCnt() + applesCnt);
        hero.setBulletsCnt(hero.getBulletsCnt() + bulletsCnt);
        hero.setHasKey(hero.isHasKey() | containsKey);
        for(Item item : contents){
            if(item.getType() == Item.Type.BOOTS)hero.addBoots(item);
            else hero.addArmor(item);
        }
        opened = true;
    }

    public Platform2D getPlatform(){
        Platform2D platform = new Platform2D(xPos,yPos,50,50);
        return platform;
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

    public List<Item> getContents() {
        return contents;
    }

    public void setContents(List<Item> contents) {
        this.contents = contents;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public int getApplesCnt() {
        return applesCnt;
    }

    public int getBulletsCnt() {
        return bulletsCnt;
    }

    public boolean isContainsKey() {
        return containsKey;
    }

    public void addItem(Item item){
        contents.add(item);
    }
}
