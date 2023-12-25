package com.example.demo;

import static android.content.ContentValues.TAG;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setting extends AppCompatActivity {
    private ImageView imgBack;
    private TextView show;

    /*FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;*/
    FragmentManager fragmentManager = getFragmentManager();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.setting);

            //顏色
            Window window=setting.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(setting.this, android.R.color.holo_orange_light));

            show =  this.findViewById(R.id.show);
            imgBack=findViewById(R.id.imgBack);
            imgBack.setOnClickListener(lis);

            /*firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("users");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        // 檢查使用者的名字是否為 "Jerry"
                        String userName = userSnapshot.child("name").getValue(String.class);
                        if ("jerry".equals(userName)) {
                            // 如果名字為 "Jerry"，則取得該使用者的密碼
                            String email = userSnapshot.child("email").getValue(String.class);
                            Log.d(TAG, "Password for Jerry: " + email);
                            show.setText(email);
                            break;  // 可能有多個使用者，我們找到 Jerry 就中斷迴圈
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/

        }
        private View.OnClickListener lis=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        };
}

