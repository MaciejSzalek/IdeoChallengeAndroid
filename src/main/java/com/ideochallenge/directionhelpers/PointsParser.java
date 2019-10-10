package com.ideochallenge.directionhelpers;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-09.
 */

public class PointsParser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private TaskLoadedCallback taskCallback;
    private String directionMode = "walking";

    public PointsParser(Context mContext, String directionMode) {
        this.taskCallback = (TaskLoadedCallback) mContext;
        this.directionMode = directionMode;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            Log.d("my log", jsonData[0]);
            DataParser parser = new DataParser();
            Log.d("my log", parser.toString());

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("my log", "Executing routes");
            Log.d("my log", routes.toString());

        } catch (Exception e) {
            Log.d("my log", e.toString());
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);
            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            if (directionMode.equalsIgnoreCase("driving")) {
                lineOptions.width(5);
                lineOptions.color(Color.MAGENTA);
            } else {
                lineOptions.width(5);
                lineOptions.color(Color.LTGRAY);
            }
            Log.d("My LOG", "onPostExecute line options decoded");
        }

        if (points != null) {
            //mMap.addPolyline(lineOptions);
            taskCallback.onTaskDone(points);
        } else {
            Log.d("My LOG", "without Polylines drawn");
        }

        // Drawing polyline in the Google Map for the i-th route
        /*if (lineOptions != null) {
            //mMap.addPolyline(lineOptions);
            taskCallback.onTaskDone(lineOptions);
        } else {
            Log.d("My LOG", "without Polylines drawn");
        }*/
    }
}
