package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

public class ShowCard{
    private CardView card;
    private TextView name;
    private TextView rating;
    private TextView status;
    private ImageView image;
    private Button button;
    private TableLayout tableLayout;
    private Context context;

    public ShowCard (final String name, final String status, final String imageURL, final String rating, final Context context)
    {
        LinearLayout.LayoutParams cardMargins = new LinearLayout.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        cardMargins.setMargins(20,20,20,20);
        this.context=context;
        this.name=new TextView(context);
        this.name.setText(name);
        this.rating=new TextView(context);
        this.rating.setText(rating);
        this.status=new TextView(context);
        this.status.setText(status);
        this.image=new ImageView(context);
        new DownloadImageFromInternet(image).execute(imageURL);
        button=new Button(context);
        button.setTextSize(8);
        card=new CardView(context);
        card.setLayoutParams(cardMargins);
        this.name.setId(R.id.show_text_debug);
        tableLayout=new TableLayout(context);
        TableRow row1=new TableRow(context);
        TableRow row2=new TableRow(context);
        TableRow row3=new TableRow(context);
        row1.addView(this.image);
        row1.addView(this.name);
        row2.addView(this.rating);
        row2.addView(this.status);
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
                    new ShowFavoritesTask().execute(name, status, rating, imageURL);
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
                                    new ShowFavoritesTask().execute(name, status, rating, imageURL);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want delete this show from favorites?").setPositiveButton("Yes", dialogClickListener)
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

    public TextView getRating() {
        return rating;
    }

    public TextView getStatus() {
        return status;
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
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);

        }
    }

    private class ShowFavoritesTask extends AsyncTask<String,Void,Void>
    {

        private boolean sucessfullyAdded=true;
        @Override
        protected Void doInBackground(String... showDetails) {
                FavoritesDatabaseHelper fb= new FavoritesDatabaseHelper(context);
                if(context instanceof ShowResultsActivity) {
                    sucessfullyAdded=fb.saveShow(fb.getReadableDatabase(), showDetails[0], showDetails[1], showDetails[2], showDetails[3]);
                }
                else
                {
                    fb.deleteShow(fb.getReadableDatabase(), showDetails[3],context,ShowCard.this);
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
