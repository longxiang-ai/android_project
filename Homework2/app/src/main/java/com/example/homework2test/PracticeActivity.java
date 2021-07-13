package com.example.homework2test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {

    int count = 0;
    final int total = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        // test toast
        Button btn1 = findViewById(R.id.button_toast);
        TextView nums = findViewById(R.id.textView3);
        nums.setText("点击下方按钮获得");
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("PracticeActivity:","尝试toast功能");
                if(count < 50)
                {
                    Toast.makeText(PracticeActivity.this,"button clicked",Toast.LENGTH_SHORT).show();
                    count++;
                }
                else if(count == 50)
                {
                    Toast.makeText(PracticeActivity.this,"No more chances",Toast.LENGTH_SHORT).show();
                }
                TextView nums = findViewById(R.id.textView3);
                String num = "获得次数:";
                nums.setText(num +count+"/"+total);
            }
        });
    }




}
