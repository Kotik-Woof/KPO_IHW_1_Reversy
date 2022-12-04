package Player;

import Player.Person;
import Reversy.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Computer extends Person {


    Mode intelligence_;
    public Computer(String _name, Box _color) {
        super(_name, _color);
    }

    @Override
    public void MarkCell(GameMap CellMap) { // позже переопределю
        super.MarkCell(CellMap);
    }

    @Override
    public void MakeMove(GameMap CellMap, Location location) {  // позже переопределю
       super.MakeMove(CellMap, location);
    }


    // установили интеллект
    public void setIntelligence(Mode intelligence) {
        intelligence_ = intelligence;
    }

    public Location getLocation(GameMap CellMap) {
//        double maxValue = 0;
//        Location resultLocation = new Location(0, 0);
        if (intelligence_ == Mode.MIDDLE) {
            return findLocation(CellMap);
            // переходим в одну функцию
        } else {
            // переходим в другую функцию
            return null;
        }
    }

    private Location findLocation(GameMap CellMap) {
        Location resultLocation = new Location(0, 0);
        double maxValue = 0;
        double tempValue = 0;
        for (Location location : Ranges.getAllLocations()) {
            if (CellMap.get(location) == Box.sqrt_Marked1) {
                tempValue = R(CellMap, location);
                if (tempValue > maxValue) {
                    maxValue = tempValue;
                    resultLocation = location;
                }
            }
        }
        return resultLocation;
    }

    // Вообще функция должна вернуть значение
    private double R(GameMap CellMap, Location location) {
        double tempValue = MakeImaganaryMove(CellMap, location);
        tempValue += estimateStepValue(location); // вычисляет ценность хода
        return tempValue;
    }

    private double estimateStepValue(Location location) {
        if (isCorner(location)) {
            return 0.8;
        } else if(isEdge(location)) {
            return 0.4;
        }
        return 0;
    }

    private boolean isCorner(Location location) {
        return (location.x == 0 && location.y == 0) ||
                (location.x == Ranges.getSize().x && location.y == 0)
                || (location.x == 0 && location.y == Ranges.getSize().y)
                || (location.x == Ranges.getSize().x && location.y == Ranges.getSize().y);
    }

    private int MakeImaganaryMove(GameMap CellMap, Location location) {
        int capturedCheckers = 0; // сумма захвата
//        setChekers(CellMap, location);
        // ревёрс всех цветов на противоположные
        Location tempLocation = new Location(0, 0);
        if (direction.get(location).contains(Direction.UP)) {  // закрашиваем верх
            tempLocation.x = location.x;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                // функция, которая вычисляет ценность замыкаемой клетки
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.y--;
            }
        }
        if (direction.get(location).contains(Direction.UP_RIGHT)) { // закрашиваем право верх
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x++;
                tempLocation.y--;
            }
        }
        if (direction.get(location).contains(Direction.RIGHT)) { // закрашиваем право
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN_RIGHT)) {  // закрашиваем право низ
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x++;
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN)) { // закрашиваем низ
            tempLocation.x = location.x;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN_LEFT)) { // закрашиваем лево низ
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x--;
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.LEFT)) { // Закрашиваем лево
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x--;
            }
        }
        if (direction.get(location).contains(Direction.UP_LEFT)) {  // Закрашиваем лево верх
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                capturedCheckers += estimateValue(tempLocation);;
                tempLocation.x--;
                tempLocation.y--;
            }
        }
        return capturedCheckers;  // вернули сумму захвата
    }

    private int estimateValue(Location location) {
        // если кромочные клетки
        if (isEdge(location)) {
            return 2;
        } else {  // остальные клетки
            return 1;
        }
    }

    private boolean isEdge(Location location) {
        return location.x == 0 || location.x == Ranges.getSize().x
                || location.y == 0 || location.y == Ranges.getSize().y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Computer computer = null;
        computer = new Computer(this.Name, this.Color);
        computer.direction = (Hashtable<Location, ArrayList<Direction>>) this.direction.clone();
        computer.setIntelligence(this.intelligence_);
        return computer;
    }
}
