package com.nl.treadmilllauncher.model;

/**
 * Created by dingo on 2018/3/17.
 */

public class RunModeData {
    private int picId;
    private String categoryTxtCn;
    private String categoryTxtEn;
    private String abstractTxt;
    private int hardDegree;

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getCategoryTxtCn() {
        return categoryTxtCn;
    }

    public void setCategoryTxtCn(String categoryTxtCn) {
        this.categoryTxtCn = categoryTxtCn;
    }

    public String getCategoryTxtEn() {
        return categoryTxtEn;
    }

    public void setCategoryTxtEn(String categoryTxtEn) {
        this.categoryTxtEn = categoryTxtEn;
    }

    public String getAbstractTxt() {
        return abstractTxt;
    }

    public void setAbstractTxt(String abstractTxt) {
        this.abstractTxt = abstractTxt;
    }

    public int getHardDegree() {
        return hardDegree;
    }

    public void setHardDegree(int hardDegree) {
        this.hardDegree = hardDegree;
    }
}
