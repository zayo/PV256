package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.App;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 26/10/15.
 */
@SuppressWarnings("ALL")
public class DataProvider {

  public interface DataLoaded {
    void onDataLoaded(ArrayList<Movie> data);
  }

  private static final String TAG = ".DataProvider";

  public static final String BASE_URL = "https://api.themoviedb.org/3";

  public static final String API_KEY = "d88bb4e2a8849bfcb805364c6f7696ed";

  private static final String[] CATEGORIES = new String[]{
    "Tento tyden",
    "Tento mesic",
    "Tento rok"
  };

  private static final String[] CAT_URLS = new String[]{
    new DiscoverHelper.Builder(BASE_URL, API_KEY).setDateFrom(System.currentTimeMillis())
      .setDateTo(System.currentTimeMillis(), 7).build(),
    new DiscoverHelper.Builder(BASE_URL, API_KEY).setDateFrom(System.currentTimeMillis())
      .setDateTo(System.currentTimeMillis(), 31).build(),
    new DiscoverHelper.Builder(BASE_URL, API_KEY).setDateFrom(System.currentTimeMillis())
      .setDateTo(System.currentTimeMillis(), 365).build(),
  };

  private static DataProvider sInstance;

  public static DataProvider get() {
    if (sInstance == null) {
      sInstance = new DataProvider();
    }
    return sInstance;
  }

  private ArrayList<Movie> mCacheData;
  private ArrayList<Movie> mNetworkData;

  private LoadMovies mLoader;

  private OkHttpClient mClient;

  private DataProvider() {
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(App.get().getCacheDir(), cacheSize);

    mClient = new OkHttpClient();
    mClient.setCache(cache);
  }

  public void cancelLoading() {
    if (mLoader != null) {
      mLoader.cancel(true);
    }
    mLoader = null;
  }

  public void loadData(boolean force_network, DataLoaded listener) {
    cancelLoading();
    mLoader = new LoadMovies(force_network, listener);
    mLoader.execute(CAT_URLS);
  }

  private class LoadMovies extends AsyncTask<String, Void, ArrayList<Movie>> {

    private boolean force_network;
    private DataLoaded listener;

    public LoadMovies(boolean force_network, DataLoaded listener) {
      this.force_network = force_network;
      this.listener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
      ArrayList<Movie> list = new ArrayList<>(params.length * 7);

      for (int i = 0, len = params.length; i < len; i++) {
        if (isCancelled()) {
          return null;
        }

        try {
          Log.d(TAG, "doInBackground() returned: " + params[i]);
          Request req = new Request.Builder()
            .url(params[i])
            .header("Accept", "application/json")
            .build();

          Response response = mClient.newCall(req).execute();

          String response_body;
          if (force_network) {
            mClient.getCache().evictAll();
          }
          response_body = response.body().string();
          response.body().close();

          list.addAll(parseJson(i, response_body));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      return list;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
      mNetworkData = movies;
      if (listener != null) {
        listener.onDataLoaded(mNetworkData);
      }
    }
  }

  private ArrayList<Movie> parseJson(int cat_index, String json) {
    ArrayList<Movie> list = new ArrayList<>(7);
    try {
      Movie[] movies;
      movies = new Gson().fromJson(json, PageWrapper.class).movies;
      list.add(new Movie(CATEGORIES[cat_index], true));
      for (int j = 0, size = movies.length > 6 ? 6 : movies.length; j < size; j++) {
        list.add(movies[j]);
      }
    } catch (Exception e) {
    }
    return list;
  }

  private class PageWrapper {
    @SerializedName("results")
    Movie[] movies;
  }
}
