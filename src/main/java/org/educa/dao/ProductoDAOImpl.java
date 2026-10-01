package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.educa.entity.ProductoEntity;
import org.educa.entity.SummaryEntity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    //implemented methods from interface, included the exceptions:

    /**
     * {@inheritDoc}
     * <p>
     * Implementation details:
     * Initializes a {@link JAXBContext} for the root class {@link Productos} and uses
     * an {@link Unmarshaller} to deserialize the provided physical XML file into memory.
     *
     * @param file the {@link File} that points to the XML File
     * @return a {@link Productos} object containing the collection of deserialized products
     * @throws JAXBException if an error occurs during unmarshalling of the XML file, the exception is thrown
     */
    @Override
    public Productos obtainList(File file) throws JAXBException {
        //1. Initialize the JAXB context using the root class = generated class Productos
        JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);
        //2. Create the Unmarshaller instance
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        //3. Unmarshall the XML file and cast it to the root class = generated class Productos
        return (Productos) unmarshaller.unmarshal(file);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementation details:
     * @param pathTxt the path of the {@link File}  were the content of the XML is going to be written
     * @param sE content of the XML encapsulated in a {@link SummaryEntity} for ensuring safety of the content
     * @throws IOException if an I/O error occurs during directory creation or file writing
     */
    @Override
    public void writeSummary(String pathTxt, SummaryEntity sE) throws IOException {
        File fileTxt = new File(pathTxt);
        File parent = fileTxt.getParentFile();
        String content = sE.toPrint();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileTxt))) {
            printWriter.print(content);
        }
    }

}
