package com.nl.treadmilllauncher.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dingo on 2018/3/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG = "BaseActivity";

    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();

        if(isNeedFullScreen()){
            //设置无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            //设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if(isNeedKeepScreenOn()){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        setContentView(getLayoutId());

        unbinder = ButterKnife.bind(this);

        initView();
        loadData();
    }

    public abstract int getLayoutId();

    public abstract boolean isNeedFullScreen();

    public abstract boolean isNeedKeepScreenOn();

    public abstract void initView();

    public abstract void loadData();

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        unbinder.unbind();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
