/*
The MainActivity Class
By James Jacobson and Phillip Nam
5/3/19
This class contains all of the fragments, and can start the ShowResultsActivity
 */

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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ShowSearchFragment.OnShowSearchListener,FavoritesFragment.OnFragmentInteractionListener, ActorSearchFragment.OnActorSearchListener,
    FetchDetails.OnResultComplete{

    private final static int SHOW_FRAGMENT=0;
    private final static int ACTOR_FRAGMENT=1;
    private final static int FAVORITES_FRAGMENT=2;
    SectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); // After the splash_3 is displayed (which takes place in MainActivity), setTheme sets the style back to the normal layout
        setContentView(R.layout.activity_main);

        //Gets the tab layout and the view pager, and adds the fragments to them
        TabLayout tabs=findViewById(R.id.tabs);
        ViewPager viewPager=findViewById(R.id.pager);
        adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        //Sets the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    //Creates a toast if there were no results found
    public void displayNoResultsFound()
    {
        Toast toast=Toast.makeText(this,"No Results Found",Toast.LENGTH_SHORT);
        toast.show();
    }

    //Adds the toolbar icons to the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Gives the toolbar icons functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about://When the about icon is clicked
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.share_app://When the share app icon is click
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                //A message that shares the Show Guru app with other people (using an IMPLICIT intent)
                shareIntent.putExtra(Intent.EXTRA_TEXT,"You should check out this awesome app called Show Guru!");
                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //When the button is clicked to search for a show, begin the search
    @Override
    public void onClickShowSearch(String show) {
        //Searching for a show sets boolean = true which tells FetchDetails to grab JSON Data related to shows
        new FetchDetails(show,this).execute(true);
    }

    //Has to implement this
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //When the button is clicked to search for an actor, begin the search
    @Override
    public void onClickActorSearch(String actor) {
        //Searching for an actor sets boolean = false which tells FetchDetails to grab JSON Data related to actors
        new FetchDetails(actor,this).execute(false);
    }

    //Starts the ShowResultsActivity with an intent from FetchDetails
    @Override
    public void startResultActivity(Intent intent) {
        startActivity(intent);
    }

    //Serves as an adapter for the ViewPager and places the correct Fragments in each tab.
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        //Returns the corresponding Fragment for each tab
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
        //Determines how many tabs will be in the TabLayout
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        //Returns the title of a Fragment
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
