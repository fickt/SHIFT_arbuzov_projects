package ru.cft.figureapp.model;


import ru.cft.figureapp.exception.InvalidNumberOfArgsException;

import java.util.List;

public abstract class Figure {
    private static final String MSG_INVALID_NUMBER_OF_ARGS = "Количество требуемых аргументов" +
            " для фигуры \"%s\" %s, получено аргументов: %s";
    private static final int DECIMAL_PLACES = 2;
    private static final String MEASUREMENT_UNIT_MILLIMETERS = "мм";
    private static final String MEASUREMENT_UNIT_DEGREE = "°";
    private static final String SYMBOL_SPACE = " ";
    protected final String name;

    public Figure(String name) {
        this.name = name;
    }

    protected abstract double calculateArea();

    protected abstract double calculatePerimeter();

    public abstract double getArea();

    public abstract double getPerimeter();

    protected void checkArgs(List<Double> args, int requiredNumberOfArgs) throws InvalidNumberOfArgsException {
        if (args.size() != requiredNumberOfArgs) {
            throw new InvalidNumberOfArgsException(String.format(
                    MSG_INVALID_NUMBER_OF_ARGS,
                    name,
                    requiredNumberOfArgs,
                    args.size())
            );
        }
    }

    protected double round(double value) {
        double scale = Math.pow(10, DECIMAL_PLACES);
        return Math.ceil(value * scale) / scale;
    }

    protected String getMeasurementUnitMillimeter() {
        return SYMBOL_SPACE + MEASUREMENT_UNIT_MILLIMETERS;
    }

    protected String getMeasurementUnitDegree() {
        return MEASUREMENT_UNIT_DEGREE;
    }
}
