package com.example.admin.apploader;

public class AppInfo {
    private String appName;
    private String url;

    public AppInfo(String appName,String url){
        this.appName=appName;
        this.url=url;
    }
    public String getAppName() {
        return appName;
    }

    public String getUrl(){
        return url;
    }
}
