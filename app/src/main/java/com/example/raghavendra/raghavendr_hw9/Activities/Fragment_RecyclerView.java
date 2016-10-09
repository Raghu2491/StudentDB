package com.example.raghavendra.raghavendr_hw9.Activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.raghavendra.raghavendr_hw9.Utils.Movie;
import com.example.raghavendra.raghavendr_hw9.Adapters.MyFirebaseRecylerAdapter;
import com.example.raghavendra.raghavendr_hw9.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;


public class Fragment_RecyclerView extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    Movie movieData=new Movie();
    PullRefreshLayout layout = null;
    private MyFirebaseRecylerAdapter mRecyclerViewAdapter;
    final Firebase mRef = new Firebase("https://example007.firebaseio.com/moviedata");
    private static final String ARG_SECTION_NUMBER = "section_number";


    public interface OnEachCardSelectedListener{
        void OnEachCardSelected(int position, HashMap<String,?> movie);
    }

    OnEachCardSelectedListener mListener;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu.findItem(R.id.action_search)==null)
            inflater.inflate(R.menu.menu_search,menu);

        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if(search!=null){

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    final Query ref = mRef.orderByChild("rating").equalTo(query);
                    ref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            System.out.println(dataSnapshot.getValue());
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public Fragment_RecyclerView() {
        // Required empty public constructor

    }

    public static Fragment_RecyclerView newInstance(HashMap<String, ?> sectionNumber) {
        Fragment_RecyclerView fragment = new Fragment_RecyclerView();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment_RecyclerView newInstance(int sectionNumber) {
        Fragment_RecyclerView fragment = new Fragment_RecyclerView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener =  (OnEachCardSelectedListener)getContext();
        }
        catch(ClassCastException exception){
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setRetainInstance(true);*/
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView;
        rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerViewAdapter = new MyFirebaseRecylerAdapter(Movie.class,R.layout.fragment_cardview, MyFirebaseRecylerAdapter.MovieViewHolder.class,mRef,getActivity());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        itemAnimation();
        adapterAnimation();


        layout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerViewAdapter.notifyItemRangeChanged(0, mRecyclerViewAdapter.getItemCount());
                Toast.makeText(getContext(),"Refreshed",Toast.LENGTH_LONG).show();
                layout.setRefreshing(false);
            }
        });
        layout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);

        mRecyclerViewAdapter.setOnCardClickListener(new MyFirebaseRecylerAdapter.onCardClickListener() {
            @Override
            public void onCardClick(View view, final int position) {
                movieData = mRecyclerViewAdapter.getItem(position);

                //Another way
                //pass Movie movieData to the OnEachCardSelected as an argument and later
                //extract from the bundle in Fragment_MovieDetail
                String id =  movieData.getId();
                mRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, ?> movie = (HashMap<String, ?>) dataSnapshot.getValue();
                        mListener.OnEachCardSelected(position, movie);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

            }

            @Override
            public void onCardLongClick(View view, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));
            }

            @Override
            public void onMoreOptionsClick(View view, final int position) {
                PopupMenu popupmenu = new PopupMenu(getActivity(), view);
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                Movie clouddelete = mRecyclerViewAdapter.getItem(position);
                                mRef.child(clouddelete.getId()).removeValue();
                                return true;
                            case R.id.action_duplicate:
                                Movie cloud = mRecyclerViewAdapter.getItem(position);
                                cloud.setName(cloud.getName() + "- New");
                                cloud.setId(cloud.getId() + "_new");
                                mRef.child(cloud.getId()).setValue(cloud);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popupmenu.getMenuInflater();
                inflater.inflate(R.menu.menu_popup, popupmenu.getMenu());
                popupmenu.show();
            }
        });

        return rootView;
    }
    private void itemAnimation(){
        FlipInBottomXAnimator animator = new FlipInBottomXAnimator();
        animator.setAddDuration(300);
        animator.setRemoveDuration(300);
        mRecyclerView.setItemAnimator(animator);
    }

    private void adapterAnimation(){
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mRecyclerViewAdapter);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(alphaAdapter);
        slideAdapter.setDuration(1000);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        slideAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(slideAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerViewAdapter.cleanup();
    }

    private class ActionBarCallBack implements ActionMode.Callback {
        int position;

        public ActionBarCallBack(int position) {
            this.position = position;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_popup,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            Movie movieData = mRecyclerViewAdapter.getItem(position);
            //HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(position);
            mode.setTitle(movieData.getName()) ;
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();

            switch (id){

                case R.id.action_delete:
                    Movie clouddelete = mRecyclerViewAdapter.getItem(position);
                    mRef.child(clouddelete.getId()).removeValue();
                    return true;
                case R.id.action_duplicate:
                    Movie cloud = mRecyclerViewAdapter.getItem(position);
                    cloud.setName(cloud.getName() + "- New");
                    cloud.setId(cloud.getId() + "_new");
                    mRef.child(cloud.getId()).setValue(cloud);
                    return true;
                default:
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

}
