package sample;

public class Coord {

    double lat;
    double lng;

    public Coord(double _lat, double _lng){
        lat = _lat;
        lng = _lng;
    }

    public double GetLng(){
        return lng;
    }

    public double GetLat(){
        return lat;
    }

    @Override
    public String toString(){
        return (this.GetLat()+ "," + this.GetLng());
    }
}
