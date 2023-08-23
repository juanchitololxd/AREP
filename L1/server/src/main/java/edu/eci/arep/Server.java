package edu.eci.arep;

import edu.eci.arep.persistence.PersistenceManage;
import edu.eci.arep.services.MovieService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 35000;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor escuchando en el puerto " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(() -> {
                try {
                    System.out.println("Cliente conectado desde: " + socket.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    String inputLine, movieName;
                    MovieService movieService = new MovieService(socket, PersistenceManage.getMovieDAO());

                    while ((inputLine = in.readLine()) != null) {
                        if (inputLine.contains("TEST"))
                            out.println(inputLine);
                        else if (inputLine.contains("/movies")) {
                            movieName = inputLine.split("=")[1].split(" ")[0].strip();
                            System.out.println(movieName);
                            out.println(respGetOK(movieService.getMovie(movieName)));

                            break;
                        } else {
                            out.println(inputLine);
                        }
                    }
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.printf("%s sale", Thread.currentThread().getName());
            });
            t.start();
        }
    }

    public static String respGetOK(String jContent) {
        return "HTTP/1.1 200 OK \r\n" +
                "Access-Control-Allow-Origin: * \r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE \r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization \r\n" +
                "Content-Type: application/json \r\n\r\n" +
                jContent;

    }

}
