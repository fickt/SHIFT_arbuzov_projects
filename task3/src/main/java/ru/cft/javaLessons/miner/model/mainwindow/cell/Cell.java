package ru.cft.javaLessons.miner.model.mainwindow.cell;

public class Cell {
    private int xCoord;
    private int yCoord;
    private boolean isMined;
    private boolean isFirstOpened;
    private boolean isFlagged;
    private int minesAroundCounter;
    private boolean isOpen;

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public boolean isMined() {
        return this.isMined;
    }

    public void setFirstOpened(boolean isFirstOpened) {
        this.isFirstOpened = isFirstOpened;
    }

    public boolean isFirstOpened() {
        return this.isFirstOpened;
    }

    public void setMined(boolean mined) {
        this.isMined = mined;
    }

    public void open() {
        this.isOpen = true;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public void flag() {
        this.isFlagged = !isFlagged;
    }

    public void setMinesAroundCounter(int minesAround) {
        this.minesAroundCounter = minesAround;
    }

    public int getMinesAroundCounter() {
        return this.minesAroundCounter;
    }
}
