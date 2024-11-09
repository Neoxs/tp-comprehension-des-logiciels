package clustering.visualization;

public class VCoord {

    private double x;
    private double y;

    public VCoord(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VCoord) {
            VCoord other = (VCoord)obj;
            return x == other.getX() && y == other.getY();
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return  String.format("Coord(%.3f,%.3f)", x, y);
    }

}
