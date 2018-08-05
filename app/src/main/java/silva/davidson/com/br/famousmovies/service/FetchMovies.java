package silva.davidson.com.br.famousmovies.service;

import android.content.Context;
import android.os.AsyncTask;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import silva.davidson.com.br.famousmovies.interfaces.AsyncTaskDelegate;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;

public class FetchMovies extends AsyncTask<URL, Void, List<Movie>> {

    private AsyncTaskDelegate delegate = null;

    public FetchMovies(Context context, AsyncTaskDelegate responder){
        this.delegate = responder;
    }

    @Override
    protected List<Movie> doInBackground(URL... urls) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            String results = IOUtils.toString(inputStream);
            inputStream.close();
            return NetworkUtils.parseJson(results, movies);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        if(delegate != null)
            delegate.processFinish(movies);
    }
}