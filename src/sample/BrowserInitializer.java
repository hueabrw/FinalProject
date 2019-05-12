package sample;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.ConsoleEvent;
import com.teamdev.jxbrowser.chromium.events.ConsoleListener;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BrowserInitializer {

    Browser browser;

    public BrowserInitializer(Browser _browser){
        browser = _browser;
    }

    public void initialize(){
        browser.addConsoleListener(new ConsoleListener() {
            public void onMessage(ConsoleEvent event) {
                System.out.println("Level: " + event.getLevel());
                System.out.println("Message: " + event.getMessage());
            }
        });
        try {
            browser.loadURL("file:C:/code/Java/FinalProject/src/sample/googlemaps.html");

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
                    window.asObject().setProperty("PolyInteract", this);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
