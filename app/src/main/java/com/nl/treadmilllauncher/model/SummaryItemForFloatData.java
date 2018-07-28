package com.nl.treadmilllauncher.model;

import java.io.Serializable;

/**
 * Created by dingo on 2018/3/20.
 */

public class SummaryItemForFloatData implements Serializable{

    private String dataItemName;
    private String dataItemAvgName;
    private String dataItemMaxName;
    private String dataItemMinName;
    private int iconId;
    private float avgValue;
    private float maxValue;
    private float minValue;

    public String getDataItemName() {
        return dataItemName;
    }

    public void setDataItemName(String dataItemName) {
        this.dataItemName = dataItemName;
    }

    public String getDataItemAvgName() {
        return dataItemAvgName;
    }

    public void setDataItemAvgName(String dataItemAvgName) {
        this.dataItemAvgName = dataItemAvgName;
    }

    public String getDataItemMaxName() {
        return dataItemMaxName;
    }

    public void setDataItemMaxName(String dataItemMaxName) {
        this.dataItemMaxName = dataItemMaxName;
    }

    public String getDataItemMinName() {
        return dataItemMinName;
    }

    public void setDataItemMinName(String dataItemMinName) {
        this.dataItemMinName = dataItemMinName;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public float getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(float avgValue) {
        this.avgValue = avgValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }
}
