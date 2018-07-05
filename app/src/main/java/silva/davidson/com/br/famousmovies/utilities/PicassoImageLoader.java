package silva.davidson.com.br.famousmovies.utilities;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(ImageView view, String source) {
        Picasso.get().load(source).into(view);
    }

    @Override
    public void loadImage(ImageView view, String source, final @NonNull ProgressBar progressBar) {
        Picasso.get().load(source).into(view , new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}