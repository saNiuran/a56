package my.exception.tools;


import my.exception.exception.RecoveryException;

/**
 * Created by zgxiaoyong on 16/8/28.
 */
public class DefaultHandlerUtil {

    private DefaultHandlerUtil() {
        throw new RecoveryException("Stub!");
    }

    private static Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        Object object = my.exception.tools.Reflect.on("com.android.internal.os.RuntimeInit$UncaughtHandler").constructor().newInstance();
        return object == null ? null : (Thread.UncaughtExceptionHandler) object;
    }

    public static boolean isSystemDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        Thread.UncaughtExceptionHandler defHandler = getDefaultUncaughtExceptionHandler();
        return defHandler != null && defHandler.getClass().isInstance(handler);
    }

}
