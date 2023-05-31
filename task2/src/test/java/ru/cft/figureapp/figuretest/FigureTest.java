package ru.cft.figureapp.figuretest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.cft.figureapp.exception.TriangleInequalityViolationException;
import ru.cft.figureapp.model.Circle;
import ru.cft.figureapp.model.Figure;
import ru.cft.figureapp.model.Rectangle;
import ru.cft.figureapp.model.Triangle;

import java.util.List;
import java.util.stream.Stream;


public class FigureTest {
    @ParameterizedTest
    @MethodSource("provideTestParamsForRectangle")
    public void shouldCalculatePerimeterAndAreaOfRectangle(double firstSideLength,
                                                           double secondSideLength,
                                                           double expectedPerimeter,
                                                           double expectedArea) {
        Figure figure = new Rectangle(List.of(firstSideLength, secondSideLength));

        Assertions.assertEquals(expectedPerimeter, figure.getPerimeter());
        Assertions.assertEquals(expectedArea, figure.getArea());
    }

    @ParameterizedTest
    @CsvSource({"10.0,15.0"})
    public void shouldGiveNotNullOutputFormatOfRectangle(double firstSideLength,
                                                         double secondSideLength) {
        Figure figure = new Rectangle(List.of(firstSideLength, secondSideLength));

        Assertions.assertNotNull(figure.toString());
    }

    @ParameterizedTest
    @MethodSource("provideTestParamsForCircle")
    public void shouldCalculatePerimeterAndAreaOfCircle(double radius,
                                                        double expectedPerimeter,
                                                        double expectedArea) {
        Figure figure = new Circle(List.of(radius));

        Assertions.assertEquals(expectedPerimeter, figure.getPerimeter());
        Assertions.assertEquals(expectedArea, figure.getArea());
    }

    @ParameterizedTest
    @CsvSource({"20.0"})
    public void shouldGiveNotNullOutputFormatOfCircle(double radius) {
        Figure figure = new Circle(List.of(radius));

        Assertions.assertNotNull(figure.toString());
    }

    @ParameterizedTest
    @MethodSource("provideTestParamsForTriangle")
    public void shouldCalculatePerimeterAndAreaOfTriangle(double firstSideLength,
                                                          double secondSideLength,
                                                          double thirdSideLength,
                                                          double expectedPerimeter,
                                                          double expectedArea) {
        Figure figure = new Triangle(List.of(firstSideLength, secondSideLength, thirdSideLength));

        Assertions.assertEquals(expectedPerimeter, figure.getPerimeter());
        Assertions.assertEquals(expectedArea, figure.getArea());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTestParamsForTriangle")
    public void shouldThrowTriangleInequalityViolationExceptionBecauseSumOfTwoSidesIsLessThanTheThirdOne(
            double firstSideLength,
            double secondSideLength,
            double thirdSideLength) {

        Assertions.assertThrows(
                TriangleInequalityViolationException.class,
                () -> new Triangle(List.of(firstSideLength, secondSideLength, thirdSideLength))
        );
    }

    @ParameterizedTest
    @CsvSource({"10.0,10.0,10.0"})
    public void shouldGiveNotNullOutputFormatOfTriangle(double firstSideLength,
                                                        double secondSideLength,
                                                        double thirdSideLength) {
        Figure figure = new Triangle(List.of(firstSideLength, secondSideLength, thirdSideLength));

        Assertions.assertNotNull(figure.toString());
    }

    private static Stream<Arguments> provideTestParamsForCircle() {
        return Stream.of(
                Arguments.of(20.0, 125.67, 1256.64),
                Arguments.of(34.2, 214.95, 3676.69),
                Arguments.of(1.0, 6.29, 3.15)
        );
    }

    private static Stream<Arguments> provideTestParamsForRectangle() {
        return Stream.of(
                Arguments.of(10.0, 15.0, 50.0, 150.0),
                Arguments.of(1.0, 1.0, 4.0, 1.0)
        );
    }

    private static Stream<Arguments> provideTestParamsForTriangle() {
        return Stream.of(
                Arguments.of(10.0, 15.0, 10.0, 35.0, 74.42),
                Arguments.of(1.0, 1.0, 1.0, 3.0, 0.44)
        );
    }

    private static Stream<Arguments> provideInvalidTestParamsForTriangle() {
        return Stream.of(
                Arguments.of(15.0, 7.0, 5.0),
                Arguments.of(20.0, 12.0, 6.0)
        );
    }
}
