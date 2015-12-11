package cz.muni.fi.pv256.movio.uco_374524.superprojekt;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by prasniatko on 21/09/15.
 */
public class App extends Application {

  private static App mInstance;

  public static App get() {
    return mInstance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mInstance = this;

    if (Config.STRICT_MODE_ENABLED) {
      initStrictMode();
    }
  }

  private void initStrictMode() {
    StrictMode.ThreadPolicy.Builder tpb = new StrictMode.ThreadPolicy.Builder()
      .detectAll()
      .penaltyLog();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      tpb.penaltyFlashScreen();
    }
    StrictMode.setThreadPolicy(tpb.build());

    StrictMode.VmPolicy.Builder vmpb = new StrictMode.VmPolicy.Builder()
      .detectLeakedSqlLiteObjects()
      .penaltyLog();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      vmpb.detectLeakedClosableObjects();
    }
    StrictMode.setVmPolicy(vmpb.build());
  }
}
