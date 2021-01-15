package com.sec.shares;

import android.app.Application;

import com.tencent.mmkv.MMKV;

import timber.log.Timber;

public class MyApplication extends Application {

    public static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 初始化日志
        Timber.plant(new Timber.DebugTree());

        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
    }
}
