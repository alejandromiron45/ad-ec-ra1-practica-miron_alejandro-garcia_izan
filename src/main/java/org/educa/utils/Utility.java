package org.educa.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.educa.entity.ProductoEntity;

import java.util.List;

public interface Utility {

    /**
     * Implementation of the {@link Utility} interface dedicated to building
     * and formatting Excel workbooks in memory using Apache POI.
     * <p>
     * This class handles workbook structure assembly, headers layout, and
     * the mapping of entity collections into spreadsheet rows.
     */
    Workbook buildInventoryWorkbook(List<ProductoEntity> productos);

}
