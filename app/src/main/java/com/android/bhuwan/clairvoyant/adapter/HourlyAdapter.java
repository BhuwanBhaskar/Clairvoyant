package com.android.bhuwan.clairvoyant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bhuwan.clairvoyant.R;
import com.android.bhuwan.clairvoyant.model.Hourly;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bhuwan on 10/6/15.
 */
public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>  {

    public Hourly[] mHourlies;
    private Context mContext;

    public HourlyAdapter(Context context, Hourly[] hourly){
        mContext = context;
        mHourlies = hourly;
    }
    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.hourly_forecast_entry,parent,false);
        HourlyViewHolder mHourlyViewHolder = new HourlyViewHolder(view);

        return mHourlyViewHolder;
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        holder.bindHour(mHourlies[position]);
    }

    @Override
    public int getItemCount() {
        return mHourlies.length;
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @Bind(R.id.timeLabel)
        TextView mTime;
        @Bind(R.id.tempLabel)
        TextView mTemp;
        @Bind(R.id.summarylabel)
        TextView mSummary;
        @Bind(R.id.iconImageView)
        ImageView mIcon;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }


        public void bindHour(Hourly hourly) {
            mTime.setText(hourly.getHour());
            mTemp.setText(hourly.getTemp() + "");
            mIcon.setImageResource(hourly.getIconId());
            mSummary.setText(hourly.getSummary());
        }

        @Override
        public void onClick(View v) {
            String time = mTime.getText().toString();
            String summary = mSummary.getText().toString();
            String temp = mTemp.getText().toString();
            
            String message = String.format("At %s temperature will be %s and %s",time,temp,summary);

            Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
        }
    }
}
