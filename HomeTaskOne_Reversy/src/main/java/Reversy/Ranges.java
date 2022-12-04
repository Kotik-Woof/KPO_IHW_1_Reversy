package Reversy;

import Reversy.Location;

import java.util.ArrayList;

public class Ranges {
    private static Location size;
    private  static ArrayList<Location> allLocations;

    static void setSize (Location _size) {
        size = _size;
        allLocations = new ArrayList<Location>();
        for (int y = 0; y < size.y; y++) {
            for (int x = 0; x < size.x; x++) {
                allLocations.add(new Location(x, y));
            }
        }
    }

    public static Location getSize() {
        return size;
    }

    public static ArrayList<Location> getAllLocations () {
        return allLocations;
    }

    static boolean inRange (Location location) {
        return location.x >= 0 && location.x < size.x &&
               location.y >= 0 && location.y < size.y;
    }
}
