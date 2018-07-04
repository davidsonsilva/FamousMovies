package silva.davidson.com.br.famousmovies.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;
import silva.davidson.com.br.famousmovies.model.Movie;


public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String MOVIE_IMAGE_BASE_URL="https://image.tmdb.org/t/p/w185";
    private ImageLoader mImageLoader;

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    public MovieAdapter(Activity context, List<Movie> movies, ImageLoader imageLoader) {
        super(context, 0, movies);
        mImageLoader = imageLoader;
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
        final ProgressBar progressBar = convertView.findViewById(R.id.progress_image);

        if(movie != null) {
            if(mImageLoader != null) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get().load(MOVIE_IMAGE_BASE_URL + movie.getPosterPath())
                        .into(iconView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(getContext(), R.string.load_image_error_message,
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        }
        return convertView;
    }

}
