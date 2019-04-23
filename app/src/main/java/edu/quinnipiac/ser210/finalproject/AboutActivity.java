package edu.quinnipiac.ser210.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme); //Important! Or else splash screen will also appear (looks messy)
        setContentView(R.layout.activity_about);
    }
}
