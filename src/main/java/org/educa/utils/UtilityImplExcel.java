package org.educa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.educa.entity.ProductoEntity;

import java.util.List;

public class UtilityImplExcel {
    public Workbook buildInventoryWorkbook(List<ProductoEntity> productos) {
        return null;

    }


    private static final byte[] GREEN_BORDER_RGB = new byte[]{(byte) 76, (byte) 175, (byte) 80};
    private static final byte[] LIGHT_GREEN_BG_RGB = new byte[]{(byte) 200, (byte) 230, (byte) 201};


    private static CellStyle createHeaderStyle(Workbook wb) {
        CellStyle style = createBaseBorderedStyle(wb);
        Font font = wb.createFont();
        font.setFontName("Calibri");
        font.setBold(true);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    private static CellStyle createCodeStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);
        Font font = wb.createFont();
        font.setFontName("Calibri");
        font.setBold(true);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return applyBackgroundIfAlt(style, isAlt);
    }

    private static CellStyle createTextStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);
        Font font = wb.createFont();
        font.setFontName("Calibri");

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return applyBackgroundIfAlt(style, isAlt);
    }

    
}

