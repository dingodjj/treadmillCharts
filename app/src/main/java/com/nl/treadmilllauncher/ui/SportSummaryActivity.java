package com.nl.treadmilllauncher.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.nl.treadmilllauncher.MainActivity;
import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.adapter.SummaryItemAdapter;
import com.nl.treadmilllauncher.base.BaseActivity;
import com.nl.treadmilllauncher.model.SummaryItemForFloatData;
import com.nl.treadmilllauncher.model.SummaryItemForIntData;
import com.nl.treadmilllauncher.utils.CommonUtils;
import com.nl.treadmilllauncher.view.DataItemChartView;
import com.nl.treadmilllauncher.view.TextProgressbar;

import butterknife.BindView;
import butterknife.OnClick;

public class SportSummaryActivity extends BaseActivity {

    @BindView(R.id.sport_summary_abstract)
    View sport_summary_abstract;
    @BindView(R.id.heart_rate_data_item)
    View heart_rate_data_item;
    @BindView(R.id.speed_data_item)
    View speed_data_item;
    @BindView(R.id.slope_data_item)
    View slope_data_item;
    @BindView(R.id.sport_summary_data)
    View sport_summary_data;

    private SummaryItemAdapter hr_adapter;
    private SummaryItemAdapter speed_adapter;
    private SummaryItemAdapter slope_adapter;

    private TextView duraion_name_tv, duraion_value_tv, kcal_value_tv,
            kcal_name_tv, km_value_tv, km_name_tv;

    private TextProgressbar casualness_progressbar, warm_up_progressbar, heart_lung_enhancement_progressbar,
            fat_combustion_progressbar, lactic_acid_threshold_progressbar, limit_sprint_progressbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sport_summary;
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
        duraion_name_tv = sport_summary_abstract.findViewById(R.id.duraion_name_tv);
        duraion_value_tv = sport_summary_abstract.findViewById(R.id.duraion_value_tv);
        kcal_value_tv = sport_summary_abstract.findViewById(R.id.kcal_value_tv);
        kcal_name_tv = sport_summary_abstract.findViewById(R.id.kcal_name_tv);
        km_value_tv = sport_summary_abstract.findViewById(R.id.km_value_tv);
        km_name_tv = sport_summary_abstract.findViewById(R.id.km_name_tv);

        casualness_progressbar = sport_summary_data.findViewById(R.id.casualness_progressbar);
        warm_up_progressbar = sport_summary_data.findViewById(R.id.warm_up_progressbar);
        heart_lung_enhancement_progressbar = sport_summary_data.findViewById(R.id.heart_lung_enhancement_progressbar);
        fat_combustion_progressbar = sport_summary_data.findViewById(R.id.fat_combustion_progressbar);
        lactic_acid_threshold_progressbar = sport_summary_data.findViewById(R.id.lactic_acid_threshold_progressbar);
        limit_sprint_progressbar = sport_summary_data.findViewById(R.id.limit_sprint_progressbar);

        Typeface numTf = CommonUtils.getTfByPath(this, "fonts/MyriadPro-BoldCond.otf");
        Typeface textTf = CommonUtils.getTfByPath(this, "fonts/airstrike.ttf");

        duraion_name_tv.setTypeface(textTf);
        kcal_name_tv.setTypeface(textTf);
        km_name_tv.setTypeface(textTf);

        duraion_value_tv.setTypeface(numTf);
        kcal_value_tv.setTypeface(numTf);
        km_value_tv.setTypeface(numTf);

    }

    @Override
    public void loadData() {
        Intent intent = getIntent();

        SummaryItemForIntData hrData = new SummaryItemForIntData();
        hrData.setDataItemName("心率 / Heart rate");
        hrData.setDataItemAvgName("平均.HR(bmp)");
        hrData.setDataItemMaxName("最高HR");
        hrData.setDataItemMinName("最低(HR)");
        hrData.setIconId(R.mipmap.summary_heart_rate);
        hrData.setAvgValue(130);
        hrData.setMaxValue(190);
        hrData.setMinValue(89);
        hr_adapter = new SummaryItemAdapter(heart_rate_data_item);
        hr_adapter.setItemData(hrData);

        SummaryItemForFloatData speedData = new SummaryItemForFloatData();
        speedData.setDataItemName("速度 / Speed");
        speedData.setDataItemAvgName("平均.速度(km/hr)");
        speedData.setDataItemMaxName("最大速度");
        speedData.setDataItemMinName("最小速度");
        speedData.setIconId(R.mipmap.summary_speed);
        speedData.setAvgValue(8.9f);
        speedData.setMaxValue(15f);
        speedData.setMinValue(5f);
        speed_adapter = new SummaryItemAdapter(speed_data_item);
        speed_adapter.setItemData(speedData);

        SummaryItemForIntData slopeData = new SummaryItemForIntData();
        slopeData.setDataItemName("心率 / Slope");
        slopeData.setDataItemAvgName("平均.坡度(%)");
        slopeData.setDataItemMaxName("最大坡度");
        slopeData.setDataItemMinName("最小坡度");
        slopeData.setIconId(R.mipmap.summary_slope);
        slopeData.setAvgValue(6);
        slopeData.setMaxValue(15);
        slopeData.setMinValue(5);
        slope_adapter = new SummaryItemAdapter(slope_data_item);
        slope_adapter.setItemData(slopeData);
    }

    @OnClick({R.id.summary_finish_ib})
    public void onViewClick(View v){
        if(R.id.summary_finish_ib == v.getId()){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
