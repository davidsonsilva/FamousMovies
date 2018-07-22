package silva.davidson.com.br.famousmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import silva.davidson.com.br.famousmovies.base.BaseFragment;
import silva.davidson.com.br.famousmovies.model.Movie;

public class ReviewsFragment extends BaseFragment {

    private Movie mMovie;

    public static ReviewsFragment newInstance(){
        return new ReviewsFragment();
    }

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);//super.onCreateView(inflater, container, savedInstanceState);
    }
}
