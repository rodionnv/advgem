package nazarrod.adventgem.advgem.view;

import javafx.scene.image.Image;

public enum GfIMG {
    PLATFORM("platform.png"),
    FINISH("finish.png"),
    CLOSED_FINISH("closed_finish.png"),
    HERO("hero.png"),
    ENEMY("enemy.png"),
    RED_BULLET("bullet_red.png"),
    BLUE_BULLET("bullet_blue.png"),
    LIVE("live.png"),
    HP("hp.png");

    public final Image img;

    GfIMG(String gfName){
        this.img = new Image(gfName);
    }
}
