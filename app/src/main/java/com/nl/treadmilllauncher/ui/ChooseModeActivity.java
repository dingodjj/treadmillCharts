package com.nl.treadmilllauncher.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.base.BaseTopActivity;
import com.nl.treadmilllauncher.adapter.RunModeAdapter;
import com.nl.treadmilllauncher.model.RunModeData;
import com.nl.treadmilllauncher.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseModeActivity extends BaseTopActivity {

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.goal_radio_btn)
    RadioButton goal_radio_btn;
    @BindView(R.id.train_radio_btn)
    RadioButton train_radio_btn;
    @BindView(R.id.phy_test_radio_btn)
    RadioButton phy_test_radio_btn;

    private RunModeAdapter adapter;
    private List<RunModeData> dataSource = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_mode;
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
    }

    @Override
    public void loadData() {
        super.loadData();

        generateTrainData();
        adapter = new RunModeAdapter(this, dataSource);
        adapter.setOnItemViewClickedListenerr(new RunModeAdapter.OnItemViewClickedListener() {
            @Override
            public void onItemClicked(RunModeData data) {
                startActivity(new Intent(ChooseModeActivity.this, ChooseWeightActivity.class));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(0, 0, 0, (int)getResources().getDimension(R.dimen.y40));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String type = intent.getStringExtra("TYPE");
        if("goal".equals(type)){
            goal_radio_btn.setChecked(true);
        }else if("phy".equals(type)){
            phy_test_radio_btn.setChecked(true);
        }else if("train".equals(type)){
            train_radio_btn.setChecked(true);
        }
    }

    private void generateTrainData() {
        dataSource.clear();

        RunModeData data1 = new RunModeData();
        data1.setPicId(R.mipmap.heart_lung_train_pic);
        data1.setCategoryTxtCn("心肺训练");
        data1.setCategoryTxtEn("ENDURANCE");
        data1.setAbstractTxt("通过心肺训练达到跑步最佳效果");
        data1.setHardDegree(2);
        dataSource.add(data1);

        RunModeData data2 = new RunModeData();
        data2.setPicId(R.mipmap.physical_train_pic);
        data2.setCategoryTxtCn("体能训练");
        data2.setCategoryTxtEn("STAMINA");
        data2.setAbstractTxt("通过体能训练达到跑步最佳效果");
        data2.setHardDegree(3);
        dataSource.add(data2);

        RunModeData data3 = new RunModeData();
        data3.setPicId(R.mipmap.lose_fat_train_pic);
        data3.setCategoryTxtCn("减脂训练");
        data3.setCategoryTxtEn("FAT");
        data3.setAbstractTxt("通过减脂训练达到跑步最佳效果");
        data3.setHardDegree(4);
        dataSource.add(data3);
    }
}
