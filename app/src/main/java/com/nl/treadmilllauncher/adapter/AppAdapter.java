package com.nl.treadmilllauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.model.AppInfo;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder>{

    private List<AppInfo> dataSource;
    private Context mContext;

    public AppAdapter(Context context, List<AppInfo> data){
        this.dataSource = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.app_item_layout, null);
        return new AppViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        final AppInfo ai = dataSource.get(position);
        holder.app_icon_iv.setBackground(ai.getAppIcon());

        String appName = ai.getAppName();
        if(!TextUtils.isEmpty(appName) && appName.length() > 4){
            appName = appName.substring(0, 4) + "..";
        }
        holder.app_name_tv.setText(appName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = mContext.getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage(ai.getPackageName());
                if(intent != null){
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

    class AppViewHolder extends RecyclerView.ViewHolder{

        ImageView app_icon_iv;
        TextView app_name_tv;

        public AppViewHolder(View itemView) {
            super(itemView);

            app_icon_iv = itemView.findViewById(R.id.app_icon_iv);
            app_name_tv = itemView.findViewById(R.id.app_name_tv);
        }
    }
}