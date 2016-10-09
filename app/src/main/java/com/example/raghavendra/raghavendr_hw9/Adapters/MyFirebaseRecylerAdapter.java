package com.example.raghavendra.raghavendr_hw9.Adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.raghavendra.raghavendr_hw9.R;
import com.example.raghavendra.raghavendr_hw9.Utils.Movie;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;


public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Movie,MyFirebaseRecylerAdapter.MovieViewHolder> {

    private Context mContext ;
    private static onCardClickListener mCardClickListener;

    public MyFirebaseRecylerAdapter(Class<Movie> modelClass, int modelLayout,
                                    Class<MovieViewHolder> holder, Query ref,Context context) {
        super(modelClass,modelLayout,holder,ref);
        this.mContext = context;
    }
/////////
    @Override
    protected void populateViewHolder(MovieViewHolder movieViewHolder, Movie movie, int i) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields

        movieViewHolder.vTitle.setText((String) movie.getName());
        movieViewHolder.vDescription.setText((String) movie.getDescription());
        Float rates = movie.getRating();
        movieViewHolder.vRatingbar.setRating(rates.floatValue() / 2);
        movieViewHolder.vRatingText.setText("(" + rates.toString() + ")");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(movie.getUrl(),movieViewHolder.vIcon);
    }

    //TODO: Populate ViewHolder and add listeners.
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public ImageView vMoreOptions;
        public RatingBar vRatingbar;
        public TextView vRatingText;
        public MovieViewHolder(View v) {
            super(v);
            vIcon = (ImageView) itemView.findViewById(R.id.movieIconCard);
            vTitle = (TextView) itemView.findViewById(R.id.movieTitleCard);
            vDescription = (TextView) itemView.findViewById(R.id.descriptionCard);
            vMoreOptions = (ImageView) itemView.findViewById(R.id.selectionCard);
            vRatingbar = (RatingBar) itemView.findViewById(R.id.ratingCard);
            vRatingText = (TextView) itemView.findViewById(R.id.ratingTextCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCardClickListener!=null)
                        mCardClickListener.onCardClick(v,getPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mCardClickListener != null)
                        mCardClickListener.onCardLongClick(v, getPosition());
                    return true;
                }
            });
            if(vMoreOptions!=null) {
                vMoreOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mCardClickListener!=null){
                            mCardClickListener.onMoreOptionsClick(v,getPosition());
                        }
                    }
                });
            }
        }
    }


    public interface onCardClickListener{
        void onCardClick(View view, int position);
        void onCardLongClick(View view, int position);
        void onMoreOptionsClick(View view, int position);
    }

    public void setOnCardClickListener(final onCardClickListener mCardClickListener1){
        mCardClickListener = mCardClickListener1;
    }
}
