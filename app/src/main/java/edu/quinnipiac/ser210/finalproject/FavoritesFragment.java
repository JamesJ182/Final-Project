package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The FavoritesFragment class
 * By James Jacobson and Phillip Nam
 * 5/3/19
 * This class is responsible for accessing the user's favorites through the use of local database.
 * Contains a linear layout that will hold multiple CardViews
 */


public class FavoritesFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<ShowCard> showCards;//All of the shows in favorites
    private ArrayList<ActorCard> actorCards;//All of the actors in favorites

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment layout inflated
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //Adds all of the favorites to the tab
    @Override
    public void onStart() {
        LinearLayout ln=(LinearLayout)getView().findViewById(R.id.favorites_linear_layout);
        ln.removeAllViews();//Needed to remove all original CardViews from the fragment upon inflating again
        new FetchFavoritesFromDatabase().execute();
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //Retrieves the favorites from each table
    private class FetchFavoritesFromDatabase extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            //Gets Prepared to retrieve the data
            FavoritesDatabaseHelper fb=new FavoritesDatabaseHelper(getContext());
            SQLiteDatabase db=fb.getReadableDatabase();
            //Gets all of the favorite shows
            Cursor cursorShow=db.rawQuery("SELECT * FROM FAVORITES_SHOW",null);
            cursorShow.moveToFirst();
            showCards=new ArrayList<ShowCard>();
            //Adds all of the favorite shows as its ShowCard to an arraylist
            while(!cursorShow.isAfterLast()) {
                String name = cursorShow.getString(1);
                String status = cursorShow.getString(2);
                String url = cursorShow.getString(3);
                String rating = cursorShow.getString(4);
                ShowCard showCard = new ShowCard(name, status, url, rating, getContext());
                showCards.add(showCard);
                cursorShow.moveToNext();
            }
            cursorShow.close();
            //Repeats this process with the actors
            Cursor cursorActor=db.rawQuery("SELECT * FROM FAVORITES_ACTOR",null);
            cursorActor.moveToFirst();
            actorCards=new ArrayList<ActorCard>();//Check this, this might crash everything
            while(!cursorActor.isAfterLast()) {
                String name = cursorActor.getString(1);
                String birthday = cursorActor.getString(2);
                String url = cursorActor.getString(5);
                String deathday = cursorActor.getString(3);
                String birthplace = cursorActor.getString(4);
                ActorCard actorCard = new ActorCard(name, birthday,url,deathday,birthplace, getContext());
                actorCards.add(actorCard);
                cursorActor.moveToNext();
            }
            cursorActor.close();
            db.close();
            return null;
        }

        //Adds these CardViews into the FavoritesFragment layout
        @Override
        protected void onPostExecute(Void aVoid) {
            LinearLayout ln=(LinearLayout) getView().findViewById(R.id.favorites_linear_layout);
            //Makes a textview that sections off the Shows from the Actors
            TextView divider=new TextView(getContext());
            divider.setText("Shows");
            divider.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            divider.setGravity(Gravity.CENTER_HORIZONTAL);
            divider.setTextSize(30);
            ln.addView(divider);
            //Adds the CardViews representing shows into the layout
            for(int i=0;i<showCards.size();i++)
            {
                ln.addView(showCards.get(i).getCard());
            }
            //Sections off the Actors with a textview, repeats same process as above with the Actor CardViews
            TextView divider2=new TextView(getContext());
            divider2.setText("People");
            divider2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            divider2.setGravity(Gravity.CENTER_HORIZONTAL);
            divider2.setTextSize(30);
            ln.addView(divider2);
            for(int i=0;i<actorCards.size();i++)
            {
                ln.addView(actorCards.get(i).getCard());
            }
            super.onPostExecute(aVoid);
        }
    }



}
