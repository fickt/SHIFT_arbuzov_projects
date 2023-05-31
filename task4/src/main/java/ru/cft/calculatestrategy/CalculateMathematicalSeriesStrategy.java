package ru.cft.calculatestrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculateMathematicalSeriesStrategy implements CalculateStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CalculateMathematicalSeriesStrategy.class);

    @Override
    public Long calculate(long fromValue, long toValue) {
        long sum = 0;
        logger.info("fromValue = {}, toValue = {}", fromValue, toValue);

        while (toValue > 0 || fromValue == toValue) {
            sum += (2 * toValue + 1);
            toValue--;
        }
        logger.info("sum = {}", sum);
        return sum;
    }
}