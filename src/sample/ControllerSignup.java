package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerSignup implements ControlledScreen{

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPass;

    @FXML
    private Pane signupPane;
    @FXML
    private ImageView profilePic;
    String picURL="";

    public void Signup(ActionEvent event) throws IOException {
        if (picURL.equals("")){
            Dialog("ErrorPic","Error");
        }
        else {
            String URL = "C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\" + txtUser.getText();
            boolean checkDir;
            Path xPath = Paths.get(URL);
            checkDir = Files.exists(xPath);
            if (checkDir) {
                Dialog("ErrorUser","Error");
            } else {
                Files.createDirectory(xPath);
                PrintWriter writer = new PrintWriter(URL + "\\info.txt", "UTF-8");
                writer.println(txtPass.getText().hashCode());
                writer.println(picURL);
                writer.close();
                Dialog("DialogSigned","You're Welcome to mammad note!");
                myController.setScreen(Main.LoginID);
            }
        }
    }

    public void picChooser(MouseEvent event) throws FileNotFoundException {

        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File selectedPic = fc.showOpenDialog(null);

        if (selectedPic!=null){
            picURL = selectedPic.getPath();
            picURL=picURL.replace("\\","/");
            profilePic.setImage(new Image(new FileInputStream(picURL)));
        }
    }

    private ScreensController myController;
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void cancel(ActionEvent event){
        Stage stage = (Stage) signupPane.getScene().getWindow();
        stage.setTitle("Login");
        myController.setScreen(Main.LoginID);
    }


    private void Dialog(String name,String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(name+".fxml"));
        Stage error = new Stage();
        error.setTitle(title);
        error.setResizable(false);
        error.setScene(new Scene(root, 595, 195));
        error.show();
    }
}
