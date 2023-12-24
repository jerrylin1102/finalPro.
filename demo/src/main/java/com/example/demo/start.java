package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class start extends AppCompatActivity {
    private Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);


        //顏色
        Window window= com.example.demo.start.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(com.example.demo.start.this, android.R.color.holo_orange_light));

        start=findViewById(R.id.start);
        start.setOnClickListener(lis);
    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showAlerDialog();
        }
    };
    private void showAlerDialog(){
        Intent nologin=new Intent();
        nologin.setClass(com.example.demo.start.this,nologin.class);

        Intent signup=new Intent();
        signup.setClass(com.example.demo.start.this,signup.class);

        Intent logining=new Intent();
        logining.setClass(com.example.demo.start.this,longing.class);
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("歡迎使用")
                .setMessage("偵測到您尚未登入\n" +
                        "請先登入以開啟所有功能!")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(logining);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(nologin);

                    }
                })
                .show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.iconcolor));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.iconcolor));
    }
}