package ru.cft.figureapp.filemanager;


import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.List;

public interface FileManager {
    List<String> readInputFile(Path file);
    void writeOutputFile(Path file, String output) throws IOException;
    boolean isInputFileValid(Path file);
    Path setOutputFile(Path filePath) throws NotDirectoryException;
}
