/*
The FetchDetails Class
By James Jacobson and Phillip Nam
5/3/19
This class retrieves the JSON from the API. This data contains Shows and Actors
 */


package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The FetchDetails class works in 2 ways (based on a boolean value).
 * If isShow is true, the AsyncTask will retrieve JSON data from the API related to TV Shows.
 * If isShow is false, the AsyncTask will retrieve JSON data from the API related to Actors (to be implemented).
 */

public class FetchDetails extends AsyncTask<Boolean,Void,Void> {

    //Instance Variables
    private String search;
    private final String PART_OF_URL_SHOW = "https://tvjan-tvmaze-v1.p.rapidapi.com/search/shows?q=";//URL for show query
    private final String PART_OF_URL_ACTOR = "https://tvjan-tvmaze-v1.p.rapidapi.com/search/people?q=";//URL for actor query
    private String[] showNames;//Strings of the NAMES of the shows
    private String[] showNameJSON;//Strings of the shows details in JSON format
    private String[] actorNames;//Strings of the NAMES of the actors
    private String[] actorNameJSON;//Strings of the actors details in JSON format
    private Context context;
    private boolean isShow;
    private boolean resultFound;
    private OnResultComplete resultListener;//A Listener that sends a message once the result is found

    public FetchDetails(String str, Context context)
    {
        this.search=str;
        this.context=context;
        resultFound=true;
    }

    @Override
    protected Void doInBackground(Boolean... booleans) {
        //Have to replace all spaces with %20
        while(search.contains(" "))
        {
            search=search.replace(" ","%20");
        }
        isShow=booleans[0];//1 boolean variable representing a show or actor

        //Connects to the API
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        if(isShow)//Connects to the show API if its a show
        {
            //Tries connecting
            try {
                URL url = new URL(PART_OF_URL_SHOW + search);//Creates complete search URL

                //Opens connection to API
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "2cf6f93237mshc56bd9d910ab8ffp137878jsn13d36c0c4b24");
                urlConnection.connect();

                //Gets JSON from API
                InputStream in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                //Gets data into a JSON string, then goes to the handler to receive JSON in an organized, useful way
                String showJsonString = getBufferStringFromBuffer(reader).toString();
                ShowSpinnerHandler SSH=new ShowSpinnerHandler();

                //Handler gets the name of the shows, and the JSON Object which contain the details
                showNames=SSH.createArrayOfShows(showJsonString);
                showNameJSON=new String[showNames.length];
                JSONObject[] temp=SSH.getShowNamesJSON();
                for(int i=0;i<temp.length;i++)
                {
                    //Converts the Json objects to Strings
                    showNameJSON[i]=temp[i].toString();
                }




            } catch (Exception e) {
                resultFound=false;// no result was found or nothing was entered in
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        return null;
                    }
                }
            }
        }
        else//is an actor
        {
            try {
                URL url = new URL(PART_OF_URL_ACTOR + search);//Creates complete search URL

                //Opens connection to API
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-RapidAPI-Key", "2cf6f93237mshc56bd9d910ab8ffp137878jsn13d36c0c4b24");
                urlConnection.connect();

                //Gets JSON from API
                InputStream in = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));

                //Gets data into a JSON string, then goes to the handler to receive JSON in an organized, useful way
                String actorJsonString = getBufferStringFromBuffer(reader).toString();
                ShowSpinnerHandler SSH=new ShowSpinnerHandler();

                //Handler gets the name of the actors, and the JSON Object which contain the details
                actorNames=SSH.createArrayOfActors(actorJsonString);
                actorNameJSON=new String[actorNames.length];
                JSONObject[] temp=SSH.getActorNamesJSON();
                for(int i=0;i<temp.length;i++)
                {
                    //Converts the Json objects to Strings
                    actorNameJSON[i]=temp[i].toString();
                }




            } catch (Exception e) {
                resultFound=false;
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent intent=new Intent(context,ShowResultsActivity.class);
        if(resultFound) {//If a result was found
            if (isShow) {//If the result was shows
                //Storing information in the intent to send the list of shows to the ShowResultsActivity
                intent.putExtra("isShow", true);
                intent.putExtra("Show Names", showNames);
                intent.putExtra("JSON Strings", showNameJSON);
            } else {
                //Storing information in the intent to send the list of actors to the ShowResultsActivity
                intent.putExtra("isShow", false);
                intent.putExtra("Actor Names", actorNames);
                intent.putExtra("JSON Actor Strings", actorNameJSON);
            }
            if (context instanceof OnResultComplete) {
                resultListener = (OnResultComplete) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnResultListener");
            }
            resultListener.startResultActivity(intent);//Tells the activity to star the result activity,
                                                            // since this class can not do that
        }
        else//If there was no result
        {
            MainActivity activity=(MainActivity)context;
            activity.displayNoResultsFound();
        }
        super.onPostExecute(aVoid);
    }

    private StringBuffer getBufferStringFromBuffer(BufferedReader br) throws Exception{
        StringBuffer buffer = new StringBuffer();

        String line;
        while((line = br.readLine()) != null){
            buffer.append(line + '\n');
        }

        if (buffer.length() == 0)
            return null;

        return buffer;
    }

    //After retrieving information, the data gets sent to the Result Activity.
    public interface OnResultComplete
    {
        void startResultActivity(Intent intent);
    }




}
