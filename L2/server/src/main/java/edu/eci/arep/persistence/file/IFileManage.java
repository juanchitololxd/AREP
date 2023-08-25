package edu.eci.arep.persistence.file;

import java.io.IOException;

public interface IFileManage {

    String getFile(String fileName) throws IOException;
    byte[] writeImage(String fileName) throws IOException;
}
