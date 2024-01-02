package com.example.demo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentToday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentToday extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView show_Food;

    public FragmentToday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentToday.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentToday newInstance(String param1, String param2) {
        FragmentToday fragment = new FragmentToday();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        com.applandeo.materialcalendarview.CalendarView calendarView = view.findViewById(R.id.calendarView);

        String account;

        //取得帳號
        userData userData1 = com.example.demo.userData.getInstance();
        account=userData1.getAccount();
        show_Food=view.findViewById(R.id.show_food);
        final Context context=getActivity();

        // 設置當前日期
        Calendar currentCalendar = Calendar.getInstance();
        long currentTimeMillis = System.currentTimeMillis();
        // 將毫秒級別的時間轉換為 Date 對象
        Date currentDate = new Date(currentTimeMillis);
        List<String> foodList = new ArrayList<>();
        ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, foodList);

        calendarView.setOnDayClickListener(new com.applandeo.materialcalendarview.listeners.OnDayClickListener() {
            @Override
            public void onDayClick(com.applandeo.materialcalendarview.EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                // 獲取選擇的日期
                Date selectedDate = new Date(clickedDayCalendar.getTimeInMillis());
                foodList.clear();
                Log.d("account","account="+account);
                Log.e("Data", selectedDate.toString());
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(account);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("hi","onDataChange");
                        //輪一次資料
                        for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                           /* Log.d("1","1="+foodSnapshot.toString());
                            Log.e("2","2="+snapshot.getChildren().toString());
                            Log.d("3","3="+foodSnapshot.getKey());*/
                            Log.d("kkk", String.valueOf(foodSnapshot.getKey().equals(selectedDate.toString())));
                            //檢查資料日期是否為seletedDate
                            if (foodSnapshot.getKey().equals(selectedDate.toString())){
                                //Log.d("show","有跑進來");
                                Log.d("show","有跑進來");
                                foodList.clear();
                                //把資料抓出來並加入foodList
                                for (DataSnapshot foods : foodSnapshot.getChildren()){
                                    FoodData foodData = foods.getValue(FoodData.class);

                                    foodList.add(foodData.food);
                                    Log.d("Food","Food="+foodList);
                                }
                            }
                        }
                        Log.d("foodlist","FoodList="+foodList);
                        // 將 foodList 的內容顯示在 TextView 中
                        if (!foodList.isEmpty()) {
                            //Log.d("Firebase", "Food List: " + "有東西!");
                            String foods = TextUtils.join(", ", foodList);
                            // 使用您的 TextView 顯示文字，確保在主線程上執行

                            show_Food.setAdapter(adapter);

                        } else {
                            //Log.d("Firebase", "Food List: " + "沒ul4t8東西!");
                             show_Food.setAdapter(adapter);
                            //runOnUiThread(() -> text.setText(selectedDate + " No food for selected date"));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // 在這裡處理選擇的日期
                // 例如，顯示一個 Toast
            }
        });

        return view;
    }

}