package silva.davidson.com.br.famousmovies.ui;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.adapters.MoviesRecycleViewAdapter;
import silva.davidson.com.br.famousmovies.adapters.ReviewAdapter;
import silva.davidson.com.br.famousmovies.adapters.TrailersAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;
import silva.davidson.com.br.famousmovies.interfaces.ReviewListener;
import silva.davidson.com.br.famousmovies.interfaces.VideosListener;
import silva.davidson.com.br.famousmovies.model.Movie;
import silva.davidson.com.br.famousmovies.model.Review;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.rest.MovieApi;
import silva.davidson.com.br.famousmovies.rest.ReviewResponse;
import silva.davidson.com.br.famousmovies.rest.VideosResponse;

public class MovieDetailActivity extends BaseActivity implements ImageLoader, ReviewListener, VideosListener {

    private static final String BUNDLE_RECORD = "MovieRecord";

    @BindView(R.id.app_bar_image)
    ImageView mAppBarImage;
    @BindView(R.id.movie_image_background)
    ImageView mMovieImageBackground;
    @BindView(R.id.movie_image)
    ImageView mMovieImage;
    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_vote_average)
    TextView mMovieVoteAverage;
    @BindView(R.id.movie_release_date)
    TextView mMovieReleaseDate;
    //Reviews
    @BindView(R.id.movie_overview)
    TextView mMovieOverView;
    //Trailers
    @BindView(R.id.review_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.trailer_list)
    RecyclerView mTrailerRecyclerView;



    private ReviewAdapter mReviewAdapter;
    private TrailersAdapter mTrailersAdapter;

    public static void startActivity (BaseActivity activity, Movie record){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECORD, record);
        activity.startActivity(new Intent(activity, MovieDetailActivity.class).putExtras(bundle));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_movies);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayout.HORIZONTAL, false);


        if(intent.hasExtra(BUNDLE_RECORD)) {
            Movie movie = intent.getParcelableExtra(BUNDLE_RECORD);

            loadImage(mMovieImageBackground,MOVIE_BASE_URL_W342  + movie.getPosterPath());
            loadImage(mAppBarImage,MOVIE_BASE_URL_W185 + movie.getBackdropPath());
            loadImage(mMovieImage,MOVIE_BASE_URL_W185 + movie.getPosterPath());

            mMovieTitle.setText(movie.getTitle());
            mMovieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
            mMovieReleaseDate.setText(movie.getReleaseDate());
            mMovieOverView.setText(movie.getOverview());

            getMoviesWithRetrofitApi(movie.getId());

            getVideosWithRetrofit(movie.getId());

            setTitle(movie.getOriginalTitle());

            mTrailerRecyclerView.setLayoutManager(manager);
        }

    }

    private void getMoviesWithRetrofitApi(int movieId) {
        MovieApi movieApi = new MovieApi(this, this, this);
        movieApi.getMovieReview(movieId);
    }

    private void getVideosWithRetrofit(int movieId){
        MovieApi movieApi = new MovieApi(this, this, this);
        movieApi.getMovieVideos(movieId);
    }

    @Override
    public void loadImage(ImageView view, String source) {
        Picasso.get().load(source).into(view);
    }

    @Override
    public void loadImage(ImageView view, String source, @NonNull ProgressBar progressBar) {

    }

    @Override
    public void success(ReviewResponse response) {
        if(response != null && response.getReviews().size() > 0){
            List<Review> reviews = response.getReviews();
            mReviewAdapter = new ReviewAdapter(this, reviews);
            mRecyclerView.setAdapter(mReviewAdapter);

        }else{
            Toast.makeText(this, R.string.no_results_error , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void success(VideosResponse response) {
        if(response != null && response.getVideos().size() > 0){
            List<Videos> trailers = response.getVideos();
            mTrailersAdapter = new TrailersAdapter(this, trailers);
            mTrailerRecyclerView.setAdapter(mTrailersAdapter);
        }else{
            Toast.makeText(this, R.string.no_results_error , Toast.LENGTH_LONG).show();
        }
    }
}
