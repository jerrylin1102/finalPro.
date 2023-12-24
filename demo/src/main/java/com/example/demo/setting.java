package com.example.demo;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class setting extends AppCompatActivity {
    private ImageView imgBack;
    FragmentManager fragmentManager = getFragmentManager();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.setting);

            //顏色
            Window window=setting.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(setting.this, android.R.color.holo_orange_light));

            imgBack=findViewById(R.id.imgBack);
            imgBack.setOnClickListener(lis);

        }
        private View.OnClickListener lis=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        };
}

