package com.example.homework2test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button btnToPractice = findViewById(R.id.button1);  // 找到button 1
        btnToPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击button 1 跳转至 practice page
                Log.i("MyActivity:","尝试进入PracActivity");
                Intent intent_to_practice = new Intent(MyActivity.this,PracticeActivity.class);
                startActivity(intent_to_practice);
            }
        });
        Button btnToBaidu = findViewById(R.id.button2);  // 找到button 2
        btnToBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击button 1 跳转至 practice page
                Log.i("MyActivity:","尝试打开百度");
                Intent intent_to_Baidu = new Intent(Intent.ACTION_VIEW);
                intent_to_Baidu.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent_to_Baidu);
            }
        });
        Button btnToDial = findViewById(R.id.button3);  // 找到button 3
        btnToDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击button 1 跳转至 practice page
                Log.i("MyActivity:","尝试拨号");
                Intent intent_to_Dial = new Intent(Intent.ACTION_CALL,Uri.parse("tel:10086"));
//                intent_to_Dial.setData(Uri.parse("tel:10086"));
                startActivity(intent_to_Dial);
            }
        });
    }

}
