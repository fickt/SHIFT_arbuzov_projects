package ru.cft.javaLessons.miner.model.mainwindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.javaLessons.miner.listeners.EndGameEventListener;
import ru.cft.javaLessons.miner.listeners.LoseGameEventListener;
import ru.cft.javaLessons.miner.listeners.StartGameEventListener;
import ru.cft.javaLessons.miner.listeners.WinGameEventListener;
import ru.cft.javaLessons.miner.model.mainwindow.cell.Cell;
import ru.cft.javaLessons.miner.view.GameType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWindowModelImpl implements MainWindowModel {
    private static final String LOG_FIRST_CELL_OPENED = "First cell is opened! X:{}, Y:{}";
    private static final String LOG_CELL_OPENED = "Cell is opened! X:{}, Y:{}";
    private static final String LOG_CELL_MINED = "Cell is mined! X:{}, Y:{}";
    private static final String LOG_WIN = "win :)";
    private static final String LOG_GAME_PROGRESS = "Mines total: {}, mined cells flagged: {}, empty cells flagged: {}";
    private static final Logger logger = LogManager.getLogger(MainWindowModelImpl.class);
    private static final int[] NEIGHBORING_CELLS_COORDINATES = new int[]{
            -1, -1,
            -1, 0,
            -1, 1,
            0, -1,
            0, 1,
            1, -1,
            1, 0,
            1, 1
    };

    private static final Random random = new Random();
    private int fieldHeight;
    private int fieldWidth;

    private int minedCellsFlagged = 0;
    private int emptyCellsFlagged = 0;
    private int minesTotal;

    private Cell[][] gameField;

    private LoseGameEventListener loseGameEventListener;

    private WinGameEventListener winGameEventListener;

    private StartGameEventListener startGameEventListener;

    private EndGameEventListener endGameEventListener;

    private boolean isFirstStepDone = false;
    private GameType gameType = GameType.NOVICE;

    @Override
    public void startGame() {
        endGameEventListener.onGameEnd();
        isFirstStepDone = false;
        gameField = new Cell[gameType.getHeight()][gameType.getWidth()];
        for (int x = 0; x < gameType.getHeight(); x++) {
            for (int y = 0; y < gameType.getWidth(); y++) {
                Cell newCell = new Cell();
                gameField[x][y] = newCell;
                newCell.setxCoord(x);
                newCell.setyCoord(y);
                gameField[x][y] = newCell;
            }
        }
        fieldHeight = gameType.getHeight();
        fieldWidth = gameType.getWidth();
        minesTotal = gameType.getMines();
        minedCellsFlagged = 0;
        emptyCellsFlagged = 0;
    }

    @Override
    public void openCellFirstStep(int x, int y) {
        isFirstStepDone = true;
        logger.info(LOG_FIRST_CELL_OPENED, x, y);
        gameField[x][y].setFirstOpened(true);
        generateMines();
        countBombsAroundCells();
        openCell(x, y);
        startGameEventListener.onStart();
    }

    @Override
    public void openCell(int x, int y) {
        Cell cell = gameField[x][y];

        if (cell.isOpen()) {
            return;
        }

        if (cell.isFlagged()) {
            cell.flag();
        }
        logger.info(LOG_CELL_OPENED, x, y);
        cell.open();
        if (!cell.isMined() && cell.getMinesAroundCounter() == 0) {
            getNeighbors(x, y)
                    .stream()
                    .filter(obj -> !obj.isFlagged())
                    .forEach(obj -> openCell(obj.getxCoord(), obj.getyCoord()));
        }
    }

    @Override
    public void setFlagFirstStep(int x, int y) {
        isFirstStepDone = true;
        startGameEventListener.onStart();
        generateMines();
        countBombsAroundCells();
        setFlag(x, y);
    }

    @Override
    public void setFlag(int x, int y) {
        Cell cell = gameField[x][y];
        if (cell.isOpen()) {
            return;
        }
        cell.flag();
        if (cell.isFlagged() && cell.isMined()) {
            minedCellsFlagged++;
        } else if (cell.isFlagged() && !cell.isMined()) {
            emptyCellsFlagged++;
        } else if (!cell.isFlagged() && cell.isMined()) {
            minedCellsFlagged--;
        } else {
            emptyCellsFlagged--;
        }

    }

    public Cell[][] getCellState() {
        return gameField;
    }

    public boolean checkWinCondition() {
        if (minesTotal == minedCellsFlagged && emptyCellsFlagged == 0) {
            logger.info(LOG_WIN);
        } else {
            logger.info(LOG_GAME_PROGRESS, minesTotal, minedCellsFlagged, emptyCellsFlagged);
        }
        return minesTotal == minedCellsFlagged && emptyCellsFlagged == 0;
    }

    @Override
    public void handleLeftButtonClick(int x, int y) {
        logger.info("LEFT BUTTON CLICK X:" + x + " Y:" + y);
        if (!isFirstStepDone) {
            startGame();
            openCellFirstStep(y, x);
        } else {
            openCell(y, x);
            if (gameField[y][x].isMined()) {
                isFirstStepDone = false;
                loseGameEventListener.onLose();
                endGameEventListener.onGameEnd();
            }
        }
    }

    @Override
    public void handleMiddleButtonClick(int x, int y) {
        logger.info("MIDDLE BUTTON CLICK" + x + " y " + y);
        if (!isFirstStepDone) {
            startGame();
            setFlagFirstStep(y, x);
        } else {
            setFlag(y, x);
        }
        if (checkWinCondition()) {
            isFirstStepDone = false;
            winGameEventListener.onWin();
            endGameEventListener.onGameEnd();
        }
    }

    @Override
    public void setWinGameEventListener(WinGameEventListener eventListener) {
        this.winGameEventListener = eventListener;
    }
    @Override
    public void setLoseGameEventListener(LoseGameEventListener eventListener) {
        this.loseGameEventListener = eventListener;
    }
    @Override
    public void setStartGameEventListener(StartGameEventListener eventListener) {
        this.startGameEventListener = eventListener;
    }
    @Override
    public void setEndGameEventListener(EndGameEventListener eventListener) {
        this.endGameEventListener = eventListener;
    }
    @Override
    public void setDifficulty(GameType gameType) {
        this.gameType = gameType;
    }
    @Override
    public GameType getDifficulty() {
        return this.gameType;
    }

    private void generateMines() {
        int count = 0;
        int randomXCoordinate = generateRandomCoordinate(fieldHeight);
        int randomYCoordinate = generateRandomCoordinate(fieldWidth);

        while (count < minesTotal) {
            Cell cell = gameField[randomXCoordinate][randomYCoordinate];
            if (!cell.isMined() && !cell.isFirstOpened()) {
                cell.setMined(true);
                logger.info(LOG_CELL_MINED, randomXCoordinate, randomYCoordinate);
                count++;
            }
            randomXCoordinate = generateRandomCoordinate(fieldHeight);
            randomYCoordinate = generateRandomCoordinate(fieldWidth);
        }
    }

    private int generateRandomCoordinate(int limit) {
        return random.nextInt(limit + 1);
    }

    private List<Cell> getNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        for (int i = 0; i < NEIGHBORING_CELLS_COORDINATES.length; i++) {
            int dx = NEIGHBORING_CELLS_COORDINATES[i];
            int dy = NEIGHBORING_CELLS_COORDINATES[++i];
            int newX = x + dx;
            int newY = y + dy;
            if (newX >= 0 && newY >= 0 && newX < gameField.length && newY < gameField[0].length) {
                neighbors.add(gameField[newX][newY]);
            }
        }
        return neighbors;
    }

    private void countBombsAroundCells() {
        for (int x = 0; x < gameField.length; x++) {
            for (int y = 0; y < gameField[0].length; y++) {
                Cell cell = gameField[x][y];
                long bombCounter = getNeighbors(x, y)
                        .stream()
                        .filter(Cell::isMined)
                        .count();
                cell.setMinesAroundCounter((int) bombCounter);
            }
        }
    }
}
