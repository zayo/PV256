package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * TODO Move to Trinerdis Utils library.
 */
@SuppressWarnings({"unused"})
public class AssetsFilesUtils {

  /**
   * Copies file/folder from assets to provided location
   *
   * @param assetManager assets manager
   * @param src Path to source file/dir
   * @param dest Path to destination dir
   */
  public static void copyFileOrDir(@NonNull AssetManager assetManager, @NonNull String src,
    @NonNull String dest) {
    String assets[];
    try {
      Log.i("tag", "copyFileOrDir() " + src);
      assets = assetManager.list(src);
      if (assets.length == 0) {
        copyFile(assetManager, src, dest);
      } else {
        String fullsrc = dest + "/" + src;
        Log.i("tag", "src=" + fullsrc);
        File dir = new File(fullsrc);
        if (!dir.exists() && !src.startsWith("images") && !src.startsWith("sounds") && !src
          .startsWith("webkit")) {
          if (!dir.mkdirs()) {
            android.util.Log.i("tag", "could not create dir " + fullsrc);
          }
        }
        for (String asset : assets) {
          String p;
          if (src.equals("")) {
            p = "";
          } else {
            p = src + "/";
          }

          if (!src.startsWith("images") && !src.startsWith("sounds") && !src
            .startsWith("webkit")) {
            copyFileOrDir(assetManager, p + asset, dest);
          }
        }
      }
    } catch (IOException ex) {
      Log.e("tag", "I/O Exception", ex);
    }
  }

  /**
   * Copies file from assets on src path to dest with src path
   *
   * @param assetManager assets manager
   * @param src Path to source file/dir
   * @param dest Path to destination dir
   */
  public static void copyFile(@NonNull AssetManager assetManager, @NonNull String src,
    @NonNull String dest) {

    InputStream in;
    OutputStream out;
    try {
      Log.i("tag", "copyFile() " + src);
      in = assetManager.open(src);
      out = new FileOutputStream(dest + "/" + src);

      byte[] buffer = new byte[1024];
      int read;
      while ((read = in.read(buffer)) != -1) {
        out.write(buffer, 0, read);
      }
      in.close();
      out.flush();
      out.close();
    } catch (Exception e) {
      Log.e("tag", "Exception in copyFile() of " + dest + "/" + src);
      Log.e("tag", "Exception in copyFile() " + e.toString());
    }
  }
}
