package edu.eci.arep.spark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SparkClone {

    static final Map<String, ISparkService> get_services = new HashMap<>();
    static final Map<String, ISparkService> post_services = new HashMap<>();

    /***
     * register the service
     * @param method GET, POST
     * @param route endpoint route
     * @param consumer function to excute
     */
    public void addService(String method, String route, ISparkService consumer){
        if (method.equals("GET")) get_services.put(route, consumer);
        else post_services.put(route, consumer);
    }


    public byte[] execute(String method, String route, String[] params) throws Exception{
        if (method.equals("GET")) return get_services.get(route).execute(params);
        else return post_services.get(route).execute(params);
    }

    public boolean hasService(String method, String route){
        if (method.equals("GET")) return get_services.get(route) != null;
        else return post_services.get(route) != null;
    }


    public void start() throws IOException {
        final int PORT = 35000;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor escuchando en el puerto " + PORT);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread t = new Thread(() -> {
                OutputStream out=null;
                List<byte[]> rta;
                try {
                    System.out.println("Cliente conectado desde: " + socket.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = socket.getOutputStream();


                    String inputLine = in.readLine();
                    if (inputLine == null) throw new IOException();
                    String[] aux = inputLine.split(" ")[1].split("\\?");
                    String url = aux.length > 1 ? aux[0] : inputLine.split(" ")[1];
                    String method = inputLine.split(" ")[0];

                    String sParams = aux.length > 1 ? aux[1] : null;
                    if (hasService(method, url)) {
                        rta = getHeaders(getContentType(in, inputLine.split(" ")[1]), 200, "OK");
                        rta.add(execute(method, url, extractParams(sParams)));
                    }else throw  new Exception();

                }catch (IndexOutOfBoundsException ex){
                    rta = getHeaders("text/plain", 400, "Bad Request");
                    rta.add("Faltan parametros en su peticion".getBytes());
                }
                catch (Exception e) {
                    System.out.println("error " + e.getMessage());
                    rta = getHeaders("text/plain", 404, "Not Found");
                    rta.add("Error no controlado. Escriba bien :)".getBytes());
                }

                try {
                    writeData(rta, out);
                    if (out != null) out.close();
                    if (!socket.isClosed()) socket.close();
                    System.out.printf("%s sale con todo cerrado\n", Thread.currentThread().getName());
                } catch (IOException ignored) {}
            });
            t.start();
        }
    }


    /**
     * Write data to the client using out channel. These data must have the headers
     */
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

    /**
     * Extract params of URL
     * @param sParams URL like param1=value&param2=fafsd....
     * @return Array with value of each param y the same order
     */
    private static String[] extractParams(String sParams) {
        String[] params = {};
        if (sParams != null) {
            params = sParams.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] keyValue = params[i].split("=");
                if (keyValue.length == 2) params[i] = keyValue[1];
            }
        }
        System.out.println(Arrays.toString(params));
        return params;
    }

    /**
     * Verify if client wants a specific content-type, if true return that content-type, else return a suggested content-type
     * @param inputLine URL like/somthing?param1=value (if it has params)

     */
    private static String getContentType(BufferedReader in, String inputLine) throws IOException {
        String line, contentType="text/plain";
        while ((line = in.readLine()) != null){
            if (line.contains("Accept")){
                if (line.contains(",")) {
                    contentType = getSuggestedContentType(inputLine);
                }else {
                    contentType = line.split(":")[1].strip();
                }
                break;
            }
        }
        return contentType;
    }

    private static String getSuggestedContentType(String inputLine) {
        String contentType, name, extension;
        if (inputLine.contains("TEST")) contentType = "text/plain";
        else if (inputLine.contains("/movies")) contentType = "application/json";
        else if (inputLine.contains(".") && isImageExt(inputLine)){
            name = inputLine.replace("/", "");
            extension = name.split("\\.")[1];
            contentType = String.format("image/%s", extension);
        }
        else if (inputLine.contains(".") && isPlainExt(inputLine)){
            name = inputLine.replace("/", "");
            extension = name.split("\\.")[1];
            String replaceExt = extension;
            if (replaceExt.equals("txt")) replaceExt = "plain";
            contentType = String.format("text/%s", replaceExt);
        }
        else {
            contentType = "text/plain";
        }
        return contentType;
    }

    private static boolean isImageExt(String fileName) {
        String extension = fileName.split("\\.")[1];
        List<String> extensions = new ArrayList<>();
        extensions.add("png");
        extensions.add("jpg");
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
}
