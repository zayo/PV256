package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

/**
 * Created by prasniatko on 18/10/15.
 */
public class Movie implements Parcelable {

  @Json(name = "id")
  public long id;

  @Json(name = "release_date")
  public String releaseDate;

  @Json(name = "backdrop_path")
  public String backdropPath;

  @Json(name = "poster_path")
  public String coverPath;

  @Json(name = "title")
  public String title;

  @Json(name = "original_title")
  public String originalTitle;

  @Json(name = "overview")
  public String overview;

  public boolean isHeader = false;

  public Movie() {
  }

  public Movie(String title, boolean isHeader) {
    this.title = title;
    this.isHeader = isHeader;
  }

  public Movie(Parcel in) {
    id = in.readLong();
    releaseDate = in.readString();
    backdropPath = in.readString();
    coverPath = in.readString();
    title = in.readString();
    originalTitle = in.readString();
    overview = in.readString();
    isHeader = in.readByte() != 0;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(releaseDate);
    dest.writeString(backdropPath);
    dest.writeString(coverPath);
    dest.writeString(title);
    dest.writeString(originalTitle);
    dest.writeString(overview);
    dest.writeByte((byte) (isHeader ? 1 : 0));
  }

  public static final Parcelable.Creator<Movie> CREATOR =
    new Parcelable.Creator<Movie>() {
      public Movie createFromParcel(Parcel in) {
        return new Movie(in);
      }

      public Movie[] newArray(int size) {
        return new Movie[size];
      }
    };

  /*public String toString() {
    StringBuilder result = new StringBuilder();
    String newLine = System.getProperty("line.separator");

    result.append(this.getClass().getName());
    result.append(" Object {");
    result.append(newLine);

    //determine fields declared in this class only (no fields of superclass)
    Field[] fields = this.getClass().getDeclaredFields();

    //print field names paired with their values
    for (Field field : fields) {
      result.append("  ");
      try {
        result.append(field.getName());
        result.append(": ");
        //requires access to private field:
        result.append(field.get(this));
      } catch (IllegalAccessException ex) {
        //nothing
      }
      result.append(newLine);
    }
    result.append("}");

    return result.toString();
  }*/

  @Override
  public boolean equals(Object o) {
    if (o instanceof Movie) {
      Movie movie = (Movie) o;
      return id == movie.id && title.equalsIgnoreCase(movie.title);
    }
    return false;
  }
}
