package com.example.home_smart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home_smart.util.Toastutil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private Button btnlogin;
    private EditText metuser;
    private EditText metpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //找到控件
        btnlogin=findViewById(R.id.lgb_1);
        metuser=findViewById(R.id.lga_1);
        metpassword=findViewById(R.id.lgp_1);
        btnlogin.setOnClickListener(this);
    }
    public void onClick(View v){
        String username=metuser.getText().toString();
        String password=metpassword.getText().toString();
        Intent intent = null;

        //弹出内容设置
        String ok="登陆成功！";
        String fail="账号或者密码有误，请重新登录";

        if(username.equals("ly")&&password.equals("1976")){
            Toastutil.showMsg(getApplicationContext(),ok);
            intent=new Intent(MainActivity.this, SlideActivity.class);
            startActivity(intent);
        }else{
            Toast toastcenter=Toast.makeText(getApplicationContext(),fail,Toast.LENGTH_SHORT);
            toastcenter.setGravity(Gravity.CENTER,0,0);
            toastcenter.show();
        }
    }
}