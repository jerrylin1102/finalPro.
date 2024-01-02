package com.example.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
    String account;
    List<String> foodList = new ArrayList<>();




    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_remove);

        adapter=new ArrayAdapter<String>(home_Remove.this, android.R.layout.simple_list_item_1, foodList);
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
        show_Food.setOnItemClickListener(show);

        //取得帳號
        userData userData1 = com.example.demo.userData.getInstance();
        account=userData1.getAccount();

    }
    private AdapterView.OnItemClickListener show = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String showfood = parent.getItemAtPosition(position).toString();
            Log.e("Data", showfood);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(account);

            // 獲取所選項目對應的 Firebase 資料的 key
            String selectedDate = getSelectedDate();

            // 宣告 keyToRemove 為最終變數陣列
            final String[] keyToRemove = new String[1];

            // 添加 ValueEventListener 以獲取 Firebase 數據
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                        if (foodSnapshot.getKey().equals(selectedDate)) {
                            for (DataSnapshot foods : foodSnapshot.getChildren()) {
                                //Log.e("Data", foods.toString());
                                FoodData foodData = foods.getValue(FoodData.class);
                                if (foodData.food.equals(showfood)) {
                                    keyToRemove[0] = foods.getKey();
                                    break;
                                }
                            }
                            if (keyToRemove[0] != null) {
                                break;
                            }
                        }
                    }

                    if (keyToRemove[0] != null) {
                        AlertDialog dialog = new AlertDialog.Builder(home_Remove.this)
                                .setTitle("刪除")
                                .setMessage("確定刪除" + showfood + "嗎?")
                                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseReference.child(selectedDate).child(keyToRemove[0]).removeValue();
                                        if (position >= 0 && position < foodList.size()) {
                                            //Log.e("DataFood", foodList.toString());
                                            foodList.remove(position);
                                            Log.e("Data", foodList.toString());
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                        Toast.makeText(home_Remove.this, "已刪除"+showfood, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 取消操作
                                    }
                                })
                                .show();
                        // 設置對話框按鈕的文字顏色
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.iconcolor));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.iconcolor));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    handleDatabaseError(error);
                }
            });
        }

        // 用於獲取選擇的日期
        private String getSelectedDate() {
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();
            return String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        }
    };

    private View.OnClickListener Sea=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.e("Data", "按鈕被按下了");
            int year = datePicker.getYear();
            int month = datePicker.getMonth() + 1;
            int day = datePicker.getDayOfMonth();
            foodList.clear();
            //List<String> foodList = new ArrayList<>();
            String selectedDate = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(account);
            //Log.d("Firebase", "Adding ValueEventListener");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //輪一次資料
                    for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                        //Log.e("Data", selectedDate);
                        //檢查資料日期是否為seletedDate
                        if (foodSnapshot.getKey().equals(selectedDate)){
                            foodList.clear();
                            //把資料抓出來並加入foodList
                            for (DataSnapshot foods : foodSnapshot.getChildren()){
                                FoodData foodData = foods.getValue(FoodData.class);

                                foodList.add(foodData.food);
                            }
                        }
                    }
                    //Log.d("Firebase", "Food List: " + foodList.toString());
                   // ArrayAdapter<String> adapter=new ArrayAdapter<String>(home_Remove.this, android.R.layout.simple_list_item_1, foodList);

                    // 將 foodList 的內容顯示在 TextView 中
                    if (!foodList.isEmpty()) {
                        //Log.d("Firebase", "Food List: " + "有東西!");
                        String foods = TextUtils.join(", ", foodList);
                        // 使用您的 TextView 顯示文字，確保在主線程上執行

                        runOnUiThread(() -> show_Food.setAdapter(adapter));

                    } else {
                        //Log.d("Firebase", "Food List: " + "沒ul4t8東西!");
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
    //返回按鈕
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
