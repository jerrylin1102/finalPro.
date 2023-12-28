package com.example.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
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

public class home_Remove extends AppCompatActivity {
    private ImageView imgBack;
    private DatePicker datePicker;
    private ListView show_Food;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_remove);


        //顏色
        Window window=home_Remove.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(home_Remove.this, android.R.color.holo_orange_light));

        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);
        show_Food=findViewById(R.id.show_Food);
        datePicker=findViewById(R.id.datePicker);
        search=findViewById(R.id.btnSearch);

        search.setOnClickListener(Sea);



    }

    private View.OnClickListener Sea=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();
            String selectedDate = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
            //Toast.makeText(home_Remove.this, selectedDate, Toast.LENGTH_SHORT).show();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("diet");
            Log.d("Firebase", "Adding ValueEventListener");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<String> foodList = new ArrayList<>();
                    //輪一次資料
                    for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                        //檢查資料日期是否為seletedDate
                        if (foodSnapshot.getKey().equals(selectedDate)){
                            //把資料抓出來並加入foodList
                            for (DataSnapshot foods : foodSnapshot.getChildren()){
                                FoodData foodData = foods.getValue(FoodData.class);
                                foodList.add(foodData.food);
                            }
                        }
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(home_Remove.this, android.R.layout.simple_list_item_1, foodList);

                    // 將 foodList 的內容顯示在 TextView 中
                    if (!foodList.isEmpty()) {
                        Log.d("Firebase", "Food List: " + foodList.toString());
                        String foods = TextUtils.join(", ", foodList);
                        // 使用您的 TextView 顯示文字，確保在主線程上執行
                        runOnUiThread(() -> show_Food.setAdapter(adapter));

                    } else {
                        runOnUiThread(() -> show_Food.setAdapter(adapter));
                        Toast.makeText(home_Remove.this, selectedDate+"無資料", Toast.LENGTH_SHORT).show();
                        //runOnUiThread(() -> text.setText(selectedDate + " No food for selected date"));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    handleDatabaseError(error);
                }
            });
        }
    };
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };

    private void handleDatabaseError(DatabaseError databaseError) {
        Log.e("Firebase", "Read data failed:" + databaseError.toException().getMessage());
    }
}
