package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.geometry.Platform2D;
import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int playgroundWidth = 800;
    private int PlaygroundHeight = 600;
    private List<Platform2D> platforms = new ArrayList<>();

    public int getPlaygroundWidth() {
        return playgroundWidth;
    }

    public void setPlaygroundWidth(int playgroundWidth) {
        this.playgroundWidth = playgroundWidth;
    }

    public int getPlaygroundHeight() {
        return PlaygroundHeight;
    }

    public void setPlaygroundHeight(int playgroundHeight) {
        PlaygroundHeight = playgroundHeight;
    }

    public List<Platform2D> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform2D> platforms) {
        this.platforms = platforms;
    }

    public void addPlatform(int wpos, int hpos, int width, int height){
        Platform2D platform2D = new Platform2D(wpos,hpos,width,height);
        platforms.add(platform2D);
    }
}
