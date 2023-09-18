package eci.arep;

import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;

@Componente
public class HelloServices {
    @GetMapping("/hola")
    public static byte[] hola(String arg){
        return "Holaaaaa".getBytes();
    }

}
