package com.ideochallenge.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.ideochallenge.R;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RouteStatisticActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private List<Route> routeList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_statistic);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = findViewById(R.id.route_statistic_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTxtColor));
        toolbar.setTitle("Route Statistic");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = findViewById(R.id.route_statistic_list);

        dbHelper = new DBHelper(this);
        if(routeList.isEmpty()){
            getRouteList();
        }
        CustomListAdapter customListAdapter = new CustomListAdapter(this, routeList, null);
        listView.setAdapter(customListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_name:
                orderByRouteCategory();
                return true;
            case R.id.order_visitors:
                orderByVisitors();
                return true;
            case R.id.order_rating:
                orderByRating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistic, menu);
        return true;
    }

    private List<Route> getRouteList(){
        try {
            routeList = dbHelper.getAllRoute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeList;
    }

    private void orderByRouteCategory(){
        Collections.sort(routeList, new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                return route1.getCategory().compareTo(route2.getCategory());
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, routeList, null);
        listView.setAdapter(customListAdapter);
    }

    private void orderByVisitors(){
        Collections.sort(routeList, new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                return Long.compare(route2.getVisitors(), route1.getVisitors());
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, routeList, null);
        listView.setAdapter(customListAdapter);
    }

    private void orderByRating(){
        Collections.sort(routeList, new Comparator<Route>() {
            @Override
            public int compare(Route route1, Route route2) {
                double rating1 = route1.getPoints() / route1.getVisitors();
                double rating2 = route2.getPoints() / route2.getVisitors();
                return Double.compare(rating2, rating1);
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, routeList, null);
        listView.setAdapter(customListAdapter);
    }
}
