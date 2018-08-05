package silva.davidson.com.br.famousmovies.interfaces;

import silva.davidson.com.br.famousmovies.data.source.remote.VideosResponse;

public interface VideosListener {
    void success(VideosResponse response);
}
