package silva.davidson.com.br.famousmovies.adapters;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ItemClickListener;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class MoviesRecycleViewAdapter extends RecyclerView.Adapter<MoviesRecycleViewAdapter.ViewHolder> {



    private static final String MOVIE_IMAGE_BASE_URL="https://image.tmdb.org/t/p/w185";
    private List<Movie> mMovieList;
    private PicassoImageLoader mImageLoader;
    private ItemClickListener mClickListener;
    private MovieViewModel mMovieViewModel;

    public MoviesRecycleViewAdapter(List<Movie> movieList, PicassoImageLoader imageLoader) {
        this.mMovieList = movieList;
        this.mImageLoader = imageLoader;
    }

    public void setMovieViewModel(MovieViewModel movieViewModel){
        mMovieViewModel = movieViewModel;
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
        @BindView(R.id.movieTitle)
        TextView mTittle;
        @BindView(R.id.fav_icon)
        ImageView mImageView;

        private Executor executor = Executors.newSingleThreadExecutor();

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pgBar.setVisibility(View.VISIBLE);
            itemView.setOnClickListener(this);
        }

        public void binding(Movie movie) {
            mImageLoader.loadImage(imageView, MOVIE_IMAGE_BASE_URL + movie.getPosterPath(),
                    pgBar);
            mTittle.setText(movie.getTitle());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    final Movie isFavorite = mMovieViewModel.getDb().getMovieDao().verifyFavorite(movie.getId());

                    if (isFavorite != null ) {
                        mImageView.setImageResource(R.drawable.ic_favorite_red_24dp);
                    } else {
                        mImageView.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    }
                }
            });


        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null)
                mClickListener.onClick(mMovieList.get(getAdapterPosition()));
        }
    }
}
