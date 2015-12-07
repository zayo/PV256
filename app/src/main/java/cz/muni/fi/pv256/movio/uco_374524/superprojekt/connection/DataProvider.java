package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection;

import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.moshi.MoshiConverterFactory;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.GenresWrapper;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.MovieWrapper;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.DateUtils;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by prasniatko on 16/11/15.
 */
public class DataProvider {

  public interface DataLoaded {
    void onDataLoaded(ArrayList<Movie> data);
  }

  public static final String BASE_URL = "https://api.themoviedb.org";

  public static final String API_KEY = "d88bb4e2a8849bfcb805364c6f7696ed";

  private static DataProvider mInstance;

  public static DataProvider get() {
    if (mInstance == null) {
      mInstance = new DataProvider();
    }
    return mInstance;
  }

  private TheMovieDatabaseService mService;

  private Call<MovieWrapper> mDataRequest;

  private DataProvider() {

    OkHttpClient client = new OkHttpClient();
    client.interceptors().add(new Interceptor() {

      private static final String TAG = ".DataProvider";

      @Override
      public com.squareup.okhttp.Response intercept(Interceptor.Chain chain) throws IOException {

        Log.d(TAG, "intercept() returned: " + chain.request().url());

        return chain.proceed(chain.request());
      }
    });

    Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create())
      .build();

    mService = retrofit.create(TheMovieDatabaseService.class);
  }

  public void cancelLoading() {
    if (mDataRequest != null) {
      mDataRequest.cancel();
    }
  }

  public void loadData(String genreIdString, DataLoaded listener) {
    cancelLoading();

    final ResultDeliverBarrier barrier = new ResultDeliverBarrier(listener);

    long milis_now = System.currentTimeMillis();
    long milis_7days = milis_now + DateUtils.getMilis(DateUtils.Type.DAY, 7);
    long milis_31days = milis_now + DateUtils.getMilis(DateUtils.Type.DAY, 31);
    long milis_365days = milis_now + DateUtils.getMilis(DateUtils.Type.DAY, 365);

    mService.getMovies(
      API_KEY,
      "popularity.desc",
      Uri.encode(genreIdString),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_now),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_7days),
      Locale.getDefault().getLanguage()
    ).enqueue(
      new Callback<MovieWrapper>() {
        @Override
        public void onResponse(Response<MovieWrapper> response,
          Retrofit retrofit) {
          barrier.addList1(response.body().movies);
        }

        @Override
        public void onFailure(Throwable t) {
          barrier.addList1(null);
        }
      });

    mService.getMovies(
      API_KEY,
      "popularity.desc",
      Uri.encode(genreIdString),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_7days),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_31days),
      Locale.getDefault().getLanguage()
    ).enqueue(
      new Callback<MovieWrapper>() {
        @Override
        public void onResponse(Response<MovieWrapper> response,
          Retrofit retrofit) {
          barrier.addList2(response.body().movies);
        }

        @Override
        public void onFailure(Throwable t) {
          barrier.addList2(null);
        }
      });

    mService.getMovies(
      API_KEY,
      "popularity.desc",
      Uri.encode(genreIdString),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_31days),
      DateUtils.format(DateUtils.DEFAULT_DAY, milis_365days),
      Locale.getDefault().getLanguage()
    ).enqueue(
      new Callback<MovieWrapper>() {
        @Override
        public void onResponse(Response<MovieWrapper> response,
          Retrofit retrofit) {
          barrier.addList3(response.body().movies);
        }

        @Override
        public void onFailure(Throwable t) {
          barrier.addList3(null);
        }
      });
  }

  public void loadGenres(Callback<GenresWrapper> callback) {
    mService.getGenres(API_KEY, Locale.getDefault().getLanguage()).enqueue(callback);
  }

  private interface TheMovieDatabaseService {

    @GET("/3/genre/movie/list")
    Call<GenresWrapper> getGenres(
      @Query("api_key") String api_key,
      @Query("language") String lang
    );

    @GET("/3/discover/movie")
    Call<MovieWrapper> getMovies(
      @Query("api_key") String api_key,
      @Query("sort_by") String sort,
      @Query("with_genres") String genres,
      @Query("primary_release_date.gte") String dateFrom,
      @Query("primary_release_date.lte") String dateTo,
      @Query("language") String lang
    );
  }
}
