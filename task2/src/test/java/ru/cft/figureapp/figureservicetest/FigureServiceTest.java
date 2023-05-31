package ru.cft.figureapp.figureservicetest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.cft.figureapp.dao.FigureDao;
import ru.cft.figureapp.service.FigureService;
import ru.cft.figureapp.filemanager.FileManager;
import ru.cft.figureapp.service.FigureServiceImpl;

import java.nio.file.NotDirectoryException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FigureServiceTest {
    @Mock
    private FileManager fileManager;
    @Mock
    private FigureDao figureDao;
    private FigureService figureService;

    @BeforeEach
    public void setup() {
        figureService = new FigureServiceImpl(figureDao, fileManager);
    }

    @Test
    public void shouldNotInvokeReadinputFileMethodBecauseArgsValidationIsFailedBecauseArgsListIsEmpty() {
        var args = new String[]{};

        figureService.start(args);

        verify(fileManager, never()).readInputFile(any());
    }

    @Test
    public void shouldNotInvokeReadinputFileMethodBecauseArgsValidationIsFailedBecauseInputFileIsInvalid() {
        var args = new String[]{"-c", "INVALID FILE PATH"};
        when(fileManager.isInputFileValid(any())).thenReturn(false);

        figureService.start(args);

        verify(fileManager, never()).readInputFile(any());
    }

    @Test
    public void shouldNotInvokeReadinputFileMethodBecauseArgsValidationIsFailedBecauseOutputFileIsInvalid() throws NotDirectoryException {
        var args = new String[]{"-f", "VALID INPUT FILE PATH", "INVALID OUTPUT FILE PATH"};
        when(fileManager.isInputFileValid(any())).thenReturn(true);
        when(fileManager.setOutputFile(any())).thenThrow(NotDirectoryException.class);

        figureService.start(args);

        verify(fileManager, never()).readInputFile(any());
    }

}
