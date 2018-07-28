package com.nl.treadmilllauncher.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.nl.treadmilllauncher.MainApplication;
import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.base.BaseTopActivity;
import com.nl.treadmilllauncher.utils.BitmapUtils;
import com.nl.treadmilllauncher.view.ScrollPickerView;
import com.nl.treadmilllauncher.view.StringScrollPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseWeightActivity extends BaseTopActivity {

    @BindView(R.id.weight_picker_view)
    StringScrollPicker weight_picker_view;
    @BindView(R.id.root_viewgroup)
    View root_viewgroup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_weight;
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
        super.initView();

        BitmapUtils.loadBigBackgroundBitmap(this, R.mipmap.input_weight_bg, root_viewgroup);
    }

    @Override
    public void loadData() {
        super.loadData();
        final List<String> datas = new ArrayList<>();
        for (int i = 35; i < 151; i++) {
            datas.add(String.valueOf(i));
        }
        weight_picker_view.setData(datas);
        weight_picker_view.setSelectedPosition(30);
        weight_picker_view.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Toast.makeText(ChooseWeightActivity.this, "选择的体重是：" + datas.get(position) + "kg", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.last_step_ib, R.id.start_ib})
    public void onViewClick(View view){
        if(R.id.last_step_ib == view.getId()){
            finish();
        }else if(R.id.start_ib == view.getId()){
            ((MainApplication)getApplication()).getActivityLifecycleHelper().finishActivity(ChooseModeActivity.class);
            startActivity(new Intent(this, RunWithDefaultSettingActivity.class));
            finish();
        }
    }
}
