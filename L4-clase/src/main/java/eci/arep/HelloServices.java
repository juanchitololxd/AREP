package eci.arep;

import eci.arep.anotaciones.Componente;
import eci.arep.anotaciones.GetMapping;

@Componente
public class HelloServices {
    @GetMapping("/hola")
    public static String hola(String arg){
        return " hola " + arg;
    }

    @GetMapping("/movie")
    public static String getMovie(String arg){
        return movieService.getMOvie("").getBytes();
    }

}
