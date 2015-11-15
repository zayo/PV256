package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prasniatko on 18/10/15.
 */
public class Movie implements Parcelable {

  @SerializedName("id")
  public long id;

  @SerializedName("release_date")
  public String releaseDate;

  @SerializedName("backdrop_path")
  public String backdropPath;

  @SerializedName("poster_path")
  public String coverPath;

  @SerializedName("title")
  public String title;

  @SerializedName("original_title")
  public String originalTitle;

  @SerializedName("overview")
  public String overview;

  public boolean isHeader = false;
  public ArrayList<Actor> actors = new ArrayList<>();

  public Movie() {
  }

  public Movie(String title, boolean isHeader) {
    this.title = title;
    this.isHeader = isHeader;
  }

  public void setActors(ArrayList<Actor> actors) {
    this.actors.addAll(actors);
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
    actors = in.createTypedArrayList(Actor.CREATOR);
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
    dest.writeTypedList(actors);
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
}
