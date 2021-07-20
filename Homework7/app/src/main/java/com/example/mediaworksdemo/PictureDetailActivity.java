package com.example.mediaworksdemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PictureDetailActivity extends AppCompatActivity {

//    String mockUrl = "https://lf1-cdn-tos.bytescm.com/obj/static/ies/bytedance_official_cn/_next/static/images/0-390b5def140dc370854c98b8e82ad394.png";
    String mockErrorUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Android_logo_2019_%28stacked%29.svg/400px-Android_logo_2019_%28stacked%29.svg.png";
    String mockUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F11709738980%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1629355973&t=302d2a20112629b749227fe7a54666d1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        ImageView imageView = findViewById(R.id.iv_detail);
        EditText textInput = findViewById(R.id.et_input);
        Button btnSuccess = findViewById(R.id.btn_load_success);
        Button btnFail = findViewById(R.id.btn_load_fail);
        Button btnRoundedCorners = findViewById(R.id.btn_rounded_corners);

        btnSuccess.setOnClickListener( v -> {
            String text = textInput.getText().toString();
            if (text.length() == 0)
                text = mockUrl;
            Glide.with(this).load(text)
                    .placeholder(R.drawable.loading_green)
                    .apply(new RequestOptions().centerCrop())
                    .transition(withCrossFade())
                    .error(R.drawable.error_red)
                    .into(imageView);
        });

        btnFail.setOnClickListener( v -> {
            String text = textInput.getText().toString();
            if (text.length() == 0)
                text = mockErrorUrl;
            Glide.with(this).load(text)
                    .placeholder(R.drawable.loading_green)
                    .error(R.drawable.error_red)
                    .into(imageView);
        });

        btnRoundedCorners.setOnClickListener( v-> {
            DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();

            Glide.with(this).load(mockUrl)
                    .placeholder(R.drawable.loading_green)
                    .error(R.drawable.error_red)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(100))) // 圆角图片
                    .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))           // 渐弱转换
                    .into(imageView);
        });
    }
}
