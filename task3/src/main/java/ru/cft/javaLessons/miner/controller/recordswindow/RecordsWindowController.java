package ru.cft.javaLessons.miner.controller.recordswindow;

import ru.cft.javaLessons.miner.view.GameType;


public interface RecordsWindowController {
    void isRecordHit(GameType gameType, int time);

    void writeRecord(String name, GameType gameType, int time);
}
