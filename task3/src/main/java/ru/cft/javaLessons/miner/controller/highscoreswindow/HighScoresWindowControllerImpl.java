package ru.cft.javaLessons.miner.controller.highscoreswindow;

import ru.cft.javaLessons.miner.model.highscoreswindow.HighScoresWindowModel;
import ru.cft.javaLessons.miner.recordsstorage.Record;
import ru.cft.javaLessons.miner.view.HighScoresWindow;

import java.util.List;

public class HighScoresWindowControllerImpl implements HighScoresWindowController {
    private final HighScoresWindowModel model;
    private final HighScoresWindow view;

    public HighScoresWindowControllerImpl(HighScoresWindow view, HighScoresWindowModel model) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getRecords() {
        List<Record> recordList = model.getRecords();
        System.out.println(recordList);
        Record novice = recordList.get(0);
        Record medium = recordList.get(1);
        Record expert = recordList.get(2);

        view.setNoviceRecord(novice.getName(), novice.getTime());
        view.setMediumRecord(medium.getName(), medium.getTime());
        view.setExpertRecord(expert.getName(), expert.getTime());
        view.setVisible(true);
    }
}
