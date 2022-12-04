package Reversy;

public class GameMap {  // игровое поле

    private Matrix ChekersMap;
    private int markedCell;  // количество выделенных клеток
    private int whiteChekers;  // количество белых шашек
    private int pinkChekers;  // количество розовых шашек

    void init () {
        ChekersMap = new Matrix(Box.sqrt1);
        placeChekers();
    }

    public Box get (Location location) {
        return ChekersMap.get(location);
    }

    public int getCountMarkedCell() {
        return markedCell;
    }

    public int getCountWhiteChekers() {
        return whiteChekers;
    }

    public int getCountPinkChekers() {
        return pinkChekers;
    }

    private void placeChekers() {
        ChekersMap.set(new Location(3, 3), Box.white_circle1);
        ChekersMap.set(new Location(4, 4), Box.white_circle1);
        ChekersMap.set(new Location(3,4), Box.pink_circle1);
        ChekersMap.set(new Location(4,3), Box.pink_circle1);
    }

    public void recountCells() {  // пересчёт шашочек
        whiteChekers = 0;
        pinkChekers = 0;
        markedCell = 0;
        for (Location location : Ranges.getAllLocations()) {
            if(ChekersMap.get(location) == Box.sqrt_Marked1) {
                markedCell++;
            } else if(ChekersMap.get(location) == Box.white_circle1) {
                whiteChekers++;
            } else if(ChekersMap.get(location) == Box.pink_circle1) {
                pinkChekers++;
            }
        }
    }
    // выделяет клетки
    public void setMark(Location location) {
        ChekersMap.set(location, Box.sqrt_Marked1);
    }
    // устанавливает белые шашки
    public void setWhiteChekers(Location location) {
        ChekersMap.set(location, Box.white_circle1);
    }

    // устанавливает розовые шашки
    public void setPinkChekers(Location location) {
        ChekersMap.set(location, Box.pink_circle1);
    }

    public void clearMarkedCell() {
        for (Location location : Ranges.getAllLocations()) {
            if (ChekersMap.get(location) == Box.sqrt_Marked1) {
                ChekersMap.set(location, Box.sqrt1);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        GameMap map = null;
        try {
            map = (GameMap) super.clone();
        } catch (CloneNotSupportedException e) {
            map = new GameMap();
        }
        map.ChekersMap = (Matrix) ChekersMap.clone();
        map.pinkChekers = this.pinkChekers;
        map.whiteChekers = this.whiteChekers;
        map.markedCell = this.markedCell;

        return map;
    }
}
