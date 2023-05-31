package ru.cft.javaLessons.miner.model.highscoreswindow;

import ru.cft.javaLessons.miner.recordsstorage.Record;

import java.util.List;

public interface HighScoresWindowModel {
    List<Record> getRecords();
}
