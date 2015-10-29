package cz.muni.fi.pv256.movio.uco_374524.superprojekt.data;

import java.util.ArrayList;
import java.util.Random;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.Config;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Actor;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 26/10/15.
 */
public class DataProvider {

    private static final String TAG = ".DataProvider";

    private static final String[] sMovies = new String[]{
        "Vykoupení z věznice Shawshank",
        "Forrest Gump",
        "Zelená míle",
        "Přelet nad kukaččím hnízdem",
        "Schindlerův seznam",
        "Sedm",
        "Kmotr",
        "Nedotknutelní",
        "Dvanáct rozhněvaných mužů",
        "Kmotr II",
        "Pelíšky",
        "Tenkrát na Západě",
        "Pulp Fiction: Historky z podsvětí",
        "Terminátor 2: Den zúčtování",
        "Mlčení jehňátek",
        "Pán prstenů: Společenstvo Prstenu",
        "Gran Torino",
        "Rivalové",
        "Pán prstenů: Návrat krále",
        "Temný rytíř"
    };

    public static final int[] sMoviesYears = new int[]{
        1994,
        1994,
        1999,
        1975,
        1993,
        1995,
        1972,
        2011,
        1957,
        1974,
        1999,
        1968,
        1994,
        1991,
        1991,
        2001,
        2008,
        2013,
        2003,
        2008
    };

    private static DataProvider sInstance;

    public static DataProvider get() {
        if (sInstance == null) {
            sInstance = new DataProvider();
        }
        return sInstance;
    }

    private ArrayList<Movie> mData;

    private DataProvider() {
        mData = generateData();
    }

    private ArrayList<Movie> generateData() {

        Random random = new Random(System.currentTimeMillis());
        int items = random.nextInt(100);

        if (Config.DEBUG) {
            boolean empty = items <= 10;
            boolean no_conn = !empty && items <= 20;

            if (no_conn) {
                return null;
            }
            if (empty) {
                return new ArrayList<>();
            }
        }

        ArrayList<Actor> actors = new ArrayList<>(2);
        actors.add(new Actor("", "Jason Clarke"));
        actors.add(new Actor("", "Tom Hanks"));

        ArrayList<Movie> data = new ArrayList<>(items);

        for (int i = 0; i < items; i++) {
            int curr = random.nextInt(sMovies.length);
            data.add(new Movie(sMoviesYears[curr], "", sMovies[curr], i % 7 == 0));
            data.get(data.size() - 1).setActors(actors);
        }

        return data;
    }

    public ArrayList<Movie> getData() {
        return mData;
    }
}
