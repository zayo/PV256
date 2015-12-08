package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Cast;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;

/**
 * Created by prasniatko on 07/12/15.
 */
public class UpdateCastEvent {

  private static final String TAG = ".UpdateCastEvent";

  private ArrayList<Cast> data;

  public UpdateCastEvent(ArrayList<Cast> data) {
    Log.d(TAG, "UpdateCastEvent ");
    this.data = data;
  }

  public ArrayList<Cast> getData() {
    Log.d(TAG, "getData ");
    return data;
  }
}
