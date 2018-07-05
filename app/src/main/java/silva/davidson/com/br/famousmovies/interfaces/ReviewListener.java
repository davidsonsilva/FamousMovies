package silva.davidson.com.br.famousmovies.interfaces;

import silva.davidson.com.br.famousmovies.rest.ReviewResponse;

public interface ReviewListener {
    void success(ReviewResponse response);
}