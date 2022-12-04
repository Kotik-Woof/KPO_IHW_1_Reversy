package Player;

import Reversy.*;

import java.util.*;

public class Person implements IPlayer {

    Box Color;
    String Name;

    protected Hashtable<Location, ArrayList<Direction>> direction = new Hashtable<>();

    // конструктор
    public Person(String _name, Box _color) {
        Name = _name;
        Color = _color;
    }

    public String getName() {
        return Name;
    }

    public Box getColor() {
        return Color;
    }


    @Override
    public void MarkCell(GameMap CellMap) {  // игрок ответственен за логику выделения клеток
        direction.clear();  // очистили направления
        // пройтись по всему полю надо.
        for (Location location : Ranges.getAllLocations()) {
            if (CellMap.get(location) != Color && CellMap.get(location) != Box.sqrt1 && CellMap.get(location) != Box.sqrt_Marked1) {
                // Нашли фишки врага.
                // Теперь надо посмотреть, где нет шашек.
                Location checkLocation = new Location(location.x, location.y - 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // пустая сверху
                    Location tempLocation = new Location(location.x, location.y + 1);
                    for (; tempLocation.y <= Ranges.getSize().y; tempLocation.y++) {  // ищем снизу нашу клетку
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {  // если пустая встретилась, то мы не должны выделять
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x, location.y - 1);
                            CellMap.setMark(addLocation); // выделяем сверху
                            addDirection(addLocation, Direction.DOWN);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x, location.y + 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // пустая снизу
                    Location tempLocation = new Location(location.x, location.y - 1);
                    for (; tempLocation.y >= 0; tempLocation.y--) {  // ищем нашу клетку свехру
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x, location.y + 1);
                            CellMap.setMark(addLocation); // выделяем снизу
                            addDirection(addLocation, Direction.UP);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x - 1, location.y);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) {  // пустая слева
                    Location tempLocation = new Location(location.x + 1, location.y);
                    for (; tempLocation.x <= Ranges.getSize().x; tempLocation.x++) { // ищем справа
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x - 1, location.y);
                            CellMap.setMark(addLocation);  // выделяем слева
                            addDirection(addLocation, Direction.RIGHT);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x + 1, location.y);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // пустая справа
                    Location tempLocation = new Location(location.x - 1, location.y);
                    for (; tempLocation.x >= 0; tempLocation.x--) { // ищем слева нашу
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x + 1, location.y);
                            CellMap.setMark(addLocation);  // выделяем справа
                            addDirection(addLocation, Direction.LEFT);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x + 1, location.y - 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // верхний правый угол пуст
                    Location tempLocation = new Location(location.x - 1, location.y + 1);
                    for (; tempLocation.x >= 0 && tempLocation.y <= Ranges.getSize().y; tempLocation.x--, tempLocation.y++) { // ищем в левом нижнем углу
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x + 1, location.y - 1);
                            CellMap.setMark(addLocation); // выделяем правый верхний
                            addDirection(addLocation, Direction.DOWN_LEFT);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x - 1, location.y + 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // нижний левый угол пуст
                    Location tempLocation = new Location(location.x + 1, location.y - 1);
                    for (; tempLocation.x <= Ranges.getSize().x && tempLocation.y >= 0; tempLocation.x++, tempLocation.y--) { // ищем в правом верхнем углу
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x - 1, location.y + 1);
                            CellMap.setMark(addLocation);  // выделяем нижний левый
                            addDirection(addLocation, Direction.UP_RIGHT);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x - 1, location.y - 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) { // верхний левый пуст
                    Location tempLocation = new Location(location.x + 1, location.y + 1);
                    for (; tempLocation.x <= Ranges.getSize().x && tempLocation.y <= Ranges.getSize().y;
                         tempLocation.x++, tempLocation.y++) { // ищем в правом нижнем углу
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x - 1, location.y - 1);
                            CellMap.setMark(addLocation); // выделяем верхний левый
                            addDirection(addLocation, Direction.DOWN_RIGHT);
                            break; // и сделать break
                        }
                    }
                }
                checkLocation = new Location(location.x + 1, location.y + 1);
                if (CellMap.get(checkLocation) == Box.sqrt1 || CellMap.get(checkLocation) == Box.sqrt_Marked1) {  // в правом нижнем углу пусто
                    Location tempLocation = new Location(location.x - 1, location.y - 1);
                    for (; tempLocation.x >= 0 && tempLocation.y >= 0; tempLocation.x--, tempLocation.y--) { // ищем в левом верхнем углу
                        if (CellMap.get(tempLocation) == Box.sqrt1 || CellMap.get(tempLocation) == Box.sqrt_Marked1) {
                            break;
                        }
                        if (CellMap.get(tempLocation) == Color) {
                            // добавляем в список клеток, которые надо выделить
                            Location addLocation = new Location(location.x + 1, location.y + 1);
                            CellMap.setMark(addLocation);  // выделяем правый нижний
                            addDirection(addLocation, Direction.UP_LEFT);
                            break; // и сделать break
                        }
                    }
                }
            }
        }
    }

    @Override
    public void MakeMove(GameMap CellMap, Location location) {
        // добавления шашки на поле, которое выделено
        setChekers(CellMap, location);
        // ревёрс всех цветов на противоположные
        Location tempLocation = new Location(0, 0);
        if (direction.get(location).contains(Direction.UP)) {  // закрашиваем верх
            tempLocation.x = location.x;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.y--;
            }
        }
        if (direction.get(location).contains(Direction.UP_RIGHT)) { // закрашиваем право верх
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x++;
                tempLocation.y--;
            }
        }
        if (direction.get(location).contains(Direction.RIGHT)) { // закрашиваем право
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN_RIGHT)) {  // закрашиваем право низ
            tempLocation.x = location.x + 1;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x++;
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN)) { // закрашиваем низ
            tempLocation.x = location.x;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.DOWN_LEFT)) { // закрашиваем лево низ
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y + 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x--;
                tempLocation.y++;
            }
        }
        if (direction.get(location).contains(Direction.LEFT)) { // Закрашиваем лево
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x--;
            }
        }
        if (direction.get(location).contains(Direction.UP_LEFT)) {  // Закрашиваем лево верх
            tempLocation.x = location.x - 1;
            tempLocation.y = location.y - 1;
            while (CellMap.get(tempLocation) != Color) {
                setChekers(CellMap, tempLocation);
                tempLocation.x--;
                tempLocation.y--;
            }
        }
    }


    private void setChekers(GameMap CellMap, Location location) {
        if (Color == Box.pink_circle1) {
            CellMap.setPinkChekers(location);
        } else {
            CellMap.setWhiteChekers(location);
        }
    }

    private void addDirection(Location addLocation, Direction typeDirection) {
        if (direction.get(addLocation) == null) {
            direction.put(addLocation, new ArrayList<Direction>());
        }
        direction.get(addLocation).add(typeDirection);
    }

    private void setDirection(Object direct) {
        direction = (Hashtable<Location, ArrayList<Direction>>)direct;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person person = null;
        try
        {
            person = (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            person = new Person(this.Name, this.Color);
        }
        person.setDirection(this.direction.clone());
        return person;
    }
}
