package com.android.bhuwan.clairvoyant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bhuwan.clairvoyant.R;
import com.android.bhuwan.clairvoyant.model.Daily;

/**
 * Created by bhuwan on 10/5/15.
 */
public class DailyAdapter extends BaseAdapter {

    private Context mContext;
    private Daily[] mDaily;

    public DailyAdapter(Context context, Daily[] daily){
        mContext = context;
        mDaily = daily;
    }
    @Override
    public int getCount() {
        return mDaily.length;
    }

    @Override
    public Object getItem(int position) {
        return mDaily[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;

        if(convertView == null){
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.forecast_entry,null);
            mViewHolder.icon = (ImageView)convertView.findViewById(R.id.iconIV);
            mViewHolder.day = (TextView)convertView.findViewById(R.id.dayTV);
            mViewHolder.tempLabel = (TextView)convertView.findViewById(R.id.tempLabel);
            convertView.setTag(mViewHolder);
        }
        else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Daily daily = mDaily[position];
        if(position == 0){
            mViewHolder.day.setText("Today");
        }
        else{
            mViewHolder.day.setText(daily.getDayofTheWeek());
        }
        mViewHolder.icon.setImageResource(daily.getIconId());
        mViewHolder.tempLabel.setText(daily.getTempMax() + "");
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView tempLabel;
        TextView day;
    }
}
