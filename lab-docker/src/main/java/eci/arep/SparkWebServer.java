package eci.arep;
import eci.arep.persistence.file.FileResourceDAO;
import eci.arep.services.FileService;
import eci.arep.services.LanguajeService;
import eci.arep.services.MathService;

import static spark.Spark.*;

public class SparkWebServer {

    public static void main(String... args){

        FileService fileService = new FileService(new FileResourceDAO());
        port(getPort());
        get("sin", (req,res) -> MathService.sin(Double.parseDouble(req.queryParams("value"))));
        get("cos", (req,res) -> MathService.cos(Double.parseDouble(req.queryParams("value"))));
        get("palindromo", (req,res) -> LanguajeService.isPalindromo(req.queryParams("value")) ?
                "Es palindromo" : "NO Es palindromo");
        get("magnitud", (req,res) -> MathService.magnitud(
                Double.parseDouble(req.queryParams("x")), Double.parseDouble(req.queryParams("y"))));
        get("index.html", (req, res) -> fileService.getFile("index.html"));
        get("files", (req, res) -> fileService.getFile(req.queryParams("value")));

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
