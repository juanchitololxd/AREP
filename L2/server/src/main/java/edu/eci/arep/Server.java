package edu.eci.arep;

import edu.eci.arep.persistence.PersistenceManage;
import edu.eci.arep.persistence.file.FileManageTarget;
import edu.eci.arep.persistence.file.IFileManage;
import edu.eci.arep.services.MovieService;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 35000;

        Map<String, String> contentTypes = new HashMap<>();
        contentTypes.put("html", "text/html");
        contentTypes.put("jpg", "image/jpg");
        contentTypes.put("png", "image/png");
        ServerSocket serverSocket = new ServerSocket(PORT);
        IFileManage fileManager= new FileManageTarget();
        System.out.println("Servidor escuchando en el puerto " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(() -> {
                try {
                    System.out.println("Cliente conectado desde: " + socket.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    OutputStream out2 = socket.getOutputStream();
                    String name, sContent, extension;
                    String inputLine = in.readLine();
                    if (inputLine == null) throw new IOException();
                    MovieService movieService = new MovieService(socket, PersistenceManage.getMovieDAO());
                    if (inputLine.contains("TEST")) out.println(inputLine);

                    else if (inputLine.contains("/movies")) {
                        name = inputLine.split("=")[1].split(" ")[0].strip();
                        out.println(respGetOK(movieService.getMovie(name), "application/json"));
                    }
                    else if (inputLine.contains(".") && inputLine.contains("GET /")){
                        try {
                            System.out.println(inputLine);
                            name = inputLine.split(" ")[1];
                            name = name.replace("/", "");
                            extension = name.split("\\.")[1];

                            if (extension.equals("jpg")) {
                                byte[] bytes = fileManager.writeImage(name);
                                out2.write("HTTP/1.1 200 OK\r\n".getBytes());
                                out2.write("Content-Type: image/jpg\r\n\r\n".getBytes());
                                out2.write(bytes);
                                out2.flush();
                                out2.close();
                            }
                            else {
                                sContent = fileManager.getFile(name);
                                out.println(respGetOK(sContent, contentTypes.get(extension)));
                            }

                        }catch (IOException e){
                            // TODO retornar 404
                            out.println("HTTP/1.1 404 NOTFOUND \r\n");
                        }

                    }
                    else {
                        out.println(inputLine);
                    }
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    System.out.println("error" + e.getMessage());
                }

                System.out.printf("%s sale", Thread.currentThread().getName());
            });
            t.start();
        }
    }

    public static String respGetOK(String jContent, String contentType) {
        return "HTTP/1.1 200 OK \r\n" +
                "Access-Control-Allow-Origin: * \r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE \r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization \r\n" +
                "Content-Type: " + contentType + "\r\n\r\n" +
                jContent;
    }

    public static void getPlainFile(String fileName, String ext){
        try {
            URI uri = new URI("target/classes/public" + fileName + "." + ext);
        } catch (URISyntaxException e) {
        }


    }

}
