package org.educa.dao;

import generated.Productos;
import jakarta.xml.bind.JAXBException;

import java.io.File;

public interface ProductoDAO {
    // method of ej1:
    Productos readFile(File file) throws JAXBException;
}