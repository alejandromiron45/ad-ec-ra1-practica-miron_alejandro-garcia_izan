package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class ProductoDAOImpl implements ProductoDAO{

    //implemented method from interface, included the exception:
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
    public Productos readFile(File file) throws JAXBException {
        //1. Initialize the JAXB context using the root class = generated class Productos
        JAXBContext jaxbContext = JAXBContext.newInstance(Productos.class);
        //2. Create the Unmarshaller instance
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        //3. Unmarshall the XML file and cast it to the root class = generated class Productos
        return (Productos) unmarshaller.unmarshal(file);
    }
}
