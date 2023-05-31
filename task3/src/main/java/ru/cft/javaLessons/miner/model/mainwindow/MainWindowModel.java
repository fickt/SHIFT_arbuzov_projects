package ru.cft.javaLessons.miner.model.mainwindow;

import ru.cft.javaLessons.miner.listeners.EndGameEventListener;
import ru.cft.javaLessons.miner.listeners.LoseGameEventListener;
import ru.cft.javaLessons.miner.listeners.StartGameEventListener;
import ru.cft.javaLessons.miner.listeners.WinGameEventListener;
import ru.cft.javaLessons.miner.model.mainwindow.cell.Cell;
import ru.cft.javaLessons.miner.view.GameType;


public interface MainWindowModel {
    void startGame();
    void openCell(int x, int y);
    void openCellFirstStep(int x, int y);
    void setFlag(int x, int y);
    void setFlagFirstStep(int x, int y);
    boolean checkWinCondition();
    void setLoseGameEventListener(LoseGameEventListener e);
    void setWinGameEventListener(WinGameEventListener e);
    void setStartGameEventListener(StartGameEventListener eventListener);
    void setEndGameEventListener(EndGameEventListener eventListener);
    void handleLeftButtonClick(int x, int y);
    void handleMiddleButtonClick(int x, int y);
    void setDifficulty(GameType gameType);
    GameType getDifficulty();
    Cell[][] getCellState();
}
