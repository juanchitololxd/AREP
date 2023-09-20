package eci.arep;

public class MovieException extends Exception{
    public MovieException(String title){
        super("Data about " + title + "'s movie can't be found");
    }
}
