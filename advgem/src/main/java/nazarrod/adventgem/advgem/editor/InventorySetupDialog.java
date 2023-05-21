package nazarrod.adventgem.advgem.editor;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import nazarrod.adventgem.advgem.GameData;
import nazarrod.adventgem.advgem.model.Item;
import nazarrod.adventgem.advgem.view.GfIMG;

import java.util.concurrent.atomic.AtomicInteger;

public class InventorySetupDialog extends Dialog<Void> {
    public InventorySetupDialog(GameData gameData) {

        setTitle("Configure Hero's inventory");
        setHeaderText(null);

        DialogPane dialogPane = getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        ImageView bulletImageView = new ImageView(GfIMG.BLUE_BULLET.img);
        bulletImageView.setFitHeight(50);
        bulletImageView.setFitWidth(50);
        gridPane.add(bulletImageView,0,0);
        gridPane.add(new Label("Set bullets quantity, [0,999]"),1,0);
        TextField bulletCnt = new TextField("30");
        gridPane.add(bulletCnt,2,0);

        gridPane.add(new ImageView(GfIMG.APPLE.img),0,1);
        gridPane.add(new Label("Set apples quantity, [0,999]"),1,1);
        TextField applesCnt = new TextField("5");
        gridPane.add(applesCnt,2,1);

        AtomicInteger cBoots = new AtomicInteger(3);
        gridPane.add(new ImageView(GfIMG.BOOTS.img),0,2);
        gridPane.add(new Label("Set speed bonus [1,10]"),1,2);
        TextField bootsName = new TextField("New Boots");
        gridPane.add(bootsName,2,2);
        TextField speedBField = new TextField("5");
        gridPane.add(speedBField,3,2);
        Button addBootsButton = new Button("+");
        addBootsButton.setOnAction(actionEvent -> {
            gameData.getHero().addBoots(
                    new Item(bootsName.getText(),parseIntDefault(speedBField.getText(),5),0,0, Item.HpBonusType.ONLY_WHEN_EQUIPPED,false, Item.Type.BOOTS));
            gridPane.add(new ImageView(GfIMG.BOOTS.img),0, cBoots.get());
            gridPane.add(new Label("Speed bonus "+parseIntDefault(speedBField.getText(),5)),1,cBoots.get());
            cBoots.getAndIncrement();
            dialogPane.getScene().getWindow().sizeToScene();
        });
        gridPane.add(addBootsButton,4,2);

        AtomicInteger cArmor = new AtomicInteger(3);
        gridPane.add(new ImageView(GfIMG.ARMOR.img),5,2);
        gridPane.add(new Label("Set armor coefficient [1,2]"),6,2);
        TextField armorName = new TextField("New Armor");
        gridPane.add(armorName,7,2);
        TextField armorQField = new TextField("1.3");
        gridPane.add(armorQField,8,2);
        Button addArmorButton = new Button("+");
        addArmorButton.setOnAction(actionEvent -> {
            gameData.getHero().addArmor(
                    new Item(armorName.getText(),0,parseDoubleDefault(armorQField.getText(),1.3),0, Item.HpBonusType.ONLY_WHEN_EQUIPPED,false, Item.Type.ARMOR));
            gridPane.add(new ImageView(GfIMG.ARMOR.img),5, cArmor.get());
            gridPane.add(new Label("Armor coefficient "+parseDoubleDefault(armorQField.getText(),1.3)),6,cArmor.get());
            cArmor.getAndIncrement();
            dialogPane.getScene().getWindow().sizeToScene();
        });
        gridPane.add(addArmorButton,9,2);

        dialogPane.setContent(gridPane);
        dialogPane.getScene().getWindow().sizeToScene();
        Button okButton = (Button)dialogPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(actionEvent -> {
            gameData.getHero().setApplesCnt(parseIntDefault(applesCnt.getText(),5));
            gameData.getHero().setBulletsCnt(parseIntDefault(bulletCnt.getText(),30));
            actionEvent.consume();
        });
        Platform.runLater(bulletCnt::requestFocus);
    }

    private int parseIntDefault(String str, int defaultValue) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }

    private double parseDoubleDefault(String str, double defaultValue) {
        double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = defaultValue;
        }
        return result;
    }
}
