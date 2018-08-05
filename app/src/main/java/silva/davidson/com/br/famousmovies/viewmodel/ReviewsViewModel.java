package silva.davidson.com.br.famousmovies.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import silva.davidson.com.br.famousmovies.model.Videos;

public class ReviewsViewModel extends ViewModel {

    private MutableLiveData<List<Videos>> mVideos =  new MutableLiveData<>();

    public MutableLiveData<List<Videos>> getVideos() {
        return mVideos;
    }

    public void setVideos(List<Videos> videos) {
        this.mVideos.setValue(videos);
    }
}
