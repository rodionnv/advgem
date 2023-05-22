package nazarrod.adventgem.advgem.model;

import nazarrod.adventgem.advgem.GameData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    @Test
    void StandingTest() {
        GameData gameData = new GameData();
        gameData.addHero(50,50);
        gameData.addPlatform(50,111);
        assert gameData.getHero().isStanding(gameData.getPlatforms()); // Hero is standing on the platform
    }

    @Test
    void tryMoveToBlockedTest() {
        GameData gameData = new GameData();
        gameData.addHero(50,50);
        gameData.addPlatform(50,111);
        gameData.addPlatform(101,30);
        gameData.getHero().updateHittingRightState(gameData.getPlatforms());
        gameData.getHero().setxSpeed(5);
        gameData.getHero().tryMove();
        assert gameData.getHero().getxPos() == 50; //Hero shouldn't move because there is a platform on his way
    }

    @Test
    void dropStandingTest(){
        GameData gameData = new GameData();
        gameData.addHero(50,50);
        gameData.addPlatform(50,111);
        gameData.getHero().updateStates(gameData.getPlatforms());
        assert gameData.getHero().getyPos() == 50; // Hero is already standing
    }

    @Test
    void dropFallingTest(){
        GameData gameData = new GameData();
        gameData.addHero(50,0);
        gameData.addPlatform(50,111);
        gameData.getHero().updateStates(gameData.getPlatforms());
        gameData.getHero().tryMove();
        System.out.println(gameData.getHero().getyPos());
        assert gameData.getHero().getyPos() == 8; // Hero should have droppen by the speed of falling (8)
    }
}