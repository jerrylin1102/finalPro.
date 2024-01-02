package com.example.demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class home_Search extends AppCompatActivity {
    private ImageView imgBack;
    private TextView show;
    private Button btnAddClick;
    String entertext="說中文";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_search);
        //顏色
        Window window=home_Search.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(home_Search.this, android.R.color.holo_orange_light));
        show = this.findViewById(R.id.show);
        btnAddClick = findViewById(R.id.btnAddClick);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);
        btnAddClick.setOnClickListener(gptlistener);

    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
    private class ChatGPTTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String userInput = params[0];
            return sendChatRequest(userInput);
        }

        @Override
        protected void onPostExecute(String result) {
            // 处理ChatGPT的响应，更新UI等

            show.setText(result);
        }
    }

    private String sendChatRequest(String userInput) {
        // 将之前提到的发送ChatGPT请求的代码放在这里
        // 返回ChatGPT的响应字符串
        return GPTrequest.sendChatRequest(userInput); // 替换为实际的ChatGPT响应
    }
    private View.OnClickListener gptlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 在这里调用ChatGPT请求的示例
            //String userInput =edt.getText().toString() ; // 替换为用户实际输入
            //new ChatGPTTask().execute(userInput);
            new ChatGPTTask().execute(entertext);
        }
    };
}
