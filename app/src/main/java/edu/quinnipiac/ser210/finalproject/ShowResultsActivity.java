package edu.quinnipiac.ser210.finalproject;

import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Integer.parseInt;

/**
 * This activity appears after a user searches for a show.
 * This Activity is a linear layout which contains multiple CardViews related to the TV Shows.
 */

public class ShowResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_show_results);
        //CardViews are organized in a linear layout
        LinearLayout ln=(LinearLayout)findViewById(R.id.linear_layout_result);
        if(getIntent().getBooleanExtra("isShow",true)) {
            String[] JSONStrings = getIntent().getStringArrayExtra("JSON Strings");
            for (String temp : JSONStrings) {
                try {
                    Log.e("in show result", "In for");
                    JSONObject temp2 = new JSONObject(temp);
                    JSONObject show = temp2.getJSONObject("show");
                    Log.e("in show result", "made show");
                    String showName = show.getString("name");
                    String rating = show.getJSONObject("rating").getString("average");
                    String status = show.getString("status");
                    String premierd = show.getString("premiered");//Unused as of now
                    String runTime = show.getString("runtime");//Unused as of now
                    String image = show.getJSONObject("image").getString("medium");
                    ShowCard card = new ShowCard(showName, status, image, rating, this);
                    ln.addView(card.getCard());
                } catch (JSONException e) {
                    Log.e("in show result", "ERROR");
                    e.printStackTrace();
                }

            }
        }
        else
        {
            String[] JSONStrings = getIntent().getStringArrayExtra("JSON Actor Strings");
            for (String temp : JSONStrings) {
                try {
                    JSONObject temp2 = new JSONObject(temp);
                    JSONObject actor = temp2.getJSONObject("person");
                    Log.e("in show result", "made show");
                    String actorName = actor.getString("name");
                    String birthday = actor.getString("birthday");
                    String deathday = actor.getString("deathday");
                    if(deathday.equals("null"))
                    {
                        deathday="Alive";
                    }
                    String birthplace=actor.getJSONObject("country").getString("name");
                    String image = actor.getJSONObject("image").getString("medium");
                    ActorCard card = new ActorCard(actorName,birthday,image,deathday,birthplace,this);
                    ln.addView(card.getCard());
                } catch (JSONException e) {
                    Log.e("in show result", "ERROR");
                    e.printStackTrace();
                }

            }

        }
        //Hard-coded Card (TEMPORARY!)



    }
}
