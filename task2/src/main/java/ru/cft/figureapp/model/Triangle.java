package ru.cft.figureapp.model;

import ru.cft.figureapp.exception.InvalidNumberOfArgsException;
import ru.cft.figureapp.exception.TriangleInequalityViolationException;

import java.util.List;

public class Triangle extends Figure {
    private static final String FIGURE_NAME_TRIANGLE = "Треугольник";
    private static final String MSG_TRIANGLE_INEQUALITY_VIOLATION = "Нарушение теоремы неравенства сторон треугольника," +
            " заданные параметры: %.2f, %.2f, %.2f";
    private static final int REQUIRED_NUMBER_OF_ARGS = 3;
    private static final int SUM_OF_ALL_ANGLES = 180;
    private final double firstSideLength;
    private final double secondSideLength;
    private final double thirdSideLength;
    private final double firstAngleDegree;
    private final double secondAngleDegree;
    private final double thirdAngleDegree;
    private final double perimeter;
    private final double area;

    public Triangle(List<Double> args) throws InvalidNumberOfArgsException, TriangleInequalityViolationException {
        super(FIGURE_NAME_TRIANGLE);

        checkArgs(args, REQUIRED_NUMBER_OF_ARGS);

        this.firstSideLength = round(args.get(0));
        this.secondSideLength = round(args.get(1));
        this.thirdSideLength = round(args.get(2));
        if (!violatesTriangleInequality()) {
            throw new TriangleInequalityViolationException(
                    String.format(MSG_TRIANGLE_INEQUALITY_VIOLATION,
                    this.firstSideLength,
                    this.secondSideLength,
                    this.thirdSideLength)
            );
        }

        this.firstAngleDegree = calculateAngle(
                this.firstSideLength,
                this.secondSideLength,
                this.thirdSideLength
        );
        this.secondAngleDegree = calculateAngle(
                this.secondSideLength,
                this.firstSideLength,
                this.thirdSideLength
        );
        this.thirdAngleDegree = calculateThirdAngle();

        this.perimeter = calculatePerimeter();
        this.area = calculateArea();
    }

    @Override
    public String toString() {
        return "Тип фигуры: " + super.name + "\n" +
                "Площадь: " + this.area + getMeasurementUnitMillimeter() + "\n" +
                "Периметр: " + this.perimeter + getMeasurementUnitMillimeter() + "\n" +
                "Длина первой стороны: " + this.firstSideLength + getMeasurementUnitMillimeter() + "\n" +
                "Длина второй стороны: " + this.secondSideLength + getMeasurementUnitMillimeter() + "\n" +
                "Длина третьей стороны: " + this.thirdSideLength + getMeasurementUnitMillimeter() + "\n" +
                "Первый угол: " + this.firstAngleDegree + getMeasurementUnitDegree() + "\n" +
                "Второй угол: " + this.secondAngleDegree + getMeasurementUnitDegree() + "\n" +
                "Третий угол: " + this.thirdAngleDegree + getMeasurementUnitDegree();
    }

    private double calculateAngle(double firstSideLength, double secondSideLength, double thirdSideLength) {
        var firstSideToSecondPower = Math.pow(firstSideLength, 2);
        var secondSideToSecondPower = Math.pow(secondSideLength, 2);
        var thirdSideToSecondPower = Math.pow(thirdSideLength, 2);
        var cos = (firstSideToSecondPower + thirdSideToSecondPower - secondSideToSecondPower) / (2 * firstSideLength * thirdSideLength);
        var radians = Math.acos(cos);
        var result = Math.toDegrees(radians);
        return round(result);
    }

    private double calculateThirdAngle() {
        return round(SUM_OF_ALL_ANGLES - (this.firstAngleDegree + this.secondAngleDegree));
    }

    @Override
    protected double calculateArea() {
        var angleToRadians = Math.toRadians(this.firstAngleDegree);
        var radiansToSin = Math.sin(angleToRadians);
        var result = 0.5 * this.firstSideLength * this.secondSideLength * radiansToSin;
        return round(result);
    }

    @Override
    protected double calculatePerimeter() {
        return round(this.firstSideLength + this.secondSideLength + this.thirdSideLength);
    }

    @Override
    public double getArea() {
        return this.area;
    }

    @Override
    public double getPerimeter() {
        return this.perimeter;
    }

    private boolean violatesTriangleInequality() {
        return this.firstSideLength < (this.secondSideLength + this.thirdSideLength) &&
                this.secondSideLength < (this.firstSideLength + this.thirdSideLength) &&
                this.thirdSideLength < (this.firstSideLength + this.secondSideLength);
    }

}
