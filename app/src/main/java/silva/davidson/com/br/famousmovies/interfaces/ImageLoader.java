package silva.davidson.com.br.famousmovies.interfaces;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ProgressBar;

public interface ImageLoader {
    void loadImage(ImageView view, String source);
    void loadImage(ImageView view, String source, final @NonNull ProgressBar progressBar) ;
}