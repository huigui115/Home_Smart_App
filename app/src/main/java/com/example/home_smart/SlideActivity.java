package com.example.home_smart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
public class SlideActivity extends AppCompatActivity {

    private ImageView ivhead;
    private SlideMenu slideMenu;
    private Button mbtlock;
    private Button mbtlight;
    private Button mbtcurtain;
    private Button mbtrobot;
    private Button mbtloginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slide);

        ivhead=findViewById(R.id.lv_head);
        slideMenu=findViewById(R.id.slideMenu);
        mbtlock=findViewById(R.id.layout_btn_main_1);
        mbtlight=findViewById(R.id.layout_btn_main_2);
        mbtcurtain=findViewById(R.id.layout_btn_main_3);
        mbtrobot=findViewById(R.id.layout_btn_main_4);
        mbtloginout=findViewById(R.id.slidemenu_login_out);

        ivhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideMenu.switchMenu();
            }
        });
        setListner();
    }

    private void setListner(){
        Onclick onclick=new Onclick();
        mbtlock.setOnClickListener(onclick);
        mbtlight.setOnClickListener(onclick);
        mbtcurtain.setOnClickListener(onclick);
        mbtrobot.setOnClickListener(onclick);
        mbtloginout.setOnClickListener(onclick);
    }

    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=null;
            int id=v.getId();
            if(id==R.id.layout_btn_main_1){
                intent=new Intent(SlideActivity.this,LockActivity.class);
            }else if(id==R.id.layout_btn_main_2){
                intent=new Intent(SlideActivity.this,LightActivity.class);
            } else if (id==R.id.layout_btn_main_3) {
                intent=new Intent(SlideActivity.this,CurtainActivity.class);
            }else if(id==R.id.layout_btn_main_4){
                intent=new Intent(SlideActivity.this,RobotActivity.class);
            }else if(id==R.id.slidemenu_login_out){
                intent=new Intent(SlideActivity.this,MainActivity.class);
            }
            startActivity(intent);
        }
    }

}