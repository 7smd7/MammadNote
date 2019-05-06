package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;


public class ControllerNewNote {

    @FXML
    AnchorPane pane;

    @FXML
    ColorPicker colorMemo;

    @FXML
    ColorPicker colorNote;

    @FXML
    ColorPicker colorTodo;

    @FXML
    TextField nameNote;

    @FXML
    TextField nameTodo;

    @FXML
    Label lblMemo;

    public void newMemo(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        String name=Calendar.getInstance().getTime().toInstant().toString().replace(":",".").replace("-",".").substring(0,19).replace("T"," ");
        PrintWriter writer = new PrintWriter(ControllerNote.newURL + "\\"+ name+".memo", "UTF-8");
        writer.println(MammadNote.hex(colorMemo.getValue()));
        writer.close();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void newTodo (ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setAlwaysOnTop(false);
        if(nameTodo.getText().equals("")){
            Dialog("ErrorNameEmpty","Error Name Empty");
        }
        else {
            String URL=ControllerNote.newURL + "\\" + nameTodo.getText()+ ".todo";
            boolean checkDir;
            Path xPath = Paths.get(URL);
            checkDir = Files.exists(xPath);
            if (checkDir) {
                Dialog("ErrorName", "Error name");
            } else {
                PrintWriter writer = new PrintWriter(URL, "UTF-8");
                writer.println(MammadNote.hex(colorTodo.getValue()));
                writer.close();
                stage.close();
            }
        }
    }

    public void newNote (ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setAlwaysOnTop(false);
        if(nameNote.getText().equals("")){
            Dialog("ErrorNameEmpty","Error Name");
        }
        else {
            String URL=ControllerNote.newURL + "\\" + nameNote.getText() + ".note";
            boolean checkDir;
            Path xPath = Paths.get(URL);
            checkDir = Files.exists(xPath);
            if (checkDir) {
                Dialog("ErrorName", "Error name");
            } else {
                PrintWriter writer = new PrintWriter(URL, "UTF-8");
                writer.println(MammadNote.hex(colorNote.getValue()));
                writer.close();
                stage.close();
            }
        }
    }

    public void cancel (ActionEvent event){
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