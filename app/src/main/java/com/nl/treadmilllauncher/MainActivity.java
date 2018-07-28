package com.nl.treadmilllauncher;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import com.nl.treadmilllauncher.base.BaseActivity;
import com.nl.treadmilllauncher.service.SuspendWindowMgrService;
import com.nl.treadmilllauncher.ui.ChooseModeActivity;
import com.nl.treadmilllauncher.ui.RunActivity;
import com.nl.treadmilllauncher.ui.TestActivity;
import com.nl.treadmilllauncher.utils.BitmapUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.root_viewgroup)
    ConstraintLayout root_viewgroup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
        BitmapUtils.loadBigBackgroundBitmap(this, R.mipmap.main_bg, root_viewgroup);
    }

    @Override
    public void loadData() {
        startService(new Intent(this, SuspendWindowMgrService.class));
    }

    @OnClick({R.id.start_btn, R.id.goal_btn, R.id.phy_ability_btn, R.id.train_btn})
    void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.start_btn:
                intent.setClass(this, RunActivity.class);
                break;
            case R.id.goal_btn:
                intent.setClass(this, ChooseModeActivity.class);
                intent.putExtra("TYPE", "goal");
                break;
            case R.id.phy_ability_btn:
                intent.setClass(this, ChooseModeActivity.class);
                intent.putExtra("TYPE", "phy");
                break;
            case R.id.train_btn:
                intent.setClass(this, TestActivity.class);
                intent.putExtra("TYPE", "train");
                break;
        }

        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, SuspendWindowMgrService.class));
        super.onDestroy();
    }
}
