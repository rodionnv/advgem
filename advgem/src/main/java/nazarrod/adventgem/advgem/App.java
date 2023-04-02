package nazarrod.adventgem.advgem;

import nazarrod.adventgem.advgem.utils.ProfileManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    private Stage stage;
    private final double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private final double screenHeight = Screen.getPrimary().getBounds().getHeight();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        loginWindow();
    }

    private void loginWindow(){
        Button newProfileButton = new Button();
        newProfileButton.setText("New profile");
        newProfileButton.setPrefWidth(200);
        newProfileButton.setOnAction(actionEvent -> createNewProfileWindow());

        Button existProfileButton = new Button();
        existProfileButton.setText("Existing profile");
        existProfileButton.setPrefWidth(200);
        existProfileButton.setOnAction(actionEvent -> chooseProfileWindow());

        VBox loginMenu = new VBox();
        loginMenu.getChildren().addAll(existProfileButton);
        loginMenu.getChildren().addAll(newProfileButton);
        loginMenu.setSpacing(8);
        loginMenu.setAlignment(Pos.CENTER);
        stage.setTitle("Adventurous GEM");
        stage.setScene(new Scene(loginMenu,screenWidth*0.25,screenHeight*0.6));
        stage.show();
    }

    private void createNewProfileWindow(){
        System.err.println("New profile button pressed");
        Label profileNameLabel = new Label("Create new nickname: ");
        TextField profileNameField = new TextField();
        Label passwordLabel = new Label("Create new password: ");
        TextField passwordField = new TextField();
        Button okButton = new Button();
        okButton.setText("OK");
        okButton.setPrefWidth(50);
        okButton.setOnAction(actionEvent -> {
            ProfileManager profileManager = new ProfileManager();
            boolean prCreated = profileManager.createNewProfile(profileNameField.getCharacters().toString(),passwordField.getCharacters().toString());
            System.out.println(prCreated);
            if(!prCreated){
                Alert prCreationError = new Alert(Alert.AlertType.ERROR,"This login name may be already taken");
                prCreationError.setHeaderText("Error creating profile");
                prCreationError.show();
                createNewProfileWindow();
            }
            else{
                Alert prCreationSuccess = new Alert(Alert.AlertType.INFORMATION,"New profile "+profileNameField.getCharacters().toString()+" has been created");
                prCreationSuccess.show();
                loginWindow();
            }
        });

        Button backButton = new Button();
        backButton.setText("Back");
        backButton.setPrefWidth(50);
        backButton.setOnAction(actionEvent -> loginWindow());

        GridPane grid = new GridPane();

        grid.add(profileNameLabel,0,0);
        grid.add(profileNameField,1,0);
        grid.add(passwordLabel,0,1);
        grid.add(passwordField,1,1);
        grid.add(okButton,0,2);
        grid.add(backButton,0,3);

        stage.setScene(new Scene(grid,screenWidth*0.25,screenHeight*0.6));
        stage.show();
    }

    private void chooseProfileWindow(){
//        File[] directories = new File("./UserProfiles/").listFiles(File::isDirectory);
//        for(int i = 0;i < directories.length;i++){
//            System.out.println(directories[i].getName());
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}