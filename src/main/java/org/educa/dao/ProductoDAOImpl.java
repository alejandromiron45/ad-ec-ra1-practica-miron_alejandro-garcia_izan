package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class ProductoDAOImpl implements ProductoDAO{

    //implemented method from interface, included the exception
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
