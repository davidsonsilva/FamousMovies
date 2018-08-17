package silva.davidson.com.br.famousmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.interfaces.ItemClickListener;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;

public class MoviesRecycleViewAdapter extends RecyclerView.Adapter<MoviesRecycleViewAdapter.ViewHolder> {



    private static final String MOVIE_IMAGE_BASE_URL="https://image.tmdb.org/t/p/w185";
    private List<Movie> mMovieList;
    private Context mContext;
    private PicassoImageLoader mImageLoader;
    private ItemClickListener mClickListener;

    public MoviesRecycleViewAdapter(Context context, List<Movie> movieList, PicassoImageLoader imageLoader) {
        this.mMovieList = movieList;
        this.mContext = context;
        this.mImageLoader = imageLoader;
    }

    public void setClickListener(ItemClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    @Override
    public MoviesRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesRecycleViewAdapter.ViewHolder holder, int position) {
        holder.binding(mMovieList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovieList != null ? mMovieList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movie_image)
        ImageView imageView;
        @BindView(R.id.progress_image)
        ProgressBar pgBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pgBar.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        public void binding(Movie movie) {
            mImageLoader.loadImage(imageView, MOVIE_IMAGE_BASE_URL + movie.getPosterPath(),
                    pgBar);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onClick(mMovieList.get(getAdapterPosition()));
        }
    }
}
