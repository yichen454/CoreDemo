package com.eason.core.utils;

import android.util.Log;

/**
 * Created by Chen on 2019-08-15
 */
public class LogUtils {

    public static boolean DEBUG = true;
    private static final String DEFAULT_TAG = "LOG";

    public static void i(String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void i(String tag, String message, String simpleName) {
        if (DEBUG) {
            Log.i(tag, getPosition(simpleName));
            Log.i(tag, message);
            Log.i(tag, "---------");
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(DEFAULT_TAG, message);
        }
    }

    public static void w(String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, String simpleName) {
        if (DEBUG) {
            Log.e(tag, getPosition(simpleName));
            Log.e(tag, message);
            Log.e(tag, "---------");
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(DEFAULT_TAG, message);
        }
    }

    public static void mark(String simpleName) {
        if (DEBUG) {
            Log.i("MARK", getPosition(simpleName));
        }
    }

    public static void v(String tag, String message) {
        if (DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(Object obj, String message) {
        if (DEBUG) {
            Log.d(obj.getClass().getSimpleName(), message);
        }
    }

    private static String getPosition(String fileName) {
        StackTraceElement element = getTargetStack(fileName);
        if (null == element) {
            return "element null";
        }
        return "------ (" +
                element.getFileName() +
                ":" +
                element.getLineNumber() +
                ") ------";
    }

    private static StackTraceElement getTargetStack(String fileName) {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains(fileName)) {
                //返回调用位置的 element
                return element;
            }
        }
        return null;
    }
}
