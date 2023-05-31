package ru.cft.javaLessons.miner.recordsstorage;

import ru.cft.javaLessons.miner.view.GameType;

import java.util.List;

public interface RecordsStorage {
    List<Record> getRecords();

    boolean isRecordHit(GameType gameType, int time);

    void writeNewRecord(GameType gameType, String name, int time);
}
