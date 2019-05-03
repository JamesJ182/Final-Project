package edu.quinnipiac.ser210.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FavoritesDatabaseHelperTestTwo {

    private SQLiteDatabase db;
    private FavoritesDatabaseHelper fbh;


    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase("FAVORITES_SHOW");
        fbh = new FavoritesDatabaseHelper(getTargetContext());
        db=fbh.getReadableDatabase();
    }

    @Test
    public void testSaveShow() {
        String url="https://www.hellomagazine.com/imagenes/film/2017070640427/Friends-stars-then-and-now/0-211-944/friends-t.jpg";
        fbh.saveShow(db,"Friends","Ended","8.8",url);
        Cursor cursor=db.rawQuery("SELECT NAME FROM FAVORITES_SHOW WHERE IMAGE_URL = \'" + url+"\'",null,null );
        cursor.moveToFirst();
        assertEquals(1,cursor.getCount());
    }

    @After
    public void tearDown() throws Exception
    {
        db.close();
    }

}