package com.example.admin.apploader;

import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
                    Message message=new Message();
                    message.what=Mainhandler.MSF_APPINFO;
                    Bundle data=new Bundle();
                    data.putString("data",response.body().string());
                    message.setData(data);
                    handler.sendMessage(message);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context,context.getAppInfos()[position].getUrl(),Toast.LENGTH_SHORT).show();
        downloadApp(context.getAppInfos()[position].getUrl(),context.getAppInfos()[position].getAppName());
    }

    private void downloadApp(String url, final String name){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=Mainhandler.MSG_DOWNLOAD;
                Bundle data=new Bundle();
                data.putString("result","failed download file ");
                message.setData(data);
                handler.sendMessage(message);
                Log.i("fail","fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String fileName =name+".apk";
                File file=createFile(fileName);
                writeFiel(file,response.body().byteStream());
                Message message=new Message();
                message.what=Mainhandler.MSG_DOWNLOAD;
                Bundle data=new Bundle();
                data.putString("result","succeed download file "+file.getAbsolutePath());
                message.setData(data);
                handler.sendMessage(message);
                Log.i("succeed","succeed");
            }
        });
    }

    private File createFile(String fileName){
        String patent = Environment.getExternalStorageDirectory()+"/apps/";
        File file=new File(patent,fileName);
        return file;
    }

    private void writeFiel(File file, InputStream inputStream){
        OutputStream outputStream= null;
        try {
            outputStream = new FileOutputStream(file);
            byte buffer[]=new byte[4*1024];//每次存4K int temp; //写入数据
            int temp;
            Log.i("wirteFile","write File");
            while((temp=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,temp);
            }
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
