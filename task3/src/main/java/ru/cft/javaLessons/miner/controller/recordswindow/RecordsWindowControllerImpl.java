package ru.cft.javaLessons.miner.controller.recordswindow;

import ru.cft.javaLessons.miner.model.recordswindow.RecordsWindowModel;
import ru.cft.javaLessons.miner.view.GameType;
import ru.cft.javaLessons.miner.view.RecordsWindow;

public class RecordsWindowControllerImpl implements RecordsWindowController {
    private final RecordsWindow view;
    private final RecordsWindowModel model;

    public RecordsWindowControllerImpl(RecordsWindow view, RecordsWindowModel model) {
        this.view = view;
        this.model = model;
    }

    public void isRecordHit(GameType gameType, int time) {
        if (model.checkIfRecordIsHit(gameType, time)) {
            view.setVisible(true);
        }
    }

    public void writeRecord(String name, GameType gameType, int time) {
        model.writeRecord(gameType, name, time);
    }
}
