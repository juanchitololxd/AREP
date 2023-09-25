package eci.arep;
import eci.arep.services.LanguajeService;
import eci.arep.services.MathService;

import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String... args){
        port(getPort());
        get("sin", (req,res) -> MathService.sin(Double.parseDouble(req.queryParams("value"))));
        get("cos", (req,res) -> MathService.cos(Double.parseDouble(req.queryParams("value"))));
        get("palindromo", (req,res) -> LanguajeService.isPalindromo(req.queryParams("value")) ?
                "Es palindromo" : "NO Es palindromo");
        get("magnitud", (req,res) -> MathService.magnitud(
                Double.parseDouble(req.queryParams("x")), Double.parseDouble(req.queryParams("y"))));
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
