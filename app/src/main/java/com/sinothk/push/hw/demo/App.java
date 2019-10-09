package com.sinothk.push.hw.demo;

import android.app.Application;

import com.huawei.android.hms.agent.HMSAgent;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HMSAgent.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HMSAgent.destroy();
    }
}
