package com.ideochallenge.animations;

import android.animation.ValueAnimator;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.animation;

/**
 * Created by Maciej Szalek on 2019-10-10.
 */

public class MarkerAnimator {


    public static void animateMarker(final List<LatLng> polyLine,
                                     final Marker marker){

        final Handler handler = new Handler();
        final LatLng[] startPos = new LatLng[1];
        final LatLng[] endPos = new LatLng[1];
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < polyLine.size() - 2; i++){
                    if(marker != null){
                        startPos[0] = polyLine.get(i);
                        endPos[0] = polyLine.get(i+1);
                        final float startRotation = marker.getRotation();
                        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.Spherical();
                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                        valueAnimator.setDuration(1000);
                        valueAnimator.setInterpolator(new LinearInterpolator());
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                try{
                                    float v = valueAnimator.getAnimatedFraction();
                                    LatLng newPos = latLngInterpolator.interpolate(v, startPos[0], endPos[0]);
                                    marker.setPosition(newPos);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        valueAnimator.start();
                    }
                }
            }
        }, 1000);
    }

    public static void startAnimation(final Marker marker,
                                      final List<LatLng> markerList,
                                      final LatLngInterpolator latLngInterpolator){

        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 2000;

        handler.post(new Runnable() {
            int index = 0;
            long elapsed;
            float t;
            float v;
            //int next;

            @Override
            public void run() {
                LatLng startPosition, endPosition;
                elapsed = SystemClock.uptimeMillis() - start;
                t = elapsed / durationInMs;
                v = interpolator.getInterpolation(t);

                if(t < 1.0){
                    handler.postDelayed(this, 16);
                } else {
                    if (index < markerList.size() - 2) {
                        startPosition = markerList.get(index);
                        endPosition = markerList.get(index + 1);
                        marker.setPosition(latLngInterpolator.interpolate(v, startPosition, endPosition));
                    }
                }
            }
        });
    }
}
