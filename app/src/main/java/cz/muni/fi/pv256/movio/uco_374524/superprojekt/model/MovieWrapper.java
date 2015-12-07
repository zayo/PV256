package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by prasniatko on 17/11/15.
 */
public class MovieWrapper {
  @Json(name = "results")
  public List<Movie> movies;
}
