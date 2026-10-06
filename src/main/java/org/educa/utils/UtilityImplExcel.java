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


    // Colors for borders and alternating background colors
    private static final byte[] GREEN_BORDER_RGB = new byte[]{(byte) 76, (byte) 175, (byte) 80};
    private static final byte[] LIGHT_GREEN_BG_RGB = new byte[]{(byte) 200, (byte) 230, (byte) 201};

    /**
     * Creates the style for header cells (bold and center text).
     *
     * @param wb the excel workbook
     * @return the header cell style
     */
    private static CellStyle createHeaderStyle(Workbook wb) {
        // Get the base style with borders first
        CellStyle style = createBaseBorderedStyle(wb);

        // Create a new bold font
        Font font = wb.createFont();
        font.setFontName("Calibri");
        font.setBold(true);

        // Set font and alignments
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true); // Wrap text if it is too long

        return style;
    }

    /**
     * Creates the style for code cells (centered and bold).
     *
     * @param wb the excel workbook
     * @param isAlt true to apply background color
     * @return the code cell style
     */
    private static CellStyle createCodeStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);

        Font font = wb.createFont();
        font.setFontName("Calibri");
        font.setBold(true);

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        // Check if we need to apply background color
        return applyBackgroundIfAlt(style, isAlt);
    }

    /**
     * Creates the style for normal text cells.
     *
     * @param wb the excel workbook
     * @param isAlt true to apply background color
     * @return the text cell style
     */
    private static CellStyle createTextStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);

        Font font = wb.createFont();
        font.setFontName("Calibri");

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        return applyBackgroundIfAlt(style, isAlt);
    }

    /**
     * Creates the style for currency values (formatted with Euro symbol).
     *
     * @param wb the excel workbook
     * @param isAlt true to apply background color
     * @return the currency cell style
     */
    private static CellStyle createCurrencyStyle(Workbook wb, boolean isAlt) {
        CellStyle style = createBaseBorderedStyle(wb);

        DataFormat dataFormat = wb.createDataFormat();
        Font font = wb.createFont();
        font.setFontName("Calibri");

        style.setFont(font);
        // Align to the right for numbers
        style.setDataFormat(dataFormat.getFormat("#,##0.00 €"));
        style.setAlignment(HorizontalAlignment.RIGHT);

        return applyBackgroundIfAlt(style, isAlt);
    }

    /**
     * Creates the style for percentage values.
     *
     * @param wb the excel workbook
     * @param isAlt true to apply background color
     * @return the percentage cell style
     */
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

    /**
     * Helper method to create a base style with thin green borders.
     *
     * @param wb the excel workbook
     * @return the base cell style with green borders
     */
    private static CellStyle createBaseBorderedStyle(Workbook wb) {
        // Cast to XSSFCellStyle to use RGB colors
        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        XSSFColor borderColor = new XSSFColor(GREEN_BORDER_RGB, null);

        // Set thin border for the 4 sides
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // Set the green color for borders
        style.setTopBorderColor(borderColor);
        style.setBottomBorderColor(borderColor);
        style.setLeftBorderColor(borderColor);
        style.setRightBorderColor(borderColor);

        return style;
    }

    /**
     * Applies the background color if the boolean parameter is true.
     *
     * @param style the cell style to modify
     * @param isAlt boolean flag for alternating background
     * @return the updated cell style
     */
    private static CellStyle applyBackgroundIfAlt(CellStyle style, boolean isAlt) {
        if (isAlt) {
            // Need to cast to XSSFCellStyle to use custom RGB
            XSSFCellStyle xssfStyle = (XSSFCellStyle) style;
            XSSFColor bgColor = new XSSFColor(LIGHT_GREEN_BG_RGB, null);

            // Set solid background color
            xssfStyle.setFillForegroundColor(bgColor);
            xssfStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        return style;
    }
}

