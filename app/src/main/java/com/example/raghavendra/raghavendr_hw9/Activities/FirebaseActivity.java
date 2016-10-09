package com.example.raghavendra.raghavendr_hw9.Activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.raghavendra.raghavendr_hw9.R;

import java.util.HashMap;


public class FirebaseActivity extends MainActivity implements Fragment_RecyclerView.OnEachCardSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        drawerLayout.addView(contentView, 0);

        if(savedInstanceState!=null)
        {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        else{
            mContent = Fragment_RecyclerView.newInstance(0);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mContent)
                .commit();

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mContent.isAdded())
            getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }

    @Override
    public void OnEachCardSelected(int position, HashMap<String,?> movie) {
        mContent =  Fragment_MovieDetail.newInstance(movie);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Fragment_MovieDetail.newInstance(movie))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.item2:
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
