package org.educa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.educa.entity.ProductoEntity;

import java.util.List;

public class UtilityImplExcel implements Utility{
public Workbook buildInventoryWorkbook(List<ProductoEntity> productos) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Inventario");

    CellStyle headerStyle = createHeaderStyle(workbook);
    CellStyle codeStyleNormal = createCodeStyle(workbook, false);
    CellStyle codeStyleAlt = createCodeStyle(workbook, true);
    CellStyle textStyleNormal = createTextStyle(workbook, false);
    CellStyle textStyleAlt = createTextStyle(workbook, true);
    CellStyle currStyleNormal = createCurrencyStyle(workbook, false);
    CellStyle currStyleAlt = createCurrencyStyle(workbook, true);
    CellStyle pctStyleNormal = createPercentStyle(workbook, false);
    CellStyle pctStyleAlt = createPercentStyle(workbook, true);




    return workbook;
}
    private static void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        String[] headers = {
                "Código", "Número de\nSerie", "Precio", "Descuento",
                "Precio\nFinal", "Coste", "Beneficio"
        };

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(32);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
}
