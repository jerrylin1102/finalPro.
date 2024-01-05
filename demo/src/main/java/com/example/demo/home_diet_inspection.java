package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class home_diet_inspection extends AppCompatActivity {
    private ImageView imgBack;
    private Button btnsearch;
    private CheckBox che_fruit,che_bread,che_vegetable,che_oil,che_fish,che_milk;
    private TextView show;
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
        btnsearch.setOnClickListener(S);

    }
    private View.OnClickListener S=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            show.setText(foodList.toString());
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
