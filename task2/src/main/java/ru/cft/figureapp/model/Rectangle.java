package ru.cft.figureapp.model;

import ru.cft.figureapp.exception.InvalidNumberOfArgsException;

import java.util.List;

public class Rectangle extends Figure {
    private static final String FIGURE_NAME_RECTANGLE = "Прямоугольник";
    private static final int REQUIRED_NUMBER_OF_ARGS = 2;
    private final double sideOneLength;
    private final double sideTwoLength;
    private final double diagonalLength;
    private final double perimeter;
    private final double area;

    public Rectangle(List<Double> args) throws InvalidNumberOfArgsException {
        super(FIGURE_NAME_RECTANGLE);

        checkArgs(args, REQUIRED_NUMBER_OF_ARGS);

        this.sideOneLength = round(args.get(0));
        this.sideTwoLength = round(args.get(1));
        this.diagonalLength = calculateDiagonalLength();
        this.perimeter = calculatePerimeter();
        this.area = calculateArea();
    }

    @Override
    public String toString() {
        return "Тип фигуры: " + super.name + "\n" +
                "Площадь: " + this.area + getMeasurementUnitMillimeter() + "\n" +
                "Периметр: " + this.perimeter + getMeasurementUnitMillimeter() + "\n" +
                "Длина диагонали: " + this.diagonalLength + getMeasurementUnitMillimeter() + "\n" +
                "Размер длинной стороны" + getLongSideLength() + getMeasurementUnitMillimeter() + "\n" +
                "Размер короткой стороны" + getShortSideLength() + getMeasurementUnitMillimeter();
    }

    private double calculateDiagonalLength() {
        var sideOneLengthToSecondPower = Math.pow(this.sideOneLength, 2);
        var sideTwoLengthToSecondPower = Math.pow(this.sideTwoLength, 2);
        var sum = sideOneLengthToSecondPower + sideTwoLengthToSecondPower;
        return round(Math.sqrt(sum));
    }

    @Override
    protected double calculatePerimeter() {
        return round(this.sideOneLength * 2 + this.sideTwoLength * 2);
    }

    @Override
    protected double calculateArea() {
        return round(this.sideOneLength * this.sideTwoLength);
    }

    private double getLongSideLength() {
        return Math.max(this.sideOneLength, this.sideTwoLength);
    }

    private double getShortSideLength() {
        return Math.min(this.sideOneLength, this.sideTwoLength);
    }

    @Override
    public double getArea() {
        return this.area;
    }

    @Override
    public double getPerimeter() {
        return this.perimeter;
    }
}
