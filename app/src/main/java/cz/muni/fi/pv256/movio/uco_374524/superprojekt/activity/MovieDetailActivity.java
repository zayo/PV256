package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service.UpdateCastEvent;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service.UpdateService;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.fragment.MovieDetailFragment;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;

/**
 * Created by prasniatko on 27/10/15.
 */
public class MovieDetailActivity extends BaseActivity {

  private static final String TAG = ".MovieDetailActivity";

  private MovieDetailFragment mMovieDetailFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    mMovieDetailFragment =
      (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.details_frag);

    mMovieDetailFragment.setMovie((Movie) getIntent().getExtras().getParcelable("movie"));
  }

  @SuppressWarnings("unused")
  public void onEvent(final UpdateCastEvent event) {
    if (event.getData() == null) {
      showNotification();
    }
    if (mMovieDetailFragment != null){
      mMovieDetailFragment.setCast(event.getData());
    }
  }

  @Override
  public void requestCast(long movie_id) {
    Intent intent = new Intent(Intent.ACTION_SYNC, null, this, UpdateService.class);
    intent.putExtra("action", "detail");
    intent.putExtra("id", movie_id);
    startService(intent);
  }

  public void showNotification(){
    NotificationCompat.Builder mBuilder =
      new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(getString(R.string.no_connection));
    mBuilder.setAutoCancel(true);
    NotificationManager mNotificationManager =
      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    // mId allows you to update the notification later on.
    mNotificationManager.notify(0, mBuilder.build());
  }
}
