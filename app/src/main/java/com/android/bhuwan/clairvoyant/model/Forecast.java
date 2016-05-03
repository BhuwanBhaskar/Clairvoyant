package com.android.bhuwan.clairvoyant.model;

import com.android.bhuwan.clairvoyant.R;

/**
 * Created by bhuwan on 10/5/15.
 */
public class Forecast {

    private Currently mCurrently;
    private Hourly[] mHourlies;
    private Daily[] mDaily;

    public Currently getCurrently() {
        return mCurrently;
    }

    public void setCurrently(Currently currently) {
        mCurrently = currently;
    }

    public Hourly[] getHourlies() {
        return mHourlies;
    }

    public void setHourlies(Hourly[] hourlies) {
        mHourlies = hourlies;
    }

    public Daily[] getDaily() {
        return mDaily;
    }

    public void setDaily(Daily[] daily) {
        mDaily = daily;
    }
    
    public static int getIconId(String iconString){
        int iconId = R.mipmap.clear_day;

        if (iconString.equals("clear-day")) {
            iconId = R.mipmap.clear_day;
        }
        else if (iconString.equals("clear-night")) {
            iconId = R.mipmap.clear_night;
        }
        else if (iconString.equals("rain")) {
            iconId = R.mipmap.rain;
        }
        else if (iconString.equals("snow")) {
            iconId = R.mipmap.snow;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.mipmap.sleet;
        }
        else if (iconString.equals("wind")) {
            iconId = R.mipmap.wind;
        }
        else if (iconString.equals("fog")) {
            iconId = R.mipmap.fog;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.mipmap.cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.mipmap.partly_cloudy;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.mipmap.cloudy_night;
        }
        return iconId;
    }
        
}
