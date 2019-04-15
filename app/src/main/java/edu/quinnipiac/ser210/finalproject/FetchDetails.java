package edu.quinnipiac.ser210.finalproject;

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


public class FetchDetails extends AsyncTask<Boolean,Void,Void> {

    private String search;
    private final String PART_OF_URL = "https://tvjan-tvmaze-v1.p.rapidapi.com/search/shows?q=";
    private String[] showNames;
    private String[] showNameJSON;

    public FetchDetails(String str)
    {
        this.search=str;
    }

    @Override
    protected Void doInBackground(Boolean... booleans) {
        boolean isShow=booleans[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        if(isShow)
        {
            try {
                URL url = new URL(PART_OF_URL + search);//Creates complete search URL

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
                Log.e("Error",e.getMessage());
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
        else
        {
            //isActor
            //To be implemented
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
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

    public String[] getShowNames()
    {
        return showNames;
    }

    public String[] getShowNamesInJSON()
    {
        return showNameJSON;
    }



}
