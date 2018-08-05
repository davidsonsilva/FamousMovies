package silva.davidson.com.br.famousmovies.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import silva.davidson.com.br.famousmovies.R;

public class BaseActivity extends AppCompatActivity {

    protected static final String MOVIE_BASE_URL_W185 ="https://image.tmdb.org/t/p/w185";
    protected static final String MOVIE_BASE_URL_W342 ="https://image.tmdb.org/t/p/w342";
    protected static final Integer MOVIE_TYPE_MOST_POPULAR =  100;
    protected static final Integer MOVIE_TYPE_TOP_RATED = 200;

    public void showSnackBarMessages(@IdRes int id,
                                     @StringRes int resId){
        Snackbar.make(findViewById(id), getString(resId), Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBarMessage(@IdRes int id, @NonNull CharSequence text){
        Snackbar.make(findViewById(id), text, Snackbar.LENGTH_SHORT).show();
    }

}
