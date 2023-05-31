package ru.cft.javaLessons.miner.recordsstorage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.javaLessons.miner.view.GameType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecordsFileStorageImpl implements RecordsStorage {
    private static final Logger logger = LogManager.getLogger(RecordsFileStorageImpl.class);
    private static final Path FILE_RECORDS_PATH = Paths.get("task3/src/main/java/ru/cft/javaLessons/miner/recordsstorage/records.txt");

    @Override
    public boolean isRecordHit(GameType gameType, int time) {
        return getRecords()
                .stream()
                .anyMatch(val -> val.getGameType().equals(gameType.name()) && val.getTime() > time);
    }

    @Override
    public void writeNewRecord(GameType gameType, String name, int time) {
        Record newRecord = new Record(name, gameType.name(), time);
        List<Record> recordList = getRecords();
        recordList = recordList.stream()
                .map(val -> {
                            if (val.getGameType().equals(gameType.name()))
                                val = newRecord;
                            return val;
                        }
                )
                .collect(Collectors.toList());
        try (BufferedWriter br = Files.newBufferedWriter(FILE_RECORDS_PATH, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Record record : recordList) {
                br.write(record.toString() + "\n");
            }
        } catch (IOException e) {
            logger.warn(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Record> getRecords() {
        List<Record> recordList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(FILE_RECORDS_PATH)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.split(",");
                recordList.add(new Record(args[0], args[1], Integer.parseInt(args[2])));
            }
            return recordList;
        } catch (IOException e) {
            logger.warn(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }
}
