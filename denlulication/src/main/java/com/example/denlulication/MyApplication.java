package com.example.denlulication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2018/1/9.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
