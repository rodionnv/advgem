package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

import static java.lang.Math.min;

public class Item implements Serializable {

    public enum HpBonusType{
        ONE_TIME, ONLY_WHEN_EQUIPPED;
    }
    private final String name;
    private final int dxSpeed;
    private final int dHP;
    private final double dArmorQ;
    private final HpBonusType hpBonusType;
    private boolean consumable;
    public boolean equipped = false;

    public Item(String name,int dxSpeed,double dArmorQ, int dHP,HpBonusType hpBonusType,boolean consumable) {
        this.name = name;
        this.dxSpeed = dxSpeed;
        this.dArmorQ = dArmorQ;
        this.dHP = dHP;
        this.hpBonusType = hpBonusType;
        this.consumable = consumable;
    }

    public void equip(Sprite sprite){
        if(this.equipped)return;
        sprite.setSpeedB(dxSpeed);
        sprite.setArmorQ(dArmorQ);
        sprite.setHP(sprite.getHP()+dHP);
        this.equipped = true;
    }

    public void unequip(Sprite sprite){
        this.equipped = false;
        sprite.setSpeedB(0);
        sprite.setArmorQ(1);
        if(hpBonusType == HpBonusType.ONLY_WHEN_EQUIPPED)sprite.setHP(min(1,sprite.getHP()-dHP));
    }
}
