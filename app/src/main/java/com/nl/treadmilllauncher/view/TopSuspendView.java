package com.nl.treadmilllauncher.view;

import android.view.View;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;

/**
 * Created by dingo on 2018/3/22.
 */

public class TopSuspendView {

    private TextView slope_value_tv;
    private TextView duraion_value_tv;
    private TextView distance_value_tv;
    private TextView kcal_value_tv;
    private TextView hr_value_tv;
    private TextView speed_value_tv;

    public TopSuspendView(View view){

        slope_value_tv = view.findViewById(R.id.slope_value_tv);
        duraion_value_tv = view.findViewById(R.id.duraion_value_tv);
        distance_value_tv = view.findViewById(R.id.distance_value_tv);
        kcal_value_tv = view.findViewById(R.id.kcal_value_tv);
        hr_value_tv = view.findViewById(R.id.hr_value_tv);
        speed_value_tv = view.findViewById(R.id.speed_value_tv);
    }

    public void refreshValues(Object...args){
        if(args.length != 5) return;
        slope_value_tv.setText(String.format("%d", args[0]));
        duraion_value_tv.setText(String.valueOf(args[1]));
        distance_value_tv.setText(String.format("%.2f", args[2]));
        kcal_value_tv.setText(String.format("%d", args[3]));
        hr_value_tv.setText(String.format("%d", args[4]));
        speed_value_tv.setText(String.format("%.1f", args[5]));
    }
}
