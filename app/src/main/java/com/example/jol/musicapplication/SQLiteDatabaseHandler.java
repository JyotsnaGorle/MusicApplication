package com.example.jol.musicapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static SQLiteDatabaseHandler sInstance;

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "MyMusicDB";
    private static final String TABLE_NAME = "MyMusic";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LISTENERS = "listeners";
    private static final String KEY_URL = "url";
    private static final String KEY_IMAGE = "image";
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_LISTENERS,
            KEY_URL, KEY_IMAGE};

    public static synchronized SQLiteDatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SQLiteDatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE MyMusic ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "listeners TEXT, " + "url TEXT," + " image BLOB)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }


    public ArrayList<Artist> allMyMusic() {

        ArrayList<Artist> artists = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Artist artist = null;

        if (cursor.moveToFirst()) {
            do {
                byte[] imageBytes = cursor.getBlob(4);
                artist = new Artist(Integer.parseInt(cursor.getString(0)),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                artists.add(artist);
            } while (cursor.moveToNext());
        }

        return artists;
    }

    public void saveArtist(Artist artist) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, artist.name);
        values.put(KEY_LISTENERS, artist.listeners);
        values.put(KEY_URL, artist.url);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        artist.image.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer=out.toByteArray();
        // insert
        values.put(KEY_IMAGE, buffer);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

}
