package com.example.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home_Add extends AppCompatActivity {
    private ImageView imgBack;
    private EditText eatFood;
    private Button btnClick;
    private DatePicker datePicker;
    private static final String NAMEFILE = "namefile" ;
    private static final String KEY_NAME = "keyname" ;
    FirebaseDatabase database;
    DatabaseReference reference;
    String account;
    int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_add);

        //顏色
        Window window=home_Add.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(home_Add.this, android.R.color.holo_orange_light));

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);
        eatFood=findViewById(R.id.edtFood);
        btnClick=findViewById(R.id.btnAddClick);
        datePicker=findViewById(R.id.datePicker);

        btnClick.setOnClickListener(btnClk);

        userData userData1 = com.example.demo.userData.getInstance();
        account=userData1.getAccount();
        //this is account

    }
    public Boolean vaidateFood(){
        String val=eatFood.getText().toString();
        if(val.isEmpty()){
            eatFood.setError("食物不得為空"); //跟你說不能沒有填東西
            return false;
        }else {
            eatFood.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener btnClk=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("account=","account="+account);
            String m;
            String d;
            String food=eatFood.getText().toString(); //取得食物名稱
            int year=datePicker.getYear(); //取得年份
            int month=(datePicker.getMonth())+1; //取得月份，因為從0開始所以要+1
            if(month<10)
            {m="0"+String.valueOf(month);}
            else
            {m=String.valueOf(month);}
            int day=datePicker.getDayOfMonth(); //取得日(Day)
            if(day<10)
            {d="0"+String.valueOf(day);}
            else
            {d=String.valueOf(day);}

            if(!vaidateFood()){

            }else {
                String Date = String.valueOf(year) + "-" + m + "-" + d;
                Toast.makeText(home_Add.this, Date + "新增了" + food, Toast.LENGTH_SHORT).show();
                eatFood.setText("");

                String dateString = String.format(Date);

                //以下為把資料輸進database
                database = FirebaseDatabase.getInstance();
                reference = database.getReference(account);
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(account);

                FoodData foodData = new FoodData(Date, food);
                Log.d("FoodData", dateString);
                reference1.child(dateString).push().setValue(foodData);
            }
        }
    };
    private String readAccount() {
        // 從 SharedPreferences 中檢索資料
        SharedPreferences preferences = getSharedPreferences(NAMEFILE, Context.MODE_PRIVATE);
        return preferences.getString(KEY_NAME, null);
    }
    private Integer num(){
        sum++;
        return sum;
    }
}
