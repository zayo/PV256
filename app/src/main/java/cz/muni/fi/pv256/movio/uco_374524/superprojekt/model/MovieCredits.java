package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by prasniatko on 08/12/15.
 */
public class MovieCredits {

  @Json(name="id")
  public long id;

  @Json(name = "cast")
  public List<Cast> cast;
}
