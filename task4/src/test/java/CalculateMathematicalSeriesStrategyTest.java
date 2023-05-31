
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.cft.calculatestrategy.CalculateMathematicalSeriesStrategy;
import ru.cft.calculatestrategy.CalculateStrategy;

import java.util.stream.Stream;

public class CalculateMathematicalSeriesStrategyTest {
    @ParameterizedTest
    @MethodSource("provideArgumentsForMathematicalSeries")
    public void shouldCalculateSumOfMathematicalSeries(int fromValue, int toValue, long expectedResult) {
        CalculateStrategy strategy = new CalculateMathematicalSeriesStrategy();
        long actualResult = strategy.calculate(fromValue, toValue);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    private static Stream<Arguments> provideArgumentsForMathematicalSeries() {
        return Stream.of(
                Arguments.of(1, 10, 120),
                Arguments.of(0, 10, 121),
                Arguments.of(1, 230, 53360)
        );
    }
}
