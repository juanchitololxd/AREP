package edu.eci.arep.persistence;

import edu.eci.arep.MovieException;

public interface IMovieDAO {

    String getMovie(String title) throws MovieException;
}
