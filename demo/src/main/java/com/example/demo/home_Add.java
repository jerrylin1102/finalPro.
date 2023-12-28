package com.example.demo;

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
    FirebaseDatabase database;
    DatabaseReference reference;
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
            String food=eatFood.getText().toString(); //取得食物名稱
            int year=datePicker.getYear(); //取得年份
            int month=(datePicker.getMonth())+1; //取得月份，因為從0開始所以要+1
            int day=datePicker.getDayOfMonth(); //取得日(Day)
            String Date=String.valueOf(year)+String.valueOf(month)+String.valueOf(day);
            Toast.makeText(home_Add.this, year+"年"+month+"月"+day+"日新增了"+food, Toast.LENGTH_SHORT).show();


            String dateString= String.format(Date);

            //以下為把資料輸進database
            database=FirebaseDatabase.getInstance();
            reference=database.getReference("diet");
            DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("diet");

            FoodData foodData=new FoodData(Date,food);
            Log.d("FoodData", dateString);
            reference1.child(dateString).push().setValue(foodData);
        }
    };
}
