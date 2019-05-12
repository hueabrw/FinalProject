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
    Browser browser;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        browser = browserView.getBrowser();
        browser.loadURL("file:C:/code/Java/FinalProject/src/sample/googlemaps.html");
        run();

    }

    public void run(){
        browser.addConsoleListener(new ConsoleListener() {
            public void onMessage(ConsoleEvent event) {
                System.out.println("Level: " + event.getLevel());
                System.out.println("Message: " + event.getMessage());
            }
        });
        try {
            InputStream is = new FileInputStream("C:/code/Java/FinalProject/src/sample/test.json");

            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine(); StringBuilder sb = new StringBuilder();

            while(line != null){
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();


            browser.addScriptContextListener(new ScriptContextAdapter() {
                @Override
                public void onScriptContextCreated(ScriptContextEvent event) {
                    JSValue window = browser.executeJavaScriptAndReturnValue("window");
                    window.asObject().setProperty("jsonData", fileAsString);
                    window.asObject().setProperty("PolyInteract", new BrowserInitializer());

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
