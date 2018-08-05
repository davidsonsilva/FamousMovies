package silva.davidson.com.br.famousmovies.ui;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.adapters.MoviesRecycleViewAdapter;
import silva.davidson.com.br.famousmovies.adapters.ReviewAdapter;
import silva.davidson.com.br.famousmovies.adapters.TrailersAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;
import silva.davidson.com.br.famousmovies.interfaces.ReviewListener;
import silva.davidson.com.br.famousmovies.interfaces.VideosListener;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.model.Review;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;
import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MovieDetailActivity extends BaseActivity implements ImageLoader, TrailersAdapter.VideosClickListener {

    private static final String BUNDLE_RECORD = "MovieRecord";
    private MovieViewModel mViewModel;
    private static final String TAG = "MovieDetailActivity";

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

        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        if(intent.hasExtra(BUNDLE_RECORD)) {
            Movie movie = intent.getParcelableExtra(BUNDLE_RECORD);

            loadImage(mMovieImageBackground,MOVIE_BASE_URL_W342  + movie.getPosterPath());
            loadImage(mAppBarImage,MOVIE_BASE_URL_W185 + movie.getBackdropPath());
            loadImage(mMovieImage,MOVIE_BASE_URL_W185 + movie.getPosterPath());

            mMovieTitle.setText(movie.getTitle());
            mMovieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
            mMovieReleaseDate.setText(movie.getReleaseDate());
            mMovieOverView.setText(movie.getOverview());

            getReviews(movie.getId());

            getTrailers(movie.getId());

            setTitle(movie.getOriginalTitle());

            mTrailerRecyclerView.setLayoutManager(manager);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if(reviews != null && reviews.size() > 0){
                    mReviewAdapter = new ReviewAdapter(getApplication(), reviews);
                    mRecyclerView.setAdapter(mReviewAdapter);

                }else{
                    Log.i(TAG, getString(R.string.no_results_error));
                    //Snackbar.make()
                }
            }
        });

        mViewModel.getVideos().observe(this, new Observer<List<Videos>>() {
            @Override
            public void onChanged(@Nullable List<Videos> videos) {
                if(videos != null && videos.size() > 0) {
                    mTrailersAdapter = new TrailersAdapter(getApplication(), videos,
                            new PicassoImageLoader());
                    mTrailerRecyclerView.setAdapter(mTrailersAdapter);
                } else {
                    Log.i(TAG, getString(R.string.no_results_error));
                }
            }
        });
    }

    private void getReviews(int movieId) {
        mViewModel.getReviews(movieId);
    }

    private void getTrailers(int movieId){
        mViewModel.getTrailers(movieId);
    }

    @Override
    public void loadImage(ImageView view, String source) {
        Picasso.get().load(source).into(view);
    }

    @Override
    public void loadImage(ImageView view, String source, @NonNull ProgressBar progressBar) {

    }

    @Override
    public void onItemClick(Videos video) {
        //Intent intent = new Intent()
    }

    void  openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
