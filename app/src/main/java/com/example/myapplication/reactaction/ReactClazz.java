package com.example.myapplication.reactaction;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

/**
 * 界面操作响应器
 */
public class ReactClazz {

    //获取当前坐标点击实现
    public void getNowXy(View view, Location location) {

        String x_value = "";
        String y_value = "";

        EditText editText = view.findViewById(R.id.editText);
        EditText editText2 = view.findViewById(R.id.editText2);

        if (location != null) {
            x_value = location.getLongitude() + "";
            y_value = location.getLatitude() + "";
            String address = "纬度：" + y_value + "经度：" + x_value;
            Log.d("kly", address);
            System.out.println(address);
        }

        //x获取
        editText.setText(x_value);
        //y获取
        editText2.setText(y_value);
    }
}
