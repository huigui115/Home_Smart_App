package com.example.home_smart;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home_smart.entity.Lock;
import com.example.home_smart.util.Httputil;
import com.example.home_smart.util.JsonUtil;

import java.util.List;

public class LockActivity extends AppCompatActivity {

    private Button lockbtn;
    private Button unlockbtn;
    private Button freshbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lock);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lockbtn = findViewById(R.id.btn_lock_open);
        unlockbtn = findViewById(R.id.btn_lock_close);
        freshbtn = findViewById(R.id.btn_search_locks);
        freshbtn.setOnClickListener(v -> {
            // 处理btnAnother的点击事件
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendGetRequest(Httputil.localhost + "/locks");
                    List<Lock> lock_list = JsonUtil.parseJsonToLockList(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}