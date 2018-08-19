package silva.davidson.com.br.famousmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.base.BaseActivity;
import silva.davidson.com.br.famousmovies.base.BaseFragment;
import silva.davidson.com.br.famousmovies.behaviors.BottomNavigationBehavior;
import silva.davidson.com.br.famousmovies.factory.ViewModelFactory;
import silva.davidson.com.br.famousmovies.ui.FragmentFavorites;
import silva.davidson.com.br.famousmovies.ui.FragmentMostPopular;
import silva.davidson.com.br.famousmovies.ui.FragmentTopRated;
import silva.davidson.com.br.famousmovies.utilities.NetworkUtils;
import silva.davidson.com.br.famousmovies.viewmodel.MovieViewModel;

public class MainActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.frame_container)
    FrameLayout mFrameLayout;

    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel.class);

        setTitle(R.string.app_name);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (verifyConnection()) {
            movieViewModel.start();
            mBottomNavigationView.setSelectedItemId(R.id.navigation_popular);
        } else {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_conection_text,
                    Snackbar.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private boolean verifyConnection() {
        return NetworkUtils.networkStatus(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemSelected = item.getItemId();
          if (verifyConnection()) {
                if (itemSelected == R.id.navigation_popular) {
                    loadFragments(FragmentMostPopular.getInstance());
                } else if (itemSelected == R.id.navigation_rated) {
                    loadFragments(FragmentTopRated.getInstance());
                } else if (itemSelected == R.id.navigation_favorites){
                    loadFragments(FragmentFavorites.getInstance());
                }
            } else {
              Snackbar.make(findViewById(R.id.myCoordinatorLayout), R.string.no_conection_text,
                      Snackbar.LENGTH_LONG).show();
            }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragments(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}



