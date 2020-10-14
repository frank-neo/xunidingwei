package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.myapplication.reactaction.ReactClazz;
import com.example.myapplication.unit.GPSUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LOCATION_CODE = 1;
    private LocationManager lm;//位置管理
    //操作响应类实例化
    private ReactClazz reactClazz = new ReactClazz();

//    //位置信息
//    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // 定位权限申请
        GPSQunaxian();
//        location = GPSUtils.getInstance(MainActivity.this).showLocation();


        //重写的监听事件必须到onCreate方案中注册才可生效
        Button bt3 = (Button) findViewById(R.id.get_now_xy);
        bt3.setOnClickListener(this);

        Location location = GPSUtils.getInstance( MainActivity.this ).showLocation();
        if (location != null) {
            String address = "纬度：" + location.getLatitude() + "经度：" + location.getLongitude();
            Log.d( "kly",address );
            Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 定位权限申请
     */
    public void GPSQunaxian() {
        lm = (LocationManager) MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("kly", "没有权限");
                // 没有权限，申请权限。
                // 申请授权。
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
                //Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();

            } else {
                // 有权限了，去放肆吧。
                //Toast.makeText(getActivity(), "有权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d("kly", "d系统检测到未开启GPS定位服务");
            Toast.makeText(MainActivity.this, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。

                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(MainActivity.this, "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //程序退出，我们移除位置监听事件
        GPSUtils.getInstance(this).removeLocationUpdatesListener();
    }


    //重写了点击事件处理
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.get_now_xy:
                //reactClazz.getNowXy(findViewById(R.id.editText), location);
                //Toast.makeText(MainActivity.this, "第四种点击事件：Activity继承View.OnClickListener，由Activity实现OnClick(View view)方法", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }
}

