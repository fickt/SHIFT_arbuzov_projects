package ru.cft.javaLessons.miner.controller.mainwindow;

import ru.cft.javaLessons.miner.listeners.EndGameEventListener;
import ru.cft.javaLessons.miner.listeners.LoseGameEventListener;
import ru.cft.javaLessons.miner.listeners.StartGameEventListener;
import ru.cft.javaLessons.miner.listeners.WinGameEventListener;
import ru.cft.javaLessons.miner.model.mainwindow.MainWindowModel;
import ru.cft.javaLessons.miner.model.mainwindow.cell.Cell;
import ru.cft.javaLessons.miner.view.ButtonType;
import ru.cft.javaLessons.miner.view.GameImage;
import ru.cft.javaLessons.miner.view.GameType;
import ru.cft.javaLessons.miner.view.MainWindow;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainWindowControllerImpl implements MainWindowController {
    private final MainWindowModel model;
    private final MainWindow view;
    private final Timer timer = new Timer();

    public MainWindowControllerImpl(MainWindow view, MainWindowModel model) {
        this.model = model;
        this.view = view;
        setStartGameEventListener(this::startTimer);
        setEndGameEventListener(this::stopTimer);
    }

    @Override
    public void openGame() {
        GameType gameType = model.getDifficulty();
        view.createGameField(gameType.getHeight(), gameType.getWidth());
        view.setBombsCount(gameType.getMines());
        view.setVisible(true);
    }

    @Override
    public void startNewGame() {
        GameType gameType = model.getDifficulty();
        view.createGameField(gameType.getHeight(), gameType.getWidth());
        view.setBombsCount(gameType.getMines());
        model.startGame();
        refreshViewState();
    }

    @Override
    public void handleMouseClick(int x, int y, ButtonType buttonType) {
        switch (buttonType) {
            case LEFT_BUTTON:
                model.handleLeftButtonClick(x, y);
                break;
            case RIGHT_BUTTON:
                model.handleMiddleButtonClick(x, y);
                break;
        }
        refreshViewState();
    }

    @Override
    public void openCell(int x, int y) {
        model.openCell(x, y);
        refreshViewState();
    }

    @Override
    public void openCellFirstStep(int x, int y) {
        model.openCellFirstStep(x, y);
        refreshViewState();
    }

    @Override
    public void setFlag(int x, int y) {
        model.setFlag(x, y);
        refreshViewState();
    }

    @Override
    public void setFlagFirstStep(int x, int y) {
        model.setFlagFirstStep(x, y);
        refreshViewState();
    }

    @Override
    public Cell getCell(int x, int y) {
        return model.getCellState()[x][y];
    }

    @Override
    public boolean checkWinCondition() {
        if (model.checkWinCondition()) {
            timer.stopTimer();
            return true;
        }
        return false;
    }

    @Override
    public int getTimerValue() {
        return timer.getTimerValue();
    }

    @Override
    public void startTimer() {
        timer.startTimer();
    }

    @Override
    public void stopTimer() {
        timer.stopTimer();
    }

    @Override
    public void setLoseGameEventListener(LoseGameEventListener e) {
        model.setLoseGameEventListener(e);
    }

    @Override
    public void setWinGameEventListener(WinGameEventListener e) {
        model.setWinGameEventListener(e);
    }

    public void setStartGameEventListener(StartGameEventListener eventListener) {
        model.setStartGameEventListener(eventListener);
    }

    @Override
    public void setEndGameEventListener(EndGameEventListener eventListener) {
        model.setEndGameEventListener(eventListener);
    }

    @Override
    public void stopApp() {
        view.dispose();
        timer.shutdown();
    }
    @Override
    public void setDifficulty(GameType gameType) {
        model.setDifficulty(gameType);
    }

    @Override
    public GameType getDifficulty() {
        return model.getDifficulty();
    }

    private void refreshViewState() {
        //view.refreshState(model.getCellState());
        JButton[][] cellButtonsView = view.getCellState();
        Cell[][] cellButtonsModel = model.getCellState();

        for (Cell[] cells : cellButtonsModel) {
            for (Cell cell : cells) {
                int xCoord = cell.getxCoord();
                int yCoord = cell.getyCoord();

                if (cell.isFlagged()) {
                    cellButtonsView[xCoord][yCoord].setIcon(GameImage.MARKED.getImageIcon());
                } else {
                    cellButtonsView[xCoord][yCoord].setIcon(GameImage.CLOSED.getImageIcon());
                }
                if (cell.isOpen())
                    if (cell.isMined()) {
                        cellButtonsView[xCoord][yCoord].setIcon(GameImage.BOMB_ICON.getImageIcon());
                    } else {
                        if (cell.getMinesAroundCounter() == 0) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.EMPTY.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 1) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_1.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 2) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_2.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 3) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_3.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 4) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_4.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 5) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_5.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 6) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_6.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 7) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_7.getImageIcon());
                        }
                        if (cell.getMinesAroundCounter() == 8) {
                            cellButtonsView[xCoord][yCoord].setIcon(GameImage.NUM_8.getImageIcon());
                        }
                    }
            }
        }
    }

    private class Timer {
        private int time;
        private boolean isGameOver = true;
        private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        private Timer() {
            Runnable runnable = () -> {
                if (!isGameOver) {
                    view.setTimerValue(++time);
                }
            };
            scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
        }

        private void stopTimer() {
            isGameOver = true;
        }

        private void startTimer() {
            time = 0;
            isGameOver = false;
        }

        private int getTimerValue() {
            return time;
        }

        private void shutdown() {
            scheduler.shutdown();
        }
    }
}