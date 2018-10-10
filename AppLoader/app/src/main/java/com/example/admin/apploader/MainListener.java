package com.example.admin.apploader;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainListener implements View.OnClickListener {

    private MainActivity context;
    private EditText serverUrl;
    private ListView applications;
    public MainListener(EditText serverUrl,ListView applications,MainActivity context){
        this.context=context;
        this.serverUrl=serverUrl;
        this.applications=applications;
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
        new DownloadThread(context).run();
    }

    private void saveUrl(){
        Toast.makeText(context,"default url changed",Toast.LENGTH_SHORT).show();
        context.setDefaultUrl();
    }

    private void selectApplication(View v){

    }


}
