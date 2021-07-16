package com.bytedance.practice5;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.model.UploadResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bytedance.practice5.Constants.BASE_URL;
import static com.bytedance.practice5.Constants.STUDENT_ID;
import static com.bytedance.practice5.Constants.USER_NAME;
import static com.bytedance.practice5.Constants.token;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final String COVER_IMAGE_TYPE = "image/*";
    private IApi api;
    private Uri coverImageUri;
    private SimpleDraweeView coverSD;
    private EditText toEditText;
    private EditText contentEditText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        setContentView(R.layout.activity_upload);
        coverSD = findViewById(R.id.sd_cover);
        toEditText = findViewById(R.id.et_to);
        contentEditText = findViewById(R.id.et_content);
        findViewById(R.id.btn_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "选择图片");
            }
        });


        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                coverSD.setImageURI(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }

            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    private void initNetwork() {
        //TODO 3
        // 创建Retrofit实例
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 生成api对象
        api = retrofit.create(IApi.class);

    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private void submit() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "封面不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "请输入TA的名字", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入想要对TA说的话", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "文件过大", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 5
        // 使用api.submitMessage()方法提交留言
        // 如果提交成功则关闭activity，否则弹出toast
        submitMessageWithretrofit(to,content,coverImageData);
//        submitMessageWithURLConnection(to,content,coverImageData);
    }

    private void submitMessageWithretrofit(String to, String content,byte[] coverImageData ) {
        MultipartBody.Part _from = MultipartBody.Part.createFormData("from",USER_NAME);
        MultipartBody.Part _to = MultipartBody.Part.createFormData("to",to);
        MultipartBody.Part _content = MultipartBody.Part.createFormData("content",content);
        MultipartBody.Part _coverPart = MultipartBody.Part. createFormData ("image",
                "cover.png",
                RequestBody.create(MediaType. parse ("multipart/form-data"),
                        coverImageData));
        try{
            Log.i("upload","尝试上传");
            Call<UploadResponse> repos = api.submitMessage(STUDENT_ID,"",_from,_to,_content,_coverPart,token);
            repos.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(final Call<UploadResponse> call, final Response<UploadResponse> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    final UploadResponse repoList = response.body();
                    if (repoList == null) {
                        return;
                    }
                }
                @Override
                public void onFailure(final Call<UploadResponse> call, final Throwable t) {
                    t.printStackTrace();
                }
            });
            finish();
        }
        catch (Exception e)
        {
            Log.i("upload","尝试上传失败");
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    //TODO 7 选做 用URLConnection的方式实现提交
    // 这个写的还是蛮困难的，不太会转换这个格式……
    // 但除了写参数的格式外其他的应该没什么问题了
    private void submitMessageWithURLConnection(String to, String content,byte[] coverImageData){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SMWUC(to,content,coverImageData);
                }
            }).start();
    }
    private void SMWUC(String to, String content, byte[] coverImageData) {
        try{
            Log.i("upload","尝试上传");
            // 以下为不同之处
            // -------------------------------------------------------------
//            String urlStr = String.format(BASE_URL+"messages?student_id=%s&extra_value=%s",STUDENT_ID,"");
//            String urlStr = String.format(BASE_URL+"messages?student_id=%s",STUDENT_ID);
            String urlStr = BASE_URL+"messages";
            try {
                URL url = new URL(urlStr);
                Log.d("URL:","当前的ulrStr:"+urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(6000);
                // 这里选择POSTmethod以进行上传
                conn.setRequestMethod("POST");
                conn.setRequestProperty("accept", "WkpVLWJ5dGVkYW5jZS1hbmRyb2lk");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                StringBuffer param = new StringBuffer();

                param.append("student_id="+STUDENT_ID);
                param.append("&extra_value=\"\"");
                param.append("&from="+USER_NAME);
                param.append("&to="+to);
                param.append("&content=").append(content);
                param.append("image_url=").append("https://lf3-hscdn-tos.pstatp.com/obj/inspirecloud-file/baas/tt41nq/4a72070720be5af9_1626443561493.png");
//                GsonConverterFactory gson = GsonConverterFactory.create();
//                gson.requestBodyConverter(to)
//                param.append("&image_w=").append("600");
//                param.append("&image_h=").append("414");
                // 创建一个输出流
                Log.d("u","当前的param格式"+param);
                OutputStream out = conn.getOutputStream();
                out.write(param.toString().getBytes("UTF-8"));
                out.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("exception:","网络异常："+e.toString());
                        Toast.makeText(UploadActivity.this, "网络异常" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            // ---------------------------------------------------------
            finish();
        }
        catch (Exception e)
        {
            Log.i("upload","尝试上传失败");
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}
