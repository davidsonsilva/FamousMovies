package silva.davidson.com.br.famousmovies.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import silva.davidson.com.br.famousmovies.model.Review;

public class TrailersViewModel extends ViewModel {


    private MutableLiveData<List<Review>> mReviews = new MutableLiveData<>();

    public MutableLiveData<List<Review>> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        this.mReviews.setValue(reviews);
    }
}
