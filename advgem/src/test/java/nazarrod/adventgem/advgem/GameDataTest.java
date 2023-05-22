package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.model.Hero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {

    @Test
    void isWinNoKeyTest() {
        GameData gameData = new GameData();
        gameData.addHero(0,0);
        gameData.addFinish(0,0);
        gameData.refreshAll(0);
        assert !gameData.isWin();
    }

    @Test
    void isWinWithKeyTest() {
        GameData gameData = new GameData();
        gameData.addHero(0,0);
        gameData.addFinish(100,100);
        gameData.getHero().changePos(80,80);
        gameData.getHero().setHasKey(true);
        gameData.refreshAll(0);
        assert gameData.isWin();
    }
}