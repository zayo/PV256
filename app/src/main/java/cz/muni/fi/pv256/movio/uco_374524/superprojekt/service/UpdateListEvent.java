package cz.muni.fi.pv256.movio.uco_374524.superprojekt.service;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.HeaderArrayList;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;

/**
 * Created by prasniatko on 07/12/15.
 */
public class UpdateListEvent {

  private static final String TAG = ".UpdateListEvent";

  private HeaderArrayList<Movie> data;

  public UpdateListEvent(HeaderArrayList<Movie> data) {
    Log.d(TAG, "UpdateListEvent ");
    this.data = data;
  }

  public HeaderArrayList<Movie> getData() {
    Log.d(TAG, "getData ");
    return data;
  }
}
