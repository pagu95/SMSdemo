package com.example.smsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.Telephony.Mms.Part.TEXT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText number,message,editinfo;
    private TextView stored_info;
    
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send1 = findViewById(R.id.button1);
        Button send2 = findViewById(R.id.button2);
        Button send3 = findViewById(R.id.button3);
        Button send4 = findViewById(R.id.button4);
        Button send5 = findViewById(R.id.button5);
        Button send6 = findViewById(R.id.button6);


        stored_info = (TextView) findViewById(R.id.default_user);
        editinfo = (EditText) findViewById(R.id.editinfo);

        Button applybt = (Button) findViewById(R.id.apply);

        applybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stored_info.setText(editinfo.getText().toString().trim());

                saveData();
            }
        });


        send1.setOnClickListener(this);
        send2.setOnClickListener(this);
        send3.setOnClickListener(this);
        send4.setOnClickListener(this);
        send5.setOnClickListener(this);
        send6.setOnClickListener(this);


        loadData();
        updateViews();
        
    }
    private void sendSMS(){
        String phoneNo = number.getText().toString().trim();
        String SMS = message.getText().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null,SMS,null,null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            Toast.makeText(this, "FAILED TO SEND", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendcode1(String code){
        String phoneNo = "13033";
        String SMS = code + stored_info.getText().toString();
        Toast.makeText(this, SMS, Toast.LENGTH_SHORT).show();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo,null, SMS,null,null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

            Toast.makeText(this, "FAILED TO SEND", Toast.LENGTH_SHORT).show();

        }


    }


    public void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT,stored_info.getText().toString().trim());

        loadData();
        editor.apply();
        Toast.makeText(this,"DATA SAVED",Toast.LENGTH_SHORT).show();

    }
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        text =sharedPreferences.getString(TEXT,"");
    }

    public void updateViews(){

        stored_info.setText(text);

    }

    @Override
    public void onClick(View v) {
        String shortcode = "";
        switch (v.getId()){
            case R.id.button1:
                shortcode = "1 " ;
                break;
            case R.id.button2:
                shortcode = "2 " ;
                break;
            case R.id.button3:
                shortcode = "3 " ;
                break;
            case R.id.button4:
                shortcode = "4 " ;
                break;
            case R.id.button5:
                shortcode = "5 " ;
                break;
            case R.id.button6:
                shortcode = "6 " ;
                break;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){


                sendcode1(shortcode);
            }else{
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
            }

        }


    }
}