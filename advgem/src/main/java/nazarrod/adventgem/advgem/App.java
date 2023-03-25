package nazarrod.adventgem.advgem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static java.lang.Math.min;

public class App extends Application {

    private Stage stage;
    private final double winWidth = Screen.getPrimary().getBounds().getWidth()*0.7;
    private final double winHeight = Screen.getPrimary().getBounds().getHeight()*0.7;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loginWindow();
    }

    private void loginWindow(){
        Button newProfileButton = new Button();
        newProfileButton.setText("New profile");
        newProfileButton.setOnAction(actionEvent -> createNewProfile());
        GridPane root = new GridPane();
        root.addRow(0,newProfileButton);
        stage.setTitle("Adventurous GEM");
        stage.setScene(new Scene(root,winWidth,winHeight));
        stage.show();
    }

    private void createNewProfile(){
        System.out.println("New profile button pressed");
        TextField profileName = new TextField();
        HBox hbox = new HBox(1,profileName);
        stage.setScene(new Scene(hbox));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}