package com.example.demo;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

                        userData userData1 = com.example.demo.userData.getInstance();
                        userData1.setName(btn_Username.getText().toString());
                        userData1.setEmail(btn_Email.getText().toString());
                        userData1.setAccount(btn_Account.getText().toString());
                        userData1.setPw(btn_Password.getText().toString());
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

    private View.OnClickListener passwordlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder reset = new AlertDialog.Builder(setting.this);
            RelativeLayout layout = new RelativeLayout(setting.this);
            final EditText input = new EditText(setting.this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            input.setLayoutParams(params);


            // 设置EditText的ID，以便设置相对位置
            input.setId(View.generateViewId());

            layout.addView(input);

            reset.setTitle("更改視窗");
            reset.setIcon(R.mipmap.reset);
            reset.setView(layout);
            reset.setMessage("新的密碼：");
            reset.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newPassword = input.getText().toString();
                    userData userData = com.example.demo.userData.getInstance();
                    userData.setPw(newPassword);
                    btn_Password.setText(userData.getPw());

                    HelperClass helperClass = new HelperClass(userData.getAccount(), userData.getEmail(), userData.getName(), userData.getPw());
                    reference.child(userData.getAccount()).setValue(helperClass);
                }
            });
            reset.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            reset.show();
        }
    };

    private View.OnClickListener usernamelistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder reset = new AlertDialog.Builder(setting.this);
            RelativeLayout layout = new RelativeLayout(setting.this);
            final EditText input = new EditText(setting.this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            input.setLayoutParams(params);


            // 设置EditText的ID，以便设置相对位置
            input.setId(View.generateViewId());

            layout.addView(input);

            reset.setTitle("更改視窗");
            reset.setIcon(R.mipmap.reset);
            reset.setView(layout);
            reset.setMessage("新的暱稱：");
            reset.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newusername = input.getText().toString();
                    userData userData = com.example.demo.userData.getInstance();
                    userData.setName(newusername);
                    btn_Username.setText(userData.getName());

                    HelperClass helperClass = new HelperClass(userData.getAccount(), userData.getEmail(), userData.getName(), userData.getPw());
                    reference.child(userData.getAccount()).setValue(helperClass);
                }
            });
            reset.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            reset.show();
        }
    };

    private View.OnClickListener emaillistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder reset = new AlertDialog.Builder(setting.this);
            RelativeLayout layout = new RelativeLayout(setting.this);
            final EditText input = new EditText(setting.this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            input.setLayoutParams(params);


            // 设置EditText的ID，以便设置相对位置
            input.setId(View.generateViewId());

            layout.addView(input);

            reset.setTitle("更改視窗");
            reset.setIcon(R.mipmap.reset);
            reset.setView(layout);
            reset.setMessage("新的email：");
            reset.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newemail = input.getText().toString();
                    userData userData = com.example.demo.userData.getInstance();
                    userData.setEmail(newemail);
                    btn_Email.setText(userData.getEmail());

                    HelperClass helperClass = new HelperClass(userData.getAccount(), userData.getEmail(), userData.getName(), userData.getPw());
                    reference.child(userData.getAccount()).setValue(helperClass);
                }
            });
            reset.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            reset.show();
        }
    };
}

