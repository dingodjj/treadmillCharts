package com.nl.treadmilllauncher.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.view.CompassScaleView;
import com.nl.treadmilllauncher.view.StepArcView;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/30.
 */

public class WalkActivity extends Activity{

    private static final String TAG = "RunActivity";

    public static final int UPDATE_TIME = 0x01;
    public static final int UPDATE_RUN_DURATION = 0x02;

    @BindView(R.id.root_viewgroup)
    ViewGroup root_viewgroup;

    @BindView(R.id.setting_ib)
    ImageButton setting_ib;
    @BindView(R.id.shorten_ib)
    ImageButton shorten_ib;
    @BindView(R.id.date_tv)
    TextView date_tv;
    @BindView(R.id.week_day_tv)
    TextView week_day_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.wifi_ib)
    ImageButton wifi_ib;
    @BindView(R.id.system_msg_ib)
    ImageButton system_msg_ib;

    @BindView(R.id.slope_up_ib)
    ImageButton slope_up_ib;
    @BindView(R.id.slope_down_ib)
    ImageButton slope_down_ib;
    @BindView(R.id.speed_up_ib)
    ImageButton speed_up_ib;
    @BindView(R.id.speed_down_ib)
    ImageButton speed_down_ib;

    @BindView(R.id.slope_stepArcView)
    StepArcView slope_stepArcView;
    @BindView(R.id.speed_stepArcView)
    StepArcView speed_stepArcView;
    @BindView(R.id.compassScaleView)
    CompassScaleView compassScaleView;

    @BindView(R.id.bg_thumbnail_tv)
    TextView bg_thumbnail_tv;
    @BindView(R.id.cal_value_tv)
    TextView cal_value_tv;
    @BindView(R.id.speed_value_tv)
    TextView speed_value_tv;
    @BindView(R.id.duration_value_tv)
    TextView duration_value_tv;

    private Unbinder unbinder;
    private Timer timer;
    private final Calendar c = Calendar.getInstance();

    private boolean isRunning = false;
    private int current_slope = 54;
    private int current_speed = 79;
    private int max_slope = 10;
    private int max_speed = 10;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TIME:
                    c.setTimeInMillis(System.currentTimeMillis());
                    String time = String.format("%02d.%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                    time_tv.setText(time);
                    break;
                case UPDATE_RUN_DURATION:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.run_layout_backup);

        unbinder = ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initData() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE_TIME);
                if (isRunning) {

                }
            }
        }, 0, 1000);
    }

    private void initView() {
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        //日期控件
        String date = String.format("%d.%02d.%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH));
        date_tv.setText(date);

        //星期几控件
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        week_day_tv.setText("星期" + mWay);

        //时间控件
        String time = String.format("%02d.%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        time_tv.setText(time);
    }

    private void changeBackground(int drawableId){
        Glide.with(root_viewgroup).asDrawable().load(drawableId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>(root_viewgroup.getWidth(), root_viewgroup.getHeight()) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        root_viewgroup.setBackground(resource);
                    }
                });

        Glide.with(bg_thumbnail_tv).asDrawable().load(drawableId)
                .into(new SimpleTarget<Drawable>(bg_thumbnail_tv.getWidth(), bg_thumbnail_tv.getHeight()) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        bg_thumbnail_tv.setBackground(resource);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        changeBackground(R.mipmap.city_bg);
    }

    @Override
    protected void onResume() {
        super.onResume();

        slope_stepArcView.setCurrentCount(max_slope, current_slope / 10f);
        speed_stepArcView.setCurrentCount(max_speed, current_speed  / 10f);
        compassScaleView.setDistance(3.6f);
        compassScaleView.setData(new int[]{30, 40, 50, 60, 70, 80});
        compassScaleView.startPointAnimator(1000);
        compassScaleView.startRingAnimator(10000, 1);
    }

    @OnClick({R.id.setting_ib, R.id.shorten_ib, R.id.system_msg_ib, R.id.bg_thumbnail_tv,
            R.id.slope_up_ib, R.id.slope_down_ib, R.id.speed_down_ib, R.id.speed_up_ib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_ib:
                break;
            case R.id.shorten_ib:
                break;
            case R.id.system_msg_ib:
                break;
            case R.id.bg_thumbnail_tv:
                break;
            case R.id.slope_up_ib:
                if(current_slope == max_slope * 10)return;
                current_slope += 1;
                slope_stepArcView.setCurrentCount(max_slope, current_slope / 10f);
                break;
            case R.id.slope_down_ib:
                if(current_slope == 0)return;
                current_slope -= 1;
                slope_stepArcView.setCurrentCount(max_slope, current_slope / 10f);
                break;
            case R.id.speed_down_ib:
                if(current_speed == 0)return;
                current_speed -= 1;
                speed_stepArcView.setCurrentCount(max_speed, current_speed  / 10f);
                break;
            case R.id.speed_up_ib:
                if(current_speed == max_speed  * 10)return;
                current_speed += 1;
                speed_stepArcView.setCurrentCount(max_speed, current_speed  / 10f);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        compassScaleView.stopRingAnimator();
        compassScaleView.clearBitmap();
        timer.cancel();
        unbinder.unbind();
        super.onDestroy();
    }
}
