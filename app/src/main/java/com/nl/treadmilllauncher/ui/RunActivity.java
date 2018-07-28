package com.nl.treadmilllauncher.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RunActivity extends BaseActivity {

    @BindView(R.id.root_viewgroup)
    ViewGroup root_viewgroup;

    private void changeBackground(int drawableId){
        Glide.with(root_viewgroup).asDrawable().load(drawableId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>(root_viewgroup.getWidth(), root_viewgroup.getHeight()) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        root_viewgroup.setBackground(resource);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.run_layout;
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

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        changeBackground(R.mipmap.city_bg);
    }

    @OnClick({R.id.speed_animView})
    public void onViewClick(View v){
        if(R.id.speed_animView == v.getId()){
            startActivity(new Intent(this, SportSummaryActivity.class));
        }
    }
}
