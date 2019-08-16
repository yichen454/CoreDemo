package com.eason.coredemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.eason.core.rxbus.RxBus;
import com.eason.core.rxbus.Subscribe;
import com.eason.core.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.get().register(this);

        Intent intent = new Intent(MainActivity.this, TestService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_container, new TestFragment());
        ft.commit();

        findViewById(R.id.btn_test_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.get().send(1001, "test1");
            }
        });

        findViewById(R.id.btn_test_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.get().send(2001, "activity");
            }
        });

        findViewById(R.id.btn_test_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.get().send(3001, "activity");
            }
        });
    }

    @Subscribe(code = 1001)
    public void receiveBus(String msg) {
        LogUtils.i("rxbus", msg, MainActivity.class.getSimpleName());
    }

    @Subscribe(code = 1002)
    public void receiveBus2(String msg) {
        LogUtils.i("rxbus", msg, MainActivity.class.getSimpleName());
    }

    @Subscribe(code = 1003)
    public void receiveBus3(String msg) {
        LogUtils.i("rxbus", msg, MainActivity.class.getSimpleName());
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unRegister(this);
        super.onDestroy();
    }
}
