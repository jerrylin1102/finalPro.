package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Objects;

import javax.net.ssl.SSLEngineResult;

public class longing extends AppCompatActivity {
    //SharedPreferences
    private static final String PREFERENCE_FILE_NAME = "login_preferences";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String NAMEFILE = "namefile" ;
    private static final String KEY_NAME = "keyname" ;

    EditText loginUsername,loginPassword;
    Button loginButton;
    TextView signupRedirecText;

    private FragmentPerson fragmentPerson;//f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logining);
        //status checked

        fragmentPerson = new FragmentPerson();//f
        //getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragmentPerson,"A").commit();
        //顏色
        Window window=longing.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(longing.this, android.R.color.holo_orange_light));
        //test
        //綁定
        loginUsername=findViewById(R.id.username);
        loginPassword=findViewById(R.id.password);
        loginButton=findViewById(R.id.loginButton);
        signupRedirecText=findViewById(R.id.loginRedirecText);

        //按鍵觸發偵測
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!vaidateUsername() | !vaidatePassword()){

                }else {
                    checkUser();
                    //確認輸入的內容是否正確
                }
            }
        });

        signupRedirecText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳轉至Sign up介面
                Intent intent=new Intent(longing.this,signup.class);
                startActivity(intent);
            }
        });
    }
    public Boolean vaidateUsername(){
        String val=loginUsername.getText().toString();
        if(val.isEmpty()){
            loginUsername.setError("Username cannot bt empty"); //跟你說不能沒有填東西
            return false;
        }else {
            loginUsername.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    public Boolean vaidatePassword(){
        String val=loginPassword.getText().toString();
        if(val.isEmpty()){
            loginPassword.setError("Password cannot bt empty");//跟你說不能沒有填東西
            return false;
        }else {
            loginPassword.setError(null);// 清除與 loginPassword 相關聯的錯誤訊息
            return true;
        }
    }
    public void checkUser() {
        String userUsername = loginUsername.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();
        //db setting
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("name").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginUsername.setError(null); // 清除錯誤訊息
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (Objects.equals(passwordFromDB, userPassword)) {
                        // 密碼正確的處理邏輯
                        // 例如，你可以在這裡啟動一個新的活動或進行其他操作
                        //test
                        userData userData = com.example.demo.userData.getInstance();
                        //userData.setAccount(u.getText().toString());
                        //userData.setEmail(signupEmail.getText().toString());
                        userData.setAccount(userUsername);

                        Bundle bundle = new Bundle();
                        bundle.putString("name", userUsername);
                        Toast.makeText(longing.this, "登入成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(longing.this, nologin.class); // 將 CorrectLoginActivity 替換為實際的活動
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        //saveStatus
                        saveLoginState(true);
                        saveName(userUsername);

                    } else {
                        loginPassword.setError("密碼錯誤");
                        loginPassword.requestFocus();

                    }
                } else {
                    loginUsername.setError("使用者錯誤");
                    loginUsername.requestFocus();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 處理取消事件的邏輯
            }
        });
    }
    //驗證登入狀態
    private void saveLoginState(boolean isLoggedIn) {
        // 將登入狀態儲存到 SharedPreferences 中
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }
    //---
    private void saveName(String name) {
        // 將登入狀態儲存到 SharedPreferences 中
        SharedPreferences.Editor editor = getSharedPreferences(NAMEFILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }


}
