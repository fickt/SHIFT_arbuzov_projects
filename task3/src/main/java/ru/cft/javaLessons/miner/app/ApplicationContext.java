package ru.cft.javaLessons.miner.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.javaLessons.miner.controller.highscoreswindow.HighScoresWindowController;
import ru.cft.javaLessons.miner.controller.highscoreswindow.HighScoresWindowControllerImpl;
import ru.cft.javaLessons.miner.controller.mainwindow.MainWindowController;
import ru.cft.javaLessons.miner.controller.mainwindow.MainWindowControllerImpl;
import ru.cft.javaLessons.miner.controller.recordswindow.RecordsWindowController;
import ru.cft.javaLessons.miner.controller.recordswindow.RecordsWindowControllerImpl;
import ru.cft.javaLessons.miner.model.highscoreswindow.HighScoresWindowModelImpl;
import ru.cft.javaLessons.miner.model.mainwindow.MainWindowModelImpl;
import ru.cft.javaLessons.miner.model.recordswindow.RecordsWindowModelImpl;
import ru.cft.javaLessons.miner.recordsstorage.RecordsFileStorageImpl;
import ru.cft.javaLessons.miner.recordsstorage.RecordsStorage;
import ru.cft.javaLessons.miner.view.*;


public class ApplicationContext {
    private static final Logger logger = LogManager.getLogger(ApplicationContext.class);
    private final RecordsStorage storage = new RecordsFileStorageImpl();
    //Views
    private final MainWindow mainWindow = new MainWindow();
    private final LoseWindow loseWindow = new LoseWindow(mainWindow);
    private final WinWindow winWindow = new WinWindow(mainWindow);
    private final RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
    private final SettingsWindow settingsWindow = new SettingsWindow(mainWindow);
    //Controllers
    private final MainWindowController mainWindowController = new MainWindowControllerImpl(
            mainWindow,
            new MainWindowModelImpl());
    private final RecordsWindowController recordsWindowController = new RecordsWindowControllerImpl(
            recordsWindow,
            new RecordsWindowModelImpl(storage)
    );
    private final HighScoresWindowController highScoresWindowController = new HighScoresWindowControllerImpl(
            new HighScoresWindow(mainWindow),
            new HighScoresWindowModelImpl(storage));

    public void start() {
        setupListenersForMainWindow();
        setupListenersForWinWindow();
        setupListenersForSettingsWindow();
        setupListenerForRecordsWindow();
        mainWindowController.openGame();
        setupListenersForLoseWindow();
        setEventListeners();
    }

    private void setEventListeners() {
        mainWindowController.setLoseGameEventListener(this::loseGame);
        mainWindowController.setWinGameEventListener(this::winGame);
    }

    private void setupListenersForMainWindow() {
        mainWindow.setNewGameMenuAction(e -> mainWindowController.startNewGame());
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindowController.getRecords());
        mainWindow.setExitMenuAction(e -> mainWindowController.stopApp());
        mainWindow.setCellListener(mainWindowController::handleMouseClick);
    }

    private void loseGame() {
        mainWindowController.stopTimer();
        loseWindow.setVisible(true);
    }

    private void winGame() {
        int timerValue = mainWindowController.getTimerValue();
        recordsWindowController.isRecordHit(mainWindowController.getDifficulty(), timerValue);
        winWindow.setVisible(true);
    }

    private void setupListenerForRecordsWindow() {
        recordsWindow.setNameListener(e -> {
            int timerValue = mainWindowController.getTimerValue();
            recordsWindowController.writeRecord(e, mainWindowController.getDifficulty(), timerValue);
        });
    }

    private void setupListenersForWinWindow() {
        winWindow.setNewGameListener(e -> mainWindowController.startNewGame());
        winWindow.setExitListener(e -> mainWindowController.stopApp());
    }

    private void setupListenersForLoseWindow() {
        loseWindow.setNewGameListener((e) -> mainWindowController.startNewGame());
        loseWindow.setExitListener(e -> mainWindowController.stopApp());
    }

    private void setupListenersForSettingsWindow() {
        settingsWindow.setGameTypeListener(e -> {
            GameType gameType;
            switch (e) {
                case NOVICE:
                    gameType = GameType.NOVICE;
                    break;
                case MEDIUM:
                    gameType = GameType.MEDIUM;
                    break;
                case EXPERT:
                    gameType = GameType.EXPERT;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown game type: " + e);
            }
            mainWindowController.setDifficulty(gameType);
            mainWindowController.startNewGame();
        });
    }
}
