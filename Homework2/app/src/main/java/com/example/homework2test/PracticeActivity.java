package com.example.homework2test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        // test toast
        Button btn1 = findViewById(R.id.button_toast);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("PracticeActivity:","尝试toast功能");
                Toast.makeText(PracticeActivity.this,"button clicked",Toast.LENGTH_SHORT).show();
            }
        });
        // 圆角按钮尝试

    }




}
