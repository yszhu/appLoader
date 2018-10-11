package com.example.admin.apploader;

import android.os.Handler;
import android.os.Message;

public class Mainhandler extends Handler {
    private MainActivity context;
    public Mainhandler(MainActivity context){
        this.context=context;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        AppInfo[] appsInfo={
                new AppInfo("HelloTrangle","the url of HelloTrangle"),
                new AppInfo("HelloTrangleAgain","the url of HelloTrangleAgain"),

        };
        context.listApps(appsInfo);
    }
}
