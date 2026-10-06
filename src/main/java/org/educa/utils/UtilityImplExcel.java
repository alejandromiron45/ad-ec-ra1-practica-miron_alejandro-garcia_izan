package org.educa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.educa.entity.ProductoEntity;

import java.util.List;

public class UtilityImplExcel implements Utility {

    /**
     * {@inheritDoc}
     * <p>
     * Implementation details:
     * Generates a complete inventory spreadsheet in memory from the given list of products.
     * Delegates styling creation to internal format helpers and populates data row by row.
     *
     * @param productos the list of {@link ProductoEntity} objects to export
     * @return the fully populated and styled {@link Workbook} instance
     */
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

            // Cell number 5 : shipping cost
            Cell c5 = row.createCell(5);
            double costeEnvioVal = (prod != null && prod.getCostes().getCostesEnvio() != null) ? prod.getCostes().getCostesEnvio().doubleValue() : 0.0;
            c5.setCellValue(costeEnvioVal);
            c5.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            // Cell number 6 : storage cost
            Cell c6 = row.createCell(6);
            double costeAlmacenajeVal = (prod != null && prod.getCostes().getCostesAlmacenaje() != null) ? prod.getCostes().getCostesAlmacenaje().doubleValue() : 0.0;
            c6.setCellValue(costeAlmacenajeVal);
            c6.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            // Cell number 7 : benefit
            Cell c7 = row.createCell(7);
            double profitVal = p.getProfit() != null ? p.getProfit().doubleValue() : 0.0;
            c7.setCellValue(profitVal);
            c7.setCellStyle(isAlt ? currStyleAlt : currStyleNormal);

            rowIdx++;
        }

        // Looping for adjusting each column
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }

        //returning workbook we have been working with
        return workbook;
    }

    /**
     * Creates and styles the header row for the inventory sheet.
     *
     * @param sheet the target {@link Sheet} where the row will be created
     * @param headerStyle the {@link CellStyle} to apply across all header cells
     */
    private static void createHeaderRow(Sheet sheet, CellStyle headerStyle) {
        // Creating array with the headers of the rows of the table
        String[] headers = {
                "Codigo", "Número de\nSerie", "Precio", "Descuento",
                "Precio\nFinal", "Costes\nEnvío", "Costes\nAlmacenaje", "Beneficio"
        };

        Row headerRow = sheet.createRow(0);
        headerRow.setHeightInPoints(32);

        // Building the header row of the table, in this case, cell by cell
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }
}