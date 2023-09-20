package eci.arep.persistence;

import eci.arep.MovieException;
import eci.arep.domain.Cache;
import eci.arep.domain.ICache;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MovieDAO {
    private static final String API_KEY = "4acc9e71";
    private static final ICache cache = Cache.getInstance(100000);
    public static String getMovie(String title) throws MovieException {
        String response = cache.get(title);
        if (response == null) {
            response = loadMovieFromURL(title);
        }
        return response;
    }

    private static String loadMovieFromURL(String title) throws MovieException {
        System.out.println("busca movie en la web");
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

        return response.toString();
    }
}
