package cz.muni.fi.pv256.movio.uco_374524.superprojekt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.database.MovieContract.MovieEntry;

/**
 * Created by prasniatko on 11/12/15.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

  public static final String DATABASE_NAME = "movies.db";
  private static final int DATABASE_VERSION = 1;

  public MovieDBHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
      MovieEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
      MovieEntry.COLUMN_TITLE + " TEXT, " +
      MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
      MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
      MovieEntry.COLUMN_COVER_PATH + " TEXT, " +
      MovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
      MovieEntry.COLUMN_RELEASE_DATE + " TEXT " +
      " );";
    db.execSQL(SQL_CREATE_LOCATION_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
    onCreate(db);
  }
}