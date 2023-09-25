package eci.arep.persistence.file;

import java.io.IOException;

public interface IFileDAO {
    String getFile(String name)  throws IOException;
}
