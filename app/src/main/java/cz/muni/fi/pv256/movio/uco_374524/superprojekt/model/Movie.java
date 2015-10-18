package cz.muni.fi.pv256.movio.uco_374524.superprojekt.model;

/**
 * Created by prasniatko on 18/10/15.
 */
public class Movie {

    public long mReleaseDate;
    public String mCoverPath;
    public String mTitle;
    public Genre mGenre;

    public Movie(long releaseDate, String coverPath, String title, String type) {
        mReleaseDate = releaseDate;
        mCoverPath = coverPath;
        mTitle = title;
        mGenre = Genre.valueOf(type.toUpperCase());
    }

    public enum Genre {
        ACTION,
        HOROR,
        COMEDY,
        ROMANCE,
        DRAMA
    }
}
