package nazarrod.adventgem.advgem.levelPlayer;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Bullet;
import nazarrod.adventgem.advgem.model.Chest;
import nazarrod.adventgem.advgem.model.Hero;
import nazarrod.adventgem.advgem.model.Item;
import nazarrod.adventgem.advgem.utils.Geometry;
import nazarrod.adventgem.advgem.utils.LevelManager;
import nazarrod.adventgem.advgem.view.GfIMG;
import nazarrod.adventgem.advgem.view.GraphicController;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;

public class GameWindow{
    private final Stage stage;
    private final Canvas canvas;
    private final GraphicController graphicsController;
    private GameData gameData;
    private boolean aKeyPressed = false;
    private boolean dKeyPressed = false;
    private boolean wPressedConstantly = false;

    public GameWindow(GameData gameData) {
        this.stage = new Stage();
        this.gameData = gameData;
        canvas = new Canvas(gameData.getPlaygroundWidth(),gameData.getPlaygroundHeight());
        graphicsController = new GraphicController(canvas,canvas.getGraphicsContext2D(),gameData);
    }

    public void start(){
        GridPane gridPane = new GridPane();
        gridPane.add(canvas,0,0);
        graphicsController.drawLevel();
        stage.setTitle(gameData.getLevelName());
        stage.setScene(new Scene(gridPane));
        stage.centerOnScreen();
        stage.show();

        // in nanoseconds
        AnimationTimer gameLoopTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) { // in nanoseconds
                if (now - lastUpdate >= 10_000_000) {
                    gameData.refreshAll(now);
                    graphicsController.drawLevel();
                    lastUpdate = now;
                    if(gameData.isWin()){
                        stop();
                        winLevel();
                    }
                    if(gameData.isLoose()){
                        stop();
                        looseLevel();
                    }
                }
            }
        };
        gameLoopTimer.start();
        stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case W -> {
                    if(!wPressedConstantly)gameData.getHero().jump();
                    wPressedConstantly = true;
                }
                case A -> {
                    gameData.getHero().moveLeft();
                    aKeyPressed = true;
                    if(!dKeyPressed)
                        gameData.getHero().moveLeft();
                }
                case D -> {
                    gameData.getHero().moveRight();
                    dKeyPressed = true;
                    if(!aKeyPressed)
                        gameData.getHero().moveRight();
                }
                case SPACE -> {
                    Bullet bullet;
                    if((gameData.getHero().getWeapon() == Hero.Weapon.BULLET) && (gameData.getHero().getBulletsCnt() != 0) ){
                        bullet = new Bullet(gameData.getHero().getxPos(), gameData.getHero().getyPos() + 20, 20, gameData.getHero().getOrientation(), false, true, 9999);
                        gameData.getHero().setBulletsCnt(max(gameData.getHero().getBulletsCnt()-1,0));
                    }
                    else{
                        gameData.getHero().setWeapon(Hero.Weapon.SWORD);
                        bullet = new Bullet(gameData.getHero().getxPos(), gameData.getHero().getyPos() + 20, 20, gameData.getHero().getOrientation(), true, true, 70);
                    }
                    gameData.addBullet(bullet);
                }
                case E -> {
                    aKeyPressed = false;
                    dKeyPressed = false;
                    gameData.getHero().setxSpeed(0);
                    openInventory(gameLoopTimer);
                }
                case H -> {
                    if(gameData.getHero().getApplesCnt() != 0){
                        gameData.getHero().setApplesCnt(max(gameData.getHero().getApplesCnt()-1,0));
                        gameData.getHero().setHP(gameData.getHero().getHP()+10);
                    }
                }
                case F -> {
                    Iterator<Chest> chestIterator = gameData.getChests().iterator();
                    Chest chest;
                    while (chestIterator.hasNext()){
                        chest = chestIterator.next();
                        if(Geometry.checkBelongs(gameData.getHero().getxPos()+gameData.getHero().getWidth()/2,gameData.getHero().getyPos()+gameData.getHero().getHeight()-1,chest.getPlatform())){
                            chest.giveItems(gameData.getHero());
                            chestIterator.remove();
                        }
                    }
                }
                case P,ESCAPE -> {
                    aKeyPressed = false;
                    dKeyPressed = false;
                    gameData.getHero().setxSpeed(0);
                    pauseLevel(gameLoopTimer);
                }
            }
        });

        stage.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case W -> {
                    wPressedConstantly = false;
                }
                case A -> {
                    aKeyPressed = false;
                    if(!dKeyPressed)gameData.getHero().setxSpeed(0);
                    else gameData.getHero().moveRight();
                }
                case D -> {
                    dKeyPressed = false;
                    if(!aKeyPressed)gameData.getHero().setxSpeed(0);
                    else gameData.getHero().moveLeft();
                }
            }
        });
    }

    public void openInventory(AnimationTimer gameLoopTimer) {
        gameLoopTimer.stop();
        Stage invStage = new Stage();
        GridPane gridPane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(100);

        gridPane.getColumnConstraints().addAll(col1);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label label = new Label("Weapon");
        label.setAlignment(Pos.CENTER);
        gridPane.add(label,0,0);
        label = new Label("Boots");
        gridPane.add(label,1,0);
        label = new Label("Armor");
        gridPane.add(label,2,0);
        label = new Label("Consumables");
        gridPane.add(label,3,0);

        ImageView swordImageView = new ImageView(GfIMG.SWORD.img);
        swordImageView.setFitHeight(50);
        swordImageView.setFitWidth(50);
        Button sword = new Button();
        sword.setPrefWidth(100);
        sword.setPrefHeight(100);
        sword.setGraphic(swordImageView);

        ImageView bulletImageView = new ImageView(GfIMG.BLUE_BULLET.img);
        bulletImageView.setFitHeight(50);
        bulletImageView.setFitWidth(50);
        Button bullet = new Button();
        bullet.setPrefWidth(100);
        bullet.setPrefHeight(100);
        bullet.setGraphic(bulletImageView);
        bullet.setText(Integer.toString(gameData.getHero().getBulletsCnt()));

        sword.setOnAction(actionEvent -> {
            gameData.getHero().setWeapon(Hero.Weapon.SWORD);
            updateWeaponButtons(sword,bullet);
        });
        bullet.setOnAction(actionEvent -> {
            gameData.getHero().setWeapon(Hero.Weapon.BULLET);
            updateWeaponButtons(sword,bullet);
        });

        updateWeaponButtons(sword,bullet);

        ImageView applesImageView = new ImageView(GfIMG.APPLE.img);
        applesImageView.setFitHeight(50);
        applesImageView.setFitWidth(50);
        Button apple = new Button();
        apple.setPrefWidth(100);
        apple.setPrefHeight(100);
        apple.setGraphic(applesImageView);
        apple.setText(Integer.toString(gameData.getHero().getApplesCnt()));
        apple.setOnAction(actionEvent -> {
            if(gameData.getHero().getApplesCnt() > 0) {
                gameData.getHero().setApplesCnt(max(gameData.getHero().getApplesCnt() - 1, 0));
                gameData.getHero().setHP(gameData.getHero().getHP() + 10);
                apple.setText(Integer.toString(gameData.getHero().getApplesCnt()));
                graphicsController.drawLevel();
            }
        });

        ImageView keyImageView = new ImageView(GfIMG.KEY.img);
        keyImageView.setFitHeight(50);
        keyImageView.setFitWidth(50);
        Button key = new Button();
        key.setPrefWidth(100);
        key.setPrefHeight(100);
        key.setGraphic(keyImageView);
        if(gameData.getHero().isHasKey())key.setText(Integer.toString(1));
        else key.setText(Integer.toString(0));

        gridPane.setGridLinesVisible( true );
        gridPane.getColumnConstraints().addAll(col1, col1, col1);
        gridPane.add(sword,0,1);
        gridPane.add(bullet,0,2);
        gridPane.add(apple,3,1);
        gridPane.add(key,3,2);

        List<Item>bootsList = gameData.getHero().getBootsList();
        List<Item>armorList = gameData.getHero().getArmorList();

        int cc = 1;
        Button selectedButton = null;
        for(Item item : bootsList){
            ImageView bootsImageView = new ImageView(GfIMG.BOOTS.img);
            Button boots = new Button();
            boots.setPrefWidth(100);
            boots.setPrefHeight(100);
            boots.setGraphic(bootsImageView);
            boots.setText(Integer.toString(item.getSpeedB()));
            System.out.println(item.isEquipped());
            if(!item.isEquipped())
                boots.setStyle("");
            else {
                selectedButton = boots;
                boots.setStyle("-fx-border-color: green; " +
                        "-fx-border-width: 5px; " +
                        "-fx-border-radius: 5px;");
            }
            Button finalSelectedButton = selectedButton;
            boots.setOnAction(actionEvent -> {
                gameData.getHero().equip(item);
                boots.setStyle("-fx-border-color: green; " +
                        "-fx-border-width: 5px; " +
                        "-fx-border-radius: 5px;");
                if(finalSelectedButton != null) finalSelectedButton.setStyle("");
            });
            gridPane.add(boots,1,cc++);
        }
        cc = 1;
        selectedButton = null;
        for(Item item : armorList){
            ImageView armorImageView = new ImageView(GfIMG.ARMOR.img);
            Button armor = new Button();
            armor.setPrefWidth(100);
            armor.setPrefHeight(100);
            armor.setGraphic(armorImageView);
            armor.setText(Double.toString(item.getdArmorQ()));
            if(!item.isEquipped())
                armor.setStyle("");
            else {
                selectedButton = armor;
                armor.setStyle("-fx-border-color: green; " +
                        "-fx-border-width: 5px; " +
                        "-fx-border-radius: 5px;");
            }
            Button finalSelectedButton = selectedButton;
            armor.setOnAction(actionEvent -> {
                gameData.getHero().equip(item);
                armor.setStyle("-fx-border-color: green; " +
                        "-fx-border-width: 5px; " +
                        "-fx-border-radius: 5px;");
                if(finalSelectedButton != null) finalSelectedButton.setStyle("");
            });
            gridPane.add(armor,2,cc++);
        }

        invStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case E,ESCAPE -> {
                    invStage.close();
                    gameLoopTimer.start();
                }
            }
        });
        invStage.centerOnScreen();
        invStage.initModality(Modality.APPLICATION_MODAL);
        invStage.setOnCloseRequest(windowEvent -> gameLoopTimer.start());
        invStage.setScene(new Scene(gridPane));
        invStage.show();
    }
    private void updateWeaponButtons(Button sword, Button bullet){
        if(gameData.getHero().getWeapon() == Hero.Weapon.SWORD){
            sword.setStyle("-fx-border-color: green; " +
                    "-fx-border-width: 5px; " +
                    "-fx-border-radius: 5px;");
            bullet.setStyle("");
        }
        else{
            bullet.setStyle("-fx-border-color: green; " +
                    "-fx-border-width: 5px; " +
                    "-fx-border-radius: 5px;");
            sword.setStyle("");
        }
    }

    public void winLevel(){
        looseLevel();
    }

    public void looseLevel(){
        Stage looseStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Button restartButton = new Button("Restart");
        restartButton.setPrefWidth(200);
        restartButton.setOnAction(actionEvent -> {
            looseStage.close();
            System.out.println(gameData.getLevelName());
            new ChooseLevelWindow(stage).startLevel("./Levels/"+gameData.getLevelName()+"/gamedata.dat");
        });
        Button exitLevelButton = new Button("Exit level");
        exitLevelButton.setPrefWidth(200);
        exitLevelButton.setOnAction(actionEvent -> {
            looseStage.close();
            new ChooseLevelWindow(stage).start();
        });

        vBox.getChildren().addAll(restartButton,exitLevelButton);
        looseStage.setScene(new Scene(vBox));
        looseStage.centerOnScreen();
        looseStage.initModality(Modality.APPLICATION_MODAL);
        looseStage.setOnCloseRequest(windowEvent -> {});
        looseStage.show();
    }

    public void pauseLevel(AnimationTimer gameLoopTimer){
        gameLoopTimer.stop();
        Stage pauseStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Button continueButton = new Button("Continue");
        continueButton.setPrefWidth(200);
        continueButton.setOnAction(actionEvent -> {
            pauseStage.close();
            gameLoopTimer.start();
        });
        Button restartButton = new Button("Restart");
        restartButton.setPrefWidth(200);
        restartButton.setOnAction(actionEvent -> {
            pauseStage.close();
            System.out.println(gameData.getLevelName());
            new ChooseLevelWindow(stage).startLevel("./Levels/"+gameData.getLevelName()+"/gamedata.dat");
        });
        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(200);
        if(LevelManager.loadLevel("./Saves/gamedata.dat") == null)loadButton.setDisable(true);
        loadButton.setOnAction(actionEvent -> {
            gameData = LevelManager.loadLevel("./Saves/gamedata.dat");
            graphicsController.setGameData(gameData);
            pauseStage.close();
            gameLoopTimer.start();
        });
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(200);
        saveButton.setOnAction(actionEvent -> {
            aKeyPressed = false;
            dKeyPressed = false;
            gameData.getHero().setxSpeed(0);
            LevelManager.makeNewSave(gameData);
            loadButton.setDisable(false);
        });
        Button exitLevelButton = new Button("Exit level");
        exitLevelButton.setPrefWidth(200);
        exitLevelButton.setOnAction(actionEvent -> {
            pauseStage.close();
            new ChooseLevelWindow(stage).start();
        });
        Button saveExitButton = new Button("Save and Exit game");
        saveExitButton.setPrefWidth(200);
        saveExitButton.setOnAction(actionEvent -> {
            pauseStage.close();
            LevelManager.makeNewSave(gameData);
            Platform.exit();
        });
        Button exitButton = new Button("Exit game");
        exitButton.setPrefWidth(200);
        exitButton.setOnAction(actionEvent -> {
            Platform.exit();
        });
        vBox.getChildren().addAll(continueButton,restartButton,saveButton,loadButton,exitLevelButton,saveExitButton,exitButton);

        pauseStage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            KeyCode code = keyEvent.getCode();
            switch (code) {
                case P,ESCAPE -> {
                    pauseStage.close();
                    gameLoopTimer.start();
                }
            }
        });
        pauseStage.setScene(new Scene(vBox));
        pauseStage.centerOnScreen();
        pauseStage.initModality(Modality.APPLICATION_MODAL);
        pauseStage.setOnCloseRequest(windowEvent -> gameLoopTimer.start());
        pauseStage.show();
    }
}
