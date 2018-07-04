package silva.davidson.com.br.famousmovies.utilities;

import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import silva.davidson.com.br.famousmovies.interfaces.ImageLoader;

public class PicassoImageLoader implements ImageLoader {

    @Override
    public void loadImage(ImageView view, String source) {
        Picasso.get().load(source).into(view);
    }
}