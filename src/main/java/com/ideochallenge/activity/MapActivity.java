package com.ideochallenge.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.ideochallenge.R;
import com.ideochallenge.animations.PlayerAnimator;
import com.ideochallenge.bot.BotCreator;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.database.HistoryRoute;
import com.ideochallenge.direction.DownloadURL;
import com.ideochallenge.direction.TaskLoadedCallback;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        TaskLoadedCallback {

    private DBHelper dbHelper;
    private GoogleMap mMap;
    private Marker mMarker;
    private Marker destinationMarker;
    private Polyline currentPolyline;

    private List<Destination> historyRouteList = new ArrayList<>();
    private List<LatLng> markerList = new ArrayList<>();
    private List<NearbyPlace> nearbyPlacesList = new ArrayList<>();
    private List<NearbyPlace> nearbyDatabaseList = new ArrayList<>();

    private LatLng mOrigin;
    private LatLng mDest;

    private PlayerAnimator playerAnimator;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        drawerLayout = findViewById(R.id.drawer_layout);
        setNavigationDrawer();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.map_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTxtColor));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        Button button = findViewById(R.id.test_btn);
        final TextView txtView = findViewById(R.id.test_txt);

        dbHelper = new DBHelper(this);

        try {
            historyRouteList = dbHelper.getAllDestination();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(historyRouteList.isEmpty()){
            try {
                HistoryRoute.createHistoryRoute(this);
                historyRouteList = dbHelper.getAllDestination();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new DownloadURL(MapActivity.this)
                        .execute(getRouteUrl(mOrigin, mDest, "walking"), "walking");*/
                startBot();
                //showNearbyPlaces();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mOrigin = new LatLng(historyRouteList.get(0).getLat(), historyRouteList.get(0).getLng());
        mDest = new LatLng(historyRouteList.get(2).getLat(), historyRouteList.get(2).getLng());
        mMap = googleMap;
        setMapLongClickListener(mMap);

        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        markerOptions.position(mOrigin);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMarker = mMap.addMarker(markerOptions);

        for(Destination destination: historyRouteList){
            LatLng latLng = new LatLng(destination.getLat(), destination.getLng());
            //markerOptions = new MarkerOptions();
            //markerOptions.position(latLng);
            //mMap.addMarker(markerOptions);
            markerList.add(latLng);
        }
        showNearbyMarkers();

    }


    private  void setMapLongClickListener(final GoogleMap map){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mOrigin = mMarker.getPosition();
                mDest = latLng;

                if(destinationMarker == null){
                    MarkerOptions markerOptions;
                    markerOptions = new MarkerOptions();
                    markerOptions.position(mDest);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    destinationMarker = mMap.addMarker(markerOptions);
                } else {
                    destinationMarker.setPosition(mDest);
                }
                new DownloadURL(MapActivity.this)
                        .execute(getRouteUrl(mOrigin, mDest, "walking"), "walking");
            }
        });
    }

    public void setNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.new_game) {
                    goToTestActivity(findViewById(R.id.activity_test));
                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.best_track) {

                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.best_place) {

                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.finish) {

                    drawerLayout.closeDrawers();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.map_type_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.map_type_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.map_type_hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.map_type_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;

            case R.id.line_distance:

                return true;
            case R.id.delete_line_distance:

                return true;

            case R.id.simulation:

                return true;

            case R.id.editor:

                return true;
            case R.id.delete_all:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        markerList = (ArrayList<LatLng>) values[0];
        if (!markerList.isEmpty()){
            playerAnimator = new PlayerAnimator(mMap, mMarker, markerList, nearbyDatabaseList);
            playerAnimator.run();
            destinationMarker.remove();
        }
    }


    /*@Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }*/

    private void animatePlayerMarker(){
        playerAnimator = new PlayerAnimator(mMap, mMarker, markerList, nearbyPlacesList);
        playerAnimator.run();
    }

    private String getRouteUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        return "https://maps.googleapis.com/maps/api/directions/" + output
                + "?" + parameters + "&key=" + getString(R.string.API_KEY);
    }

    public void goToTestActivity(View view){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    private void startBot(){
        BotCreator botCreator = new BotCreator(this, mMap);
        botCreator.createNewBot();
    }

    private void showNearbyMarkers(){
        try {
            nearbyDatabaseList = dbHelper.getAllNearbyPlace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(NearbyPlace nearbyPlace: nearbyDatabaseList){
            LatLng latLng = new LatLng(nearbyPlace.getNearbyLat(),
                    nearbyPlace.getNearbyLng());
            MarkerOptions markerOptions;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
        }
    }
}
