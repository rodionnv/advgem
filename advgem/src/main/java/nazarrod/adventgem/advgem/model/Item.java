package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Item implements Serializable {

    public enum HpBonusType{
        ONE_TIME, ONLY_WHEN_EQUIPPED;
    }
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
