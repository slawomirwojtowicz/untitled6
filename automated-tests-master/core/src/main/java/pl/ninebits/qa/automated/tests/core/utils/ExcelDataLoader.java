package pl.ninebits.qa.automated.tests.core.utils;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public class ExcelDataLoader {

    public static Object[][] loadData(String resourceName, String sheetName, Class... valueTypes) throws IOException {
        InputStream xlsFile = ExcelDataLoader.class.getResourceAsStream(resourceName);
        HSSFWorkbook workbook = new HSSFWorkbook(xlsFile);

        HSSFSheet sheet;
        if (!StringUtils.isEmpty(sheetName)) {
            sheet = workbook.getSheet(sheetName);
        } else {
            sheet = workbook.getSheetAt(0);
        }

        int lastRowNum = sheet.getLastRowNum();
        Object[][] data = new Object[lastRowNum][valueTypes.length];

        int columnsNo = valueTypes.length;
        int rowNo = 0;
        for (Row row : sheet) {
            if (rowNo == 0) {
                rowNo++;
                continue;
            }
            data[rowNo - 1] = new Object[columnsNo];
            for (int colNo = 0; colNo < columnsNo; colNo++) {
                data[rowNo - 1][colNo] = getCellValue(row.getCell(colNo), valueTypes[colNo]);
            }
            rowNo++;
        }
        xlsFile.close();
        return data;
    }

    private static Object getCellValue(Cell cell, Class valueType) {
        if (cell == null) {
            return null;
        }

        if (Integer.class.equals(valueType)) {
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        return cell.getStringCellValue();
    }
}
