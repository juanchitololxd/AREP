package edu.eci.arep.persistence.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public class FileManageTarget implements  IFileManage{

    @Override
    public String getFile(String fileName)  throws IOException{
        StringBuilder content = new StringBuilder();
        System.out.println(fileName);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("public/" + fileName);
        if (inputStream == null) throw new IOException();
        Scanner scanner = new Scanner(inputStream, "UTF-8");

        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine()).append("\n");
        }

        return content.toString();
    }


    public byte[] writeImage(String fileName) throws IOException {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("public/img/" + fileName)).readAllBytes();
    }
}
