package cz.muni.fi.pv256.movio.uco_374524.superprojekt.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection.service.UpdateService;
import de.greenrobot.event.EventBus;

/**
 * Created by prasniatko on 08/12/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

  private static final String TAG = ".BaseActivity";

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBus.register(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    Intent intent = new Intent(this, UpdateService.class);
    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
  }

  @Override
  public void onStop() {
    super.onStop();
    unbindService(mConnection);
  }

  public abstract void requestCast(long movie_id);
}
