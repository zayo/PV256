package cz.muni.fi.pv256.movio.uco_374524.superprojekt.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by prasniatko on 15/11/15.
 */
public class NetworkStateChangedReceiver extends BroadcastReceiver {

  public interface NetworkStateChanged {
    void isConnected(boolean isConnected);
  }

  private final NetworkStateChanged mListener;

  public NetworkStateChangedReceiver(NetworkStateChanged listener) {
    this.mListener = listener;
  }

  /**
   * @see BroadcastReceiver#onReceive(Context, Intent)
   */
  @Override
  public void onReceive(Context context, Intent intent) {
    boolean isConnected = isNetworkAvailable(context);
    if (mListener != null) {
      mListener.isConnected(isConnected);
    }
  }

  /**
   * Determines if there is internet access.
   *
   * @param context Application context.
   *
   * @return True if there is internet access.
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager manager = (ConnectivityManager)
      context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }
}
