package ru.cft.tableprinter.formatter;

import java.util.ArrayList;

public interface MultiplicationTableFormatter {
    int calculateFirstColumnWidth(int input);

    int calculateOtherColumnsWidth(int input);

    StringBuilder getUnderscore(int input, int firstColumnWidth, int otherColumnWidth);
    void printSpacesBeforeNumber(int number, int columnWidth);

    void printSpacesBeforeZero(int columnWidth);
    void printNumberWithDelimiter(int number);
}
