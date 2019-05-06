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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerNewBooklet {

    @FXML
    TextField txtName;

    @FXML
    ImageView bookletPic;

    @FXML
    AnchorPane pane;

    String picURL="C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\booklet.png";

    public void newBooklet(ActionEvent event) throws IOException {
        if(txtName.getText().equals("")){
            Dialog("ErrorNameEmpty","Error Name Empty");
        }
        else {
            String URL = MammadNote.URLUser+"\\"+ txtName.getText();
            boolean checkDir;
            Path xPath = Paths.get(URL);
            checkDir = Files.exists(xPath);
            if (checkDir) {
                Dialog("ErrorName","Error");
            } else {
                Files.createDirectory(xPath);
                PrintWriter writer = new PrintWriter(URL + "\\info.txt", "UTF-8");
                writer.println(picURL);
                writer.close();
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void picChooser(MouseEvent event) throws FileNotFoundException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setAlwaysOnTop(false);
        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.jpg"));
        File selectedPic = fc.showOpenDialog(null);

        if (selectedPic!=null){
            picURL = selectedPic.getPath().replace("\\","/");
            bookletPic.setImage(new Image(new FileInputStream(picURL)));
        }
    }

    public void cancel(ActionEvent event){
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    private void Dialog(String name,String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(name+".fxml"));
        Stage Dialog;
        Dialog = new Stage();
        Dialog.setTitle(title);
        Dialog.setResizable(false);
        Dialog.setScene(new Scene(root, 595, 195));
        Dialog.show();
    }
}
