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
}
