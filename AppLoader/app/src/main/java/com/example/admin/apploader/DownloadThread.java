package com.example.admin.apploader;

public class DownloadThread extends Thread {
    private MainActivity context;
    public DownloadThread(MainActivity context){
        this.context=context;
    }
    @Override
    public void run() {
        super.run();
        AppInfo[] appsInfo={
                new AppInfo("HelloTrangle","the url of HelloTrangle"),
                new AppInfo("HelloTrangleAgain","the url of HelloTrangleAgain"),

        };
        context.listApps(appsInfo);
    }
}
