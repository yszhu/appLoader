package com.example.admin.apploader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mainhandler extends Handler {
    private MainActivity context;
    public static final int MSG_DOWNLOAD=1;
    public static final int MSF_APPINFO=2;
    public Mainhandler(MainActivity context){
        this.context=context;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case MSF_APPINFO:
                Bundle data=msg.getData();
                try {
            /*JSONObject jsonObject =new JSONObject(data.getString("data"));
            AppInfo[] appsInfo={
                    new AppInfo(jsonObject.getString("name"),jsonObject.getString("description"))
            };*/

                    JSONArray jsonArray=new JSONArray(data.getString("data"));
                    AppInfo[] appsInfo=new AppInfo[jsonArray.length()];
                    for(int i=0;i<jsonArray.length();++i){
                        appsInfo[i]=new AppInfo(jsonArray.getJSONObject(i).getString("name"),jsonArray.getJSONObject(i).getString("description"));
                    }
                    context.listApps(appsInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case MSG_DOWNLOAD:
                Toast.makeText(context,msg.getData().getString("result"),Toast.LENGTH_SHORT).show();
                break;
        }


    }
}
