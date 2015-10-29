package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by prasniatko on 18/10/15.
 */
public class Movie implements Parcelable {

    public long releaseDate;
    public String coverPath;
    public String title;
    public boolean isHeader;
    public ArrayList<Actor> actors;

    public Movie(long releaseDate, String coverPath, String title, boolean isHeader) {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
        this.isHeader = isHeader;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public Movie(Parcel in) {
        releaseDate = in.readLong();
        coverPath = in.readString();
        title = in.readString();
        isHeader = in.readByte() != 0;
        actors = in.createTypedArrayList(Actor.CREATOR);
        //in.readTypedList(actors, Actor.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(releaseDate);
        dest.writeString(coverPath);
        dest.writeString(title);
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
