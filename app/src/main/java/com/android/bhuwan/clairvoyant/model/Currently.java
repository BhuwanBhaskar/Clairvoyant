package com.android.bhuwan.clairvoyant.model;

import com.android.bhuwan.clairvoyant.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by bhuwan on 9/30/15.
 */
public class Currently {
    private long mTime;
    private String mSummary;
    private String mIcon;
    private double mPrecipeProb;
    private double mTemp;
    private double mHumidity;
    private String mTimeZone;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String formattedTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("h : mm a");
        sdf.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(getTime()*1000);

        return sdf.format(date);
    }

    public int  selectIcons(){
        return Forecast.getIconId(mIcon);
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getPrecipeProb() {
        return (int)Math.round(mPrecipeProb*100);
    }

    public void setPrecipeProb(double precipeProb) {
        mPrecipeProb = precipeProb;
    }

    public int getTemp() {
        return (int)Math.round(mTemp);
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }
}
