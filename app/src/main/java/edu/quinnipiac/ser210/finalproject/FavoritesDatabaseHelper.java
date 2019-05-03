/*
The FavoritesDatabaseHelper class
By James Jacobson and Phillip Nam
5/3/19
This class handles all database related tasks, such as creating tables, adding to those tables,
    and deleting from those tables
 */

package edu.quinnipiac.ser210.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class FavoritesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="fBase";
    private static final int DB_VERSION=1;

    FavoritesDatabaseHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    //Creates 2 tables, one for actors and one for shows
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<1)
        {
            db.execSQL("CREATE TABLE FAVORITES_SHOW (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "RATING TEXT, "
                    + "IMAGE_URL TEXT, "
                    + "STATUS TEXT);");

            db.execSQL("CREATE TABLE FAVORITES_ACTOR (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "BIRTHDAY TEXT, "
                    + "DEATHDAY TEXT, "
                    + "BIRTHPLACE TEXT, "
                    + "IMAGE_URL TEXT);");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    //Saves a show into the FAVORITES_SHOW table
    protected static boolean saveShow(SQLiteDatabase db, String name,String status,String rating,String url)
    {
        //The cursor is used to check by the image url if this show already exists in the database
        Cursor cursor=db.rawQuery("SELECT NAME FROM FAVORITES_SHOW WHERE IMAGE_URL = \'" + url+"\'",null,null );
        cursor.moveToFirst();
        if(cursor.getCount()==0) {//If this entry in the database doesnt exist
            //Inserts the show into the database
            ContentValues showValues = new ContentValues();
            showValues.put("NAME", name);
            showValues.put("STATUS", status);
            showValues.put("RATING", rating);
            showValues.put("IMAGE_URL", url);
            db.insert("FAVORITES_SHOW", null, showValues);
            cursor.close();
            return true;
        }
        else
        {
            return false;
        }

    }

    //Deletes a entry from the FAVORITES_SHOW table
    protected static void deleteShow(SQLiteDatabase db,String url,Context context,ShowCard showCard)
    {
        db.execSQL("DELETE FROM FAVORITES_SHOW WHERE IMAGE_URL = \'" + url+"\'");
    }

    //Saves a show into the FAVORITES_ACTOR table
    protected static boolean saveActor(SQLiteDatabase db, String name,String birthday,String deathday,String birthplace,String url)
    {
        //The cursor is used to check by the image url if this actor already exists in the database
        Cursor cursor=db.rawQuery("SELECT NAME FROM FAVORITES_ACTOR WHERE IMAGE_URL = \'" + url+"\'",null,null );
        cursor.moveToFirst();
        if(cursor.getCount()==0) {//If the entry doesnt exist
            //Adds the actor to the database
            ContentValues actorValues = new ContentValues();
            actorValues.put("NAME", name);
            actorValues.put("BIRTHDAY", birthday);
            actorValues.put("DEATHDAY", deathday);
            actorValues.put("BIRTHPLACE", birthplace);
            actorValues.put("IMAGE_URL", url);
            db.insert("FAVORITES_ACTOR", null, actorValues);
            return true;
        }
        else//If the entry does exist
        {
            return false;
        }
    }

    //Deletes an actor from the FAVORITES_ACTOR table
    protected static void deleteActor(SQLiteDatabase db, String url)
    {
        db.execSQL("DELETE FROM FAVORITES_ACTOR WHERE IMAGE_URL = \'" + url+"\'");
    }

}
