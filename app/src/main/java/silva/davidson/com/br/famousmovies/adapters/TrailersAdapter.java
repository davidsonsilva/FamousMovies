package silva.davidson.com.br.famousmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.interfaces.VideosListener;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.service.MovieDBService;
import silva.davidson.com.br.famousmovies.ui.MovieDetailActivity;
import silva.davidson.com.br.famousmovies.utilities.PicassoImageLoader;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private List<Videos> mTrailers;
    private Context mContext;
    private PicassoImageLoader mImageLoader;
    private VideosClickListener mClickListener;

    public interface VideosClickListener {
        void onItemClick(Videos video);
    }

    public TrailersAdapter(Context context, List<Videos> trailers, PicassoImageLoader imageLoader) {
        mTrailers = trailers;
        mContext = context;
        mImageLoader = imageLoader;
    }

    public void setOnItemClick(VideosClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    @NonNull
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.ViewHolder holder, int position) {
        holder.binding(mTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailers != null ? mTrailers.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.avatar_image)
        ImageView mAvatarImage;
        @BindView(R.id.video_title)
        TextView mVideoTitle;
        @BindView(R.id.video_size)
        TextView mVideoSize;
        @BindView(R.id.media_image)
        ImageView mMediaImage;
        @BindView(R.id.video_name)
        TextView mVideoName;
        @BindView(R.id.supporting_text)
        TextView mSupportingText;
        @BindView(R.id.progress_image)
        ProgressBar mProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void binding(Videos video){
            mVideoTitle.setText(video.getType());
            mVideoName.setText(video.getSite());
            mVideoSize.setText(String.valueOf(video.getSize()));
            mSupportingText.setText(video.getName());
            mImageLoader.loadImage(mMediaImage,
                    MovieDBService.buildYoutubeThumbnailUrl(video.getKey()),mProgressBar);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onItemClick(mTrailers.get(getAdapterPosition()));
            }

        }
    }
}
