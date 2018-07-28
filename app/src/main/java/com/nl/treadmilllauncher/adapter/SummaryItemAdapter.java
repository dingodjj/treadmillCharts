package com.nl.treadmilllauncher.adapter;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.model.SummaryItemForFloatData;
import com.nl.treadmilllauncher.model.SummaryItemForIntData;
import com.nl.treadmilllauncher.utils.CommonUtils;
import com.nl.treadmilllauncher.view.DataItemChartView;

/**
 * Created by dingo on 2018/3/20.
 */

public class SummaryItemAdapter {

    private TextView data_item_name, data_item_avg_name_tv, data_item_max_value_tv,
            data_item_max_name_tv, data_item_min_value_tv, data_item_min_name_tv;

    private DataItemChartView dataItemChartView;

    public SummaryItemAdapter(View view){
        if(view == null){
            throw new IllegalArgumentException(" argument can not be null");
        }

        data_item_name = view.findViewById(R.id.data_item_name);
        data_item_avg_name_tv = view.findViewById(R.id.data_item_avg_name_tv);
        data_item_max_value_tv = view.findViewById(R.id.data_item_max_value_tv);
        data_item_max_name_tv = view.findViewById(R.id.data_item_max_name_tv);
        data_item_min_value_tv = view.findViewById(R.id.data_item_min_value_tv);
        data_item_min_name_tv = view.findViewById(R.id.data_item_min_name_tv);
        dataItemChartView = view.findViewById(R.id.dataItemChartView);

        Typeface numTf = CommonUtils.getTfByPath(view.getContext(), "fonts/MyriadPro-BoldCond.otf");
        data_item_max_value_tv.setTypeface(numTf);
        data_item_min_value_tv.setTypeface(numTf);
    }

    public void setItemData(SummaryItemForIntData data){
        data_item_name.setText(data.getDataItemName());
        data_item_avg_name_tv.setText(data.getDataItemAvgName());
        data_item_max_name_tv.setText(data.getDataItemMaxName());
        data_item_min_name_tv.setText(data.getDataItemMinName());

        dataItemChartView.setData(data.getMaxValue(), data.getAvgValue());
        dataItemChartView.setDrawable(data.getIconId());
        data_item_max_value_tv.setText(String.valueOf(data.getMaxValue()));
        data_item_min_value_tv.setText(String.valueOf(data.getMinValue()));
    }

    public void setItemData(SummaryItemForFloatData data){
        data_item_name.setText(data.getDataItemName());
        data_item_avg_name_tv.setText(data.getDataItemAvgName());
        data_item_max_name_tv.setText(data.getDataItemMaxName());
        data_item_min_name_tv.setText(data.getDataItemMinName());
        dataItemChartView.setDrawable(data.getIconId());

        dataItemChartView.setData(data.getMaxValue(), data.getAvgValue());
        data_item_max_value_tv.setText(String.format("%.1f", data.getMaxValue()));
        data_item_min_value_tv.setText(String.format("%.1f", data.getMinValue()));
    }

    public void setIntData(int max, int avg, int min){
        dataItemChartView.setData(max, avg);
        data_item_max_value_tv.setText(String.valueOf(max));
        data_item_min_value_tv.setText(String.valueOf(min));
    }

    public void setIntData(float max, float avg, float min){
        dataItemChartView.setData(max, avg);
        data_item_max_value_tv.setText(String.format("%.1f", max));
        data_item_min_value_tv.setText(String.format("%.1f", min));
    }
}
