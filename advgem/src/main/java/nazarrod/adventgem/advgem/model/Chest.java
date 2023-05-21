package nazarrod.adventgem.advgem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chest implements Serializable {
    private List<Item> contents = new ArrayList<>();
    private boolean opened = false;
    private final int applesCnt;
    private final int bulletsCnt;
    private final boolean hasKey;

    public Chest(List<Item> contents,int applesCnt,int bulletsCnt,boolean hasKey) {
        this.contents = contents;
        this.applesCnt = applesCnt;
        this.bulletsCnt = bulletsCnt;
        this.hasKey = hasKey;
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

    public boolean isHasKey() {
        return hasKey;
    }

    public void addItem(Item item){
        contents.add(item);
    }
}
