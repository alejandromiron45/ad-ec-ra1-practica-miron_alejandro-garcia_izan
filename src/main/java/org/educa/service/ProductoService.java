package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    // DAO instance for handling XML data access operations

    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    /**
     * Reads product data from the specified XML file and calculates financial metrics
     *
     * @param fileXml path to the XML file
     * @return entity list of processed ProductoEntity instances
     * @throws JAXBException if an error occurs during XML unmarshalling
     */
    public List<ProductoEntity> readFile(String fileXml) throws JAXBException {
        if (fileXml == null || fileXml.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        File file = new File(fileXml);

        // Get products from DAO
        Productos productsObj = productoDAO.obtainList(file);

        List<ProductoEntity> productEntities = new ArrayList<>();

        // 2. Map generated Producto objects to ProductoEntity and calculate metrics
        if (productsObj != null && productsObj.getProducto() != null) {
            for (Producto product : productsObj.getProducto()) {
                ProductoEntity entity = new ProductoEntity();
                entity.setProducto(product);

                // Calculate metrics (Final Price, Total Cost, Profit)
                calculateProductMetrics(entity);

                productEntities.add(entity);
            }
        }

        return productEntities;
    }

    private void calculateProductMetrics(ProductoEntity entity) {
        if (entity == null || entity.getProducto() == null) {
            return;
        }

        Producto product = entity.getProducto();

        // 1. Calculate Final Price
        BigDecimal finalPrice = calculateFinalPrice(product);
        entity.setPrecioFinal(finalPrice);

        // 2. Calculate Total Cost
        BigDecimal totalCost = calculateTotalCost(product);
        entity.setCost(totalCost);

        // 3. Calculate Profit = Final Price - Total Cost
        BigDecimal profit = finalPrice.subtract(totalCost).setScale(2, RoundingMode.HALF_UP);
        entity.setProfit(profit);
    }

    // Calculate Final Price = Price - (Price * Discount / 100)
    private static BigDecimal calculateFinalPrice(Producto product) {
        BigDecimal price = product.getPrecio() != null ? product.getPrecio() : BigDecimal.ZERO;
        BigDecimal discountPctg = product.getDescuento() != null ? product.getDescuento() : BigDecimal.ZERO;

        if (discountPctg.compareTo(BigDecimal.ZERO) == 0) {
            return price.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal discountFactor = discountPctg.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal discountAmount = price.multiply(discountFactor);

        return price.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    //Calculate Total Cost = Storage Cost + Shipping Cost
    private static BigDecimal calculateTotalCost(Producto product) {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (product.getCostes() != null) {
            BigDecimal storageCost = product.getCostes().getCostesAlmacenaje() != null ?
                    product.getCostes().getCostesAlmacenaje() : BigDecimal.ZERO;
            BigDecimal shippingCost = product.getCostes().getCostesEnvio() != null ?
                    product.getCostes().getCostesEnvio() : BigDecimal.ZERO;

            totalCost = storageCost.add(shippingCost).setScale(2, RoundingMode.HALF_UP);
        }
        return totalCost;
    }

    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        //TODO: Implementar

    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        //TODO: Implementar
    }
}
