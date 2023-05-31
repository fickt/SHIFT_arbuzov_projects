package ru.cft;

import ru.cft.tableprinter.formatter.MultiplicationTableFormatterImpl;
import ru.cft.tableprinter.service.MultiplicationTableService;
import ru.cft.tableprinter.service.MultiplicationTableServiceImpl;

/**
 * @author Michael Arbuzov
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        MultiplicationTableService tableService = new MultiplicationTableServiceImpl(
                new MultiplicationTableFormatterImpl()
        );

        tableService.start();
    }
}