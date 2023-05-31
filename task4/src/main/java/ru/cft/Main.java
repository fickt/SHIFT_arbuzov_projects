package ru.cft;

import ru.cft.calculatestrategy.CalculateMathematicalSeriesStrategy;
import ru.cft.calculator.Calculator;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        int fromValue = 1;
        System.out.println("Enter number: ");
        var scanner = new Scanner(System.in);
        int toValue = scanner.nextInt();

        Calculator calculator = new Calculator(
                fromValue,
                toValue,
                new CalculateMathematicalSeriesStrategy()
        );

        calculator.calculate();
        System.out.println("TOTAL: " + calculator.getSumOfAllCalculationResults());
    }
}