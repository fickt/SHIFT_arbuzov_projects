package ru.cft.javaLessons.miner.view;

public enum GameType {
    NOVICE(10, 10, 10),
    MEDIUM(17, 17, 40),
    EXPERT(17, 31, 99);

    private final int height;
    private final int width;
    private final int mines;

    GameType(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getMines() {
        return this.mines;
    }
}
