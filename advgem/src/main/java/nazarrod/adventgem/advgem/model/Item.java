package nazarrod.adventgem.advgem.model;

import java.io.Serializable;


/**
 * Item that can be equipped by a sprite
 */
public class Item implements Serializable {

    /**
     * Defines type of the HP bonus
     */
    public enum HpBonusType{
        ONE_TIME, ONLY_WHEN_EQUIPPED;
    }

    /**
     * Defines the type of the item
     */
    public enum Type{
        BOOTS,ARMOR;
    }
    private final String name;
    private final int speedB;
    private final int dHP;
    private final double dArmorQ;
    private final HpBonusType hpBonusType;
    private final boolean consumable;
    private boolean equipped = false;
    private Type type;

    /**
     * @param name name of the item
     * @param speedB speed bonus
     * @param dArmorQ armor coefficient bonus
     * @param dHP hp bonus
     * @param hpBonusType hp bonus type
     * @param consumable is it consumable or constant
     * @param type type of the item
     */
    public Item(String name, int speedB, double dArmorQ, int dHP, HpBonusType hpBonusType, boolean consumable, Type type) {
        this.name = name;
        this.speedB = speedB;
        this.dArmorQ = dArmorQ;
        this.dHP = dHP;
        this.hpBonusType = hpBonusType;
        this.consumable = consumable;
        this.type = type;
    }

    public int getSpeedB() {
        return speedB;
    }

    public int getdHP() {
        return dHP;
    }

    public double getdArmorQ() {
        return dArmorQ;
    }

    public String getName() {
        return name;
    }

    public HpBonusType getHpBonusType() {
        return hpBonusType;
    }

    public boolean isConsumable() {
        return consumable;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
