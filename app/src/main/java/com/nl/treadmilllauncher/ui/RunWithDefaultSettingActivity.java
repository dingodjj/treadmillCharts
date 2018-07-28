package com.nl.treadmilllauncher.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.base.BaseActivity;
import com.nl.treadmilllauncher.utils.CommonUtils;
import com.nl.treadmilllauncher.view.WaveBackgroundView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dingo on 2018/3/6.
 */

public class RunWithDefaultSettingActivity extends BaseActivity {

    @BindView(R.id.root_viewgroup)
    ViewGroup root_viewgroup;
    @BindView(R.id.time_duration_tv)
    TextView time_duration_tv;
    @BindView(R.id.wavebg)
    WaveBackgroundView wavebg;

    @Override
    public int getLayoutId() {
        return R.layout.run_with_default_setting_layout;
    }

    @Override
    public boolean isNeedFullScreen() {
        return true;
    }

    @Override
    public boolean isNeedKeepScreenOn() {
        return true;
    }

    @Override
    public void initView() {
        time_duration_tv.setTypeface(CommonUtils.getTfByPath(this, "fonts/MyriadPro-BoldCond.otf"));
    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.speed_animView})
    public void onViewClick(View v){
        if(R.id.speed_animView == v.getId()){
//            startActivity(new Intent(this, SportSummaryActivity.class));
            wavebg.startMove();
        }
    }
}
