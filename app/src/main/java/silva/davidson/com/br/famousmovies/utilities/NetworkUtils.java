package silva.davidson.com.br.famousmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import silva.davidson.com.br.famousmovies.model.Movie;

public class NetworkUtils {

    private static NetworkInfo mNetworkInfo;

    public static void parseJson(String data, ArrayList<Movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);
            JSONArray resArray = mainObject.getJSONArray("results");

            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                Movie movie = new Movie(); //New Movie object
                movie.setId(jsonObject.getInt("id"));
                movie.setVoteAverage(jsonObject.getInt("vote_average"));
                movie.setVoteCount(jsonObject.getInt("vote_count"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setPopularity(jsonObject.getDouble("popularity"));
                movie.setBackdropPath(jsonObject.getString("backdrop_path"));
                movie.setOverview(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setPosterPath(jsonObject.getString("poster_path"));
                //Adding a new movie object into ArrayList
                list.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null)
            mNetworkInfo = manager.getActiveNetworkInfo();

        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

}
