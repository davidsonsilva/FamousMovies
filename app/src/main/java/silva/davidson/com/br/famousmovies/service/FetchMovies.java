package silva.davidson.com.br.famousmovies.service;

import android.os.AsyncTask;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import silva.davidson.com.br.famousmovies.model.Movie;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;

public class FetchMovies extends AsyncTask<URL, Void, List<Movie>> {

    @Override
    protected List<Movie> doInBackground(URL... urls) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String results = IOUtils.toString(inputStream);
            NetworkUtils.parseJson(results, movies);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
    }
}