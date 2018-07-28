package com.nl.treadmilllauncher.base;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dingo on 2018/3/17.
 */

public abstract class BaseTopActivity extends BaseActivity {

    public static final int UPDATE_TIME = 0x01;

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

    private Timer timer;
    private final Calendar c = Calendar.getInstance();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_TIME:
                    c.setTimeInMillis(System.currentTimeMillis());
                    String time = String.format("%02d.%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                    time_tv.setText(time);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void initView() {
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

    @Override
    public void loadData() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(UPDATE_TIME);
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        mHandler.removeMessages(UPDATE_TIME);
        super.onDestroy();
    }

    @OnClick({R.id.back_ib, R.id.setting_panel})
    public void onTopStatusBarViewClick(View v){
        if(R.id.back_ib == v.getId()){
            finish();
        }else if(R.id.setting_panel == v.getId()){

        }
    }
}
