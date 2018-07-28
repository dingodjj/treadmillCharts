package com.nl.treadmilllauncher.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.view.RightSuspendView;

/**
 * Created by dingo on 2018/3/21.
 */

public class SuspendWindowManager {

    private static SuspendWindowManager INSTANCE = null;
    private static WindowManager mWindowManager;
    private Context context;

    private View topSuspendView;
    private View bottomSuspendView;
    private View rightSuspendView;

    private WindowManager.LayoutParams topLayoutParams;
    private WindowManager.LayoutParams bottomLayoutParams;
    private WindowManager.LayoutParams rightLayoutParams;

    private boolean isTopViewAdded = false;
    private boolean isBottomViewAdded = false;
    private boolean isRightViewAdded = false;

    private SuspendWindowManager(){

    }

    public static SuspendWindowManager getSuspendWindowManager(Context context){
        if(INSTANCE == null){
            INSTANCE = new SuspendWindowManager();
            INSTANCE.context = context.getApplicationContext();
        }
        return INSTANCE;
    }

    private WindowManager getWindowManager(){
        if(mWindowManager == null){
            mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

            topLayoutParams = new WindowManager.LayoutParams();
            topLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            topLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            topLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            topLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            topLayoutParams.gravity = Gravity.TOP;
            topLayoutParams.format = PixelFormat.TRANSPARENT;

            rightLayoutParams = new WindowManager.LayoutParams();
            rightLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            rightLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            rightLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            rightLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            rightLayoutParams.gravity = Gravity.RIGHT;
            rightLayoutParams.format = PixelFormat.TRANSPARENT;

            bottomLayoutParams = new WindowManager.LayoutParams();
            bottomLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            bottomLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            bottomLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            bottomLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            bottomLayoutParams.gravity = Gravity.BOTTOM;
            bottomLayoutParams.format = PixelFormat.TRANSPARENT;
        }
        return mWindowManager;
    }

    private View getSuspendView(Context context, int layoutId){
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public void addTopSuspendWin(){
        if(isTopViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        if(topSuspendView == null) {
            topSuspendView = getSuspendView(context, R.layout.top_suspend_window_layout);
        }
        wm.addView(topSuspendView, topLayoutParams);
        isTopViewAdded = true;
    }

    public void addRightSuspendWin(){
        if(isRightViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        if(rightSuspendView == null) {
            rightSuspendView = getSuspendView(context, R.layout.right_suspend_window_layout);
        }
        RightSuspendView rightView = new RightSuspendView(rightSuspendView);
        rightView.initView();
        wm.addView(rightSuspendView, rightLayoutParams);
        isRightViewAdded = true;
    }

    public void addBottomSuspendWin(){
        if(isBottomViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        if(bottomSuspendView == null) {
            bottomSuspendView = getSuspendView(context, R.layout.bottom_suspend_window_layout);
        }
        wm.addView(bottomSuspendView, bottomLayoutParams);
        isBottomViewAdded = true;
    }

    public void removeTopSuspendWin(){
        if(!isTopViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        wm.removeView(topSuspendView);
        isTopViewAdded = false;
    }

    public void removeRightSuspendWin(){
        if(!isRightViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        wm.removeView(rightSuspendView);
        isRightViewAdded = false;
    }

    public void removeBottomSuspendWin(){
        if(!isBottomViewAdded){
            return;
        }
        WindowManager wm = getWindowManager();
        wm.removeView(bottomSuspendView);
        isBottomViewAdded = false;
    }
}
