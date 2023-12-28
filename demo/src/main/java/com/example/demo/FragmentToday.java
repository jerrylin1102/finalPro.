package com.example.demo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.sql.Date;
import java.util.Calendar;

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

        // 设置当前日期
        Calendar currentCalendar = Calendar.getInstance();
        long currentTimeMillis = System.currentTimeMillis();
        // 將毫秒級別的時間轉換為 Date 對象
        Date currentDate = new Date(currentTimeMillis);

        calendarView.setOnDayClickListener(new com.applandeo.materialcalendarview.listeners.OnDayClickListener() {
            @Override
            public void onDayClick(com.applandeo.materialcalendarview.EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                // 獲取選擇的日期
                Date selectedDate = new Date(clickedDayCalendar.getTimeInMillis());

                // 在這裡處理選擇的日期
                // 例如，顯示一個 Toast
                Toast.makeText(getContext(), "選擇的日期：" + selectedDate.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}