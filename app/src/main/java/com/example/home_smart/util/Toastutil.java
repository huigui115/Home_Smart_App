package com.example.home_smart.util;
import android.content.Context;
import android.widget.Toast;

public class Toastutil {
    private static Toast mtoast;
    public static void showMsg(Context context,String msg){
        if(mtoast==null){
            mtoast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else{
            mtoast.setText(msg);
        }
        mtoast.show();
    }
}
