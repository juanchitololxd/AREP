package eci.arep.services;

import eci.arep.persistence.FileDAO;
import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;

import java.io.IOException;

@Componente
public class HTMLService {

    @GetMapping(value = "/", contentType = "text/html")
    public static byte[] getHtml() throws IOException {
        return FileDAO.getFile("index.html").getBytes();
    }
}
