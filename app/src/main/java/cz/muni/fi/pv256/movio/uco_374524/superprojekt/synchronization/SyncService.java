package cz.muni.fi.pv256.movio.uco_374524.superprojekt.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by prasniatko on 11/12/15.
 */
public class SyncService extends Service {

  private static SyncAdapter sSyncAdapter = null;

  private static final Object sSyncAdapterLock = new Object();

  /*
   * Instantiate the sync adapter object.
   */
  @Override
  public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
    synchronized (sSyncAdapterLock) {
      if (sSyncAdapter == null) {
        sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
      }
    }
  }

  /**
   * Return an object that allows the system to invoke
   * the sync adapter.
   */
  @Override
  public IBinder onBind(Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
    return sSyncAdapter.getSyncAdapterBinder();
  }
}