package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.DataProvider;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.NetworkStateChangedReceiver;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.model.Movie;
import cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils.Log;
import de.greenrobot.event.EventBus;

/**
 * Created by prasniatko on 07/12/15.
 */
public class UpdateService extends IntentService {

  private static final String TAG = ".UpdateService";

  private final IBinder mBinder = new UpdateServiceBinder();

  private final EventBus mBus = EventBus.getDefault();

  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind ");
    return mBinder;
  }

  public class UpdateServiceBinder extends Binder {
    public UpdateService getService() {
      Log.d(TAG, "getService ");
      return UpdateService.this;
    }
  }

  @Override
  protected void onHandleIntent(Intent workIntent) {
    Log.d(TAG, "onHandleIntent ");
    String dataString = workIntent.getStringExtra("genres");
    DataProvider.get().loadData(dataString, new DataProvider.DataLoaded() {
      @Override
      public void onDataLoaded(ArrayList<Movie> data) {
        mBus.removeAllStickyEvents();
        if (NetworkStateChangedReceiver.isNetworkAvailable(UpdateService.this)) {
          mBus.postSticky(new UpdateEvent(data));
        } else {
          mBus.postSticky(new UpdateEvent(null));
        }
      }
    });
  }

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public UpdateService() {
    super(TAG);
    Log.d(TAG, "UpdateService ");
  }
}
