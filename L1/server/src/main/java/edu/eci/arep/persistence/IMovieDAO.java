package edu.eci.arep.persistence;

import edu.eci.arep.MovieException;

public interface IMovieDAO {

    /**
     * Get data about the title
     * @param title title name
     * @return
     * @throws MovieException
     */
    String getMovie(String title) throws MovieException;
}
