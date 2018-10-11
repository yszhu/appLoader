package com.example.admin.apploader;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainListener implements View.OnClickListener , AdapterView.OnItemClickListener {

    private MainActivity context;
    private EditText serverUrl;
    private Mainhandler handler;
    public MainListener(EditText serverUrl,ListView applications,MainActivity context,Mainhandler handler){
        this.context=context;
        this.serverUrl=serverUrl;
        this.handler=handler;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connect_btn:
                connectServer();
                break;
            case R.id.save_url_btn:
                saveUrl();
                break;
            case R.id.applications:
                selectApplication(v);
                break;
        }
    }

    private void connectServer(){
        Toast.makeText(context,serverUrl.getText().toString(),Toast.LENGTH_SHORT).show();
        //new DownloadThread(context).run();
        getAppInfo();
    }

    private void saveUrl(){
        Toast.makeText(context,"default url changed",Toast.LENGTH_SHORT).show();
        context.setDefaultUrl();
    }

    private void selectApplication(View v){

    }

    private void getAppInfo(){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().get().url(context.getServerUrl().getText().toString()).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendMessage(new Message());
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,context.getAppInfos()[position].getUrl(),Toast.LENGTH_SHORT).show();
    }
}
