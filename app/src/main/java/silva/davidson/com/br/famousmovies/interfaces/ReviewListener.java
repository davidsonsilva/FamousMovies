package silva.davidson.com.br.famousmovies.interfaces;

import silva.davidson.com.br.famousmovies.data.source.remote.ReviewResponse;

public interface ReviewListener {
    void success(ReviewResponse response);
}