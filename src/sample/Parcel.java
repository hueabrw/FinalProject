package sample;

public class Parcel {

    Coord[] points;
    int id;

    public Parcel(int  _id, Coord[] _points){
        id = _id;
        points = _points;
    }

    public Coord[] GetPoints(){
        return points;
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
