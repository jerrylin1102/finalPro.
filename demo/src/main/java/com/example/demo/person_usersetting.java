package com.example.demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;


public class person_usersetting extends AppCompatActivity {
    private ImageView imgBack;
    private Button btn_Account, btn_Password, btn_Username, btn_Email,btn_logout;
    FragmentManager fragmentManager = getFragmentManager();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    private static final String PREFERENCE_FILE_NAME = "login_preferences";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    @Override
    public void onBackPressed() {
        // 在这里添加您的返回键逻辑
        // 例如，关闭 FragmentPerson
        Intent intent = new Intent(this,nologin.class);
        startActivity(intent);
        finish();
        // 调用 super.onBackPressed() 以确保默认的返回键行为
        super.onBackPressed();
    }
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peron_setting);

        //顏色
        Window window = person_usersetting.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(person_usersetting.this, android.R.color.holo_orange_light));
        btn_logout = this.findViewById(R.id.btn_logout);
        btn_Account = findViewById(R.id.btn_Account);
        btn_Username = findViewById(R.id.btn_Username);
        btn_Password = findViewById(R.id.btn_Password);
        btn_Email = findViewById(R.id.btn_Email);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(lis);

        btn_Email.setOnClickListener(emaillistener);
        btn_Username.setOnClickListener(usernamelistener);
        btn_Password.setOnClickListener(passwordlistener);
        btn_logout.setOnClickListener(logoutlistener);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try{
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
                                //btn_Password.setText(password);
                                String star="";
                                for(int i=0 ; i<password.length() ; i++){
                                    star+="*";
                                }
                                btn_Password.setText(star);

                                userData userData1 = com.example.demo.userData.getInstance();
                                userData1.setName(btn_Username.getText().toString());
                                userData1.setEmail(btn_Email.getText().toString());
                                userData1.setAccount(btn_Account.getText().toString());
                                userData1.setPw(btn_Password.getText().toString());
                            }
                        }
                    }
                    catch (NullPointerException n){
                        n.printStackTrace();
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
            /*Intent intent = new Intent(setting.this,nologin.class);
            startActivity(intent);
            finish();*/
            Intent intent = new Intent(person_usersetting.this, nologin.class);
            intent.putExtra("selectedFragment", 2); // 將選擇的 Fragment 索引傳遞給 nologin Activity
            startActivity(intent);
            finish();

        }
    };

    private View.OnClickListener passwordlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder reset = new AlertDialog.Builder(person_usersetting.this);
            RelativeLayout layout = new RelativeLayout(person_usersetting.this);
            final EditText input = new EditText(person_usersetting.this);
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
            reset.setIcon(R.drawable.setting);
            reset.setView(layout);
            reset.setMessage("新的密碼：");
            reset.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newPassword = input.getText().toString();
                    userData userData = com.example.demo.userData.getInstance();
                    //把資料存進userData裡面，再利用出來
                    userData.setPw(newPassword);
                    btn_Password.setText(userData.getPw());

                    HelperClass helperClass = new HelperClass(userData.getAccount(), userData.getEmail(), userData.getName(), userData.getPw());
                    reference.child(userData.getAccount()).setValue(helperClass);
                    Toast.makeText(person_usersetting.this, "密碼更改成功", Toast.LENGTH_SHORT).show();
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

            RelativeLayout layout = new RelativeLayout(person_usersetting.this);
            final EditText input = new EditText(person_usersetting.this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            input.setLayoutParams(params);


            // 设置EditText的ID，以便设置相对位置
            input.setId(View.generateViewId());

            layout.addView(input);

            AlertDialog.Builder reset = new AlertDialog.Builder(person_usersetting.this);
            reset.setTitle("更改視窗");
            reset.setIcon(R.drawable.setting);
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
                    Toast.makeText(person_usersetting.this, "暱稱更改成功", Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder reset = new AlertDialog.Builder(person_usersetting.this);
            RelativeLayout layout = new RelativeLayout(person_usersetting.this);
            final EditText input = new EditText(person_usersetting.this);
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
            reset.setIcon(R.drawable.setting);
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
                    Toast.makeText(person_usersetting.this, "Email更改成功", Toast.LENGTH_SHORT).show();
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
    private View.OnClickListener logoutlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();

            logout();
        }
    };
    private void logout() {
        // 清除登入狀態
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
        finish();
        goToLoginActivity();


    }
    private void goToLoginActivity() {
        // 跳轉到登入畫面
        Intent intent = new Intent(this, start.class);
        startActivity(intent);
        finish(); // 結束主畫面，以防使用者回到該畫面
    }
}

