package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * MainActivity class is responsible for nesting a TabLayout (containing 3 tabs)
 * and each tab nests a fragment (ShowSearchFragment, ActorSearchFragment, FavoritesFragment)
 *
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        ShowSearchFragment.OnShowSearchListener,FavoritesFragment.OnFragmentInteractionListener, ActorSearchFragment.OnActorSearchListener,
    FetchDetails.OnResultComplete{

    private final static int SHOW_FRAGMENT=0;
    private final static int ACTOR_FRAGMENT=1;
    private final static int FAVORITES_FRAGMENT=2;
    SectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); // After the Splash Screen is displayed (which takes place in MainActivity), setTheme sets the style back to the normal layout
        setContentView(R.layout.activity_main);
        TabLayout tabs=findViewById(R.id.tabs);
        ViewPager viewPager=findViewById(R.id.pager);
        adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClickShowSearch(String show) {
        //Searching for a show sets boolean = true which tells FetchDetails to grab JSON Data related to shows
        new FetchDetails(show,this).execute(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClickActorSearch(String actor) {
        //Searching for an actor sets boolean = false which tells FetchDetails to grab JSON Data related to actors
        new FetchDetails(actor,this).execute(false);
    }

    @Override
    public void startResultActivity(Intent intent) {
        startActivity(intent);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {
                case SHOW_FRAGMENT:
                    return new ShowSearchFragment();
                case ACTOR_FRAGMENT:
                    return new ActorSearchFragment();
                case FAVORITES_FRAGMENT:
                    return new FavoritesFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case SHOW_FRAGMENT:
                    return "Show";
                case ACTOR_FRAGMENT:
                    return "Actor";
                case FAVORITES_FRAGMENT:
                    return "Favorites";
            }
            return null;
        }
    }
}
