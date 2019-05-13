package sample;

public class Parcel {

    Coord[] points;
    int id;
    String[] filepaths;
    boolean active;

    public Parcel(int  _id, Coord[] _points){
        id = _id;
        points = _points;
        filepaths = new String[3];
        active = false;
    }

    public Coord[] GetPoints(){
        return points;
    }

    public void setActive(boolean bool){
        active = bool;
    }

    public boolean getActive(){
        return active;
    }

    public void addFilePath(int index, String path){
        filepaths[index] = path;
    }

    public String getFilePath(int index){
        return filepaths[index];
    }

    @Override
    public String toString(){
        String tempString = "";
        for(int i = 0; i < points.length; i ++){
            tempString += points[i].toString() + " ";
        }
        return tempString;
    }
}
