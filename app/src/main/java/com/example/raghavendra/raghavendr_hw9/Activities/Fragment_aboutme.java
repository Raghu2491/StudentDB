package com.example.raghavendra.raghavendr_hw9.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raghavendra.raghavendr_hw9.R;

/**
 * Created by Raghavendra on 3/29/2016.
 */

public class Fragment_aboutme extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public Fragment_aboutme() {
        // Required empty public constructor

    }

    public static Fragment_aboutme newInstance(int sectionNumber) {
        Fragment_aboutme fragment = new Fragment_aboutme();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_aboutme, container, false);

        return rootView;
    }

}

