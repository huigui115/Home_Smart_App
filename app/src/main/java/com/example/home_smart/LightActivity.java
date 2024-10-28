package com.example.home_smart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.home_smart.entity.Light;
import com.example.home_smart.util.Httputil;
import com.example.home_smart.util.JsonUtil;

import java.util.List;

public class LightActivity extends AppCompatActivity {

    private Button btnback;
    private Button btnlighton;
    private Button btnlightoff;
    private Button freshbtn;
    private LinearLayout lightContainer;
    private View selectedItem = null;
    private List<Light> light_list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_light_chng);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnlightoff = findViewById(R.id.btn_light_close);
        btnlighton = findViewById(R.id.btn_light_open);
        freshbtn = findViewById(R.id.btn_lockpage_search_locks);
        lightContainer = findViewById(R.id.light_list_container);
        btnback = findViewById(R.id.btn_lightpage_back);
        freshbtn.setOnClickListener(v -> {
            // 处理btnAnother的点击事件
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendGetRequest(Httputil.localhost + "/lights");
                    light_list = JsonUtil.parseJsonToLightList(response);
                    runOnUiThread(() -> {
                        lightContainer.removeAllViews();
                        LayoutInflater inflater = LayoutInflater.from(this);
                        for(Light light : light_list) {
                            View item = inflater.inflate(R.layout.lock_item, lightContainer, false);
                            TextView lightname = item.findViewById(R.id.lock_name);
                            TextView state = item.findViewById(R.id.lock_state);
                            TextView lightid = item.findViewById(R.id.lock_id);
                            lightname.setText(light.getLockName());
                            state.setText(light.isStatus() ? "已开" : "已关");
                            lightid.setText(light.getId().toString());
                            item.setOnClickListener(v1 -> {
                                if (selectedItem != null) {
                                    selectedItem.setSelected(false);
                                }
                                item.setSelected(true);
                                selectedItem = item;
                            });
                            lightContainer.addView(item);
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        btnlighton.setOnClickListener(v -> {
            if (selectedItem == null) {
                return;
            }
            TextView idtext = selectedItem.findViewById(R.id.lock_id);
            String lightid = idtext.getText().toString();
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendPutRequest(
                            Httputil.localhost + "/lights/" + lightid + "/setstate/1"
                            , null );
                    Light light = JsonUtil.parseJsonToLight(response);
                    if(light.isStatus()) {
                        runOnUiThread(() -> {
                            TextView state = selectedItem.findViewById(R.id.lock_state);
                            state.setText("已开");
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
        btnlightoff.setOnClickListener(v -> {
            if (selectedItem == null) {
                return;
            }
            TextView idtext = selectedItem.findViewById(R.id.lock_id);
            String lockid = idtext.getText().toString();
            new Thread(() -> {
                try {
                    String response;
                    response = Httputil.sendPutRequest(
                            Httputil.localhost + "/lights/" + lockid + "/setstate/0"
                            , null );
                    Light light = JsonUtil.parseJsonToLight(response);
                    if(!light.isStatus()) {
                        runOnUiThread(() -> {
                            TextView state = selectedItem.findViewById(R.id.lock_state);
                            state.setText("已关");
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