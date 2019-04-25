package edu.quinnipiac.ser210.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    public ShowCard (String name, String status, String imageURL, String rating, Context context)
    {
        LinearLayout.LayoutParams cardMargins = new LinearLayout.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
        cardMargins.setMargins(20,20,20,20);
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
        button.setText("Add to Favorites");
        card=new CardView(context);
        card.setLayoutParams(cardMargins);
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

}
