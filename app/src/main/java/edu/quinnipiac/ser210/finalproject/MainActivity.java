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

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        ShowSearchFragment.OnShowSearchListener,FavoritesFragment.OnFragmentInteractionListener,FetchDetails.OnResultComplete{

    private final static int SHOW_FRAGMENT=0;
    private final static int ACTOR_FRAGMENT=1;
    private final static int FAVORITES_FRAGMENT=2;
    private static boolean isSearchingForShow;
    private static boolean isSearchingForActor;
    private Fragment fragAt0;
    SectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabs=findViewById(R.id.tabs);
        ViewPager viewPager=findViewById(R.id.pager);
        adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        isSearchingForActor=true;
        isSearchingForShow=true;
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
        new FetchDetails(show,this).execute(true);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //PooPoo
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
                    //return new ActorFragment();
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
