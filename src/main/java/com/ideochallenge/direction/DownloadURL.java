package com.ideochallenge.direction;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.ideochallenge.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maciej Szalek on 2019-10-09.
 */

public class DownloadURL extends AsyncTask<String, Void, String> {
    private Context mContext;
    private String directionMode = "walking";
    private LatLng origin, dest;

    public DownloadURL(Context mContext, LatLng origin, LatLng dest) {
        this.mContext = mContext;
        this.origin = origin;
        this.dest = dest;
    }

    @Override
    protected String doInBackground(String... strings) {
        // For storing data from web service
        String data = "";
        try {
            // Fetching the data from web service
            data = downloadUrl(getRouteUrl());
            Log.d("Log", "Background task data " + data);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        RoutePointsParser parserTask = new RoutePointsParser(mContext, directionMode);
        parserTask.execute(s);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("My LOG", "Downloaded URL: " + data);
            br.close();
        } catch (Exception e) {
            Log.d("My LOG", "Exception downloading URL: " + e.toString());
        } finally {
            assert iStream != null;
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private String getRouteUrl() {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        return "https://maps.googleapis.com/maps/api/directions/" + output
                + "?" + parameters + "&key=" + mContext.getString(R.string.API_KEY);
    }
}
