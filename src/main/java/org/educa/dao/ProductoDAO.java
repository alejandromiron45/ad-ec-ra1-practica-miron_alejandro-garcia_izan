package org.educa.dao;

import generated.Productos;

import java.io.File;

public interface ProductoDAO {
    //method of ej1:
    Productos readFile(File file);
}
