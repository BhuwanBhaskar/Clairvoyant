package com.android.bhuwan.clairvoyant.controller;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bhuwan.clairvoyant.R;
import com.android.bhuwan.clairvoyant.adapter.DailyAdapter;
import com.android.bhuwan.clairvoyant.model.Daily;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

//we can extend it to Activity but then we need to inject ListView
public class DailyForecastActivity extends ListActivity {
    private Daily[] mDaily;
    //@Bind(android.R.id.list) ListView mListView;
    //@Bind(android.R.id.empty) TextView mEmptyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        //ButterKnife.Bind(this);
        /*String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                            android.R.layout.simple_list_item_1,days);
        setListAdapter(adapter);
        */
        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.WEEKLY_FORECAST);
        mDaily = Arrays.copyOf(parcelables,parcelables.length,Daily[].class);

        DailyAdapter adapter = new DailyAdapter(this,mDaily);
        setListAdapter(adapter);
       /* mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //same as onListItemClick(...)
            }
        });
        */
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Daily daily = mDaily[position];
        int temp = daily.getTempMax();
        String summary = daily.getSummary();
        String day = daily.getDayofTheWeek();

        String message = String.format("On %s maximum temperature will be %s and overall day will be" +
                " %s ",day,temp+"",summary);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
