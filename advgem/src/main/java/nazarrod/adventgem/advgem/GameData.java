package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.model.Platform2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData implements Serializable {
    private final Random rnd = new Random();
    private String levelName = "NewLevel" + rnd.nextInt(1000000007); // Add random string to avoid name duplication
    private int playgroundWidth = 1920;
    private int PlaygroundHeight = 1080;
    private List<Platform2D> platforms = new ArrayList<>();

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

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
