package edu.eci.arep.services;

import edu.eci.arep.persistence.IMovieDAO;

public class MovieService{

    private IMovieDAO movieDAO;
    public MovieService(IMovieDAO movieDAO){
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
