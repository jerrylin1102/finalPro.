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
    private TextView text;

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
        text=findViewById(R.id.show);

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
                    Log.d("Firebase", "DataSnapshot: " + snapshot.toString());
                    List<String> foodList = new ArrayList<>();
                    for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                        FoodData foodData = foodSnapshot.getValue(FoodData.class);
                        Log.d("Firebase", "FoodSnapshot: " + foodSnapshot);
                        Log.d("Firebase", "FoodData: " + foodData);
                        if (foodData.getFood() != null && foodData.getDate() != null && foodData.getDate().equals(selectedDate)) {
                            String food = foodData.getFood();
                            Log.d("food","food:"+food);
                            foodList.add(food);
                        }
                    }
                    Log.d("foodList","foodList:"+foodList);
                    // 將 foodList 的內容顯示在 TextView 中
                    if (!foodList.isEmpty()) {
                        Log.d("Firebase", "Food List: " + foodList.toString());
                        String foods = TextUtils.join(", ", foodList);
                        // 使用您的 TextView 顯示文字，確保在主線程上執行
                        runOnUiThread(() -> text.setText(foods));
                    } else {
                        runOnUiThread(() -> text.setText(selectedDate + " No food for selected date"));
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
