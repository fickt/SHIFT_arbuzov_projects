package ru.cft.figureapp.dao;

import ru.cft.figureapp.exception.FigureNotFoundException;
import ru.cft.figureapp.exception.InvalidNumberOfArgsException;
import ru.cft.figureapp.model.Circle;
import ru.cft.figureapp.model.Figure;
import ru.cft.figureapp.model.Rectangle;
import ru.cft.figureapp.model.Triangle;

import java.util.List;

public class FigureDaoImpl implements FigureDao {
    private static final String FIGURE_CIRCLE = "CIRCLE";
    private static final String FIGURE_RECTANGLE = "RECTANGLE";
    private static final String FIGURE_TRIANGLE = "TRIANGLE";
    private static final String MSG_FIGURE_NOT_FOUND = "Фигура \"%s\" не найдена!";

    @Override
    public Figure getFigure(String figureName, List<Double> figureParams) throws FigureNotFoundException, InvalidNumberOfArgsException {
        return switch (figureName) {
            case FIGURE_RECTANGLE -> new Rectangle(figureParams);
            case FIGURE_CIRCLE -> new Circle(figureParams);
            case FIGURE_TRIANGLE -> new Triangle(figureParams);

            default -> throw new FigureNotFoundException(String.format(MSG_FIGURE_NOT_FOUND, figureName));
        };
    }
}
