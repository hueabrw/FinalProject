package sample;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.events.*;
import javafx.scene.input.MouseEvent;
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



public class DeveloperPageController implements Initializable{


    @FXML
    private TextField textField;

    @FXML
    private BrowserView browserView;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Browser browser = browserView.getBrowser();
        BrowserInitializer bi = new BrowserInitializer(browser);
        bi.initialize();

    }

    public void onPolyClick(){
        System.out.println("worked");
    }


}
