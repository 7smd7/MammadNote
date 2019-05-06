package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerLogin implements ControlledScreen{

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPass;

    @FXML
    private Pane loginPane;


    public void btnGuest(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.close();
        MammadNote mammadNote=new MammadNote("guest");
        mammadNote.show();
    }

    public void Login(ActionEvent event) throws IOException {
        if (txtUser.getText().equals("") || txtPass.getText().equals("")){
            Dialog("ErrorWrongUserPass", "Error");
        }
        else {
            String URL = "C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\" + txtUser.getText();
            boolean checkDir;
            Path xPath = Paths.get(URL);
            checkDir = Files.exists(xPath);
            if (checkDir) {
                BufferedReader reader = new BufferedReader(new FileReader(URL + "\\info.txt"));
                if (txtPass.getText().hashCode() == Integer.parseInt(reader.readLine())) {
                    PrintWriter writer = new PrintWriter("C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\info.txt", "UTF-8");
                    writer.println(txtUser.getText());
                    writer.close();
                    Stage stage = (Stage) loginPane.getScene().getWindow();
                    stage.close();
                    MammadNote mammadNote=new MammadNote(txtUser.getText());
                    mammadNote.show();
                } else
                    Dialog("ErrorWrongUserPass", "Error");

            } else {
                Dialog("ErrorWrongUserPass", "Error");
            }
        }
    }

    public void Signup(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setTitle("Sign up");
        myController.setScreen(Main.SignupID);
    }

    private ScreensController myController;
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
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
