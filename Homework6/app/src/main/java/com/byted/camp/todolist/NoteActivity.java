package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract.TodoNote;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;


public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    private AppCompatRadioButton lowRadio;

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    // 选择采用文件读写的方式来存储draft
    // 获取绝对路径+分隔符+本文件的名称 这里命名为draftFile
    private String draftFileName;
    private static boolean needDraft = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        // 获取缓存文件名
        draftFileName =  getFilesDir().getAbsolutePath() + File.separator + "draftFile.txt";

        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        lowRadio = findViewById(R.id.btn_low);
        lowRadio.setChecked(true);
        // ------------ 添加 draft 功能--------------
        needDraft = true;// 默认在新打开activity的时候将needDraft置为1
        readNodeDraft();
        // ------------ 添加 draft 功能--------------
        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
//                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(),
                        getSelectedPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                clearFile();
                needDraft = false;
                Toast.makeText(NoteActivity.this,
                        "draft cleared", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void clearFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file =new File(draftFileName);
                try {
                    FileWriter fileWriter =new FileWriter(file);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void readNodeDraft() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(draftFileName);
                if (!file.exists()) {
                    try {
                        boolean isSuccess = file.createNewFile();
                        if (!isSuccess) {
                            throw new IOException("create exception error.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    final StringBuffer sb = new StringBuffer();
                    while (inputStream.read(bytes) != -1) {
                        sb.append(new String(bytes));
                    }
                    if (sb.length()!=0)// 只有当content不为空的时候才进行读取操作
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                int separatorIndex = sb.lastIndexOf("\t");
                                String content = sb.substring(0,separatorIndex);
                                editText.setText(content);
                                int priority = sb.charAt(separatorIndex+1) -'0';
                                Log.d("read note from draft", "当前的priority = "+priority);
                                if (priority == Priority.Medium.intValue) {
                                    AppCompatRadioButton MediumRadio = findViewById(R.id.btn_medium);
                                    MediumRadio.setChecked(true);
                                } else if (priority == Priority.High.intValue)
                                {
                                    AppCompatRadioButton HighRadio = findViewById(R.id.btn_high);
                                    HighRadio.setChecked(true);
                                }
                                else if(priority == Priority.Low.intValue){

                                    lowRadio.setChecked(true);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ------------ 添加 draft 功能--------------
        if(needDraft)        saveNoteDraft();
        // ------------ 添加 draft 功能--------------
        database.close();
        database = null;
        dbHelper.close();
        dbHelper = null;
    }

    private void saveNoteDraft() {
        CharSequence content = editText.getText();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(NoteActivity.this,
                    "No content to draft", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean succeed = saveNote2Draft(content.toString().trim(),
                getSelectedPriority());
        if (succeed) {
            Toast.makeText(NoteActivity.this,
                    "Note added to Draft", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_OK);
        } else {
            Toast.makeText(NoteActivity.this,
                    "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean saveNote2Draft(final String trim, final Priority selectedPriority) {

        boolean isSuccess = true;
        // 文件读写操作新创一个线程来进行
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 创建文件
                    File file = new File(draftFileName);
                    if (!file.exists()) {
                        try {
                            boolean isSuccess = file.createNewFile();
                            if (!isSuccess) {
                                throw new IOException("create exception error.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // 获取文件写入流
                    FileOutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        // content
                        outputStream.write(trim.getBytes());
                        // priority
                        outputStream.write(("\t"+selectedPriority.intValue).getBytes());
                    }  catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != outputStream) {
                                outputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        catch (Exception e)
        {
            isSuccess = false;
            Toast.makeText(NoteActivity.this,
                    "Draft Error", Toast.LENGTH_SHORT).show();
        }
        return isSuccess;
    }

    private boolean saveNote2Database(String content, Priority priority) {
        // TODO: 2021/7/19 8. 这里插入数据库
        if (database == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT, content);// 填入内容
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);// 填入状态
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis());// 填入时间
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);// 填入优先级
        // 进行插入操作
        long rowId = database.insert(TodoNote.TABLE_NAME, null, values);
        return rowId != -1;
    }

    private Priority getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Priority.High;
            case R.id.btn_medium:
                return Priority.Medium;
            default:
                return Priority.Low;
        }
    }


}
