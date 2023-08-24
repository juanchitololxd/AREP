package edu.eci.arep.persistence.file;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileManageTarget implements  IFileManage{

    @Override
    public String getFile(String fileName) {
        StringBuilder content = new StringBuilder();
        System.out.println(fileName);
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("public/" + fileName)) {
            if (inputStream == null) throw new IOException();
            Scanner scanner = new Scanner(inputStream, "UTF-8");

            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }

        } catch (IOException ignored) {}

        return content.toString();
    }


    public byte[] writeImage(String fileName) throws IOException {
        byte[] imagesData = Thread.currentThread().getContextClassLoader().getResourceAsStream("public/img/" + fileName).readAllBytes();
        return imagesData;
    }
}
