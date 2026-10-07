package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProductoDAO {
    // method of ej1:

    /**
     * Reads and deserializes an inventory XML file into a domain object representation.
     *
     * @param file the  {@link File}  the XML file
     * @return a {@link Productos} object containing the collection of deserialized products
     * @throws JAXBException if an error occurs during unmarshalling of the XML file, the exception is thrown
     */
    Productos obtainList(File file) throws JAXBException;

    //method of ej2:

    /**
     * Writes content of the XML file {@link SummaryEntity} into a file defined by the {@link String}
     *
     * @param pathTxt the path of the {@link File}  were the content is going to be written
     * @param sE      content of the XML encapsulated in a {@link SummaryEntity} for ensuring safety of the content
     * @throws IOException if an I/O error occurs during directory creation or file writing
     */
    void writeSummary(String pathTxt, SummaryEntity sE) throws IOException;

    //method of ej3:

    /**
     * Exports a list of products to an Excel spreadsheet file.
     *
     * @param pathExcel the destination file path where the .xlsx file will be saved
     * @param productos the list of {@link ProductoEntity} objects containing the inventory data to export
     * @throws IOException if an I/O error occurs during the file export process
     */
    void exportExcel(String pathExcel, List<ProductoEntity> productos) throws IOException;
}