package com.example.demo;

import android.accounts.Account;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class setting extends AppCompatActivity {
    private ImageView imgBack;
    private Button btn_Account, btn_Password, btn_Username, btn_Email;
    FragmentManager fragmentManager = getFragmentManager();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        //顏色
        Window window = setting.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(setting.this, android.R.color.holo_orange_light));

        btn_Account = findViewById(R.id.btn_Account);
        btn_Username = findViewById(R.id.btn_Username);
        btn_Password = findViewById(R.id.btn_Password);
        btn_Email = findViewById(R.id.btn_Email);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);

        btn_Account.setOnClickListener(accountlistener);
        btn_Email.setOnClickListener(emaillistener);
        btn_Username.setOnClickListener(usernamelistener);
        btn_Password.setOnClickListener(passwordlistener);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren())
                {
                    userData userData = com.example.demo.userData.getInstance();
                    String account = userSnapshot.child("name").getValue(String.class);
                    if(userData.getAccount().equals(account)) {
                        btn_Account.setText(account);
                        String email = userSnapshot.child("email").getValue(String.class);
                        btn_Email.setText(email);
                        String username = userSnapshot.child("username").getValue(String.class);
                        btn_Username.setText(username);
                        String password = userSnapshot.child("password").getValue(String.class);
                        btn_Password.setText(password);
                        Log.e("ER", userData.getAccount());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private View.OnClickListener lis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener accountlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener passwordlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener usernamelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener emaillistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}

