package eci.arep.services;

import eci.arep.persistence.MovieDAO;
import eci.arep.spring.anotaciones.Componente;
import eci.arep.spring.anotaciones.GetMapping;
import eci.arep.spring.anotaciones.RequestParam;


@Componente
public class MovieService {
    @GetMapping("/movies")
    public static byte[] getMovie(@RequestParam String name) {
        String sMovieData = null;
        try {
            sMovieData = MovieDAO.getMovie(name);
        } catch (Exception ignored) {
        }
        return sMovieData.getBytes();
    }
}
