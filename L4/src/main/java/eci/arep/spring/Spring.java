package eci.arep.spring;

import eci.arep.ComponentLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Spring {

    public static void main(String[] args) throws Exception {
        cargarClases();
        final int PORT = 35000;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor escuchando en el puerto " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            try {
                System.out.println("Cliente conectado desde: " + socket.getInetAddress());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream out = socket.getOutputStream();

                List<byte[]> rta;
                String[] aux = getDataFromInput(in);
                String method = aux[0], contentType = aux[3], endpoint = aux[1], params = aux[2];

                rta = getHeaders(contentType, 200, "OK");
                rta.add(ComponentLoader.ejecutar(endpoint, params));
                writeData(rta, out);
                out.close();
                socket.close();
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
            }

            System.out.printf("%s sale\n", Thread.currentThread().getName());

        }

    }


    private static String[] getDataFromInput(BufferedReader in) throws IOException {
        String line;
        String[] data = new String[4];//GET, endpoint, params, contenttype

        boolean firstLine = true;
        while ((line = in.readLine()) != null){
            if (line.startsWith("Accept")) {
                if (line.contains(",")) data[3] = line.split("\\:")[1].split(",")[0];
                else data[3] = line.split("\\:")[1];
                break;
            }
            if (firstLine){
                // GET /movies?name=2e24 fdas
                data[0] = line.split(" ")[0];
                String aux = line.split(" ")[1];
                if (aux.contains("\\?")){
                    data[1] = aux.split("\\?")[0];
                    data[2] = aux.split("\\?")[1];
                }else {
                    data[1] = aux;
                    data[2] = null;
                }
                firstLine = false;
            }
        }

        return data;
    }


    /**
     * Orquestador de carga de clases dentro de ComponentLoader
     */

    private static void cargarClases() throws ClassNotFoundException {
        List<String> fileNames = new ArrayList<>();
        Spring.getClassNames(new File("target/classes"), fileNames);
        for (String fileName : fileNames) {
            ComponentLoader.load(fileName);
        }
    }

    /**
     * Retorna el nombre de las clases (junto con su paquete) que tienen la anotacion
     */
    public static void getClassNames(File carpeta, List<String> fileNames) {
        File[] archivos = carpeta.listFiles();

        if (archivos == null) throw new RuntimeException();
        for (File archivo : archivos) {
            if (archivo.isFile()) {
                String packageAndName = archivo.getPath().replace("target\\classes\\", "")
                        .replace(".class", "").replace("\\", ".");
                fileNames.add(packageAndName);
            } else if (archivo.isDirectory()) {
                getClassNames(archivo, fileNames);
            }
        }
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

    private static List<byte[]> getHeaders(String contentType, int result, String answer) {
        List<byte[]> headers = new ArrayList<>();
        headers.add(String.format("HTTP/1.1 %d %s", result, answer).getBytes());
        headers.add("Access-Control-Allow-Origin: * ".getBytes());
        headers.add("Access-Control-Allow-Methods: * ".getBytes());
        headers.add("Access-Control-Allow-Headers: Content-Type, Authorization ".getBytes());
        headers.add(String.format("Content-Type: %s", contentType).getBytes());
        return headers;
    }
}
