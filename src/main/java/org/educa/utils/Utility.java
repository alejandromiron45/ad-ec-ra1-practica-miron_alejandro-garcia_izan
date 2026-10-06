package org.educa.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.educa.entity.ProductoEntity;

import java.util.List;

public interface Utility {

     Workbook buildInventoryWorkbook(List<ProductoEntity> productos);

    }
