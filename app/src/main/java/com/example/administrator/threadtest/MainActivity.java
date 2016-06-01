package com.example.administrator.threadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
private String TAG="thread1";
    private Button btnEnd;
    private Button closeBtn;
    private TextView labelTimer;
    private Thread clockThread;
    private boolean isRunning=true;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnEnd=(Button)findViewById(R.id.btn);
        closeBtn=(Button)findViewById(R.id.closebtn);
        labelTimer=(TextView)findViewById(R.id.labelTimer);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                isRunning=!isRunning;
//                Log.d("Running flag",":"+isRunning);
                isRunning = true;
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning=false;
            }
        });
        handler=new Handler(){
            @Override
             public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        long sysTime=System.currentTimeMillis();
                        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
                        Date curDate=new Date(sysTime);
                        String str=format.format(curDate);
                        labelTimer.setText(str);
//                        labelTimer.setText("lost time:"+msg.obj+"s");
                }
            }
        };

        clockThread=new Thread(new Runnable() {
            @Override
            public void run() {
                int timer=0;
                while (isRunning){
                    try{
                        Thread.currentThread().sleep(1000);
                        timer++;
                        //labelTimer.setText("run of " + timer + "s");
                        Message msg=new Message();
                        msg.obj=timer;
                        msg.what=0;
                        handler.sendMessage(msg);
                        Log.d(TAG, "lost time" + timer);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread.start();
    }
}
