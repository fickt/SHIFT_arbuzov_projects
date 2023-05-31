package ru.cft.javaLessons.miner.model.recordswindow;

import ru.cft.javaLessons.miner.view.GameType;

public interface RecordsWindowModel {
    boolean checkIfRecordIsHit(GameType gameType, int time);

    void writeRecord(GameType gameType, String name, int time);
}
