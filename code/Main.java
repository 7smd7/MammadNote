package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {
    public static String LoginID = "loginPane";
    public static String LoginFile = "login.fxml";
    public static String SignupID = "signupPane";
    public static String SignupFile = "signup.fxml";
//    public static String NoteID = "notePane";
//    public static String NoteFile = "note.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\info.txt"));
        String lastUser = reader.readLine();
        if(lastUser==null) {
            ScreensController mainContainer = new ScreensController();
            mainContainer.loadScreen(LoginID, LoginFile);
            mainContainer.loadScreen(SignupID, SignupFile);

            mainContainer.setScreen(LoginID);

            Group root = new Group();
            root.getChildren().addAll(mainContainer);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Login");
            String picURL = "C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\note.png";
            primaryStage.getIcons().add(new Image(new FileInputStream(picURL)));
            primaryStage.setScene(new Scene(root, 287, 285));
            primaryStage.show();
        }
        else {
            MammadNote mammadNote=new MammadNote(lastUser);
            mammadNote.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
