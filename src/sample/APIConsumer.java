package sample;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.sql.DriverManager;
import java.util.ArrayList;


public class APIConsumer{


    public void Consume() throws Exception
    {
        ArrayList<Parcel> parcels = new ArrayList<>();


        URL url = new URL("https://gismaps.wichita.gov/agsweb/rest/services/COWGIS/Property_and_Location/MapServer/4/query?where=UPPER(Owner)%20like%20'%25WICHITA%20CITY%20OF%25'&outFields=OBJECTID&orderByFields=OBJECTID&outSR=4326&f=json");//API Connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }

        /*FileOutputStream out = new FileOutputStream("jsonfile.json");
        out.write(conn.getInputStream().readAllBytes());

        conn.disconnect();
        */

        Object obj = new JSONParser().parse(new InputStreamReader(conn.getInputStream()));

        conn.disconnect();

        JSONObject jsonObject = (JSONObject)obj;

        JSONArray firstLayer = (JSONArray) jsonObject.get("features");

        JSONArray coordinates = new JSONArray();

        for(int i = 0; i < firstLayer.size(); i++)
        {
            coordinates.add(((JSONObject)((JSONObject) firstLayer.get(i)).get("geometry")).get("rings"));
        }

        for(int i = 0; i < coordinates.size(); i++){
            parcels.add(new Parcel(i,GrabCoord(coordinates.get(i))));
        }


        JSONArray storedData = new JSONArray();
        for(int i = 0; i < coordinates.size(); i++){
            storedData.add(StoreData(parcels.get(i)));
            //System.out.println(parcels.get(i).toString() + parcels.get(i).id);
        }

        //System.out.println(storedData.toJSONString());

        File jsonfile = new File(".\\src\\sample\\test.json");
        FileOutputStream writer = new FileOutputStream(jsonfile);
        writer.write(storedData.toJSONString().getBytes());
        writer.flush();
        writer.close();

    }

    private JSONObject StoreData(Parcel parcel) {
        JSONObject parcelData = new JSONObject();

        parcelData.put("ID", parcel.id);
        JSONArray coords = new JSONArray();
        for(int i = 0; i < parcel.points.length; i ++){
            JSONObject tempObj = new JSONObject();
            tempObj.put("lat", parcel.points[i].GetLat());
            tempObj.put("lng", parcel.points[i].GetLng());
            coords.add(tempObj);
        }
        parcelData.put("Coord", coords);

        return parcelData;
    }


    public Coord[] GrabCoord(Object obj){

        JSONArray dataInstance = (JSONArray) obj;

        JSONArray test = (JSONArray) dataInstance.get(0);
        Coord[] tempCoord = new Coord[test.size()];

        for(int i = 0; i < test.size(); i ++)
        {
            tempCoord[i] = new Coord((double)((JSONArray)test.get(i)).get(1),(double)((JSONArray)test.get(i)).get(0));
        }

        return tempCoord;
    }
}
