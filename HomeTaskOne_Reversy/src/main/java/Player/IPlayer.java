package Player;

import Reversy.GameMap;
import Reversy.Location;

public interface IPlayer {

    void MarkCell(GameMap CellMap);  // выделение клеток, в которые можно ходить

    void MakeMove(GameMap CellMap, Location location);  // обработка хода (ЛКМ)

    public Object clone() throws CloneNotSupportedException;
}
