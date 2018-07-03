package silva.davidson.com.br.famousmovies.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.model.Movie;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String MOVIE_IMAGE_BASE_URL="https://image.tmdb.org/t/p/w185";

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView iconView = convertView.findViewById(R.id.movie_image);

        if(movie != null) {
            Picasso.get().load(MOVIE_IMAGE_BASE_URL + movie.getPosterPath())
                    .into(iconView);
        }
        return convertView;
    }

}
