package com.example.demo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home_Search extends AppCompatActivity {
    private ImageView imgBack;
    private TextView show;
    private Button btnAddClick;
    private DatePicker datePicker;
    String account;
    List<String>foodList=new ArrayList<>();
    String entertext="說中文";//發送給gpt
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
        datePicker=findViewById(R.id.datePicker);
        btnAddClick = findViewById(R.id.btnAddClick);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);
        btnAddClick.setOnClickListener(search_food);
       // btnAddClick.setOnClickListener(gptlistener);

        userData userData1=com.example.demo.userData.getInstance();
        account=userData1.getAccount();
    }
    private View.OnClickListener search_food=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(account);
            String selectedDate = getSelectedDate();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    foodList.clear();
                    for(DataSnapshot foodSnapshot:snapshot.getChildren()){
                        if(foodSnapshot.getKey().equals(selectedDate)){
                            //foodList.clear();
                            for(DataSnapshot foods:foodSnapshot.getChildren()){
                                FoodData foodData=foods.getValue(FoodData.class);
                                foodList.add(foodData.food);
                            }
                        }
                    }
                    if(!foodList.isEmpty()){
                        String sendtoGPT=foodList.toString();
                        show.setText(sendtoGPT);
                    }else{
                        show.setText(null);
                        Toast.makeText(home_Search.this, selectedDate+"無資料", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    };
    private String getSelectedDate() {
        String m;
        String d;
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        if(month<10)
        {m="0"+String.valueOf(month);}
        else
        {m=String.valueOf(month);}
        int day = datePicker.getDayOfMonth();
        if(day<10)
        {d="0"+String.valueOf(day);}
        else
        {d=String.valueOf(day);}
        return String.valueOf(year)+"-"+m+"-"+d;
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
