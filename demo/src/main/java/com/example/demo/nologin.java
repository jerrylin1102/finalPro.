package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class nologin extends AppCompatActivity {
    PageAdapter pageAdapter;
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    public class PageAdapter extends FragmentStateAdapter{
        FragmentHome Home;
        FragmentToday Today;
        FragmentPerson Person;
        public PageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle){
            super(fragmentManager,lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position){
            switch (position){
                case 0:
                    Home=new FragmentHome();
                    return Home;
                case 1:
                    Today=new FragmentToday();
                    return Today;
                case 2:
                    Person=new FragmentPerson();
                    return Person;
                default:
                    return null;
            }
        }
        @Override
        public int getItemCount(){
            return 3;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nologin);

        Window window=nologin.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(nologin.this, android.R.color.holo_orange_light));

        viewPager2 =findViewById(R.id.viewPage);
        bottomNavigationView=findViewById(R.id.nav_view);



        Intent intent=getIntent();
        if(intent !=null&&intent.getExtras()!=null)
        {
            Bundle extras=intent.getExtras();
            String username=extras.getString("username");

            FragmentPerson fragmentPerson=new FragmentPerson();
            Bundle fragmentBundle=new Bundle();
            fragmentBundle.putString("username",username);
            fragmentPerson.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                   // .replace(R.id.person,fragmentPerson)
                    .commit();
        }

        pageAdapter=new PageAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(pageAdapter);

        //viewPager
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.today);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.person);
                        break;
                }
            }
        });

        //bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        viewPager2.setCurrentItem(0);
                        break;
                    case  R.id.today:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.person:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }




}
