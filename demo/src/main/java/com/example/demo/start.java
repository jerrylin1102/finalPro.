package com.example.demo;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class start extends AppCompatActivity {
    private Button start;
    private static final String PREFERENCE_FILE_NAME = "login_preferences";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String NAMEFILE = "namefile" ;
    private static final String KEY_NAME = "keyname" ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users");
        //save account
        userData userData = com.example.demo.userData.getInstance();
        userData.setAccount(readAccount());
        //顏色
        Window window= com.example.demo.start.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(com.example.demo.start.this, android.R.color.holo_orange_light));

        start=findViewById(R.id.start);
        start.setOnClickListener(lis);
    }
    private View.OnClickListener lis=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (isLoggedIn()) {
                // 如果已經登入，跳轉到主畫面
                Toast.makeText(start.this, "歡迎回來", Toast.LENGTH_SHORT).show();
                goToMainActivity();
                finish();
                Log.e("-->",readAccount()+"---");
            } else {
                showAlerDialog();
            }
        }
    };
    private void showAlerDialog(){
        Intent nologin=new Intent();
        nologin.setClass(com.example.demo.start.this,nologin.class);

        Intent signup=new Intent();
        signup.setClass(com.example.demo.start.this,signup.class);

        Intent logining=new Intent();
        logining.setClass(com.example.demo.start.this,longing.class);
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("歡迎使用")
                .setMessage("偵測到您尚未登入\n" +
                        "請先登入以開啟所有功能!")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(logining);
                        finish();

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(nologin);

                    }
                })
                .show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.iconcolor));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.iconcolor));
    }
    private boolean isLoggedIn() {
        // 從 SharedPreferences 中檢查登入狀態
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    private void goToMainActivity() {
        // 跳轉到主畫面
        Intent intent = new Intent(this,nologin.class);
        startActivity(intent);
        finish(); // 結束登入畫面，以防使用者回到該畫面
    }
    private void saveAccount(String account) {
        // 將登入狀態儲存到 SharedPreferences 中
        SharedPreferences.Editor editor = getSharedPreferences(NAMEFILE, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAME, account);
        editor.apply();
    }
    private String readAccount() {
        // 從 SharedPreferences 中檢索資料
        SharedPreferences preferences = getSharedPreferences(NAMEFILE, Context.MODE_PRIVATE);
        return preferences.getString(KEY_NAME, null);
    }

}