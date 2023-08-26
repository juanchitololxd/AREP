package edu.eci.arep;

import edu.eci.arep.persistence.PersistenceManage;
import edu.eci.arep.persistence.file.FileManageTarget;
import edu.eci.arep.persistence.file.IFileManage;
import edu.eci.arep.services.MovieService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 35000;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor escuchando en el puerto " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(() -> {
                try {
                    MovieService movieService = new MovieService(socket, PersistenceManage.getMovieDAO());
                    System.out.println("Cliente conectado desde: " + socket.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    OutputStream out = socket.getOutputStream();
                    List<byte[]> rta;

                    String inputLine = in.readLine();
                    if (inputLine == null) throw new IOException();
                    String url =inputLine.split(" ")[1];

                    if (!url.equals("/") ) {
                        rta = processGetURL(url, movieService);
                        writeData(rta, out);
                    }
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    System.out.println("error" + e.getMessage());
                }


                System.out.printf("%s sale\n", Thread.currentThread().getName());
            });
            t.start();
        }
    }

    private static List<byte[]> getHeaders(String contentType, int result, String answer) {
        List<byte[]> headers = new ArrayList<>();
        headers.add(String.format("HTTP/1.1 %d %s", result, answer).getBytes());
        headers.add("Access-Control-Allow-Origin: * ".getBytes());
        headers.add("Access-Control-Allow-Methods: * ".getBytes());
        headers.add("Access-Control-Allow-Headers: Content-Type, Authorization ".getBytes());
        headers.add(String.format("Content-Type: %s", contentType).getBytes());
        return headers;
    }


    private static List<byte[]> processGetURL(String inputLine, MovieService movieService){
        String name, extension, replaceExt;
        IFileManage fileManager = new FileManageTarget();
        List<byte[]> allContent = new ArrayList<>();

        try {
            if (inputLine.contains("TEST")) allContent.add(inputLine.getBytes());

            else if (inputLine.contains("/movies")) {
                name = inputLine.split("=")[1].strip();
                allContent = getHeaders("application/json", 200, "OK");
                allContent.add(movieService.getMovie(name).getBytes());
            }
            else if (inputLine.contains(".") && isImageExt(inputLine)){
                name = inputLine.replace("/", "");
                extension = name.split("\\.")[1];

                allContent = getHeaders(String.format("image/%s", extension), 200, "OK");
                allContent.add(fileManager.writeImage(name));
            }
            else if (inputLine.contains(".") && isPlainExt(inputLine)){
                name = inputLine.replace("/", "");
                extension = name.split("\\.")[1];
                replaceExt = extension;
                if (replaceExt.equals("txt")) replaceExt = "plain";
                allContent = getHeaders(String.format("text/%s", replaceExt), 200, "OK");
                allContent.add(fileManager.getFile(name).getBytes());
            }
            else {
                allContent.add(inputLine.getBytes());
            }
        }catch (Exception e){
            System.out.println("fallo " + e.getMessage());
            allContent = getHeaders("text/plain", 404, "NOTFOUND");
        }
        return allContent;

    }

    private static boolean isImageExt(String fileName) {
        String extension = fileName.split("\\.")[1];
        List<String> extensions = new ArrayList<>();
        extensions.add("png");
        extensions.add("jpg");
        extensions.add("png");
        return extensions.contains(extension);
    }

    private static boolean isPlainExt(String fileName) {
        String extension = fileName.split("\\.")[1];
        List<String> extensions = new ArrayList<>();
        extensions.add("txt");
        extensions.add("css");
        extensions.add("js");
        extensions.add("html");
        return extensions.contains(extension);
    }

    private static void writeData(List<byte[]> data, OutputStream out){
        try {
            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() -1) {
                    out.write("\r\n".getBytes());
                    out.write(data.get(i));
                }else {
                    out.write(data.get(i));
                    out.write("\r\n".getBytes());
                }


            }
            //out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
