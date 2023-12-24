package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class signup extends AppCompatActivity {
    EditText signupAccount,signupEmail,signupUsername,signupPassword;
    TextView loginRedirecText;
    Button signupButton;

    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //顏色
        Window window=signup.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(signup.this, android.R.color.holo_orange_light));

        signupAccount=this.findViewById(R.id.account);
        signupEmail=findViewById(R.id.signup_email);
        signupUsername=findViewById(R.id.signup_username);
        signupPassword=findViewById(R.id.signup_password);
        loginRedirecText=findViewById(R.id.signupText);
        signupButton=findViewById(R.id.signup_Button);

        signupButton.setOnClickListener(signup);
        loginRedirecText.setOnClickListener(loginR);
    }
    private View.OnClickListener signup=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            database=FirebaseDatabase.getInstance(); //取得數據庫實例
            reference=database.getReference("users"); //取得路徑參考

            //取得Edit Text輸入的內容
            String name=signupAccount.getText().toString();
            String email=signupEmail.getText().toString();
            String username=signupUsername.getText().toString();
            String password=signupPassword.getText().toString();

            /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            if(accoount.equals(reference.orderByChild("username").equalTo(accoount))){
                Toast.makeText(signup.this, "帳號重複，請嘗試其他組合!", Toast.LENGTH_SHORT).show();
            }else{
                //創建的Class，將資訊存入數據庫
                HelperClass helperClass=new HelperClass(accoount,email,username,password);
                reference.child(accoount).setValue(helperClass);

                Toast.makeText(signup.this, "註冊成功! ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(signup.this,nologin.class);
                startActivity(intent);
            }*/
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

            reference.orderByChild("username").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // 帳號已存在，顯示錯誤訊息
                        Toast.makeText(signup.this, "帳號重複，請嘗試其他組合!", Toast.LENGTH_SHORT).show();
                    } else {
                        // 帳號不存在，可以進行註冊
                        HelperClass helperClass = new HelperClass(name, email, username, password);
                        reference.child(name).setValue(helperClass);

                        Toast.makeText(signup.this, "註冊成功! ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(signup.this, nologin.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // 處理取消事件的邏輯
                }
            });


        }
    };
    private View.OnClickListener loginR=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(signup.this,longing.class);
            startActivity(intent);
        }
    };
}
