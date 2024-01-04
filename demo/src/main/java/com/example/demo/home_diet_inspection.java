package com.example.demo;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class home_diet_inspection extends AppCompatActivity {
    private ImageView imgBack;
    private CheckBox che_fruit,che_bread,che_vegetable,che_oil,che_fish,che_milk;
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


        imgBack.setOnClickListener(lis);
    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            finish();
        }
    };
}
