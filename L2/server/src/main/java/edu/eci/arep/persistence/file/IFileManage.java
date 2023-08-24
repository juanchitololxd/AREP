package edu.eci.arep.persistence.file;

import java.awt.image.RenderedImage;
import java.io.IOException;

public interface IFileManage {

    String getFile(String fileName);
    byte[] writeImage(String fileName) throws IOException;
}
