package eci.arep.services;

public class LanguajeService {

    public static boolean isPalindromo(String str){
        String reverse = new StringBuilder(str).reverse().toString();
        return reverse.equals(str);
    }
}
