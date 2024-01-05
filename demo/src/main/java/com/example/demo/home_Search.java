package com.example.demo;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
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
import java.util.Arrays;
import java.util.List;

public class home_Search extends AppCompatActivity {
    private ImageView imgBack,imgQ,imgA;
    private TextView show,show2,showQ,showA;
    private Button btnAddClick;
    private DatePicker datePicker;
    private String foodresult;
    private ProgressDialog progressDialog;
    String account;
    String[]ar1=new String[10];
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
        show2=findViewById(R.id.show1);
        datePicker=findViewById(R.id.datePicker);
        btnAddClick = findViewById(R.id.btnAddClick);
        imgBack=findViewById(R.id.imgBack);
        imgQ=findViewById(R.id.imgQ);
        imgA=findViewById(R.id.imgA);
        showA=findViewById(R.id.showA);
        showQ=findViewById(R.id.showQ);

        imgBack.setOnClickListener(lis);
        //btnAddClick.setOnClickListener(search_food);
        btnAddClick.setOnClickListener(gptlistener);

        userData userData1=com.example.demo.userData.getInstance();
        account=userData1.getAccount();
    }
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
            //顯示
            Log.d("s","result="+result);
            showA.setBackgroundColor(Color.parseColor("#FFED97"));
            showQ.setBackgroundColor(Color.parseColor("#FFED97"));
            showA.setVisibility(View.VISIBLE);
            showQ.setVisibility(View.VISIBLE);
            //String[]array=result.trim().split("\n");
            Arrays.fill(ar1,null);
            ar1=result.trim().split("、");
            int num=0;
            int arnum1=0;
            Log.d("re","result="+result);
            Log.d("d","ar1="+ar1);


            if (result.contains("水果")) {
                Log.d("n","num有接收到水果");
                num=num+1;
            }
            if(result.contains("榖根莖")){
                Log.d("n","num有接收到全榖根莖");
                num=num+1;
            }
            if (result.contains("蔬菜")) {
                Log.d("n","num有接收到蔬菜");
                num=num+1;
            }
            if (result.contains("奶類")){
                Log.d("n","num有接收到奶類");
                num=num+1;
            }
            if(result.contains("油脂")){
                Log.d("n","num有接收到油脂");
                num=num+1;
            }
            if (result.contains("豆蛋")) {
                Log.d("n","num有接收到蛋豆魚肉");
                num=num+1;
            }

            Log.d("num","num="+num);
            Log.d("arnum","arnum="+arnum1);
            show.setBackgroundColor(Color.parseColor("#FFFFCE"));
            showQ.setText("你今天吃了");
            showA.setText("你今天總共攝取了:");
            show.setText(foodresult);
            if(num<3)
            {
                show2.setBackgroundColor(Color.parseColor("#FFFFCE"));
                show2.setText(Arrays.toString(ar1)+"\n你好爛請繼續努力");
                imgA.setImageResource(R.drawable.angre);
            }
            if(num>=3&&num<5)
            {
                show2.setBackgroundColor(Color.parseColor("#FFFFCE"));
                show2.setText(Arrays.toString(ar1)+"\n還可以更好!");
                imgA.setImageResource(R.drawable.happy);
            }
            if(num>=5)
            {
                show2.setBackgroundColor(Color.parseColor("#FFFFCE"));
                show2.setText(Arrays.toString(ar1)+"\n鵝鵝愛尼");
                imgA.setImageResource(R.drawable.love);
            }
            progressDialog.dismiss();
            imgQ.setVisibility(View.VISIBLE);
            imgA.setVisibility(View.VISIBLE);


        }
    }

    private String sendChatRequest(String userInput) {

        return GPTrequest.sendChatRequest(userInput);
    }
    private View.OnClickListener gptlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog = new ProgressDialog(home_Search.this);
            progressDialog.setMessage("Waiting for ChatGPT...嚶嚶嚶");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
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
                        String strfood=foodList.toString();//Send to GPT
                        foodresult = strfood;
                        /*String sendtoGPT="請根據食物類別(水果類、蔬菜類、全穀根莖類、豆蛋魚肉類、奶類、" +
                                         "油脂與堅果種子類)分類以下餐點(只需顯示有攝取到的類別完整名稱(例如:水果類、蔬菜類...等等)，不需顯示食物名稱，也不得顯示類別名稱外的其他文字，每顯示一個類別就幫我換一行。" +
                                         "請仔細檢查每種餐點對應到的類別。每個類別名稱只能顯示一遍，不得重複出現，請一定要符合所有的規則。)餐點有："+strfood;*/
//                        String sendtoGPT = "請根據括號內的食物類別(水果類、蔬菜類、全穀根莖類、豆蛋魚肉類、奶類、油脂與堅果種子類)列出以下所有餐點的食物類別，" +
//                                "沒有餐點的食物類別不用顯示(只能顯示\"食物類別\"，並以條列式顯示，不要顯示\"餐點名稱\"也不要\"任何符號\"，請必須遵守以上規則與限制)："+strfood;
                        String sendtoGPT = "水果類\n蔬菜類\n全穀根莖類\n豆蛋魚肉類\n奶類\n油脂與堅果種子類\n請分析\""+strfood+
                                            "\"這些食物包含上面6個類別的哪幾個，並以條列式的方式顯示，格式為每行一類，只能顯示類別，不能顯示食物名稱。豬排屬於豆蛋魚肉類";
                        new ChatGPTTask().execute(sendtoGPT);
                        //show.setText(sendtoGPT);
                    }else{
                        progressDialog.dismiss();
                        show.setText(null);
                        show2.setText(null);
                        showA.setText(null);
                        showQ.setText(null);
                        show.setBackgroundColor(Color.TRANSPARENT);
                        show2.setBackgroundColor(Color.TRANSPARENT);
                        showA.setBackgroundColor(Color.TRANSPARENT);
                        showQ.setBackgroundColor(Color.TRANSPARENT);
                        imgA.setVisibility(View.INVISIBLE);
                        imgQ.setVisibility(View.INVISIBLE);
                        Toast.makeText(home_Search.this, selectedDate+"無資料", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });
            // 在这里调用ChatGPT请求的示例
            //String userInput =edt.getText().toString() ; // 替换为用户实际输入
            //new ChatGPTTask().execute(userInput);
            //new ChatGPTTask().execute(entertext);
            //

        }
    };

}
