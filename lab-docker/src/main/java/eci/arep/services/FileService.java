package eci.arep.services;

import eci.arep.persistence.file.IFileDAO;

import java.io.IOException;
public class FileService{

    private final IFileDAO fileDAO;
    public FileService(IFileDAO fileDAO){
        this.fileDAO = fileDAO;
    }

    public String getFile( String name)  throws IOException{
        return fileDAO.getFile(name);
    }
}

