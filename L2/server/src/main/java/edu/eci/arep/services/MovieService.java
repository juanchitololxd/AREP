package edu.eci.arep.services;

import edu.eci.arep.domain.cache.ICache;
import edu.eci.arep.persistence.IMovieDAO;

import java.net.Socket;

public class MovieService{

    private IMovieDAO movieDAO;

    Socket socket;
    public MovieService(Socket socket, IMovieDAO movieDAO){
        this.socket = socket;
        this.movieDAO = movieDAO;
    }

    public String getMovie(String title) {
        String sMovieData = null;
        try {
            sMovieData = movieDAO.getMovie(title);
        } catch (Exception ignored) {
        }
        return sMovieData;
    }

}
