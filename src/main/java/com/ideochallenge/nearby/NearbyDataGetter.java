package com.ideochallenge.nearby;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.NearbyPlace;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-18.
 */

public class NearbyDataGetter extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private Context context;

    public NearbyDataGetter (Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            String url = (String) params[0];
            NearbyDownloadUrl downloadUrl = new NearbyDownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesMap;
        NearbyDataParser dataParser = new NearbyDataParser();
        nearbyPlacesMap =  dataParser.parse(result);
        getNearbyPlaceList(nearbyPlacesMap);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void getNearbyPlaceList(List<HashMap<String, String>> nearbyPlacesMap) {
        DBHelper dbHelper = new DBHelper(context);
        for (int i = 0; i < nearbyPlacesMap.size(); i++) {
            Log.d("onPostExecute", "Entered into showing locations");
            HashMap<String, String> googlePlace = nearbyPlacesMap.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");

            NearbyPlace nearbyPlace = new NearbyPlace();
            nearbyPlace.setNearbyName(placeName);
            nearbyPlace.setNearbyVicinity(vicinity);
            nearbyPlace.setNearbyLat(lat);
            nearbyPlace.setNearbyLng(lng);
            try {
                dbHelper.createNewNearbyPlace(nearbyPlace);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("NearbyPlace: ", "dbHelper ERROR");
            }
        }
    }
}
