package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

public class MammadNote {
    public static String user;
    public static String URLUser;

    MammadNote(String user) throws FileNotFoundException, UnsupportedEncodingException {
        MammadNote.user = user;
        MammadNote.URLUser = "C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\" + user;
    }
    public void show() throws IOException {
        Stage note = new Stage();
        String picURL="C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\note.png";
        Parent root = FXMLLoader.load(getClass().getResource("note.fxml"));
        note.getIcons().add(new Image(new FileInputStream(picURL)));
        note.setTitle("mammad note");
        note.setMinHeight(600);
        note.setMinWidth(800);
        note.setScene(new Scene(root, 800, 600));
        note.show();
    }

    public String getUser() {
        return user;
    }

    public String getURLUser() {
        return URLUser;
    }

    public static String hex(Color color){
        String red= Integer.toHexString((int) (color.getRed()*256));
        String green= Integer.toHexString((int) (color.getGreen()*256));
        String blue= Integer.toHexString((int) (color.getBlue()*256));
        if(red.length()==1)
            red="0"+red;
        if (green.length()==1)
            green="0"+green;
        if (blue.length()==1)
            blue="0"+blue;
        String hex=red+green+blue;
        if (hex.equals("100100100"))
            hex="ffffff";
        return hex;
    }
}
