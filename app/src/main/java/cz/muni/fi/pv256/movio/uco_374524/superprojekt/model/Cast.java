package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import com.squareup.moshi.Json;

/**
 * Created by prasniatko on 08/12/15.
 */
public class Cast {

  @Json(name="cast_id")
  public long cast_id;

  @Json(name="character")
  public String character;

  @Json(name="id")
  public long id;

  @Json(name="name")
  public String name;

  @Json(name="order")
  public long order;

  @Json(name="profile_path")
  public String image;
}
