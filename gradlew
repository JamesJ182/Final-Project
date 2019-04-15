/*
The ResultActivity Class
This class displays the results of the search in a list,
 and handles the operation of the item being clicked
Date:3/21/19
Author:James Jacobson
 */
package edu.quinnipiac.ser210.serassignment33;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import javax.xml.transform.Result;

public class ResultActivity extends AppCompatActivity implements ResultFragment.OnFragmentInteractionListener {

    //Instance Variables
    String[] showNames;
    JSONObject[] showNamesJSON;
    int REQUEST_CODE = 0;
    LinearLayout l;
    LinearLayout lContain;//The layout that contains the fragment
    ListView list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Toolbar implementation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Sets the color of the background
        ResultFragment result=(ResultFragment)getSupportFragmentManager().getFragments().get(0);
        l = (LinearLayout) result.getView();
        lContain=(LinearLayout)findViewById(R.id.result_activity_linear);
        setBackgroundColor(MainActivity.backgroundColor);
        //Gets the results from the MainActivity search
        Intent intent = getIntent();
        showNames = intent.getStringArrayExtra("Show Titles");
        showNamesJSON = new JSONObject[showNames.length];
        String temp[] = (intent.getStringArrayExtra("JSON Shows"));
        try {
            for (int i = 0; i < temp.length; i++) {
                //Converts String to JSON object
                showNamesJSON[i] = new JSONObject(temp[i]);
            }
        } catch (Exception e) {
            Log.e("Error", "A show is null");
        } finally {

            //Creates an ArrayAdapter that takes the list of titles as its list

                list = (ListView) result.getListView();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, showNames);
                list.setAdapter(adapter);

        }
    }

    //Used for setting the color if this activity is returned to
    @Override
    protected void onRestart() {
        setBackgroundColor(MainActivity.backgroundColor);
        super.onRestart();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String[] items=new String[list.getAdapter().getCount()];
        for(int i=0;i<items.length;i++)
        {
            items[i]=(String)list.getAdapter().getItem(i);
        }
        outState.putStringArray("Exisiting List",items);
        super.onSaveInstanceState(outState);
    }

    //Used to change the background color
    public void setBackgroundColor(int color) {
        if (color == 0) {
           // l.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundWhite));
            lContain.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundWhite));
        } else if (color == 1) {
            //l.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundRed));
            lContain.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundRed));
        } else if (color == 2) {
            //l.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundGreen));
            lContain.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundGreen));
        } else if (color == 3) {
           // l.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundBlue));
            lContain.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundBlue));
        }
    }


    //Toolbar methods
    //Up-Button implementation
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Used after the SettingActivity has ended, changing the background color
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //Checks if the result code and request code are as expected, and if there is a color chosen
        if(resultCode == RESULT_OK  && requestCode == REQUEST_CODE&&data.hasExtra("color")){
            //changes the backgroundColor variable
            MainActivity.backgroundColor=data.getIntExtra("color",0);
            setBackgroundColor(MainActivity.backgroundColor);
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    //Creates icons 