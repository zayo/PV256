package cz.muni.fi.pv256.movio.uco_374524.superprojekt.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.App;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 09/12/15.
 */
public class MovieDB {

  private static final String TAG = ".MovieDB";

  SQLiteDatabase mDatabase;

  private static MovieDB sInstance;

  public static MovieDB get() {
    return get(false);
  }

  public static MovieDB get(boolean test_variant) {
    if (sInstance == null) {
      sInstance = new MovieDB(test_variant);
    }
    return sInstance;
  }

  private MovieDB(boolean test_variant) {
    mDatabase = SQLiteDatabase.openDatabase(App.get().getDatabaseFile(test_variant), null,
      SQLiteDatabase.OPEN_READWRITE);
  }

  public ArrayList<Movie> getMovies() {
    Log.d(TAG, "getMovies() called with: " + "");
    ArrayList<Movie> list = new ArrayList<>();

    Cursor cursor = mDatabase.rawQuery("SELECT * FROM movies ORDER BY releaseDate ASC", null);
    while (cursor.moveToNext()) {
      list.add(getMovieFromCursor(cursor));
    }
    cursor.close();

    return list;
  }

  public Movie getMovie(long id) {
    Cursor cursor = mDatabase.rawQuery("SELECT * FROM movies WHERE id ==" + id, null);
    cursor.moveToFirst();
    Movie movie = getMovieFromCursor(cursor);
    cursor.close();
    return movie;
  }

  public Cursor getMoviesCursor() {
    Log.d(TAG, "getMoviesCursor() called with: " + "");
    return mDatabase.rawQuery("SELECT * FROM movies ORDER BY releaseDate ASC", null);
  }

  public Set<Long> getIds() {
    Log.d(TAG, "getIds() called with: " + "");
    Set<Long> ids = new HashSet<>();

    Cursor cursor = mDatabase.rawQuery("SELECT DISTINCT id FROM movies", null);
    while (cursor.moveToNext()) {
      ids.add(cursor.getLong(0));
    }
    cursor.close();
    return ids;
  }

  public void insert(Movie movie) {
    Log.d(TAG, "insert() called with: " + "movie = [" + movie + "]");
    ContentValues contentValues = getContentValuesFromMovie(movie);
    contentValues.put("id", movie.id);
    mDatabase.insert("movies", null, contentValues);
  }

  public void delete(Movie movie) {
    Log.d(TAG, "delete() called with: " + "movie = [" + movie + "]");
    delete(movie.id);
  }

  public void delete(long id) {
    Log.d(TAG, "delete() called with: " + "id = [" + id + "]");
    mDatabase.delete("movies", "id=" + id, null);
  }

  public void deleteAll() {
    mDatabase.execSQL("delete from movies");
  }

  public void update(Movie movie) {
    Log.d(TAG, "update() called with: " + "movie = [" + movie + "]");
    mDatabase.update("movies", getContentValuesFromMovie(movie), "id=" + movie.id, null);
  }

  public static Movie getMovieFromCursor(Cursor openedCursor) {
    Movie movie = new Movie();
    movie.id = openedCursor.getLong(0);
    movie.title = openedCursor.getString(1);
    movie.originalTitle = openedCursor.getString(2);
    movie.overview = openedCursor.getString(3);
    movie.coverPath = openedCursor.getString(4);
    movie.backdropPath = openedCursor.getString(5);
    movie.releaseDate = openedCursor.getString(6);
    movie.isHeader = false;
    return movie;
  }

  public static ContentValues getContentValuesFromMovie(Movie movie) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("backdropPath", movie.backdropPath);
    contentValues.put("coverPath", movie.coverPath);
    contentValues.put("releaseDate", movie.releaseDate);
    contentValues.put("originalTitle", movie.originalTitle);
    contentValues.put("title", movie.title);
    contentValues.put("overview", movie.overview);
    return contentValues;
  }
}
