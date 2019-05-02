package edu.quinnipiac.ser210.finalproject;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class FavoritesDatabaseHelperTest extends TestCase {

    private FavoritesDatabaseHelper db;
    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = new MainActivity();
        db = new FavoritesDatabaseHelper(mainActivity);
    }

    @Test
    public void testSaveShow() {

        @
         assertTrue(db.saveShow(db.getReadableDatabase(),"Friends", "Ended","8.5","https://www.hellomagazine.com/imagenes/film/2017070640427/Friends-stars-then-and-now/0-211-944/friends-t.jpg"));
    }

}