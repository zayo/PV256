package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;

/**
 * Created by prasniatko on 07/12/15.
 */
public class UpdateEvent {

  private static final String TAG = ".UpdateEvent";

  private ArrayList<Movie> data;

  public UpdateEvent(ArrayList<Movie> data) {
    Log.d(TAG, "UpdateEvent ");
    this.data = data;
  }

  public ArrayList<Movie> getData() {
    Log.d(TAG, "getData ");
    return data;
  }
}
