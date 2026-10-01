package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBException;
import org.educa.entity.ProductoEntity;

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

    void writeSummary(File fileTxt, String content) throws IOException;
}