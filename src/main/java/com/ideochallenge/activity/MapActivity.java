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
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ideochallenge.R;
import com.ideochallenge.animations.PlayerAnimator;
import com.ideochallenge.bot.Events;
import com.ideochallenge.bot.BotManager;
import com.ideochallenge.bot.Timer;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.dialogs.CloseAppDialog;
import com.ideochallenge.dialogs.TutorialDialog;
import com.ideochallenge.direction.DownloadURL;
import com.ideochallenge.direction.TaskLoadedCallback;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        TaskLoadedCallback {

    private TextView txtView;
    private DrawerLayout drawerLayout;

    private EventBus eventBus = EventBus.getDefault();
    private DBHelper dbHelper;
    private BotManager botManager;
    private Timer timer;

    private GoogleMap mMap;
    private Marker playerMarker;
    private Marker targetMarker;

    private List<Destination> playerDestinationList = new ArrayList<>();
    private List<NearbyPlace> nearbyDatabaseList = new ArrayList<>();
    private ArrayList<Marker> destinationMarkerList = new ArrayList<>();
    private ArrayList<Marker> botMarkerList = new ArrayList<>();

    private LatLng mOrigin;
    private LatLng mDest;

    private boolean timerStatus = false;
    private String routeCategory;
    private Integer timerHours = 0;
    private Integer timerMinutes = 0;
    private static Integer botCount = 0;

    private static final int NEW_ROUTE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = findViewById(R.id.map_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTxtColor));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        txtView = findViewById(R.id.test_txt);
        drawerLayout = findViewById(R.id.drawer_layout);
        setNavigationDrawer();

        dbHelper = new DBHelper(this);
        eventBus.register(this);
        TutorialDialog.showTutorialDialog(this);

        if (savedInstanceState != null) {
            timerStatus = savedInstanceState.getBoolean("timerStatus");
            routeCategory = savedInstanceState.getString("routeCategory");
            double lat = savedInstanceState.getDouble("mMarkerLat");
            double lng = savedInstanceState.getDouble("mMarkerLng");
            mOrigin = new LatLng(lat, lng);
        } else {
            mOrigin = new LatLng(50.037427, 22.004873);
        }

        if(!timerStatus){
            timer = new Timer();
            timer.startTimer();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapLongClickListener(mMap);

        if(playerMarker == null){
            playerMarker = MarkerDrawer.playerMarker(mMap, mOrigin);
        }
        if(routeCategory != null){
            destinationMarkerList.clear();
            destinationMarkerList = MarkerDrawer.destinationMarkerList(
                    dbHelper,
                    mMap,
                    routeCategory);
        }
        nearbyDatabaseList = MarkerDrawer.nearbyMarkerList(dbHelper, mMap);
    }

    private void setMapLongClickListener(final GoogleMap map){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mOrigin = playerMarker.getPosition();
                mDest = latLng;
                if(targetMarker != null){
                    targetMarker.remove();
                }
                targetMarker = MarkerDrawer.targetMarker(mMap, mDest);
                new DownloadURL(MapActivity.this, mOrigin, mDest).execute();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("mMarkerLat", playerMarker.getPosition().latitude);
        outState.putDouble("mMarkerLng", playerMarker.getPosition().longitude);
        outState.putString("routeCategory", routeCategory);
        outState.putBoolean("timerStatus", true);
    }

    @Subscribe
    public void getBotEvent(Events.BotEvent botEvent){
        botCount = botEvent.getCount();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == NEW_ROUTE_REQUEST_CODE){
                routeCategory = data.getStringExtra("ROUTE_CATEGORY");
                if(!destinationMarkerList.isEmpty()){
                    for(Marker marker: destinationMarkerList){
                        marker.remove();
                    }
                    destinationMarkerList.clear();
                }
                destinationMarkerList = MarkerDrawer.destinationMarkerList(
                        dbHelper,
                        mMap,
                        routeCategory);
            }
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        ArrayList<LatLng> routePolylineList = (ArrayList<LatLng>) values[0];
        if (!routePolylineList.isEmpty()){
            PlayerAnimator playerAnimator = new PlayerAnimator(mMap, playerMarker,
                    routePolylineList,
                    nearbyDatabaseList);
            playerAnimator.run();
            targetMarker.remove();
        }
    }

    public void goToNewRouteActivity(){
        Intent intent = new Intent(this, NewRouteActivity.class);
        startActivityForResult(intent, NEW_ROUTE_REQUEST_CODE);
    }

    public void goToRouteStatisticActivity(){
        Intent intent = new Intent(this, RouteStatisticActivity.class);
        startActivity(intent);
    }

    public void goToDestinationStatisticActivity(){
        Intent intent = new Intent(this, DestinationStatisticActivity.class);
        startActivity(intent);
    }

    public void setNavigationDrawer() {
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.new_route) {
                    goToNewRouteActivity();
                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.route_statistic) {
                    goToRouteStatisticActivity();
                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.destination_statistic) {
                    goToDestinationStatisticActivity();
                    drawerLayout.closeDrawers();

                } else if (itemId == R.id.exit) {
                    CloseAppDialog.showCloseAppDialog(MapActivity.this);
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
}
