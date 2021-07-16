package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }
    private SocketActivity.SocketCallback callback;
    //head请求内容
    private static String domain_name = "www.zju.edu.cn";
    private static int port_number = 80;
    private static String content = "HEAD / HTTP/1.1\r\nHost:"+domain_name+"\r\n\r\n";
    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示

        Log.i("SocketRun","尝试进行callback");
        try{
            Socket socket = new Socket(domain_name,port_number); //服务器IP及端口(Http 默认为80）
            // 输入输出流
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            // 读文件
            byte[] data = new byte[1024 * 5];//每次读取的字节数
            if(socket.isConnected())
            {
                Log.d("socket", "客户端发送请求:" + content);
                    os.write(content.getBytes());
                    os.flush();
                    int receiveLen = is.read(data);
                    if (receiveLen!=-1){
                        String receive = new String(data, 0, receiveLen);
                        Log.d("socket", "客户端收到 " + receive);
                        // 显示收到的信息
                        callback.onResponse(receive);
                    }else {
                        Log.d("socket", "客户端收到-1");
                    }
            }
            Log.d("socket", "客户端断开 ");
            os.flush();
            os.close();
            socket.close(); //关闭socket
        }
        catch (Exception e)
        {
             e.printStackTrace();
        }
    }
}