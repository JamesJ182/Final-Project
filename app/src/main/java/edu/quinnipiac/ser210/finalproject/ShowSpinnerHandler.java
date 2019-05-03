/*
The ShowSpinnerHandler Class
By James Jacobson and Phillip Nam
Date:5/3/19
This class handles the JSON strings and objects to be sent back to the MainActivity in
    a more organized manner
 */
package edu.quinnipiac.ser210.finalproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowSpinnerHandler {

    //Instance Variables
    private JSONObject[] showNamesJSON;//The JSON objects that contain the details
    private String[] showNames;//The titles of the shows
    private JSONObject[] actorNamesJSON;//The JSON objects that contain the details
    private String[] actorNames;//The titles of the shows


    public String[]createArrayOfShows(String showString) throws org.json.JSONException
    {
        JSONArray allShows=new JSONArray(showString);//The entire JSON string, converted into a JSON array
        showNamesJSON=new JSONObject[allShows.length()];
        showNames=new String[allShows.length()];
        for(int i=0;i<allShows.length();i++)
        {
            JSONObject singleShow=allShows.getJSONObject(i);//Gets a show at position i
            JSONObject showName=singleShow.getJSONObject("show");//Gets the title of the show
            showNamesJSON[i]=singleShow;//Saves that show
            showNames[i]=showName.getString("name");//Saves the shows title
        }
        return showNames;//Returns the shows title
    }

    public String[]createArrayOfActors(String actorString) throws org.json.JSONException
    {
        JSONArray allActors=new JSONArray(actorString);//The entire JSON string, converted into a JSON array
        actorNamesJSON=new JSONObject[allActors.length()];
        actorNames=new String[allActors.length()];
        for(int i=0;i<allActors.length();i++)
        {
            JSONObject singleShow=allActors.getJSONObject(i);//Gets an actor at position i
            JSONObject showName=singleShow.getJSONObject("person");//Gets the name of the actor
            actorNamesJSON[i]=singleShow;//Saves that show
            actorNames[i]=showName.getString("name");//Saves the name of that person
        }
        return actorNames;//Returns the actors names
    }

    //Gets the shows and its details as a JSONObject array
    public JSONObject[] getShowNamesJSON()
    {
        return showNamesJSON;
    }
    public JSONObject[] getActorNamesJSON()
    {
        return actorNamesJSON;
    }

}
