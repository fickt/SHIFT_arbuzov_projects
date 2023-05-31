package ru.cft.figureapp;


import ru.cft.figureapp.dao.FigureDaoImpl;
import ru.cft.figureapp.filemanager.FileManagerImpl;
import ru.cft.figureapp.service.FigureService;
import ru.cft.figureapp.service.FigureServiceImpl;

public class Main {
    public static void main(String[] args) {
        FigureService app = new FigureServiceImpl(
                new FigureDaoImpl(),
                new FileManagerImpl()
        );
        var arg = new String[]{"-f", "C:\\Users\\admin\\Desktop\\input.txt", "C:\\Users\\admin\\Desktop\\output.txt",};
        app.start(arg);
    }
}