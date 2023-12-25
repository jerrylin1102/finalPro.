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
    public Boolean vaidateAccount(){
        String val=signupAccount.getText().toString();
        if(val.isEmpty()){
            signupAccount.setError("Username cannot bt empty"); //跟你說不能沒有填東西
            return false;
        }else {
            signupAccount.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    public Boolean vaidateEmail(){
        String val=signupEmail.getText().toString();
        if(val.isEmpty()){
            signupEmail.setError("Username cannot bt empty"); //跟你說不能沒有填東西
            return false;
        }else {
            signupEmail.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    public Boolean vaidatePassword(){
        String val=signupPassword.getText().toString();
        if(val.isEmpty()){
            signupPassword.setError("Username cannot bt empty"); //跟你說不能沒有填東西
            return false;
        }else {
            signupPassword.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    public Boolean vaidateUsername(){
        String val=signupUsername.getText().toString();
        if(val.isEmpty()){
            signupUsername.setError("Username cannot bt empty"); //跟你說不能沒有填東西
            return false;
        }else {
            signupUsername.setError(null); // 清除與 loginUsername 相關聯的錯誤訊息
            return true;
        }
    }
    private View.OnClickListener signup=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!vaidatePassword()|!vaidateUsername()|!vaidateAccount()|!vaidateEmail()){

            }
            else{
                database=FirebaseDatabase.getInstance(); //取得數據庫實例
                reference=database.getReference("users"); //取得路徑參考

                //取得Edit Text輸入的內容
                String account=signupAccount.getText().toString();
                String email=signupEmail.getText().toString();
                String username=signupUsername.getText().toString();
                String password=signupPassword.getText().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                reference.orderByChild("name").equalTo(account).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // 帳號已存在，顯示錯誤訊息
                            Toast.makeText(signup.this, "帳號重複，請嘗試其他組合!", Toast.LENGTH_SHORT).show();
                        } else {
                            // 帳號不存在，可以進行註冊
                            HelperClass helperClass = new HelperClass(account, email, username, password);
                            reference.child(account).setValue(helperClass);

                            Toast.makeText(signup.this, "註冊成功! ", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("name", account);
                            Intent intent = new Intent(signup.this, nologin.class);

                            intent.putExtras(bundle);
                            startActivity(intent);

                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // 處理取消事件的邏輯
                    }
                });
            }


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
