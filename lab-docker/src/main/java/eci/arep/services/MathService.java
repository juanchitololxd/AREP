package eci.arep.services;

public class MathService {

    public static double sin(double value){
        return Math.sin(value);
    }

    public static double cos(double value){
        return Math.cos(value);
    }

    public static double magnitud(double x, double y){
        return Math.sqrt(Math.pow(x, 2) +  Math.pow(y, 2));
    }
}
