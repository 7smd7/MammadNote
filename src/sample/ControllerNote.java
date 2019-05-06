package sample;


import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import static sample.Main.*;

public class ControllerNote implements Initializable{

    @FXML
    TreeView <String> booklets;

    @FXML
    Button btnAddtodo;

    @FXML
    Label lblWlcm;

    @FXML
    ImageView profilePic;

    @FXML
    Button btnChangePass;

    @FXML
    AnchorPane pane;

    @FXML
    AnchorPane righPane;

    @FXML
    private ImageView imgLock;

    @FXML
    private ImageView imgDelete;

    @FXML
    ScrollPane scrollVBox;

    @FXML
    private ImageView imgEdit;

    @FXML
    private ImageView imgSearch;

    @FXML
    private Label lblName;

    @FXML
    private VBox vBox;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button btnSave;

    @FXML
    private ImageView imgClose;

    @FXML
    private TextField txtNewtodo;

    @FXML
    private Button btnDeteletodo;

    @FXML
    private TextField txtDeletetodo;

    @FXML
    private ImageView imgShare;

    @FXML
    private Label lblDelete;

    @FXML
    private Button btnOkDelete;

    @FXML
    private Button btnCancelDelete;

    public static String newURL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[1-9]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        txtDeletetodo.setTextFormatter(textFormatter);

        lblWlcm.setText("Hello "+MammadNote.user+"!");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(MammadNote.URLUser + "\\info.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String picURL= (String) reader.lines().toArray()[1];
        try {
            profilePic.setImage(new Image(new FileInputStream(picURL)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        treeBooklet();
    }

    public void mouseClicked(MouseEvent event) throws IOException {
        TreeItem<String> item=booklets.getSelectionModel().getSelectedItem();

        imgLock.disableProperty().setValue(false);
        imgShare.disableProperty().setValue(false);
        imgDelete.disableProperty().setValue(false);
        imgEdit.disableProperty().setValue(false);

        try {
            if (item.getParent().getValue().equals("Booklets")) {
                if (item.getValue().equals("New Booklet") && event.getClickCount()<=1) {
                    Parent root = FXMLLoader.load(getClass().getResource("newBooklet.fxml"));
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Make new booklet");
                    newWindow.setResizable(false);
                    newWindow.setScene(new Scene(root, 300, 300));
                    newWindow.setAlwaysOnTop(true);
                    newWindow.showAndWait();
                    treeBooklet();
                }
                else {
                    showBookletControll();
                }
            } else {
                if (item.getValue().equals("New Memo/Note/To-doList") && event.getClickCount()<=1) {
                    Parent root = FXMLLoader.load(getClass().getResource("newNote.fxml"));
                    newURL = MammadNote.URLUser + "\\" + item.getParent().getValue();
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Make new Memo/Note/To-doList ");
                    newWindow.setResizable(false);
                    newWindow.setScene(new Scene(root, 300, 260));
                    newWindow.setAlwaysOnTop(true);
                    newWindow.showAndWait();
                    treeBooklet();
                }
                else {
                    hideControll();
                    showNoteControll();
                    if (item.getValue().contains("todo")) {
                        hide();
                        BufferedReader reader = new BufferedReader(new FileReader(MammadNote.URLUser+"\\"+item.getParent().getValue()+"\\"+item.getValue()));
                        Object[] lines=reader.lines().toArray();

                        Text title = new Text("Works:\n");
                        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        vBox.getChildren().add(title);

                        for (int i=1;i<lines.length;i++){
                            CheckBox todo=new CheckBox(((String) lines[i]).substring(1));
                            if(((String) lines[i]).charAt(0)=='T')
                                todo.setSelected(true);
                            vBox.getChildren().add(todo);
                        }
                        String a="-fx-background-color: #"+lines[0]+";";
                        vBox.setStyle(a);
                        lblName.setText(item.getValue().substring(0,item.getValue().length()-5));
                        lblName.setTextFill(Paint.valueOf("#000000"));
                        lblName.visibleProperty().setValue(true);
                        scrollVBox.visibleProperty().setValue(true);
                        btnSave.visibleProperty().setValue(true);
                        imgClose.visibleProperty().setValue(true);
                        btnAddtodo.visibleProperty().setValue(true);
                        txtNewtodo.visibleProperty().setValue(true);
                        btnDeteletodo.visibleProperty().setValue(true);
                        txtDeletetodo.visibleProperty().setValue(true);

                    }
                    else if(item.getValue().contains("note")){
                        hide();
                        lblName.setText(item.getValue().substring(0,item.getValue().length()-5));
                        lblName.setTextFill(Paint.valueOf("#000000"));
                        lblName.visibleProperty().setValue(true);
                        htmlEditor.visibleProperty().setValue(true);
                        btnSave.visibleProperty().setValue(true);
                        imgClose.visibleProperty().setValue(true);
                        BufferedReader reader = new BufferedReader(new FileReader(MammadNote.URLUser+"\\"+item.getParent().getValue()+"\\"+item.getValue()));
                        Object[] lines= reader.lines().toArray();
                        String sum=null;
                        for (int i=1;i<lines.length;i++){
                            sum=sum+lines[i]+"\n";
                        }
                        String a=    "-fx-base: #"+lines[0]+";";
                        System.out.println(lines[0]);
                        htmlEditor.setStyle(a);
                        htmlEditor.setHtmlText(sum.replace("null",""));
                        htmlEditor.visibleProperty().setValue(true);

                    }
                    else if (item.getValue().contains("memo")){
                        hide();
                        lblName.setText(item.getValue().substring(0,item.getValue().length()-5));
                        lblName.setTextFill(Paint.valueOf("#000000"));
                        lblName.visibleProperty().setValue(true);
                        txtArea.visibleProperty().setValue(true);
                        btnSave.visibleProperty().setValue(true);
                        imgClose.visibleProperty().setValue(true);

                        BufferedReader reader = new BufferedReader(new FileReader(MammadNote.URLUser+"\\"+item.getParent().getValue()+"\\"+item.getValue()));
                        Object[] lines= reader.lines().toArray();
                        String sum=null;
                        for (int i=1;i<lines.length;i++){
                            sum=sum+lines[i]+"\n";
                        }
                        String a="    -fx-control-inner-background: #"+lines[0]+";";

                        txtArea.setStyle(a);
                        txtArea.setText(sum);
                        txtArea.visibleProperty().setValue(true);
                    }
                }
            }
        }catch (NullPointerException ex){}
    }

    public void signout(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\User\\info.txt", "UTF-8");
        writer.print("");
        writer.close();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
        ScreensController mainContainer = new ScreensController();

        mainContainer.loadScreen(LoginID,LoginFile);
        mainContainer.loadScreen(SignupID,SignupFile);

        mainContainer.setScreen(LoginID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Stage primaryStage= new Stage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
        String picURL="C:\\Users\\7smd7\\IdeaProjects\\mammadnote\\src\\sample\\note.png";
        primaryStage.getIcons().add(new Image(new FileInputStream(picURL)));
        primaryStage.setScene(new Scene(root,287,285));
        primaryStage.show();
    }
    private void treeBooklet(){
        TreeItem<String> Booklets = new TreeItem<>("Booklets");
        File[] fileList = new File(MammadNote.URLUser).listFiles();
        if(fileList != null){
            int nBooklets=fileList.length-1;
            String[] nameBooklets=new String[nBooklets];
            for (int i=0,j=0;i < fileList.length;i++) {
                if (!(fileList[i].getName().equals("info.txt"))) {
                    nameBooklets[j] = fileList[i].getName();
                    j++;
                }
            }
            TreeItem<String>[] booklet = new TreeItem[nBooklets];

            for (int i=0;i < nBooklets;i++) {
                booklet[i] = treeNote(nameBooklets[i],MammadNote.URLUser+"/"+nameBooklets[i]);
                Booklets.getChildren().add(booklet[i]);
            }
        }
        TreeItem<String> add = new TreeItem<>("New Booklet");
        Booklets.getChildren().add(add);
        Booklets.setExpanded(true);
        booklets.setRoot(Booklets);
    }

    private TreeItem<String> treeNote(String name, String URL){
        TreeItem<String> Booklets = new TreeItem<>(name);
        File[] fileList = new File(URL).listFiles();
        if (fileList != null){
            int nBooklets=fileList.length-1;
            String[] nameBooklets=new String[nBooklets];
            for (int i=0,j=0;i < fileList.length;i++) {
                if (!(fileList[i].getName().equals("info.txt"))) {
                    nameBooklets[j] = fileList[i].getName();
                    j++;
                }
            }
            TreeItem<String>[] booklet = new TreeItem[nBooklets];
            for (int i=0;i < nBooklets;i++) {
                booklet[i] = new TreeItem<>(nameBooklets[i]);
                Booklets.getChildren().add(booklet[i]);
            }
        }
        TreeItem<String> add = new TreeItem<>("New Memo/Note/To-doList");
        Booklets.getChildren().add(add);
        return Booklets;
    }

    private void hide(){
        btnSave.visibleProperty().setValue(false);
        txtArea.visibleProperty().setValue(false);
        htmlEditor.visibleProperty().setValue(false);
        scrollVBox.visibleProperty().setValue(false);
        vBox.getChildren().clear();
        lblName.visibleProperty().setValue(false);
        txtNewtodo.visibleProperty().setValue(false);
        btnAddtodo.visibleProperty().setValue(false);
        btnDeteletodo.visibleProperty().setValue(false);
        imgClose.visibleProperty().setValue(false);
        txtDeletetodo.visibleProperty().setValue(false);
        hideControll();
    }

    public void addTask(ActionEvent event) {
        CheckBox todo=new CheckBox(txtNewtodo.getText());
        vBox.getChildren().add(todo);
        txtNewtodo.clear();
    }

    public void deleteTask(ActionEvent event){
        vBox.getChildren().remove(Integer.parseInt(txtDeletetodo.getText()));
        txtDeletetodo.clear();
    }

    public void save(ActionEvent event) throws IOException {
        TreeItem<String> item = booklets.getSelectionModel().getSelectedItem();
        if (item.getValue().contains("todo")) {
            String URL = MammadNote.URLUser + "\\" + item.getParent().getValue() + "\\" + item.getValue();
            BufferedReader reader = new BufferedReader(new FileReader(URL));
            String color=reader.readLine();
            PrintWriter writer = new PrintWriter(URL, "UTF-8");
            writer.println(color);
            for (int i = 1; i < vBox.getChildren().size(); i++) {
                CheckBox a= (CheckBox) vBox.getChildren().get(i);
                if (a.isSelected())
                    writer.print("T");
                else
                    writer.print("F");
                writer.println(a.getText());
            }
            writer.close();
        }
        else if(item.getValue().contains("note")){
            String URL = MammadNote.URLUser + "\\" + item.getParent().getValue() + "\\" + item.getValue();
            BufferedReader reader = new BufferedReader(new FileReader(URL));
            String color=reader.readLine();
            PrintWriter writer = new PrintWriter(URL, "UTF-8");
            writer.println(color);
            writer.print(htmlEditor.getHtmlText());
            writer.close();
        }
        else if (item.getValue().contains("memo")){
            String URL = MammadNote.URLUser + "\\" + item.getParent().getValue() + "\\" + item.getValue();
            BufferedReader reader = new BufferedReader(new FileReader(URL));
            String color=reader.readLine();
            PrintWriter writer = new PrintWriter(URL, "UTF-8");
            writer.println(color);
            writer.print(txtArea.getText());
            writer.close();
        }

    }

    public void setImgClose(){
        hide();
    }

    public void changePass() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("changePass.fxml"));
        Stage pass = new Stage();
        pass.setTitle("Change password");
        pass.setResizable(false);
        pass.setScene(new Scene(root, 300, 300));
        pass.showAndWait();
        btnChangePass.setText("Password changed successfully");
        btnChangePass.disableProperty().setValue(true);
        btnChangePass.setPrefSize(270,33);
    }

    public void delete(MouseEvent event){
        hideControll();
        lblDelete.visibleProperty().setValue(true);
        btnCancelDelete.visibleProperty().setValue(true);
        btnOkDelete.visibleProperty().setValue(true);
    }

    public void okDelete(){
        TreeItem<String> item = booklets.getSelectionModel().getSelectedItem();
        String path;
        if (item.getParent().getValue().equals("Booklets")) {
            path=MammadNote.URLUser+"\\"+item.getValue();
        }
        else
            path=MammadNote.URLUser+"\\"+item.getParent().getValue()+"\\"+item.getValue();
        File file = new File(path);
        file.delete();
    }

    public void cancelDelete(){
        btnCancelDelete.visibleProperty().setValue(false);
        btnOkDelete.visibleProperty().setValue(false);
        lblDelete.visibleProperty().setValue(false);
    }

    private void showBookletControll(){
        imgShare.visibleProperty().setValue(true);
        imgDelete.visibleProperty().setValue(true);
        imgEdit.visibleProperty().setValue(true);
        imgSearch.visibleProperty().setValue(true);
    }

    private void showNoteControll(){
        imgLock.visibleProperty().setValue(true);
        imgDelete.visibleProperty().setValue(true);
        imgEdit.visibleProperty().setValue(true);
        imgSearch.visibleProperty().setValue(true);
    }

    private void hideControll(){
        imgLock.visibleProperty().setValue(false);
        imgShare.visibleProperty().setValue(false);
        imgDelete.visibleProperty().setValue(false);
        imgEdit.visibleProperty().setValue(false);
        imgSearch.visibleProperty().setValue(false);
    }

}
