package com.example.raghavendra.raghavendr_hw9.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.raghavendra.raghavendr_hw9.Utils.Movie;
import com.example.raghavendra.raghavendr_hw9.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;


public class Fragment_MovieDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";

    Movie movieData=new Movie();
    HashMap movie;
    private ShareActionProvider mShareActionProvider;

    public Fragment_MovieDetail() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment FrontPage.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_MovieDetail newInstance(HashMap<String,?> sectionNumber) {
        Fragment_MovieDetail fragment = new Fragment_MovieDetail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=null;
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            movie = (HashMap<String, ?>) getArguments().getSerializable(ARG_SECTION_NUMBER);
        }
        rootView = inflater.inflate(R.layout.fragment_moviedetail, container, false);


        ImageView movie_poster = (ImageView) rootView.findViewById(R.id.icon);
        TextView movie_title = (TextView) rootView.findViewById(R.id.title);
        TextView movie_stars = (TextView) rootView.findViewById(R.id.starsname);
        TextView movie_year = (TextView) rootView.findViewById(R.id.year);
        TextView movie_desc = (TextView) rootView.findViewById(R.id.descrip);
        RatingBar movie_rating = (RatingBar) rootView.findViewById(R.id.rating);
        TextView movie_director = (TextView) rootView.findViewById(R.id.directorname);
        TextView movie_rating_text = (TextView) rootView.findViewById(R.id.ratingText);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage((String)movie.get("url"),movie_poster);
        movie_title.setText((String) movie.get("name"));
        movie_stars.setText((String)movie.get("stars"));
        movie_year.setText("(" + (String) movie.get("year") + ")");
        movie_desc.setText((String)movie.get("description"));
        movie_director.setText((String) movie.get("director"));
        Float rates = Float.parseFloat((String)movie.get("rating"));
        movie_rating.setRating(rates.floatValue()/2);
        movie_rating_text.setText(rates.toString());
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
