package com.eason.coredemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.eason.core.base.BaseFragment;
import com.eason.core.rxbus.RxBus;
import com.eason.core.rxbus.Subscribe;
import com.eason.core.utils.LogUtils;

/**
 * Created by Chen on 2019-08-16
 */
public class TestFragment extends BaseFragment {

    private Button btn_test;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.get().register(this);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_test, container, false);
            btn_test = view.findViewById(R.id.btn_test);
        }
        return view;
    }

    @Override
    protected void selfInit() {
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.get().send(1002, "fragment");
            }
        });
    }

    @Subscribe(code = 2001)
    public void onReceive(String msg) {
        LogUtils.i("rxbus", msg, TestFragment.class.getSimpleName());
    }

    @Override
    public void onDestroyView() {
        RxBus.get().unRegister(this);
        super.onDestroyView();
    }
}
