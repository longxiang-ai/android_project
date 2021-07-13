package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Switch switchBig = findViewById(R.id.SwitchBig);
        final TextView tv = findViewById(R.id.tv_title);
        final ImageView iv = findViewById(R.id.imageView);
        final EditText et = findViewById(R.id.editText);

        btn1.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View v) {
                count ++;
                String str = "目前点击的数量:"+count;
                tv.setText(str);
                Log.println(Log.INFO,"张三","Click Counter +1!");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iv.getVisibility()==View.INVISIBLE)
                {
                    iv.setVisibility(View.VISIBLE);
                    Log.println(Log.INFO,"李四","设置图片可见");
                }
                else if(iv.getVisibility() == View.VISIBLE)
                {
                    iv.setVisibility(View.INVISIBLE);
                    Log.println(Log.INFO,"李四","设置图片不可见");
                }

            }
        });
        switchBig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final float text_size = tv.getTextSize();
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    tv.setTextSize((float)1.1*text_size);
                }
                else
                {
                    tv.setTextSize(text_size);
                }
                Log.println(Log.INFO,"王五","改变文字大小");
            }
        });

        Log.i("13579", "onCreate: 246810");
    }
}
