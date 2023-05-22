package nazarrod.adventgem.advgem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    @Test
    void equipBootsTest() {
        Hero hero = new Hero(0,0,100);
        Item oldItem = new Item("old item",1,1,0, Item.HpBonusType.ONE_TIME,true, Item.Type.BOOTS);
        hero.setBoots(oldItem); //old item to be unequipped
        Item item = new Item("test item",5,1,0, Item.HpBonusType.ONLY_WHEN_EQUIPPED,false, Item.Type.BOOTS);
        hero.equip(item);
        Item item2 = hero.getBoots();
        assert (item2 == item && !oldItem.isEquipped() && item2.isEquipped());
    }

    @Test
    void equipArmorTest() {
        Hero hero = new Hero(0,0,100);
        Item oldItem = new Item("old item",1,1,0, Item.HpBonusType.ONE_TIME,true, Item.Type.ARMOR);
        hero.setBoots(oldItem); //old item to be unequipped
        Item item = new Item("test item",1,2,0, Item.HpBonusType.ONLY_WHEN_EQUIPPED,false, Item.Type.ARMOR);
        hero.equip(item);
        Item item2 = hero.getArmor();
        assert (item2 == item && !oldItem.isEquipped() && item2.isEquipped());
    }

    @Test
    void unequipOnlyBootsTest() {
        Hero hero = new Hero(0,0,100);
        Item bootsItem = new Item("old item",1,1,0, Item.HpBonusType.ONE_TIME,true, Item.Type.BOOTS);
        Item armorItem = new Item("old item",1,1,0, Item.HpBonusType.ONE_TIME,true, Item.Type.ARMOR);
        hero.setBoots(bootsItem);
        hero.setArmor(armorItem);
        hero.unequip(hero.getBoots());
        assert (hero.getBoots() == null && hero.getArmor() == armorItem);
    }
}