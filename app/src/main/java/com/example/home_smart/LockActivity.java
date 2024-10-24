package com.example.home_smart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private LinearLayout lockContainer;
    private View selectedItem = null;
    private List<Lock> lock_list = null;
    private Button btnback;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lock);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lockbtn = findViewById(R.id.btn_lock_close);
        unlockbtn = findViewById(R.id.btn_lock_open);
        freshbtn = findViewById(R.id.btn_search_locks);
        lockContainer = findViewById(R.id.lock_list_container);
        btnback = findViewById(R.id.btn_back);
        freshbtn.setOnClickListener(v -> {
            // 处理btnAnother的点击事件
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendGetRequest(Httputil.localhost + "/locks");
                    lock_list = JsonUtil.parseJsonToLockList(response);
                    runOnUiThread(() -> {
                        lockContainer.removeAllViews();
                        LayoutInflater inflater = LayoutInflater.from(this);
                        for(Lock lock : lock_list) {
                            View item = inflater.inflate(R.layout.lock_item, lockContainer, false);
                            TextView lockname = item.findViewById(R.id.lock_name);
                            TextView state = item.findViewById(R.id.lock_state);
                            TextView lockid = item.findViewById(R.id.lock_id);
                            lockname.setText(lock.getLockName());
                            state.setText(lock.isStatus() ? "已锁" : "未锁");
                            lockid.setText(lock.getId().toString());
                            item.setOnClickListener(v1 -> {
                                if (selectedItem != null) {
                                    selectedItem.setSelected(false);
                                }
                                item.setSelected(true);
                                selectedItem = item;
                            });
                            lockContainer.addView(item);
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        lockbtn.setOnClickListener(v -> {
            if (selectedItem == null) {
                return;
            }
            TextView idtext = selectedItem.findViewById(R.id.lock_id);
            String lockid = idtext.getText().toString();
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendPutRequest(
                            Httputil.localhost + "/locks/" + lockid + "/setstate/1"
                            , null );
                    Lock lock = JsonUtil.parseJsonToLock(response);
                    if(lock.isStatus()) {
                        runOnUiThread(() -> {
                            TextView state = selectedItem.findViewById(R.id.lock_state);
                            state.setText("已锁");
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        unlockbtn.setOnClickListener(v -> {
            if (selectedItem == null) {
                return;
            }
            TextView idtext = selectedItem.findViewById(R.id.lock_id);
            String lockid = idtext.getText().toString();
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendPutRequest(
                            Httputil.localhost + "/locks/" + lockid + "/setstate/0"
                            , null );
                    Lock lock = JsonUtil.parseJsonToLock(response);
                    if(!lock.isStatus()) {
                        runOnUiThread(() -> {
                            TextView state = selectedItem.findViewById(R.id.lock_state);
                            state.setText("未锁");
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        btnback.setOnClickListener(v -> {
            finish();
        });
        freshbtn.callOnClick();
    }
}