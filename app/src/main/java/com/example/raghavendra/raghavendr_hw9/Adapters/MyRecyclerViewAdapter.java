package com.example.raghavendra.raghavendr_hw9.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.raghavendra.raghavendr_hw9.R;

import java.util.List;
import java.util.Map;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Map<String,?>> mDataSet;
    private Context mContext;
    private onCardClickListener mCardClickListener;

    @Override
    public int getItemViewType(int position) {
        Map<String,?> item = mDataSet.get(position);
        Double rating = (Double)item.get("rating");
        return rating.intValue();
    }


    public MyRecyclerViewAdapter(Context myContext, List<Map<String,?>> mDataSet){
        this.mContext=myContext;
        this.mDataSet=mDataSet;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        Map<String, ?> movie = mDataSet.get(position);
        holder.vIcon.setImageResource((Integer) movie.get("image"));
        holder.vTitle.setText((String) movie.get("name"));
        holder.vDescription.setText((String) movie.get("description"));
        Double rates = (Double)movie.get("rating");
        holder.vRatingbar.setRating(rates.floatValue() / 2);
        holder.vRatingText.setText("(" + rates.toString() + ")");

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public ImageView vMoreOptions;
        public RatingBar vRatingbar;
        public TextView vRatingText;

        public ViewHolder(View itemView) {

            super(itemView);
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

    public void setOnCardClickListener(final onCardClickListener mCardClickListener){
        this.mCardClickListener = mCardClickListener;
    }

}
