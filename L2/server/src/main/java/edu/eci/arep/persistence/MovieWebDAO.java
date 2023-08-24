package edu.eci.arep.persistence;

import edu.eci.arep.MovieException;
import edu.eci.arep.domain.cache.ICache;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieWebDAO implements IMovieDAO {
    private static final String API_KEY = "4acc9e71";
    private final ICache cache;

    public MovieWebDAO(ICache cache) {
        this.cache = cache;

    }

    public String getMovie(String title) throws MovieException {
        String response = cache.get(title);
        if (response == null) {
            response = loadMovieFromURL(title);
        }
        return response;
    }

    public boolean existInCache(String title) {
        return cache.get(title) != null;
    }

    private String loadMovieFromURL(String title) throws MovieException {
        System.out.println("Entra load movie");
        StringBuilder response = new StringBuilder();
        try {
            String URL = String.format("https://www.omdbapi.com/?apikey=%s&t=%s", API_KEY, title.replace(" ", "+"));
            URL obj = new URL(URL);

            String inputLine;

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK)
                throw new MovieException(title);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            cache.put(title, response.toString());
            in.close();
        } catch (Exception ex) {
            throw new MovieException(title);
        }
        System.out.println(response.toString());

        return response.toString();
    }

}
