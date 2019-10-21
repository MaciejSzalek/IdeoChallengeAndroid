package com.ideochallenge.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.ideochallenge.R;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.database.HistoryRoute;
import com.ideochallenge.database.RouteCreator;
import com.ideochallenge.dialogs.NetworkDialog;
import com.ideochallenge.models.Route;
import com.ideochallenge.nearby.NearbyDataGetter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private List<Route> routeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DBHelper dbHelper = new DBHelper(this);

        progressBar = findViewById(R.id.start_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setProgress(0);

        if(isConnectedToNetwork()){
            try {
                routeList = dbHelper.getAllRoute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(routeList.isEmpty()){
                new DatabaseTask().execute();
            } else {
                lunchProgressBar();
            }
        } else {
            NetworkDialog.showNetworkDialog(this);
        }
    }

    private class DatabaseTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        protected String doInBackground(String... strings) {
            createRouteCategory();
            createAllRoute();
            getNearbyFromGoogle();
            return null;
        }

        @Override
        protected void onPostExecute(String string){
            super.onPostExecute(string);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(100);
                    goToMapActivity();
                    finish();
                }
            }, 2000);
        }
    }

    private void createRouteCategory(){
        try {
            RouteCreator.createRoute(this);
            progressBar.setProgress(25);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAllRoute(){
        try {
            HistoryRoute.createHistoryRoute(this);
            progressBar.setProgress(50);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToMapActivity(){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private boolean isConnectedToNetwork(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }
        return isConnected;
    }

    private void getNearbyFromGoogle(){
        List<String> typeList = new ArrayList<>();
        typeList.add("bank");
        typeList.add("atm");
        typeList.add("school");
        for(String type: typeList){
            String url = getNearbyUrl(50.032397, 22.000574, type);
            Object[] DataTransfer = new Object[1];
            DataTransfer[0] = url;
            NearbyDataGetter getNearbyPlacesData = new NearbyDataGetter(StartActivity.this);
            getNearbyPlacesData.execute(DataTransfer);
        }
        progressBar.setProgress(75);
    }

    private String getNearbyUrl(double latitude, double longitude, String nearbyPlace) {
        int PROXIMITY_RADIUS = 5000;
        StringBuilder googlePlacesUrl =
                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + getString(R.string.API_KEY));
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    private void lunchProgressBar(){
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1; i<=100; i++){
                    try {
                        Thread.sleep(100);
                        progressBar.setProgress(i);
                        i = i + 1;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                goToMapActivity();
                finish();
            }
        }).start();
    }
}
