package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import com.squareup.moshi.Json;

/**
 * Created by prasniatko on 16/11/15.
 */
public class Genre {
  @Json(name = "id")
  public long id;

  @Json(name = "name")
  public String name;

  public boolean isChecked;

  @Override
  public String toString() {
    return name;
  }
}
