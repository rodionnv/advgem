package nazarrod.adventgem.advgem.model;

import java.io.Serializable;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Item implements Serializable {

    public enum HpBonusType{
        ONE_TIME, ONLY_WHEN_EQUIPPED;
    }
    public enum Type{
        BOOTS,ARMOR,OTHER;
    }
    private final String name;
    private final int dxSpeed;
    private final int dHP;
    private final double dArmorQ;
    private final HpBonusType hpBonusType;
    private boolean consumable;
    private boolean equipped = false;
    private Type type;

    public Item(String name,int dxSpeed,double dArmorQ, int dHP,HpBonusType hpBonusType,boolean consumable,Type type) {
        this.name = name;
        this.dxSpeed = dxSpeed;
        this.dArmorQ = dArmorQ;
        this.dHP = dHP;
        this.hpBonusType = hpBonusType;
        this.consumable = consumable;
        this.type = type;
    }

    public void equip(Sprite sprite){
        if(this.equipped)return;
        if((type == Type.BOOTS) && (sprite.getBoots() != null))
            sprite.getBoots().unequip(sprite);
        if((type == Type.ARMOR) && (sprite.getArmor() != null))
            sprite.getArmor().unequip(sprite);
        sprite.setSpeedB(dxSpeed);
        sprite.setArmorQ(dArmorQ);
        sprite.setHP(sprite.getHP()+dHP);
        sprite.setBoots(this);
        this.equipped = true;
    }

    public void unequip(Sprite sprite){
        this.equipped = false;
        sprite.setSpeedB(0);
        sprite.setArmorQ(1);
        if(hpBonusType == HpBonusType.ONLY_WHEN_EQUIPPED)sprite.setHP(max(1,sprite.getHP()-dHP));
        if(type == Type.BOOTS)sprite.setBoots(null);
        if(type == Type.ARMOR)sprite.setArmor(null);
    }
}
