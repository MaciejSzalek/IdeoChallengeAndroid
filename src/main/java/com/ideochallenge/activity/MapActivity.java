package com.ideochallenge.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
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
import com.ideochallenge.R;
import com.ideochallenge.animations.PlayerAnimator;
import com.ideochallenge.bot.Events;
import com.ideochallenge.bot.BotManager;
import com.ideochallenge.bot.Timer;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.direction.DownloadURL;
import com.ideochallenge.direction.TaskLoadedCallback;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        TaskLoadedCallback {

    private GoogleMap mMap;
    private EventBus eventBus = EventBus.getDefault();
    private DBHelper dbHelper;
    private Timer timer;
    private PlayerAnimator playerAnimator;
    private BotManager botManager;
    private DrawerLayout drawerLayout;

    private Marker playerMarker;
    private Marker destinationMarker;

    private List<Destination> destinationList = new ArrayList<>();
    private ArrayList<LatLng> routePointList = new ArrayList<>();
    private List<NearbyPlace> nearbyDatabaseList = new ArrayList<>();

    private LatLng mOrigin;
    private LatLng mDest;

    private TextView txtView;

    private Integer timerHours = 0;
    private Integer timerMinutes = 0;
    private Integer botCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        txtView = findViewById(R.id.test_txt);

        dbHelper = new DBHelper(this);
        eventBus.register(this);
        timer = new Timer();
        timer.startTimer();

        if(destinationList.isEmpty()){
            try {
                destinationList = dbHelper.getAllDestination();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (savedInstanceState != null) {
            double lat = savedInstanceState.getDouble("mMarkerLat");
            double lng = savedInstanceState.getDouble("mMarkerLng");
            mOrigin = new LatLng(lat, lng);
        } else {
            mOrigin = new LatLng(50.037427, 22.004873);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botManager = new BotManager(MapActivity.this, mMap);
                botManager.manageBot(0, timerHours);
                //startTimer();
            }
        });
    }

    @Subscribe
    public void getBotEvent(Events.BotEvent botEvent){
        botCount = botEvent.getCount();
        botManager = new BotManager(this, mMap);
        botManager.manageBot(botCount, timerHours);
        //txtView.setText("Bots = " + botEvent.getCount());
    }

    @Subscribe
    public void getTimerEvent(Events.TimerEvent timerEvent){
        timerHours = timerEvent.getHours();
        timerMinutes = timerEvent.getMinutes();
        DecimalFormat decimalFormat = new DecimalFormat("#00");
        String hours =  String.valueOf(decimalFormat.format(timerHours));
        String minutes =  String.valueOf(decimalFormat.format(timerMinutes));
        txtView.setText("CLOCK: " + hours + " : " + minutes + "  bot count: " + botCount);

        botManager = new BotManager(this, mMap);
        botManager.manageBot(botCount, timerHours);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("mMarkerLat", playerMarker.getPosition().latitude);
        outState.putDouble("mMarkerLng", playerMarker.getPosition().longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapLongClickListener(mMap);

        if(playerMarker == null){
            drawPlayerMarker();
        }
        showNearbyMarkers();
    }

    private  void setMapLongClickListener(final GoogleMap map){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mOrigin = playerMarker.getPosition();
                mDest = latLng;
                MarkerOptions markerOptions;
                markerOptions = new MarkerOptions();
                markerOptions.position(mDest);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.target_48));
                destinationMarker = mMap.addMarker(markerOptions);
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

            case R.id.polyline_show:
                playerAnimator.showPolyline(true);
                return true;
            case R.id.polyline_hide:
                playerAnimator.showPolyline(false);
                return true;
            case R.id.time_one:
                eventBus.post(new Events.TimerScale(1));
                return true;
            case R.id.time_four:
                eventBus.post(new Events.TimerScale(4));
                return true;
            case R.id.time_eight:
                eventBus.post(new Events.TimerScale(8));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onTaskDone(Object... values) {
        routePointList = (ArrayList<LatLng>) values[0];
        if (!routePointList.isEmpty()){
            playerAnimator = new PlayerAnimator(mMap, playerMarker,
                    routePointList,
                    nearbyDatabaseList);
            playerAnimator.run();
            destinationMarker.remove();
        }
    }


    public void goToTestActivity(View view){
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    private void drawPlayerMarker(){
        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        markerOptions.position(mOrigin);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.man_72));
        playerMarker = mMap.addMarker(markerOptions);
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
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.nearby_24));
            mMap.addMarker(markerOptions);
        }
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
}
