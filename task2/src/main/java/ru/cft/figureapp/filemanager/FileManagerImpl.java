package ru.cft.figureapp.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.cft.figureapp.service.FigureServiceImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManagerImpl implements FileManager {
    private static final String DEFAULT_OUTPUT_FILE_NAME = "output.txt";
    private static final String MSG_NOT_DIRECTORY = "Выходная директория/файл не является директорией или файлом. Указанный путь: %s";
    private static final String EXTENSION_TXT = ".txt";
    private static final Logger log = LogManager.getLogger(FigureServiceImpl.class);

    @Override
    public List<String> readInputFile(Path file) {
        List<String> figureArgs = new ArrayList<>();
        try (var br = Files.newBufferedReader(file)) {
            String line = br.readLine();
            figureArgs.add(line);
            line = br.readLine();
            while (line != null) {
                for (var arg : line.split(" ")) {
                    figureArgs.add(arg);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            log.warn(e.getStackTrace());
            throw new RuntimeException(e);
        }
        return figureArgs;
    }

    @Override
    public void writeOutputFile(Path file, String output) throws IOException {
        Files.write(file, output.getBytes());
    }

    @Override
    public boolean isInputFileValid(Path filePath) {
        return Files.exists(filePath);
    }

    @Override
    public Path setOutputFile(Path filePath) throws NotDirectoryException {
        if (!Files.isRegularFile(filePath)) {
            return createOutputFile(filePath);
        }
        return filePath;
    }


    private Path createOutputFile(Path filePath) throws NotDirectoryException {
        if (filePath.toString().endsWith(EXTENSION_TXT)) { //если указан файл
            try {
                return Files.createFile(filePath);
            } catch (NoSuchFileException e) {
                log.info(String.format(MSG_NOT_DIRECTORY, filePath));
                throw new RuntimeException(e);
            } catch (IOException e) {
                log.warn(e.getStackTrace());
                throw new RuntimeException(e);
            }
        } else { //если указана только директория
            if (!Files.exists(filePath)) {
                throw new NotDirectoryException(String.format(MSG_NOT_DIRECTORY, filePath));
            }
            filePath = Paths.get(filePath.toString(), DEFAULT_OUTPUT_FILE_NAME);
            try {
                return Files.createFile(filePath);
            } catch (FileAlreadyExistsException e) {
                return filePath;
            } catch (IOException e) {
                log.warn(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }
}
