package ru.cft.figureapp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.figureapp.dao.FigureDao;
import ru.cft.figureapp.exception.FigureNotFoundException;
import ru.cft.figureapp.exception.InvalidNumberOfArgsException;
import ru.cft.figureapp.exception.NegativeArgException;
import ru.cft.figureapp.exception.TriangleInequalityViolationException;
import ru.cft.figureapp.filemanager.FileManager;
import ru.cft.figureapp.model.Figure;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class FigureServiceImpl implements FigureService {
    private static final String MSG_NO_ARGS_PROVIDED = "Аргументы запуска пусты, укажите тип вывода результата (-с или -f)," +
            "\n путь до файла с данными и путь до файла для вывода результата, в случае, если  выбран тип '-f'";
    private static final String MSG_INPUT_FILE_NOT_FOUND = "Входной файл не найден! Путь: %s";
    private static final String MSG_OPERATION_SUCCESS = "Операция завершена успешно!";
    private static final String MSG_INVALID_ARGUMENT = "Невалидный аргумент! \"%s\" - не является числом";
    private static final String MSG_NEGATIVE_PARAMETER = "Невалидный аргумент! \"%.2f\" - является отрицательным параметром!";
    private static final String MSG_FILE_OUTPUT_TYPE_CHOSEN_BUT_NO_FILE_PROVIDED = "Выбран параметр \"-f\"," +
            " но не указан путь до выходного файла";
    private static final String MSG_INVALID_ARGUMENTS_NUMBER = "Задано невалидное количество аргументов," +
            " требуется ввести только тип вывода результата (-с/-f)," +
            " \n входной файл и директорию для вывода результата, если был указан тип вывода (-f)";
    private static final String LOG_VALIDATION_FAILED = "Validation failed! Reason: {}";
    private static final String LOG_APPLICATION_START = "Application has been started on OS: {}, java version: {}";
    private static final String OUTPUT_TYPE_CONSOLE = "-c";
    private static final String OUTPUT_TYPE_FILE = "-f";

    private static final int CONSOLE_ARGUMENT_LIMIT = 3;

    private static final Logger log = LogManager.getLogger(FigureServiceImpl.class);

    private final FigureDao figureDao;
    private final FileManager fileManager;

    private String currentOutputType;
    private Path inputFile;
    private Path outputFile;

    public FigureServiceImpl(FigureDao figureDao, FileManager fileManager) {
        this.figureDao = figureDao;
        this.fileManager = fileManager;
    }

    @Override
    public void start(String[] args) {
        printStartLog();
        var argsList = Arrays.stream(args).collect(toList());
        if (!isConsoleArgsValid(argsList)) {
            return;
        }

        var figureParams = fileManager.readInputFile(inputFile);
        var figureName = figureParams.get(0);
        Figure figure;
        try {
            figure = figureDao.getFigure(
                    figureName,
                    figureParamsFromStringToDouble(figureParams)
            );
        } catch (FigureNotFoundException |
                 InvalidNumberOfArgsException |
                 NumberFormatException |
                 NegativeArgException |
                 TriangleInequalityViolationException e) {
            log.warn(e.getMessage());
            return;
        }

        writeResult(figure.toString());
    }

    private List<Double> figureParamsFromStringToDouble(List<String> figureParams) throws NumberFormatException, NegativeArgException {
        return figureParams.stream()
                .skip(1)
                .map(val -> {
                    try {
                        return Double.parseDouble(val);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(String.format(MSG_INVALID_ARGUMENT, val));
                    }
                })
                .filter(val -> {
                    if (val < 0) {
                        throw new NegativeArgException(String.format(MSG_NEGATIVE_PARAMETER, val));
                    }
                    return true;
                })
                .toList();
    }

    private void writeResult(String result) {
        if (currentOutputType.equals(OUTPUT_TYPE_FILE)) {
            try {
                fileManager.writeOutputFile(outputFile, result);
            } catch (IOException e) {
                log.warn(e.getStackTrace());
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(result);
        }
        log.info(MSG_OPERATION_SUCCESS);
    }

    private boolean isConsoleArgsValid(List<String> args) {
        if (args.isEmpty()) {
            log.info(LOG_VALIDATION_FAILED, MSG_NO_ARGS_PROVIDED);
            return false;
        }

        if (args.size() > CONSOLE_ARGUMENT_LIMIT) {
            log.info(MSG_INVALID_ARGUMENTS_NUMBER);
            return false;
        }
        //Если не указан тип вывода результата, ставим "-f"
        if (!Objects.equals(args.get(0), OUTPUT_TYPE_CONSOLE) && !Objects.equals(args.get(0), OUTPUT_TYPE_FILE)) {
            currentOutputType = OUTPUT_TYPE_FILE;
        } else {
            currentOutputType = args.get(0);
            args.remove(0);
        }

        return isFilesValid(args);
    }

    private boolean isFilesValid(List<String> args) {
        if (!fileManager.isInputFileValid(Path.of(args.get(0)))) {
            log.info(LOG_VALIDATION_FAILED, String.format(MSG_INPUT_FILE_NOT_FOUND, inputFile));
            return false;
        }

        inputFile = Path.of(args.get(0));
        args.remove(0);
        //Если тип вывода - файл, проверяем, существует ли выходной файл
        if (Objects.equals(currentOutputType, OUTPUT_TYPE_FILE)) {
            if (args.isEmpty()) {
                log.info(MSG_FILE_OUTPUT_TYPE_CHOSEN_BUT_NO_FILE_PROVIDED);
                return false;
            }

            try {
                outputFile = fileManager.setOutputFile(Path.of(args.get(0)));
            } catch (NotDirectoryException e) {
                log.info(e.getMessage());
                return false;
            }
        }
        return true;
    }

    private void printStartLog() {
        log.info(LOG_APPLICATION_START,
                System.getProperty("os.name"),
                System.getProperty("java.version")
        );
    }
}
