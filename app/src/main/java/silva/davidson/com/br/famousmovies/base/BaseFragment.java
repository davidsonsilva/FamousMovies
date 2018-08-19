package silva.davidson.com.br.famousmovies.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected static final String BUNDLE_RECORD = "MovieRecord";

    public void showSnackBarMessages(@IdRes int id,
                                     @StringRes int resId){
        Snackbar.make(getActivity().findViewById(id), getString(resId), Snackbar.LENGTH_SHORT).show();
    }

    public void showSnackBarMessage(@IdRes int id, @NonNull CharSequence text){
        Snackbar.make(getActivity().findViewById(id), text, Snackbar.LENGTH_SHORT).show();
    }
}
