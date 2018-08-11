package silva.davidson.com.br.famousmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.data.Movie;
import silva.davidson.com.br.famousmovies.utilities.ListAdapter;

public class MovieFavoritesActivity extends BaseActivity {

    private static final String BUNDLE_RECORD = "MovieRecord";
    private static final String TAG = "MovieFavoritesActivity";

    public static void startActivity (BaseActivity activity, Movie record){
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECORD, record);
        activity.startActivity(new Intent(activity, MovieFavoritesActivity.class).putExtras(bundle));
    }

    public static void startActivity (BaseActivity activity){
        activity.startActivity(new Intent(activity, MovieFavoritesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_parallax);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_parallax));
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.title_activity_collapsing_toolbar));

        final int MAX_ITEMS = 20;
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= MAX_ITEMS; i++) {
            items.add("Item " + i);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ListAdapter(items));
    }
}
