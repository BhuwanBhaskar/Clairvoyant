package com.android.bhuwan.clairvoyant.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bhuwan.clairvoyant.R;
import com.android.bhuwan.clairvoyant.model.Currently;
import com.android.bhuwan.clairvoyant.model.Daily;
import com.android.bhuwan.clairvoyant.model.Forecast;
import com.android.bhuwan.clairvoyant.model.Hourly;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String WEEKLY_FORECAST = "WEEKLY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    private Forecast mForecast = new Forecast();

    @Bind(R.id.time) TextView mTime;
    @Bind(R.id.humidityVal) TextView mHumidity;
    @Bind(R.id.icons) ImageView mIcons;
    @Bind(R.id.precipeVal) TextView mPrecipe;
    @Bind(R.id.summary) TextView mSummary;
    @Bind(R.id.location) TextView mLocation;
    @Bind(R.id.temp) TextView mTemp;
    @Bind(R.id.refresh) ImageView mRefresh;
    @Bind(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final double lattitude = 37.8267;
        final double longitude = -122.423;
        mProgressBar.setVisibility(View.INVISIBLE);

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getForecast(lattitude, longitude);
            }
        });
        getForecast(lattitude, longitude);
    }

    private void getForecast(double lattitude, double longitude) {
        String pkey = "fff4a7807f192352c636a478ba7cf4b7";
        String url = "https://api.forecast.io/forecast/" + pkey + "/"+lattitude + "," + longitude;
        if(isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    //alertUser("Oops","Server might be busy,Try Again !!","OK");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                            alertUser();
                        }
                    });

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toggleRefresh();
                            }
                        });
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()){
                            mForecast = populateForecast(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUiThread();
                                }
                            });
                        }
                        else
                            alertUser();
                            //alertUser("Oops","seems to be some problem,Try Again !!","OK");
                    } catch (IOException e) {
                        Log.e(TAG, " Exception Caught : " + e);
                    }
                    catch(JSONException e){
                        Log.e(TAG, " Exception Caught : " + e);
                    }
                }
            });
        }
        else
            Toast.makeText(this, "Network Not Available !!", Toast.LENGTH_LONG);
        //alertUser("Oops","Network Unavailable","OK");

        //Below Code is an example of Synchronous Connection and it runs on Main UI thread
        //so whenever you run it ,you will see an exception
        //You should use asynchronous methods to run connection on worker thread :)
        /*
        try {
            Response response = call.execute();
            if(response.isSuccessful())
                Log.v(TAG,response.body().string());
        } catch (IOException e) {
            Log.e(TAG, " Exception Caught : " + e);
        }*/
    }

    private void updateUiThread() {
        Currently cw = mForecast.getCurrently();

        mHumidity.setText(cw.getHumidity() + "");
        mTemp.setText(cw.getTemp() + "");
        mPrecipe.setText(cw.getPrecipeProb() + "%");
        mSummary.setText(cw.getSummary());
        mTime.setText("At " + cw.formattedTime() + " it will be ");

        Drawable drawable = getResources().getDrawable(cw.selectIcons());
        mIcons.setImageDrawable(drawable);
    }

    private Forecast populateForecast(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrently(populateCurrentWeather(jsonData));
        forecast.setHourlies(populateHouralies(jsonData));
        forecast.setDaily(populateDailies(jsonData));
        return forecast;
    }

    private Daily[] populateDailies(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject dailyForecast = forecast.getJSONObject("daily");
        JSONArray dailies = dailyForecast.getJSONArray("data");

        Daily[] daily = new Daily[dailies.length()];

        for(int i = 0; i < daily.length; i++){
            JSONObject eachDay = dailies.getJSONObject(i);
            Daily mDaily = new Daily();
            mDaily.setTimeZone(timezone);
            mDaily.setTime(eachDay.getInt("time"));
            mDaily.setSummary(eachDay.getString("summary"));
            mDaily.setIcon(eachDay.getString("icon"));
            mDaily.setTempMax(eachDay.getDouble("temperatureMax"));
            mDaily.setTempMin(eachDay.getDouble("temperatureMin"));
            daily[i] = mDaily;
        }

        return daily;
    }

    private Hourly[] populateHouralies(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject hourlyForecast = forecast.getJSONObject("hourly");
        JSONArray houralies = hourlyForecast.getJSONArray("data");

        Hourly[] hours = new Hourly[houralies.length()];

        for(int i = 0; i < hours.length; i++){
            JSONObject eachHour = houralies.getJSONObject(i);
            Hourly hour = new Hourly();
            hour.setIcon(eachHour.getString("icon"));
            hour.setSummary(eachHour.getString("summary"));
            hour.setTemp(eachHour.getDouble("temperature"));
            hour.setTime(eachHour.getInt("time"));
            hour.setTimeZone(timezone);
            hours[i] = hour;
        }
        return hours;
    }

    private  Currently populateCurrentWeather(String jsonData) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonData);
        Currently cw = new Currently();

        cw.setTimeZone(jsonObj.getString("timezone"));
        JSONObject currentObject = jsonObj.getJSONObject("currently");
        cw.setTime(currentObject.getLong("time"));
        cw.setSummary(currentObject.getString("summary"));
        cw.setIcon(currentObject.getString("icon"));
        cw.setPrecipeProb(currentObject.getDouble("precipProbability"));
        cw.setTemp(currentObject.getDouble("temperature"));
        cw.setHumidity(currentObject.getDouble("humidity"));

        Log.d(TAG, cw.getSummary());
        return cw;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if(info != null && info.isConnected())
            return true;
        else
            return false;
    }

    private void alertUser() {
        AlertUser alert = new AlertUser();
        alert.show(getFragmentManager(),"error_dialog");
    }

    private void toggleRefresh(){
        if(mProgressBar.getVisibility() == View.INVISIBLE){
            mRefresh.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            mRefresh.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }
    @OnClick(R.id.weeklyButton)
    public void startOnWeekly(View view){
        Intent intent = new Intent(this,DailyForecastActivity.class);
        intent.putExtra(WEEKLY_FORECAST,mForecast.getDaily());
        startActivity(intent);
    }
    @OnClick(R.id.hourlyButton)
    public void startOnHourly(View view){
        Intent intent = new Intent(this,HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST, mForecast.getHourlies());
        startActivity(intent);
    }
}
