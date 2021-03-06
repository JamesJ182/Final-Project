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
    }

    @Override
    protected void onStart() {
        ResultFragment result=(ResultFragment)getSupportFragmentManager().getFragments().get(0);
        l = (LinearLayout) result.getView();
        lContain=(LinearLayout)findViewById(R.id.result_activity_linear);
        setBackgroundColor(MainActivity.backgroundColor);
        //Gets the results from the MainActivity search
        Intent intent = getIntent();
        showNames = intent.getStringArrayExtra("Show Titles");
        showNamesJSON = new JSONObject[showNames.length];
        String temp[] = (intent.getStringArrayE