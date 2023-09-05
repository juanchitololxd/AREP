package eci.arep;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{

    static Map<String, IServicioConParametro> serivicios = new HashMap<>();
    public static void main( String[] args )
    {
        port(50000);
        get("/hello", (req, res) -> "Hello World");
        get("/hello2", (req,res)-> req.queryParams());
        Get("/cos", str -> "" + Math.cos(Double.parseDouble(str)));
    }


    public static void Get(String param, IServicioConParametro service){
        serivicios.put(param, service);
    }

    public static E getValueQueryParam(String key, E clase){
        return clase;

    }
}
