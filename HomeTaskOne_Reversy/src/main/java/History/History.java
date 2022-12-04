package History;

import Player.ActivePlayer;
import Player.IPlayer;
import Reversy.GameMap;
import Reversy.GameState;

import java.util.Stack;

public class History {  // хранит историю ходов
    // надо сохранять поле и распространения
    // будет реализовано на двух стеках,
    // чтобы продвигаться вперёд и назад
    Stack<GameMap> gameMapStack;
    Stack<IPlayer> firstPlayerStack;
    Stack<IPlayer> secondPlayerStack;
    Stack<ActivePlayer> activePlayerStack;
    Stack<GameState> gameStateStack;

    public History() {
        gameMapStack = new Stack<>();
        firstPlayerStack = new Stack<>();
        secondPlayerStack = new Stack<>();
        activePlayerStack = new Stack<>();
        gameStateStack = new Stack<>();
    }

    public void add(GameMap gameMap, IPlayer firstPayer, IPlayer secondPlayer, ActivePlayer activePlayer, GameState gameState) throws CloneNotSupportedException {
        try {
            gameMapStack.add((GameMap) gameMap.clone());
            firstPlayerStack.add((IPlayer)(firstPayer.clone()));
            secondPlayerStack.add((IPlayer) secondPlayer.clone());
            activePlayerStack.add(activePlayer);
            gameStateStack.add(gameState);
        } catch (CloneNotSupportedException e) {
            return;
        }
    }

    public GameMap getBackGameMap() {
        return gameMapStack.pop();
    }

    public IPlayer getBackFirstPlayer() {
        return firstPlayerStack.pop();
    }

    public IPlayer getBackSecondPlayer() {
        return secondPlayerStack.pop();
    }

    public ActivePlayer getBackActivePlayer() {
        return activePlayerStack.pop();
    }

    public GameState getBackGameState() {
        return gameStateStack.pop();
    }

    public boolean isEmpty() {
        return gameMapStack.isEmpty();
    }
}
