/*
The AboutActivity Class
By James Jacobson and Phillip Nam
5/3/19
This class is responsible for telling the user how to use the app, the creators
    and how to contact them
 */



package edu.quinnipiac.ser210.finalproject;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); //Important! Or else splash screen will also appear (looks messy)
        setContentView(R.layout.activity_about);
        //Implements the toolbar and the up button
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }
}
