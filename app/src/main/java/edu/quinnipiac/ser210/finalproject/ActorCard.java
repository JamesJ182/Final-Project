/*
The ActorCard Class
By James Jacobson and Phillip Nam
5/3/19
This Class represents an actor or actress when either searched for, or when they are
    in the favorites fragment. Uses a CardView
 */

package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class ActorCard {

    //Instance Variables
    //Layouts
    private CardView card;
    private TextView name;
    private TextView birthday;
    private TextView deathday;
    private TextView birthplace;
    private ImageView image;
    private Button button;
    private Button buttonShare;
    private TableLayout tableLayout;

    //The activity the card is located
    private Context context;

    public ActorCard (final String name, final String birthday, final String imageURL, final String deathday, final String birthplace, final Context context)
    {
        //Sets the layout of the card
        LinearLayout.LayoutParams cardMargins = new LinearLayout.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        cardMargins.setMargins(20,20,20,20);
        this.context=context;
        this.name=new TextView(context);
        this.name.setText(name);
        this.birthday=new TextView(context);
        this.birthday.setText(birthday);
        this.deathday=new TextView(context);
        this.deathday.setText(deathday);
        this.birthplace=new TextView(context);
        this.birthplace.setText(birthplace);
        this.image=new ImageView(context);
        new ActorCard.DownloadImageFromInternet(image).execute(imageURL);//Turns the url into a picture
        button=new Button(context);
        button.setTextSize(10);
        buttonShare=new Button(context);
        buttonShare.setTextSize(10);
        card=new CardView(context);
        card.setLayoutParams(cardMargins);
        tableLayout=new TableLayout(context);
        TableRow row1=new TableRow(context);
        TableRow row2=new TableRow(context);
        TableRow row3=new TableRow(context);
        row1.addView(this.image);
        row1.addView(this.name);
        row2.addView(this.birthday);
        row2.addView(this.deathday);
        row3.addView(this.birthplace);
        row3.addView(this.button);
        row3.addView(this.buttonShare);
        tableLayout.addView(row1);
        tableLayout.addView(row2);
        tableLayout.addView(row3);
        card.addView(tableLayout);

        //Share Show button-related code
        buttonShare.setText("Share Actor");
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                //Share the show's information with other people (using an IMPLICIT intent)
                shareIntent.putExtra(Intent.EXTRA_TEXT, "You should check out this actor: " + name + ", Birthday: " + birthday + ", Birthplace: " + birthplace);
                context.startActivity(Intent.createChooser(shareIntent, "Share Using"));
            }
        });

        //Sets the button to add this person to the database
        //Occurs when in the card is in the ShowResultsActivity
        if(this.context instanceof  ShowResultsActivity) {
            button.setText("Add to Favorites");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ActorFavoritesTask().execute(name,birthday,deathday,birthplace,imageURL);
                }
            });
        }
        else
        {
            //Sets the button to delete this person to the database
            //Occurs when in the card is in the MainActivity
            button.setText("Delete from Favorites");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Dialog box, makes sure the user wants to delete it from the database/favorites
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE://Yes
                                    new ActorFavoritesTask().execute(name,birthday,deathday,birthplace,imageURL);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE://No
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete this person from favorites?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

        }


    }

    //Getters
    public TextView getName() {
        return name;
    }

    public CardView getCard() {
        return card;
    }

    //This private class converts image urls to an actual image
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        private DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            //Sets the image
            imageView.setImageBitmap(result);

        }
    }

    //When the add/delete button is clicked, it does its appropriate action based on context
    private class ActorFavoritesTask extends AsyncTask<String,Void,Void>
    {

        private boolean sucessfullyAdded=true;//Used to check if the Actor is already in the database

        @Override
        protected Void doInBackground(String... actorDetails) {
            FavoritesDatabaseHelper fb= new FavoritesDatabaseHelper(context);
            if(context instanceof  ShowResultsActivity) {
                sucessfullyAdded=fb.saveActor(fb.getReadableDatabase(), actorDetails[0], actorDetails[1], actorDetails[2], actorDetails[3], actorDetails[4]);
            }
            else
            {
                fb.deleteActor(fb.getReadableDatabase(),actorDetails[4]);
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            getCard().setVisibility(View.GONE);//Removes the card from the view
            if(!sucessfullyAdded)
            {
                Toast toast=Toast.makeText(context,"Already in favorites",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
