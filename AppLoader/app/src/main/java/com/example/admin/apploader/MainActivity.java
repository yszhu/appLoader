package com.example.admin.apploader;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText serverUrl;
    private Button connectBtn;
    private Button saveUrlBtn;
    private ListView applications;
    private MainListener listener;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private String defaultUrl="http://192.168.10.16:8080/phpbin/applications.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setListener();
    }

    private void initViews(){
        serverUrl= findViewById(R.id.server);
        connectBtn =findViewById(R.id.connect_btn);
        saveUrlBtn=findViewById(R.id.save_url_btn);
        applications=findViewById(R.id.applications);



        preferences=getPreferences(MODE_PRIVATE);
        editor=preferences.edit();

        String url=preferences.getString("defaultServerUrl",null);
        if(url==null){
            editor.putString("defaultServerUrl",defaultUrl);
            editor.commit();
        }
        else {
            defaultUrl=url;
        }
        serverUrl.setText(defaultUrl);



    }

    private void setListener(){
        listener=new MainListener(serverUrl,applications,this);
        connectBtn.setOnClickListener(listener);
        saveUrlBtn.setOnClickListener(listener);
    }

    public void setDefaultUrl(){
        defaultUrl=serverUrl.getText().toString();
        editor.putString("defaultServerUrl",defaultUrl);
        editor.commit();
    }

    public void listApps(AppInfo[] appsInfo){
        applications.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData(appsInfo)));
    }

    private List<String> getData(AppInfo[] appsInfo){
        List<String> data = new ArrayList<>();
        for(int i=0;i<appsInfo.length;++i){
            data.add(appsInfo[i].getAppName());
        }
        return data;
    };

}
