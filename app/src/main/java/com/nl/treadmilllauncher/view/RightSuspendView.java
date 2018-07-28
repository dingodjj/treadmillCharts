package com.nl.treadmilllauncher.view;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.adapter.AppAdapter;
import com.nl.treadmilllauncher.model.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingo on 2018/3/21.
 */

public class RightSuspendView {

    private RecyclerView appRecyclerView;
    private Context context;

    public RightSuspendView(View view){
        context = view.getContext();
        appRecyclerView = view.findViewById(R.id.app_recycleview);
    }

    public void initView(){
        appRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        appRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 0,
                0, (int)context.getResources().getDimension(R.dimen.y40)));
        List<AppInfo> appInfoList = getAppInfos();
        AppAdapter appAdapter = new AppAdapter(context, appInfoList);
        appRecyclerView.setAdapter(appAdapter);
    }

    public List<AppInfo> getAppInfos(){
        //首先获取到包的管理者
        PackageManager packageManager = context.getPackageManager();
        //获取到所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<>();
        for (PackageInfo installedPackage : installedPackages) {
            int flags = installedPackage.applicationInfo.flags;
            if((flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;
            AppInfo appInfo = new AppInfo();
            //程序包名
            String packageName = installedPackage.packageName;
            appInfo.setPackageName(packageName);
            //获取到图标
            Drawable icon = installedPackage.applicationInfo.loadIcon(packageManager);
            appInfo.setAppIcon(icon);
            //获取到应用的名字
            String appName = installedPackage.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setAppName(appName);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
