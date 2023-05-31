package ru.cft.figureapp.figuredaotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.cft.figureapp.dao.FigureDao;
import ru.cft.figureapp.dao.FigureDaoImpl;
import ru.cft.figureapp.exception.FigureNotFoundException;
import ru.cft.figureapp.exception.InvalidNumberOfArgsException;
import ru.cft.figureapp.model.Circle;
import ru.cft.figureapp.model.Figure;
import ru.cft.figureapp.model.Triangle;
import ru.cft.figureapp.model.Rectangle;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FigureDaoTest {
    private final FigureDao figureDao = new FigureDaoImpl();

    @ParameterizedTest
    @MethodSource("provideInvalidFigureNames")
    public void shouldThrowFigureNotFoundException(String figureName) {
        Assertions.assertThrows(
                FigureNotFoundException.class,
                () -> figureDao.getFigure(figureName, Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidFigureNamesWithValidParams")
    public void shouldNotThrowFigureNotFoundException(String figureName, List<Double> figureParams) {
        Assertions.assertDoesNotThrow(
                () -> figureDao.getFigure(figureName, figureParams)
        );
    }

    @ParameterizedTest
    @CsvSource({"CIRCLE,3.0"})
    public void shouldReturnFigureCircleWhileHavingValidFigureName(String figureName, double radius) {
        Figure result = figureDao.getFigure(figureName, List.of(radius));
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof Circle);

    }

    @ParameterizedTest
    @CsvSource({"TRIANGLE,3.0,3.0,3.0"})
    public void shouldReturnFigureTriangleWhileHavingValidFigureName(String figureName,
                                                                     double firstSideLength,
                                                                     double secondSideLength,
                                                                     double thirdSideLength) {
        Figure result = figureDao.getFigure(figureName, List.of(firstSideLength,
                secondSideLength,
                thirdSideLength)
        );

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof Triangle);

    }

    @ParameterizedTest
    @CsvSource({"RECTANGLE,3.0,3.0"})
    public void shouldReturnFigureRectangleWhileHavingValidFigureName(String figureName,
                                                                      double firstSideLength,
                                                                      double secondSideLength) {
        Figure result = figureDao.getFigure(figureName, List.of(firstSideLength, secondSideLength));
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result instanceof Rectangle);

    }

    @ParameterizedTest
    @CsvSource({"CIRCLE,3.0,3.0"})
    public void shouldThrowInvalidNumberOfArgsExceptionBecauseCircleRequiresOneParametersButGivenTwo(String figureName,
                                                                               double radius,
                                                                               double invalidValue) {
        Assertions.assertThrows(InvalidNumberOfArgsException.class,
                () -> figureDao.getFigure(figureName, List.of(radius, invalidValue))
        );
    }

    private static Stream<Arguments> provideValidFigureNamesWithValidParams() {
        return Stream.of(
                Arguments.of("RECTANGLE", List.of(10.0, 20.0)),
                Arguments.of("TRIANGLE", List.of(20.0, 10.0, 20.0)),
                Arguments.of("CIRCLE", List.of(5.0))
        );
    }

    private static Stream<Arguments> provideInvalidFigureNames() {
        return Stream.of(
                Arguments.of("INVALID FIGURE", "FIGURE", "23", " ", ""),
                Arguments.of("RECTANGLE1", "TRIANGLE2", "CIRCLE3")
        );
    }
}
