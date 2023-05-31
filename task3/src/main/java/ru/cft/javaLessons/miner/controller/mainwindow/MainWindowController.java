package ru.cft.javaLessons.miner.controller.mainwindow;


import ru.cft.javaLessons.miner.listeners.EndGameEventListener;
import ru.cft.javaLessons.miner.listeners.LoseGameEventListener;
import ru.cft.javaLessons.miner.listeners.StartGameEventListener;
import ru.cft.javaLessons.miner.listeners.WinGameEventListener;
import ru.cft.javaLessons.miner.model.mainwindow.cell.Cell;
import ru.cft.javaLessons.miner.view.ButtonType;
import ru.cft.javaLessons.miner.view.GameType;


public interface MainWindowController {
    void openGame();

    void startNewGame();
    void handleMouseClick(int x, int y, ButtonType buttonType);

    void stopApp();
    void openCell(int x, int y);
    void setFlag(int x, int y);
    void setFlagFirstStep(int x, int y);
    boolean checkWinCondition();
    int getTimerValue();
    Cell getCell(int x, int y);
    void startTimer();

    void openCellFirstStep(int y, int x);

    void stopTimer();
    void setLoseGameEventListener(LoseGameEventListener e);
    void setWinGameEventListener(WinGameEventListener e);
    void setStartGameEventListener(StartGameEventListener e);
    void setEndGameEventListener(EndGameEventListener eventListener);
    void setDifficulty(GameType gameType);
    GameType getDifficulty();
}
