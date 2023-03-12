package ru.academit.podlatov.phonebookspring.service.xlsxtablewriter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.academit.podlatov.phonebookspring.model.ConvertableToXlsxRow;

import java.io.OutputStream;
import java.util.List;

@Component
public class XlsxTableWriter {
    public void writeToStream(List<? extends ConvertableToXlsxRow> convertableEntities,
                              OutputStream destinationStream,
                              boolean isRowNumerationNeeded
    ) {
        if (convertableEntities.size() == 0) {
            throw new IllegalArgumentException("Список элементов для конвертирования пуст.");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet();

            Integer rowNumber = 0;

            createHeaders(
                    sheet.createRow(rowNumber++),
                    convertableEntities.get(0).getFieldsNames(),
                    isRowNumerationNeeded
            );
            fillTable(
                    convertableEntities,
                    sheet,
                    rowNumber,
                    isRowNumerationNeeded
            );
            autosizeColumns(sheet);
            workbook.write(destinationStream);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage() == null ? "Ошибка при создании xlsx файла." : e.getMessage();
            throw new RuntimeException(message);
        }
    }


    private void createHeaders(Row headers, List<String> names, boolean isRowNumerationNeeded) {
        int index = 0;
        if (isRowNumerationNeeded) {
            headers.createCell(index++).setCellValue("№");
        }

        for (String name : names) {
            headers.createCell(index++).setCellValue(name);
        }
    }


    private void fillTable(List<? extends ConvertableToXlsxRow> convertableEntities,
                           XSSFSheet sheet, Integer rowNumber, boolean isRowNumerationNeeded) {
        for (ConvertableToXlsxRow convertable : convertableEntities) {
            Row row = sheet.createRow(rowNumber);

            int colNumber = 0;
            if (isRowNumerationNeeded) {
                row.createCell(colNumber++).setCellValue(rowNumber);
            }

            for (String data : convertable.getFieldsValues()) {
                row.createCell(colNumber++).setCellValue(data);
            }

            rowNumber++;
        }
    }

    private static void autosizeColumns(XSSFSheet sheet) {
        int columnsCount = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < columnsCount; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}