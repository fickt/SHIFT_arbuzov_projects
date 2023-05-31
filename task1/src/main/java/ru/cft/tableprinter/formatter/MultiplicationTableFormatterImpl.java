package ru.cft.tableprinter.formatter;

public class MultiplicationTableFormatterImpl implements MultiplicationTableFormatter {

    private final String SYMBOL_HORIZONTAL_DELIMITER = "—";
    private final String SYMBOL_VERTICAL_DELIMITER = "|";
    private final String SYMBOL_JOINT = "+";
    private final String SYMBOL_SPACE = " ";
    private final String SYMBOL_NEXT_LINE = "\n";

    @Override
    public StringBuilder getUnderscore(int input,
                                       int firstColumnLength,
                                       int otherColumnLength) {
        var sb = new StringBuilder();

        sb.append(SYMBOL_NEXT_LINE);
        for (var i = 0; i < firstColumnLength; i++) { //подчёркивания в первой колонке
            sb.append(SYMBOL_HORIZONTAL_DELIMITER);
        }
        sb.append(SYMBOL_JOINT);

        for (int i = 0; i < input; i++) {
            for (var j = 0; j < otherColumnLength; j++) { //подчёркивания под числами после первой колонки
                sb.append(SYMBOL_HORIZONTAL_DELIMITER);
            }
            sb.append(SYMBOL_JOINT);
        }

        sb.deleteCharAt(sb.length() - 1); //удаляем в конце строки ненужный "+"
        return sb;
    }

    @Override
    public void printSpacesBeforeNumber(int number, int columnLength) {
            for (var i = 0; i < columnLength - String.valueOf(number).length(); i++) {
                System.out.print(SYMBOL_SPACE);
            }
    }

    @Override
    public void printSpacesBeforeZero(int columnLength) {
        for (var i = 0; i < columnLength; i++) {
            System.out.print(SYMBOL_SPACE);
        }
        System.out.print(SYMBOL_VERTICAL_DELIMITER);
    }

    @Override
    public void printNumberWithDelimiter(int number) {
        System.out.print(number + SYMBOL_VERTICAL_DELIMITER);
    }

    @Override
    public int calculateFirstColumnWidth(int input) {
        return String.valueOf(input).length();
    }

    @Override
    public int calculateOtherColumnsWidth(int input) {
        var maxNumber = input * input;
        return String.valueOf(maxNumber).length();
    }
}
