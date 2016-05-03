package com.android.bhuwan.clairvoyant.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by bhuwan on 10/5/15.
 */
public class Daily implements Parcelable{

    private String mSummary;
    private double mTempMax,mTempMin;
    private long mTime;
    private String mIcon;
    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTempMax() {
        return (int)Math.round(mTempMax);
    }

    public void setTempMax(double tempMax) {
        mTempMax = tempMax;
    }

    public double getTempMin() {
        return mTempMin;
    }

    public void setTempMin(double tempMin) {
        mTempMin = tempMin;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getDayofTheWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        sdf.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date date = new Date(mTime*1000);
        return sdf.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSummary);
        dest.writeDouble(mTempMax);
        dest.writeDouble(mTempMin);
        dest.writeString(mIcon);
        dest.writeLong(mTime);
        dest.writeString(mTimeZone);
    }

    private Daily(Parcel src){
        mSummary = src.readString();
        mTempMax = src.readDouble();
        mTempMin = src.readDouble();
        mIcon = src.readString();
        mTime = src.readLong();
        mTimeZone = src.readString();
    }
    public Daily(){}

    public static final Creator<Daily> CREATOR = new Creator<Daily>(){

        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
