package com.eason.core.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.eason.core.AppManager;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * Created by Chen on 2019-08-15
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    {
        AppManager.getInstance().addActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init(savedInstanceState);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);
}
