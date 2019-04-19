package edu.quinnipiac.ser210.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

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
        //Hard-coded Card (TEMPORARY!)
        ShowCard card=new ShowCard("Friends","ended","https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_UY268_CR0,0,182,268_AL_.jpg"
                ,8,this);
        ln.addView(card.getCard());
    }
}
