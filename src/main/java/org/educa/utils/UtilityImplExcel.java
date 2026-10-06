package org.educa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.educa.entity.ProductoEntity;

import java.util.List;

public class UtilityImplExcel implements Utility {
    @Override
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

        createHeaderRow(sheet, headerStyle);

        // Looping through the rows of the table
        int rowIdx = 1;
        for (ProductoEntity p : productos) {
            Row row = sheet.createRow(rowIdx);
            boolean isAlt = (rowIdx % 2 != 0);

            generated.Producto prod = p.getProducto();

            // Cell number 0
            Cell c0 = row.createCell(0);
            c0.setCellValue(prod != null && prod.getCodigo() != null ? prod.getCodigo() : "");
            c0.setCellStyle(isAlt ? codeStyleAlt : codeStyleNormal);

            // Cell number 1: series number
            Cell c1 = row.createCell(1);
            c1.setCellValue(prod != null && prod.getNumeroSerie() != null ? prod.getNumeroSerie() : "");
            c1.setCellStyle(isAlt ? textStyleAlt : textStyleNormal);

            // Cell number 2 : price
            Cell c2 = row.createCell(2);
            double precioVal = (prod != null && prod.getPrecio() != null) ? prod.getPrecio().doubleValue() : 0.0;
            c2.setCellValue(precioVal);
            c2.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            // Cell number 3 : discount
            Cell c3 = row.createCell(3);
            double descVal = (prod != null && prod.getDescuento() != null) ? prod.getDescuento().doubleValue() / 100.0 : 0.0;
            c3.setCellValue(descVal);
            c3.setCellStyle(isAlt ? pctStyleAlt : pctStyleNormal);

            // Cell number 4 : final price
            Cell c4 = row.createCell(4);
            double precioFinalVal = p.getPrecioFinal() != null ? p.getPrecioFinal().doubleValue() : 0.0;
            c4.setCellValue(precioFinalVal);
            c4.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            // Cell number 5 : cost
            Cell c5 = row.createCell(5);
            double costVal = p.getCost() != null ? p.getCost().doubleValue() : 0.0;
            c5.setCellValue(costVal);
            c5.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            // Cell number 6 : benefit
            Cell c6 = row.createCell(6);
            double profitVal = p.getProfit() != null ? p.getProfit().doubleValue() : 0.0;
            c6.setCellValue(profitVal);
            c6.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            rowIdx++;
        }

        // Looping for adjusting each column
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }


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
