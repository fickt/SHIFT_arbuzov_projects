package ru.cft.task;

import ru.cft.calculatestrategy.CalculateStrategy;

import java.util.concurrent.Callable;

public class Task implements Callable<Long> {
    private final int fromValue;
    private final int toValue;
    private final CalculateStrategy calculateStrategy;

    private long result;

    public Task(CalculateStrategy calculateStrategy, int fromValue, int toValue) {
        this.calculateStrategy = calculateStrategy;
        this.fromValue = fromValue;
        this.toValue = toValue;
    }

    @Override
    public Long call() {
        result = calculateStrategy.calculate(fromValue, toValue);
        return result;
    }

    public long getResult() {
        return result;
    }
}