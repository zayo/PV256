package cz.muni.fi.pv256.movio.uco_374524.superprojekt.viewmodel;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.R;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.DataProvider;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service.UpdateEvent;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service.UpdateService;
import de.greenrobot.event.EventBus;
import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Created by prasniatko on 08/11/15.
 */
public class MoviesProvider extends AbstractViewModel<IMovie> {

  private static final String TAG = ".MoviesProvider";

  private UpdateService mService;

  private final EventBus mBus = EventBus.getDefault();

  private ServiceConnection mConnection = new ServiceConnection() {

    public void onServiceConnected(ComponentName className, IBinder binder) {
      mService = ((UpdateService.UpdateServiceBinder) binder).getService();
    }

    public void onServiceDisconnected(ComponentName className) {
      mService = null;
    }
  };

  public void requestData(String genres) {
    Context context = (Context) getView();
    Intent intent = new Intent(Intent.ACTION_SYNC, null, context, UpdateService.class);
    intent.putExtra("genres", genres);

    context.startService(intent);

    //DataProvider.get().loadData(genres, listener);
  }

  public void cancelLoading() {
    DataProvider.get().cancelLoading();
  }

  @Override
  public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
    super.onCreate(arguments, savedInstanceState);
    mBus.register(MoviesProvider.this);
  }

  @Override
  public void onModelRemoved() {
    super.onModelRemoved();
    mBus.unregister(MoviesProvider.this);
  }

  @Override
  public void onStart() {
    super.onStart();
    Context context = (Context) getView();
    Intent intent = new Intent(context, UpdateService.class);
    context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  public void onStop() {
    super.onStop();
    Context context = (Context) getView();
    context.unbindService(mConnection);
  }

  public void onEvent(final UpdateEvent event) {
    Context context = (Context) getView();
    if (context != null){
      if (event.getData() == null){
        NotificationCompat.Builder mBuilder =
          new NotificationCompat.Builder(context)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.no_connection));
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
          (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
      }
      getView().setData(event.getData());
    }
  }
}
