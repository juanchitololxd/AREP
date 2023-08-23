package edu.eci.arep.persistence;

import edu.eci.arep.MovieException;
import edu.eci.arep.domain.cache.Cache;
import junit.framework.TestCase;

public class MovieWebDAOTest extends TestCase {


    public void testGetMovieUsingCache() throws MovieException, InterruptedException {
        long time = 2000;
        MovieWebDAO movieDAO = new MovieWebDAO(Cache.getInstance(time));
        String[] titles = {"Guardians of the Galaxy"};
        for (String title : titles) assertFalse(movieDAO.existInCache(title));

        for (String title : titles) assertNotNull(movieDAO.getMovie(title));

        for (String title : titles) assertTrue(movieDAO.existInCache(title));

        Thread.sleep(time + 1);

        for (String title : titles) assertFalse(movieDAO.existInCache(title));
    }

}