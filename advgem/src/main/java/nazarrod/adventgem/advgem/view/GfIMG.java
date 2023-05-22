package nazarrod.adventgem.advgem.view;

import javafx.scene.image.Image;

/**
 * Enum object that contains Image instances for all graphic objects
 */
public enum GfIMG {
    PLATFORM("platform.png"),
    FINISH("finish.png"),
    CLOSED_FINISH("closed_finish.png"),
    HERO("hero.png"),
    ENEMY("enemy.png"),
    RED_BULLET("bullet_red.png"),
    BLUE_BULLET("bullet_blue.png"),
    SWORD("sword.png"),
    LIVE("live.png"),
    BOOTS("boots.png"),
    ARMOR("armor.png"),
    APPLE("apple.png"),
    CHEST("chest.png"),
    KEY("key.png"),
    HP("hp.png");

    public final Image img;

    /**
     * Get Image instance from the file
     * @param gfName Image file name
     */
    GfIMG(String gfName){
        this.img = new Image(gfName);
    }
}
