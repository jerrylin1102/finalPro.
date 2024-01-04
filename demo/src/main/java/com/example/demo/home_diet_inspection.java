package com.example.demo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home_diet_inspection extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnsearch;
    private CheckBox che_fruit,che_bread,che_vegetable,che_oil,che_fish,che_milk;
    private TextView show;
    private String foodresult;
    private ProgressDialog progressDialog;
    List<String> foodList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_inspection);
        Window window= home_diet_inspection.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(home_diet_inspection.this, android.R.color.holo_orange_light));
        imgBack = findViewById(R.id.imgBack);
        che_fruit=findViewById(R.id.check_fruit);
        che_bread=findViewById(R.id.check_bread);
        che_vegetable=findViewById(R.id.check_vegetable);
        che_oil=findViewById(R.id.checkOil);
        che_fish=findViewById(R.id.checkFish);
        che_milk=findViewById(R.id.checkMilk);
        btnsearch=findViewById(R.id.btnSearch);
        show=findViewById(R.id.textView12);


        che_fruit.setOnCheckedChangeListener(chk);
        che_bread.setOnCheckedChangeListener(chk);
        che_vegetable.setOnCheckedChangeListener(chk);
        che_oil.setOnCheckedChangeListener(chk);
        che_fish.setOnCheckedChangeListener(chk);
        che_milk.setOnCheckedChangeListener(chk);
        imgBack.setOnClickListener(lis);
        btnsearch.setOnClickListener(gptlistener);
        foodList.clear();

    }
    private class ChatGPTTask01 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String userInput = params[0];
            return sendChatRequest(userInput);
        }

        @Override
        protected void onPostExecute(String result) {
            // 处理ChatGPT的响应，更新UI等
            //顯示
            progressDialog.dismiss();
            show.setText(result);
        }
    }
    private String sendChatRequest(String userInput) {

        return GPTrequest.sendChatRequest(userInput);
    }
    private View.OnClickListener gptlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog = new ProgressDialog(home_diet_inspection.this);
            progressDialog.setMessage("Waiting for ChatGPT...嚶嚶嚶");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            // 在这里调用ChatGPT请求的示例
            String send = foodList.toString()+"中，根據逗號前面的類別，有哪些括號內的類別剛剛沒出現" +
                    " (水果類、蔬菜類、五穀根莖類、蛋豆魚肉類、乳品類、油脂類)，" +
                    "並且根據沒有被選到的類別分別推薦幾樣餐點給我。(沒出現在括號中的類別都一定要推薦，且只要顯示推薦的餐點就好，請以條列式顯示)";
            new ChatGPTTask01().execute(send);
            Log.e("foodlist",foodList.toString());
            foodList.clear();
        }
    };
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private CompoundButton.OnCheckedChangeListener chk=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            foodList.clear();
            if(che_fruit.isChecked()){
                foodList.add("水果類");
            }
            if(che_bread.isChecked()){
                foodList.add("五榖根莖類");
            }
            if(che_vegetable.isChecked()){
                foodList.add("蔬菜類");
            }
            if(che_oil.isChecked()){
                foodList.add("油脂與堅果類");
            }
            if(che_fish.isChecked()){
                foodList.add("蛋豆魚肉類");
            }
            if(che_milk.isChecked()){
                foodList.add("乳品類");
            }
        }
    };
}
