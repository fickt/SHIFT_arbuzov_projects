package ru.cft.figureapp.model;

import ru.cft.figureapp.exception.InvalidNumberOfArgsException;

import java.util.List;

public class Circle extends Figure {
    private static final String FIGURE_NAME_CIRCLE = "Круг";
    private static final int REQUIRED_NUMBER_OF_ARGS = 1;
    private final double radius;
    private final double diameter;
    private final double perimeter;
    private final double area;

    public Circle(List<Double> args) throws InvalidNumberOfArgsException {
        super(FIGURE_NAME_CIRCLE);
        checkArgs(args, REQUIRED_NUMBER_OF_ARGS);

        this.radius = round(args.get(0));
        this.diameter = calculateDiameter();

        this.perimeter = calculatePerimeter();
        this.area = calculateArea();
    }

    @Override
    public String toString() {
        return "Тип фигуры: " + super.name + "\n" +
                "Площадь: " + this.area + getMeasurementUnitMillimeter() + "\n" +
                "Периметр: " + this.perimeter + getMeasurementUnitMillimeter() + "\n" +
                "Радиус: " + this.radius + getMeasurementUnitMillimeter() + "\n" +
                "Диаметр: " + this.diameter + getMeasurementUnitMillimeter();
    }

    private double calculateDiameter() {
        return round(this.radius * 2);
    }

    @Override
    protected double calculatePerimeter() {
        return round(this.diameter * Math.PI);
    }

    @Override
    protected double calculateArea() {
        return round(Math.pow(this.radius, 2) * Math.PI);
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
