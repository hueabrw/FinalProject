package sample;

import java.net.URL;
import java.util.ResourceBundle;
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
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;



public class DeveloperPageController implements Initializable{


    @FXML
    private TextField textField;

    @FXML
    private BrowserView browserView;

    @Override
    public void initialize(URL url, ResourceBundle rb){

        browserView.getBrowser().loadURL("file:///C:/code/Java/FinalProject/src/sample/googlemaps.html");

    }

    public void loadURL(ActionEvent actionEvent) {


        browserView.getBrowser().loadURL("file:googlemaps.html");
    }
}
