package ru.cft.javaLessons.miner.model.highscoreswindow;

import ru.cft.javaLessons.miner.recordsstorage.Record;
import ru.cft.javaLessons.miner.recordsstorage.RecordsStorage;

import java.util.List;

public class HighScoresWindowModelImpl implements HighScoresWindowModel {
    private final RecordsStorage recordsStorage;

    public HighScoresWindowModelImpl(RecordsStorage recordsStorage) {
        this.recordsStorage = recordsStorage;
    }

    @Override
    public List<Record> getRecords() {
        return recordsStorage.getRecords();
    }
}
