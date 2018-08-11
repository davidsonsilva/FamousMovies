package silva.davidson.com.br.famousmovies.ui;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.adapters.ReviewAdapter;
import silva.davidson.com.br.famousmovies.adapters.TrailersAdapter;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.model.Review;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.rest.MovieApi;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class MovieDetailActivity extends BaseActivity implements ImageLoader, TrailersAdapter.VideosClickListener {

    private static final String BUNDLE_RECORD = "MovieRecord";
    private MovieViewModel mViewModel;
    private static final String TAG = "MovieDetailActivity";

    @BindView(R.id.movie_image_toolbar)
    ImageView mMovieImage;
    @BindView(R.id.poster_movie)
    ImageView mPosterMovie;
    @BindView(R.id.movie_title)
    TextView mMovieTitle;
    @BindView(R.id.movie_release_date)
    TextView mMovieReleaseDate;
    @BindView(R.id.text_duration)
    TextView mMovieDuration;
    @BindView(R.id.movie_vote_average)
    TextView mMovieVoteAverage;
    @BindView(R.id.ratingBar)
    RatingBar mMovieRating;
    @BindView(R.id.text_popularity)
    TextView mMoviePopularity;
    @BindView(R.id.movie_overview)
    TextView mMovieOverView;
    @BindView(R.id.users_reviews)
    RecyclerView mRecyclerViewReviews;
    @BindView(R.id.trailer_list)
    RecyclerView mTrailerRecyclerView;

 /*   @BindView(R.id.movie_image_background)
    ImageView mMovieImageBackground;
    @BindView(R.id.image_view_aux)
    ImageView aux;
    @BindView(R.id.movie_image)
    ImageView mMovieImage;
    @BindView(R.id.label_review)
    TextView labelReviews;
    @BindView(R.id.label_trailers)
    TextView labelTraillers;*/

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
        setContentView(R.layout.activity_movie_parallax);
        ButterKnife.bind(this);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_parallax));
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.collapsing_toolbar);


        Intent intent = getIntent();

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayout.HORIZONTAL, false);

        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        if(intent.hasExtra(BUNDLE_RECORD)) {
            Movie movie = intent.getParcelableExtra(BUNDLE_RECORD);

            loadImage(mMovieImage,MOVIE_BASE_URL_W342 + movie.getBackdropPath());
            loadImage(mPosterMovie,MOVIE_BASE_URL_W185 + movie.getPosterPath());
            mMovieTitle.setText(movie.getTitle());
            mMovieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
            mMovieReleaseDate.setText(movie.getReleaseDate());
            mMovieDuration.setText("149 minutes");
            mMovieOverView.setText(movie.getOverview());
            mMovieRating.setRating(movie.getVoteCount());
            mMoviePopularity.setText(String.valueOf(movie.getPopularity()));

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
/*                    labelReviews.setText(String.format(getString(R.string.label_reviews),
                            String.valueOf(reviews.size())));*/
                    mReviewAdapter = new ReviewAdapter(getApplication(), reviews);
                    mRecyclerViewReviews.setAdapter(mReviewAdapter);

                }else{
/*                    labelReviews.setText(String.format(getString(R.string.label_reviews),
                            String.valueOf(0)));*/
                }
            }
        });

        mViewModel.getVideos().observe(this, new Observer<List<Videos>>() {
            @Override
            public void onChanged(@Nullable List<Videos> videos) {
                if(videos != null && videos.size() > 0) {
/*                    labelTraillers.setText(String.format(getString(R.string.label_trailers),
                            String.valueOf(videos.size())));*/
                    mTrailersAdapter = new TrailersAdapter(getApplication(), videos,
                            new PicassoImageLoader());
                    mTrailerRecyclerView.setAdapter(mTrailersAdapter);
                    mTrailersAdapter.setOnItemClick(MovieDetailActivity.this);
                } else {
/*                    labelTraillers.setText(String.format(getString(R.string.label_trailers),
                            String.valueOf(0)));*/
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
        String url = MovieApi.buildYoutubeUrl(video.getKey());
        openWebPage(url);
    }

    /**
     * Open video on Youtube Site
     * @param url
     */
    private  void  openWebPage(String url) {
        Uri webPage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
