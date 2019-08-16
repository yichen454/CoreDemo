package com.eason.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eason.core.AppManager;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Chen on 2019-08-15
 */
public abstract class BaseFragment extends RxFragment {
    protected WeakReference<AppCompatActivity> parentActivity;
    protected View view;
    private boolean isReady = false;
    private boolean isLoad = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = new WeakReference<>(AppManager.getInstance().currentActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = initView(inflater, container);
        isReady = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (parentActivity.get() == null) {
            parentActivity = new WeakReference<>((AppCompatActivity) getActivity());
        }
        init();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    private void onVisible() {
        init();
    }

    private void onInvisible() {
    }

    private void init() {
        if (isReady && !isLoad) {
            selfInit();
            isLoad = true;
        }
    }

    protected abstract void selfInit();
}
