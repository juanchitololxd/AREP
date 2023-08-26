package edu.eci.arep;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.IOException;
import java.net.Socket;

public class ServerTest extends TestCase
{

    public void testConversateConcurrently() throws InterruptedException {
        Thread[] clientThreads = new Thread[50];

        for (int i = 0; i < clientThreads.length; i ++){
            Thread clientThread = new Thread(() -> {
                try {
                    Socket socket = new Socket("localhost", 35000);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    for (int j = 0; j < 10; j++) {
                        String message = "TEST by" + Thread.currentThread().getName() + " " + j;
                        out.println(message);
                    }

                    socket.close();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            clientThreads[i] = clientThread;
            clientThread.start();
        }

        for (Thread clientThread : clientThreads) {
            clientThread.join();
        }
    }

    public void testGetMovie() throws IOException {
        Socket socket = new Socket("localhost", 35000);

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String movie = "Guardians of the galaxy";
        out.println(movie);

        String dataMovie = in.readLine();
        assertNotNull(dataMovie);
        in.close();
        out.close();
    }
}
