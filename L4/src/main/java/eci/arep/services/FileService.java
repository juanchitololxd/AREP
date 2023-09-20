package eci.arep.services;

import eci.arep.persistence.FileDAO;
import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;
import eci.arep.spring.anotaciones.RequestParam;

import java.io.IOException;


@Componente
public class FileService{

    @GetMapping(value = "/files", contentType="text/plain")
    public static byte[] getFile(@RequestParam() String name)  throws IOException{
        return FileDAO.getFile(name).getBytes();
    }

    @GetMapping(value = "/images", contentType="image/jpg")
    public static byte[] getImage(@RequestParam() String name) throws IOException {
        return FileDAO.getImage(name);
    }
}

