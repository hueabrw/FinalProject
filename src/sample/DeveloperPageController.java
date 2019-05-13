package sample;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.events.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.parser.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DeveloperPageController implements Initializable{

    public BrowserInitializer bi;
    public ImageView fileImage1;
    public Label fileLabel1;
    public ImageView rendering;
    FileChooser fileChooser;
    Stage stage;
    Connection dbcon;
    Image highlightedImage;

    @FXML
    public Button minimize;
    @FXML
    public Pane parcelInfo;
    @FXML
    public VBox vbox;
    @FXML
    public Label number;
    @FXML
    public Label id;

    @FXML
    private BrowserView browserView;
    Browser browser;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        fileChooser = new FileChooser();
        browser = browserView.getBrowser();
        browser.loadURL("file:C:/code/Java/FinalProject/src/sample/googlemaps.html");
        run();

        try {
            dbcon = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/parceldb", "abrhuerta", "Green123");
            System.out.println("Connected to db");
        }catch (Exception e){
            System.out.println(e);
        }

    }

    public void run(){
        browser.addConsoleListener(new ConsoleListener() {
            public void onMessage(ConsoleEvent event) {
                System.out.println("Level: " + event.getLevel());
                System.out.println("Message: " + event.getMessage());
            }
        });
        FileInputStream is = null;
        try {
            is = new FileInputStream("C:/code/Java/FinalProject/src/sample/test.json");

            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine(); StringBuilder sb = new StringBuilder();

            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();


            bi = new BrowserInitializer(this, fileAsString);


            browser.addScriptContextListener(new ScriptContextAdapter() {
                @Override
                public void onScriptContextCreated(ScriptContextEvent event) {
                    JSValue window = browser.executeJavaScriptAndReturnValue("window");
                    window.asObject().setProperty("jsonData", fileAsString);
                    window.asObject().setProperty("PolyInteract", bi);
                }

            });

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void maximize(){
        parcelInfo.setVisible(true);
        parcelInfo.setPrefWidth(200);
    }

    public void upload(ActionEvent actionEvent) {
        stage = (Stage) parcelInfo.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            openFile(file);
            String filepath;
            if(vbox.getChildren().size() == 1){
                filepath = "`FilePath2`";
            }else if(vbox.getChildren().size() == 2){
                filepath = "`FilePath2`";
            }else{
                filepath = "`FilePath3`";
            }
            try{
                dbcon.createStatement().executeUpdate("UPDATE `Parcel` SET "+filepath+"=\"" + file.toURI().toString() + "\" WHERE `ParcelID` = "+ id.getText());
            }
            catch (Exception e){
                System.out.println(e);
            }
        }


    }

    public void preview(ActionEvent actionEvent)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("renderView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rendering");
            stage.setScene(new Scene(fxmlLoader.load(), 450, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Image image = new Image(highlightedImage.getUrl());
                rendering.setImage(image);
            }
        });
    }

    public void minimize(ActionEvent actionEvent) {
        parcelInfo.setVisible(false);
        parcelInfo.setPrefWidth(0);
    }

    public void logout(ActionEvent actionEvent) {
        try{
            stage = (Stage) parcelInfo.getScene().getWindow();
            Pane pane = FXMLLoader.load(
                    LoginController.class.getResource("login.fxml"));

            stage.setTitle("Login");
            stage.setScene(new Scene(pane, 630, 580));
            stage.show();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void populatePanel(Parcel parcel) {

        Platform.runLater(new Runnable() {
            @Override public void run() {
                id.setText(String.valueOf(parcel.id));
                number.setText(String.valueOf(vbox.getChildren().size()));

                try{
                    ResultSet result = dbcon.createStatement().executeQuery("SELECT * FROM `Parcel` WHERE `ParcelID` = "+ parcel.id + " LIMIT 1");
                    if(result.next()){
                        for (int i = 0; i < 3; i++) {
                            try {
                                String pathName = result.getString(i+2);
                                File file = new File(URI.create(pathName));
                                System.out.println(file.toURI().toString());
                                openFile(file);
                            }catch (NullPointerException n){
                                if(i == 0){
                                    ClearFiles();
                                }
                                break;
                            }
                        }
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
        });
    }

    private void ClearFiles() {
        if(vbox.getChildren().size() > 1){
            vbox.getChildren().remove(1);
        }
        fileImage1.setImage(null);
        fileLabel1.setText("No Files");
    }

    private void openFile(File file){
        try {
            if (!fileLabel1.getText().equals("No Files")) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                Label label = new Label();
                label.setPadding(new Insets(20,0,0,10));
                HBox hbox = new HBox();
                hbox.getChildren().add(0,imageView);
                hbox.getChildren().add(1,label);
                label.setText(file.getName());
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                vbox.getChildren().add(vbox.getChildren().size(), hbox);
            } else {
                fileLabel1.setText(file.getName());
                Image image = new Image(file.toURI().toString());
                fileImage1.setImage(image);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void highlight(MouseEvent mouseEvent) {
        ((HBox)mouseEvent.getSource()).setStyle("-fx-background-color: #7f7f7f;");
        highlightedImage = ((ImageView)((HBox)mouseEvent.getSource()).getChildren().get(0)).getImage();
    }
}
