package Reversy;

import History.History;
import Player.ActivePlayer;
import Player.Computer;
import Player.IPlayer;
import Player.Person;

public class Game {

    private History historyGame;
    private ActivePlayer activePlayer;
    private GameState gameState;
    private GameMap chekersMap;
    public IPlayer firstPlayer;
    public IPlayer secondPlayer;
    public  Game (int cols, int rows) {

        Ranges.setSize(new Location(cols, rows));
        chekersMap = new GameMap();
    }

    public void start (Mode mode) {
        // выбирается режим игры (по дефолту это будет любительский уровень против компа)
        // Тогда я должна создать функцию, которая выбирает режим игры
        Mode.setMode(mode);  // игра с компом
        activePlayer = ActivePlayer.FIRST;  // установили активного игрока
        gameState = GameState.StepFirst;
        initPlayers();
        chekersMap.init();
        firstPlayer.MarkCell(chekersMap);
        chekersMap.recountCells();
        historyGame = new History();
    }

    public Box getBox(Location location) {
        return chekersMap.get(location);
    }

    public void pressLeftButton(Location location){
        try {
            historyGame.add(chekersMap, firstPlayer, secondPlayer, activePlayer, gameState);  // в истории должны храниться поле, игроки, очерёдность хода, активный игрок
        } catch (CloneNotSupportedException e) {
            start(Mode.getMode());
        }
        // надо сначала проверить, что ход был сделан в маркированную клетку
        if (chekersMap.get(location) == Box.sqrt_Marked1) {
            if (activePlayer == ActivePlayer.FIRST) { // ходит первый игрок
                firstPlayer.MakeMove(chekersMap, location);  // сходил первый игрок
                chekersMap.clearMarkedCell(); // стереть старые маркированные клетки
                secondPlayer.MarkCell(chekersMap);  // пометили клетки для второго игрока
                gameState = GameState.StepSecond;  // ходит второй игрок
                // меняем активного игрока на второго или на компьютер
                if (Mode.getMode() == Mode.FRIEND) {
                    activePlayer = ActivePlayer.SECOND;
                } else {
                    activePlayer = ActivePlayer.COMPUTER;
                    // перед тем как походит комп сохраняем поле
                    chekersMap.recountCells();
                    if (CheckGame()) { return;} // выходим из функции, если нельзя больше ходить
                    try {
                        historyGame.add(chekersMap, firstPlayer, secondPlayer, activePlayer, gameState);  // в истории должны храниться поле, игроки, очерёдность хода, активный игрок
                    } catch (CloneNotSupportedException e) {
                        start(Mode.getMode());
                    }
                    Location location1 = ((Computer)secondPlayer).getLocation(chekersMap);  // комп расчитал координату
                    secondPlayer.MakeMove(chekersMap, location1);  // комп сходил
                    chekersMap.clearMarkedCell();  // очистить поле от меток
                    firstPlayer.MarkCell(chekersMap); // пометить нужные клетки
                    activePlayer = ActivePlayer.FIRST;
                    gameState = GameState.StepFirst;
                }
            } else if (activePlayer == ActivePlayer.SECOND) {
                // ходит второй игрок
                secondPlayer.MakeMove(chekersMap, location);
                // стереть старые маркированные клетки
                chekersMap.clearMarkedCell();
                firstPlayer.MarkCell(chekersMap);
                // меняем активного игрока на первого
                activePlayer = ActivePlayer.FIRST;
                gameState = GameState.StepFirst; // ходит первый игрок
            }
        }
        // сделать проверку на конец игры
        chekersMap.recountCells();
        CheckGame(); // проверяет состояние игры
    }

    private boolean CheckGame() {
        if (chekersMap.getCountMarkedCell() == 0) {  // игра заканчивается и выявляется победитель
            determineWinner();  // определили победителя
            return true;
        }
        return false;
    }

    private void determineWinner() {  // определение победителя
        if (chekersMap.getCountPinkChekers() > chekersMap.getCountWhiteChekers()) {
            gameState = GameState.WinFirs;
        } else if (chekersMap.getCountPinkChekers() < chekersMap.getCountWhiteChekers()) {
            gameState =  GameState.WinSecond;
        } else {
            gameState =  GameState.Draw;
        }
    }

    private void initPlayers() {  // создание игроков
        if (Mode.getMode() == Mode.FRIEND) {
            firstPlayer = new Person("Katya", Box.pink_circle1);
            secondPlayer = new Person("Masha", Box.white_circle1);
        } else if (Mode.getMode() == Mode.MIDDLE) {
            firstPlayer = new Person("Katya", Box.pink_circle1);
            secondPlayer = new Computer("Computer", Box.white_circle1);
            ((Computer)secondPlayer).setIntelligence(Mode.MIDDLE);
        } else {
            firstPlayer = new Person("Katya", Box.pink_circle1);
            secondPlayer = new Computer("Clever Computer", Box.white_circle1);
            ((Computer)secondPlayer).setIntelligence(Mode.SENIOR);  // установить более умный алгоритм
        }
    }

    public void pressStepBack() {
        if (!historyGame.isEmpty()) {
            System.out.println("Back");
            backHistory();
            if (activePlayer == ActivePlayer.COMPUTER) {
                backHistory();
            }
        }
    }

    private void backHistory() {
        chekersMap = historyGame.getBackGameMap();
        firstPlayer = historyGame.getBackFirstPlayer();
        secondPlayer = historyGame.getBackSecondPlayer();
        activePlayer = historyGame.getBackActivePlayer();
        gameState = historyGame.getBackGameState();
    }

    public void pressStepForward() {  // было лень реализовывать
        System.out.println("Forward");
    }

    public GameState getState() {
        return gameState;
    }
}
