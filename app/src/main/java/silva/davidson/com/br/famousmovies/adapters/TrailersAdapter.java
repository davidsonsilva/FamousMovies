package silva.davidson.com.br.famousmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.model.Videos;
import silva.davidson.com.br.famousmovies.ui.MovieDetailActivity;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    private List<Videos> mTrailers;
    private Context mContext;

    public TrailersAdapter(Context context, List<Videos> trailers) {
        mTrailers = trailers;
        mContext = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

/*        @BindView(R.id.video_image)
        NetworkImageView mNetworkImageView;*/
        @BindView(R.id.play_buttom)
        ImageButton imageButtonPlay;
        @BindView(R.id.video_title)
        TextView mVideoTitle;
        @BindView(R.id.video_size)
        TextView mVideoSize;
        @BindView(R.id.video_name)
        TextView mVideoName;
        @BindView(R.id.video_error_message)
        TextView mErrorMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void binding(Videos video){
            mVideoTitle.setText(video.getName());
            mVideoName.setText(video.getSite());
            mVideoSize.setText(String.valueOf(video.getSize()));
        }
    }
}
