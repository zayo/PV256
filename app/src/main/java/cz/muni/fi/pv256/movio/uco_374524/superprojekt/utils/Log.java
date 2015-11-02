package cz.muni.fi.pv256.movio.uco_374524.superprojekt.utils;

import cz.muni.fi.pv256.movio.uco_374524.superprojekt.BuildConfig;

/**
 * Android logging class wrapper to be able to disable log.
 */
public class Log {

  /* Public Constants *****************************************************************************/

    public static final int NONE = Integer.MIN_VALUE;

    public static final int ALL = Integer.MAX_VALUE;

  /* Public Static Attributes *********************************************************************/

    /**
     * Currently selected logging level.
     */
    public static int LEVEL = BuildConfig.LOGGING_ENABLED ? ALL : NONE;

  /* Public Static Methods ************************************************************************/

    /**
     * Verbose log message.
     */
    public static int v(String tag, String message) {
        if (LEVEL > NONE) {
            return android.util.Log.v(tag, message);
        }
        return 0;
    }

    /**
     * Verbose log message with exception.
     */
    public static int v(String tag, String message, Throwable exception) {
        if (LEVEL > NONE) {
            return android.util.Log.v(tag, message, exception);
        }
        return 0;
    }

    /**
     * Debug log message.
     */
    public static int d(String tag, String message) {
        if (LEVEL > NONE) {
            return android.util.Log.d(tag, message);
        }
        return 0;
    }

    /**
     * Debug log message with exception.
     */
    public static int d(String tag, String message, Throwable exception) {
        if (LEVEL > NONE) {
            return android.util.Log.d(tag, message, exception);
        }
        return 0;
    }

    /**
     * Informational log message.
     */
    public static int i(String tag, String message) {
        if (LEVEL > NONE) {
            return android.util.Log.i(tag, message);
        }
        return 0;
    }

    /**
     * Informational log message with exception.
     */
    public static int i(String tag, String message, Throwable exception) {
        if (LEVEL > NONE) {
            return android.util.Log.i(tag, message, exception);
        }
        return 0;
    }

    /**
     * Warning log message.
     */
    public static int w(String tag, String message) {
        if (LEVEL > NONE) {
            return android.util.Log.w(tag, message);
        }
        return 0;
    }

    /**
     * Warning log message with exception.
     */
    public static int w(String tag, String message, Throwable exception) {
        if (LEVEL > NONE) {
            return android.util.Log.w(tag, message, exception);
        }
        return 0;
    }

    /**
     * Error log message.
     */
    public static int e(String tag, String message) {
        if (LEVEL > NONE) {
            return android.util.Log.e(tag, message);
        }
        return 0;
    }

    /**
     * Error log message with exception.
     */
    public static int e(String tag, String message, Throwable exception) {
        if (LEVEL > NONE) {
            return android.util.Log.e(tag, message, exception);
        }
        return 0;
    }
}
