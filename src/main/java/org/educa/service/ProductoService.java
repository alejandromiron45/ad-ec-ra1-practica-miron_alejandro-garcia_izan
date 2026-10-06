package org.educa.service;

import generated.Producto;
import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.dao.ProductoDAO;
import org.educa.dao.ProductoDAOImpl;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        // Null file and empty file cases control
        if (fileXml == null || fileXml.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        // New instance of File that points to the XML File
        File file = new File(fileXml);

        // Get products from DAO
        Productos productsObj = productoDAO.obtainList(file);
        // New List that will contain the Productos object list of Producto
        List<ProductoEntity> productEntities = new ArrayList<>();

        // Handle null/empty cases with the Productos object
        // returned by productoDAO.obtainList()
        if (productsObj != null && productsObj.getProducto() != null) {
            // Looping through the list inside the object Productos
            for (Producto product : productsObj.getProducto()) {
                ProductoEntity entity = new ProductoEntity();
                entity.setProducto(product);
                // Calculate metrics (Final Price, Total Cost, Profit)
                calculateProductMetrics(entity);
                // For each Producto inside Productos, add the Producto to the list
                // created outside the loop
                productEntities.add(entity);
            }
        }

        return productEntities;
    }


    /**
     * Receives an entity and updates its specific financial metrics according to the business logic.
     *
     * @param entity the {@link ProductoEntity}  received by the method
     */
    // Auxiliary method for calculating necessary values for the business logic
    private void calculateProductMetrics(ProductoEntity entity) {
        // Check for null or empty input
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

    /**
     * Exports a summary in .txt containing metadata and total profit
     *
     * @param path    destination directory path
     * @param fileXml path to the XML file
     * @throws JAXBException if an error occurs during XML unmarshalling
     * @throws IOException   if file reading / writing operations fail
     */
    public void exportSummary(String path, String fileXml) throws JAXBException, IOException {
        // 1. Process products from XML
        List<ProductoEntity> productList = readFile(fileXml);

        // 2. Extract XML metadata
        File xmlFile = new File(fileXml);
        String absolutePath = xmlFile.getAbsolutePath();
        String fileName = xmlFile.getName();
        long fileSize = xmlFile.length();

        // 3. Extract inventory dynamic name
        String inventoryKey = extractInventoryKey(fileName);

        // 4. Calculate total profit
        BigDecimal totalProfit = calculateTotalProfit(productList);

        // 5. Fill the SummaryEntity object with the calculated data.
        SummaryEntity summary = new SummaryEntity(
                inventoryKey,
                productList.size(),
                totalProfit,
                absolutePath,
                fileName,
                fileSize
        );

        //6. Make sure the destination folder exists before saving the file.
        Files.createDirectories(Paths.get(path));

        //7. Define output TXT file path
        String outputFilePath = path + "result_" + inventoryKey + ".txt";

        // 8. Delegate file creation and writing to the DAO layer
        productoDAO.writeSummary(outputFilePath, summary);

    }

    /**
     * Calculates total profit across all products
     *
     * @param productList list of processed products
     * @return sum of all products profits
     */
    private BigDecimal calculateTotalProfit(List<ProductoEntity> productList) {
        // Preparing variable total with initial value of 0 before starting to operate with benefits
        BigDecimal total = BigDecimal.ZERO;
        // Checking with NullPointerException if the input list is null
        if (productList != null) {
            // Iterate through each product entity in the list
            for (ProductoEntity entity : productList) {
                // Null safety check: skip entities where profit has not been set
                if (entity.getProfit() != null) {
                    total = total.add(entity.getProfit());
                }
            }
        }
        // Taking final result of the sum and adjusting this result with two decimals
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Extracts month and year from the XML file name
     *
     * @param fileName XML file name
     * @return inventory key string (month and year)
     */
    private String extractInventoryKey(String fileName) {
        // Return a default String if the input is null or lacks a file extension
        if (fileName == null || !fileName.contains(".")) {
            return "summary";
        }

        // Strip the file extension by taking everything before the last dot
        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        // If an underscore is present, extract the substring following the first underscore
        if (nameWithoutExtension.contains("_")) {
            return nameWithoutExtension.substring(nameWithoutExtension.indexOf('_') + 1);
        }

        // Fall back to the base file name if no underscore separator is found
        return nameWithoutExtension;
    }

    public void exportExcel(String path, String fileXml) throws JAXBException, IOException, ParseException {
        List<ProductoEntity> productos = readFile(fileXml);

        File xmlFile = new File(fileXml);
        String fileName = xmlFile.getName();
        String dateKey = extractInventoryKey(fileName);

        if (path != null && !path.endsWith("/") && !path.endsWith("\\")) {
            path += "/";
        }

        String finalExcelPath = path + "export_" + dateKey + ".xlsx";

        productoDAO.exportExcel(finalExcelPath, productos);
    }

}
