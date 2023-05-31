package ru.cft.javaLessons.miner.recordsstorage;

public class Record {
    private final String name;
    private final String gameType;
    private final int time;

    public Record(String name, String gameType, int time) {
        this.name = name;
        this.gameType = gameType;
        this.time = time;
    }

    public String getName() {
        return this.name;
    }

    public String getGameType() {
        return this.gameType;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return name + "," + gameType + "," + time;
    }
}
