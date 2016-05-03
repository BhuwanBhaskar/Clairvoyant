package com.android.bhuwan.clairvoyant.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by bhuwan on 10/5/15.
 */
public class Hourly implements Parcelable{
    private String mSummary;
    private double mTemp;
    private long mTime;
    private String mIcon;
    private String mTimeZone;

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public int getTemp() {
        return (int)Math.round(mTemp);
    }

    public void setTemp(double temp) {
        mTemp = temp;
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

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getHour(){
        SimpleDateFormat sdf = new SimpleDateFormat("h a");
        sdf.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date date = new Date(mTime*1000);
        return sdf.format(date);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSummary);
        dest.writeDouble(mTemp);
        dest.writeString(mIcon);
        dest.writeLong(mTime);
        dest.writeString(mTimeZone);
    }

    public Hourly(){}

    private Hourly(Parcel src){
        mSummary = src.readString();
        mTemp = src.readDouble();
        mIcon = src.readString();
        mTime = src.readLong();
        mTimeZone = src.readString();
    }

    public static final Creator<Hourly> CREATOR = new Creator<Hourly>(){

        @Override
        public Hourly createFromParcel(Parcel source) {
            return new Hourly(source);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };
}
