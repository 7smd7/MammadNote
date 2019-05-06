package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;

public class ControllerChangePass {

    @FXML
    private PasswordField txtAgainPass;

    @FXML
    private AnchorPane pane;

    @FXML
    private PasswordField txtNewPass;

    @FXML
    private PasswordField txtOldPass;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl3;

    @FXML
    void change(ActionEvent event) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(MammadNote.URLUser+"\\"+"info.txt"));
        boolean valid=true;
        if(!(Integer.parseInt(reader.readLine()) ==txtOldPass.getText().hashCode())){
            valid=false;
            lbl1.visibleProperty().setValue(true);
        }
        else
            lbl1.visibleProperty().setValue(false);
        if (!txtNewPass.getText().equals(txtAgainPass.getText())){
            valid=false;
            lbl2.visibleProperty().setValue(true);
        }
        else
            lbl2.visibleProperty().setValue(false);

        if (txtNewPass.getText().equals(txtOldPass.getText())){
            lbl3.visibleProperty().setValue(true);
            valid=false;
        }
        else
            lbl3.visibleProperty().setValue(false);

        if(txtNewPass.getText().equals(""))
            valid=false;

        if(valid){
            String urlPic=reader.readLine();
            PrintWriter writer = new PrintWriter(MammadNote.URLUser+"\\"+"info.txt", "UTF-8");
            writer.println(txtNewPass.getText().hashCode());
            writer.print(urlPic);
            writer.close();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }

    }

}
