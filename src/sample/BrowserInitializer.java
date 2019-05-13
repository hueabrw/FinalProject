package sample;

import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.JSValue;
import javafx.application.Platform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BrowserInitializer {

    DeveloperPageController dev;
    JSONArray jsonFile;
    JSONArray array;

    public BrowserInitializer(DeveloperPageController _dev, String jsonString){
        dev = _dev;
        try {
            Object obj = new JSONParser().parse(jsonString);
            array = (JSONArray)obj;
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onPolyClick(double id){
        dev.maximize();
        System.out.println(id);
        dev.populatePanel(grabParcel(((int) id)));
    }



    public Parcel grabParcel(int id){
        for(int i = 0; i < array.size(); i++){
            if(((JSONObject)array.get(i)).get("ID").toString().equals(String.valueOf(id))){
                System.out.println("made it");
                JSONObject parObj = (JSONObject)array.get(i);
                JSONArray coordArray = ((JSONArray)parObj.get("Coord"));
                Coord[] points = new Coord[coordArray.size()];
                for(int j = 0; j < coordArray.size() ; j++){
                    Coord point = new Coord(Double.valueOf((((JSONObject)coordArray.get(j)).get("lat")).toString()), Double.valueOf((((JSONObject)coordArray.get(j)).get("lng")).toString()));
                    points[j] = point;
                }
                Parcel parcel = new Parcel((Integer.valueOf(parObj.get("ID").toString())),points);
                return parcel;
            }
        }
        return null;
    }

}
