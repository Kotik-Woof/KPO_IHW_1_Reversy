package Reversy;

public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return (2 * this.x + 3 * this.y) % 17;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            return ((Location)obj).x == this.x && ((Location)obj).y == this.y;
        }
        return false;
    }
}
