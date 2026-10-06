package org.educa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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

    private static CellStyle createCurrencyStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);
        DataFormat dataFormat = wb.createDataFormat();
        Font font = wb.createFont();
        font.setFontName("Calibri");

        style.setFont(font);
        style.setDataFormat(dataFormat.getFormat("#,##0.00 €"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        return applyBackgroundIfAlt(style, isAlt);
    }

    private static CellStyle createPercentStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);
        DataFormat dataFormat = wb.createDataFormat();
        Font font = wb.createFont();
        font.setFontName("Calibri");

        style.setFont(font);
        style.setDataFormat(dataFormat.getFormat("0.00%"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        return applyBackgroundIfAlt(style, isAlt);
    }

    private static CellStyle createBaseBorderedStyle(Workbook wb) {
        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        XSSFColor borderColor = new XSSFColor(GREEN_BORDER_RGB, null);

        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        style.setTopBorderColor(borderColor);
        style.setBottomBorderColor(borderColor);
        style.setLeftBorderColor(borderColor);
        style.setRightBorderColor(borderColor);

        return style;
    }

    private static CellStyle applyBackgroundIfAlt(CellStyle style, boolean isAlt) {
        if (isAlt) {
            XSSFCellStyle xssfStyle = (XSSFCellStyle) style;
            XSSFColor bgColor = new XSSFColor(LIGHT_GREEN_BG_RGB, null);
            xssfStyle.setFillForegroundColor(bgColor);
            xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        return style;
    }
}

