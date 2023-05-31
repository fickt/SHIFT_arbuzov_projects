package ru.cft.tableprinter.service;

import ru.cft.tableprinter.formatter.MultiplicationTableFormatter;

import java.util.Scanner;

public class MultiplicationTableServiceImpl implements MultiplicationTableService {

    private final int MIN_VALUE = 1;
    private final int MAX_VALUE = 32;
    private final String MSG_INVALID_ARGUMENT = "\"%s\" - невалидный аргумент, он должен быть " +
            "целочисленным и попадать под диапазон значений от %s до %s";
    private final String MSG_ENTER_NUMBER = "Введите число: ";

    private final MultiplicationTableFormatter formatter;

    public MultiplicationTableServiceImpl(MultiplicationTableFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void start() {
        System.out.println(MSG_ENTER_NUMBER);
        var input = getUserInput();

        if (!isArgsValid(input)) {
            System.out.printf(MSG_INVALID_ARGUMENT, input, MIN_VALUE, MAX_VALUE);
            return;
        }

        printTable(Integer.parseInt(input));
    }

    private void printTable(int input) {
        var firstColumnWidth = formatter.calculateFirstColumnWidth(input);
        var otherColumnsWidth = formatter.calculateOtherColumnsWidth(input);
        var underScore = formatter.getUnderscore(input, firstColumnWidth, otherColumnsWidth);

        printFirstLineOfMultipliers(input, firstColumnWidth, otherColumnsWidth);
        printRestOfLinesWithMultiplierAndProducts(input, firstColumnWidth, otherColumnsWidth, underScore);
    }

    private void printFirstLineOfMultipliers(int input,
                                             int firstColumnWidth,
                                             int otherColumnsWidth) {

        formatter.printSpacesBeforeZero(firstColumnWidth);
        for (var i = 1; i <= input; i++) {
            formatter.printSpacesBeforeNumber(i, otherColumnsWidth);
            if (i < input) {
                formatter.printNumberWithDelimiter(i);
            } else {
                System.out.print(i);
            }
        }
    }

    private void printRestOfLinesWithMultiplierAndProducts(int input,
                                                           int firstColumnWidth,
                                                           int otherColumnsWidth,
                                                           StringBuilder underScore) {
        int multiplier = 1;
        for (var j = 0; j < input; j++) {
            System.out.println(underScore);

            for (var i = 0; i <= input; i++) {
                if (i == 0) {
                    formatter.printSpacesBeforeNumber(multiplier, firstColumnWidth);
                    formatter.printNumberWithDelimiter(multiplier);
                    continue;
                }
                var currentProduct = i * multiplier;
                formatter.printSpacesBeforeNumber(currentProduct, otherColumnsWidth);
                if (i < input) {
                    formatter.printNumberWithDelimiter(currentProduct);
                } else {
                    System.out.print(currentProduct);
                }
            }
            multiplier++;
        }
        System.out.println(underScore);
    }

    private String getUserInput() {
        var scanner = new Scanner(System.in);
        return scanner.next();
    }

    private boolean isArgsValid(String input) {
        int inputAsInt;
        try {
            inputAsInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return inputAsInt >= MIN_VALUE && inputAsInt <= MAX_VALUE;
    }
}
