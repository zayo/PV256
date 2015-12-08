package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;

/**
 * Created by prasniatko on 07/12/15.
 */
public class UpdateListEvent {

  private static final String TAG = ".UpdateListEvent";

  private ArrayList<Movie> data;

  public UpdateListEvent(ArrayList<Movie> data) {
    Log.d(TAG, "UpdateListEvent ");
    this.data = data;
  }

  public ArrayList<Movie> getData() {
    Log.d(TAG, "getData ");
    return data;
  }
}
