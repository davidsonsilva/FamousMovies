package silva.davidson.com.br.famousmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import silva.davidson.com.br.famousmovies.data.Movie;

public class NetworkUtils {

    private static NetworkInfo mNetworkInfo;

    public static List<Movie> parseJson(String data, ArrayList<Movie> list){

        try {
            JSONObject mainObject = new JSONObject(data);
            JSONArray resArray = mainObject.getJSONArray("results");

            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);
                Movie movie = new Movie(); //New Movie object
                movie.setId(jsonObject.optInt("id"));
                movie.setVoteAverage(jsonObject.optDouble("vote_average"));
                movie.setVoteCount(jsonObject.optInt("vote_count"));
                movie.setOriginalTitle(jsonObject.optString("original_title"));
                movie.setTitle(jsonObject.optString("title"));
                movie.setPopularity(jsonObject.optDouble("popularity"));
                movie.setBackdropPath(jsonObject.optString("backdrop_path"));
                movie.setOverview(jsonObject.optString("overview"));
                movie.setReleaseDate(jsonObject.optString("release_date"));
                movie.setPosterPath(jsonObject.optString("poster_path"));
                //Adding a new movie object into ArrayList
                list.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static Boolean networkStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(manager != null)
            mNetworkInfo = manager.getActiveNetworkInfo();

        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

}
