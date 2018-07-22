package silva.davidson.com.br.famousmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import silva.davidson.com.br.famousmovies.R;
import silva.davidson.com.br.famousmovies.model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {


    private List<Review> mReviewList;
    private Context context;

    public ReviewAdapter(@NonNull Context context, @Nullable List<Review> reviewList){
        this.context = context;
        this.mReviewList = reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.mReviewList = mReviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding(mReviewList.get(position));
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mReviewList != null ? mReviewList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.textViewAuthor)
        TextView textViewAuthor;
        @Nullable
        @BindView(R.id.textViewContent)
        TextView textViewContext;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void binding(Review review){
            textViewAuthor.setText(review.getAuthor());
            textViewContext.setText(review.getContent());
        }
    }
}
