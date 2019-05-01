package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.DialogInterface;
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

    private CardView card;
    private TextView name;
    private TextView birthday;
    private TextView deathday;
    private TextView birthplace;
    private ImageView image;
    private Button button;
    private TableLayout tableLayout;
    private Context context;

    public ActorCard (final String name, final String birthday, final String imageURL, final String deathday, final String birthplace, final Context context)
    {
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
        new ActorCard.DownloadImageFromInternet(image).execute(imageURL);
        button=new Button(context);
        Button shareButton=new Button(context);
        button.setTextSize(8);
        button.setText("Add to Favorites");
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
        tableLayout.addView(row1);
        tableLayout.addView(row2);
        tableLayout.addView(row3);
        card.addView(tableLayout);

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
            button.setText("Delete from Favorites");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    new ActorFavoritesTask().execute(name,birthday,deathday,birthplace,imageURL);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

        }


    }

    public TextView getName() {
        return name;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getBirthday() {
        return birthday;
    }

    public TextView getBirthplace() {
        return birthplace;
    }
    public TextView getDeathday() {
        return deathday;
    }
    public CardView getCard() {
        return card;
    }

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
            imageView.setImageBitmap(result);

        }
    }


    private class ActorFavoritesTask extends AsyncTask<String,Void,Void>
    {

        private boolean sucessfullyAdded=true;
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
            getCard().setVisibility(View.GONE);
            if(!sucessfullyAdded)
            {
                Toast toast=Toast.makeText(context,"Already in favorites",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
