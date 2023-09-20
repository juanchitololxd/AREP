package eci.arep.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileDAO {
    public static String getFile(String fileName)  throws IOException {
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



    public static byte[] getImage(String fileName) throws IOException {
        System.out.println("public/img/" + fileName);
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("public/img/" + fileName).readAllBytes();
    }
}
