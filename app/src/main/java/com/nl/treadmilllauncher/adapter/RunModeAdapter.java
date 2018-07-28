package com.nl.treadmilllauncher.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.model.RunModeData;
import com.nl.treadmilllauncher.utils.CommonUtils;
import java.util.List;

/**
 * Created by dingo on 2018/3/17.
 */

public class RunModeAdapter extends RecyclerView.Adapter<RunModeAdapter.RunModeViewHolder> {

    private List<RunModeData> modeDataList;
    private Context mContext;
    private OnItemViewClickedListener listener;

    public void setOnItemViewClickedListenerr(OnItemViewClickedListener listener) {
        this.listener = listener;
    }

    public RunModeAdapter(Context context, List<RunModeData> dataSource){
        this.modeDataList = dataSource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RunModeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_mode_item, parent,false);
        return new RunModeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunModeViewHolder holder, int position) {
        final RunModeData data = modeDataList.get(position);
        holder.thumnail_pic.setBackgroundResource(data.getPicId());
        holder.train_category_txt_cn.setText(data.getCategoryTxtCn());
        holder.train_category_txt_en.setText(data.getCategoryTxtEn());
        holder.abstract_tv.setText(data.getAbstractTxt());

        Drawable star_unchosen = mContext.getResources().getDrawable(R.mipmap.star_unchosen);
        Drawable star_chosen = mContext.getResources().getDrawable(R.mipmap.star_chosen);
        if(data.getHardDegree() >= 5){
            holder.star_iv1.setBackground(star_chosen);
            holder.star_iv2.setBackground(star_chosen);
            holder.star_iv3.setBackground(star_chosen);
            holder.star_iv4.setBackground(star_chosen);
            holder.star_iv5.setBackground(star_chosen);
        }else if(data.getHardDegree() == 4){
            holder.star_iv1.setBackground(star_chosen);
            holder.star_iv2.setBackground(star_chosen);
            holder.star_iv3.setBackground(star_chosen);
            holder.star_iv4.setBackground(star_chosen);
            holder.star_iv5.setBackground(star_unchosen);
        }else if(data.getHardDegree() == 3){
            holder.star_iv1.setBackground(star_chosen);
            holder.star_iv2.setBackground(star_chosen);
            holder.star_iv3.setBackground(star_chosen);
            holder.star_iv4.setBackground(star_unchosen);
            holder.star_iv5.setBackground(star_unchosen);
        }else if(data.getHardDegree() == 2){
            holder.star_iv1.setBackground(star_chosen);
            holder.star_iv2.setBackground(star_chosen);
            holder.star_iv3.setBackground(star_unchosen);
            holder.star_iv4.setBackground(star_unchosen);
            holder.star_iv5.setBackground(star_unchosen);
        }else if(data.getHardDegree() == 1){
            holder.star_iv1.setBackground(star_chosen);
            holder.star_iv2.setBackground(star_unchosen);
            holder.star_iv3.setBackground(star_unchosen);
            holder.star_iv4.setBackground(star_unchosen);
            holder.star_iv5.setBackground(star_unchosen);
        }else if(data.getHardDegree() < 1){
            holder.star_iv1.setBackground(star_unchosen);
            holder.star_iv2.setBackground(star_unchosen);
            holder.star_iv3.setBackground(star_unchosen);
            holder.star_iv4.setBackground(star_unchosen);
            holder.star_iv5.setBackground(star_unchosen);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClicked(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modeDataList == null ? 0 : modeDataList.size();
    }

    class RunModeViewHolder extends RecyclerView.ViewHolder{

        private ImageView thumnail_pic;
        private TextView train_category_txt_cn;
        private TextView train_category_txt_en;
        private TextView abstract_tv;
        private ImageView star_iv1;
        private ImageView star_iv2;
        private ImageView star_iv3;
        private ImageView star_iv4;
        private ImageView star_iv5;

        public RunModeViewHolder(View itemView) {
            super(itemView);

            thumnail_pic = itemView.findViewById(R.id.thumnail_pic);
            train_category_txt_cn = itemView.findViewById(R.id.train_category_txt_cn);
            train_category_txt_en = itemView.findViewById(R.id.train_category_txt_en);
            train_category_txt_en.setTypeface(CommonUtils.getTfByPath(mContext, "fonts/airstrike.ttf"));
            abstract_tv = itemView.findViewById(R.id.abstract_tv);
            star_iv1 = itemView.findViewById(R.id.star_iv1);
            star_iv2 = itemView.findViewById(R.id.star_iv2);
            star_iv3 = itemView.findViewById(R.id.star_iv3);
            star_iv4 = itemView.findViewById(R.id.star_iv4);
            star_iv5 = itemView.findViewById(R.id.star_iv5);
        }
    }

    public interface OnItemViewClickedListener{
        void onItemClicked(RunModeData data);
    }
}
