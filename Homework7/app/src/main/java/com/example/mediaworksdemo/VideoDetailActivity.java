package com.example.mediaworksdemo;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VideoDetailActivity extends AppCompatActivity {

    String defaultMockUrl = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
    Context context=this;
    EditText VideoUrl;
    VideoView videoView;
    Button btn_play;
    RelativeLayout.LayoutParams defaultLayoutParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        VideoUrl = findViewById(R.id.et_input_video);
        videoView = findViewById(R.id.vv_detail);
        btn_play = findViewById(R.id.btn_play);
        // 点击播放按钮则进行edittext中输入的url进行播放，如果没有输入的话，则默认为原先的视频地址进行播放
        btn_play.setOnClickListener(v -> {
            String Url = VideoUrl.getText().toString();
            if (Url.length() == 0)
            {
                Url = defaultMockUrl;
            }
            videoView.setVideoURI(Uri.parse(Url));
//            videoView.setVideoPath(Url);
            videoView.setMediaController(new MediaController(context));
            videoView.requestFocus();
            videoView.start();
        });
        defaultLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        defaultLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        defaultLayoutParams.setMargins(0,0,0,0);
        videoView.setLayoutParams(defaultLayoutParams);

        // ------------------ 添加播放本地视频文件功能  ------------------------
        // TODO 目前已经完成了大部分本地视频的成功打开，但某些视频不知为何打不开，暂未找到原因
        Uri uri = getIntent().getData();
        String path = getIntent().getDataString();
        if(path != null)
        {
//            videoView.setVideoPath(path);
            videoView.setVideoURI(uri);
            videoView.setMediaController(new MediaController(context));
            videoView.requestFocus();
//            videoView.start();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer arg0)
                {
                    videoView.start();
                }
            });
        }
        // ------------------ 添加播放本地视频文件功能  ------------------------

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 切换到横屏的模式，对显示尺寸进行修改
        if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.FILL_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            videoView.setLayoutParams(layoutParams);
        }
        else
        {
            videoView.setLayoutParams(defaultLayoutParams);
        }
    }
}
