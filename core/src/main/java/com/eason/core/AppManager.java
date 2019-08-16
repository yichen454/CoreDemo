package com.eason.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.eason.core.utils.LogUtils;

import java.util.Stack;

/**
 * Created by Chen on 2019-08-15
 */
public class AppManager {
    private static String TAG = "APP_MANAGER";
    @SuppressLint("StaticFieldLeak")
    private volatile static AppManager singleton = null;
    private static Stack<AppCompatActivity> activityStack = new Stack<>();

    //applicationContext
    private Context context;

    private AppManager() {
    }

    public static AppManager getInstance() {
        if (singleton == null) {
            synchronized (AppManager.class) {
                if (singleton == null) {
                    singleton = new AppManager();
                }
            }
        }
        return singleton;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(AppCompatActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        LogUtils.i(TAG, "add--" + activity.getClass().getSimpleName());
        activityStack.add(activity);
    }

    /**
     * 移除Activity出堆栈
     */
    public void removeActivity(AppCompatActivity activity) {
        LogUtils.i(TAG, "remove--" + activity.getClass().getSimpleName());
        if (activityStack.contains(activity))
            activityStack.remove(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public AppCompatActivity currentActivity() {
        if (activityStack.size() > 0)
            return activityStack.lastElement();
        else
            return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if (activityStack.size() > 0) {
            AppCompatActivity activity = activityStack.lastElement();
            if (activity != null)
                finishActivity(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(AppCompatActivity activity) {
        if (activity != null && !activity.isFinishing()) {
            LogUtils.i(TAG, "finish--" + activity.getClass().getSimpleName());
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null) {
            return;
        }
        for (AppCompatActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack == null) {
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
                //break;
            }
        }
        activityStack.clear();
    }

    public void restartApp() {
        LogUtils.i(TAG, "restartApp");
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
            assert intent != null;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getContext().startActivity(intent);
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
