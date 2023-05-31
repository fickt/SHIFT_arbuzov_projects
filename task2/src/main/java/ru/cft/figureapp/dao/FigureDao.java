package ru.cft.figureapp.dao;

import ru.cft.figureapp.model.Figure;

import java.util.List;

public interface FigureDao {
    Figure getFigure(String figureName, List<Double> figureParams);
}
