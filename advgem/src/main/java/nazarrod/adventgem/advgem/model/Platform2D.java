package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

public class Platform2D implements Serializable {
    /*
    * 2D Platform
    * wpos,hpos - coords of the bottom left corner
     */
    private int wpos;
    private int hpos;
    private int width;
    private int height;

    public Platform2D(int wpos, int hpos, int width, int height) {
        this.wpos = wpos;
        this.hpos = hpos;
        this.width = width;
        this.height = height;
    }

    public int getWpos() {
        return wpos;
    }

    public void setWpos(int wpos) {
        this.wpos = wpos;
    }

    public int getHpos() {
        return hpos;
    }

    public void setHpos(int hpos) {
        this.hpos = hpos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
