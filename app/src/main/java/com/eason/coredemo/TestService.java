package com.eason.coredemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;

import com.eason.core.rxbus.RxBus;
import com.eason.core.rxbus.Subscribe;
import com.eason.core.utils.LogUtils;

/**
 * Created by Chen on 2019-08-16
 */
public class TestService extends Service {
    private static final String CHANNEL_ID = "NFCService";

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.get().register(this);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("主服务")//标题
                .setContentText("运行中...")//内容
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = null;
            channel = new NotificationChannel(CHANNEL_ID, "主服务", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);//设置提示灯
            channel.setLightColor(Color.RED);//设置提示灯颜色
            channel.setShowBadge(true);//显示logo
            channel.setDescription("xxxxxxxx");//设置描述
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //设置锁屏可见 VISIBILITY_PUBLIC=可见
            manager.createNotificationChannel(channel);
            builder.setChannelId(CHANNEL_ID);
        }
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RxBus.get().send(1003, "service: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Subscribe(code = 3001)
    public void receiveBus(String msg) {
        LogUtils.i("rxbus", msg, TestService.class.getSimpleName());
    }

    @Override
    public void onDestroy() {
        RxBus.get().unRegister(this);
        super.onDestroy();
    }
}
