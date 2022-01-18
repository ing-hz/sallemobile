package com.example.occupationdessalles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplachActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Create","onCreate");
        Thread t = new Thread(){
            public  void run(){
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        t.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Fin","Activité en pause");
        finish();
    }
}