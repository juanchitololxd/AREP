package edu.eci.arep.spark;

import java.util.HashMap;
import java.util.Map;

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
}
