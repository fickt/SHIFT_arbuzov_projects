package ru.cft.javaLessons.miner.model.recordswindow;

import ru.cft.javaLessons.miner.recordsstorage.RecordsStorage;
import ru.cft.javaLessons.miner.view.GameType;

public class RecordsWindowModelImpl implements RecordsWindowModel {
    private final RecordsStorage recordsStorage;

    public RecordsWindowModelImpl(RecordsStorage recordsStorage) {
        this.recordsStorage = recordsStorage;
    }

    @Override
    public boolean checkIfRecordIsHit(GameType gameType, int time) {
        return recordsStorage.isRecordHit(gameType, time);
    }

    @Override
    public void writeRecord(GameType gameType, String name, int time) {
        recordsStorage.writeNewRecord(gameType, name, time);
    }
}
