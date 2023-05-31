package ru.cft.calculator;

import ru.cft.task.Task;
import ru.cft.calculatestrategy.CalculateStrategy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Calculator {
    private final int fromValue;
    private final int toValue;
    private final CalculateStrategy calculateStrategy;

    private long result;
    public Calculator(int fromValue, int toValue, CalculateStrategy calculateStrategy) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.calculateStrategy = calculateStrategy;
    }


    public void calculate() throws InterruptedException, ExecutionException {
        var threads = Runtime.getRuntime().availableProcessors();
        var service = Executors.newFixedThreadPool(threads);

        var taskList = new ArrayList<Task>();
        for (int i = 1; i <= toValue; i++) {
            taskList.add(new Task(calculateStrategy, fromValue, i));
        }

        var futureList = service.invokeAll(taskList);

        long sum = 0;
        for (var number : futureList) {
            sum += number.get();
        }

        service.shutdown();
        result = sum;
    }

    public long getSumOfAllCalculationResults() {
        return result;
    }
}
