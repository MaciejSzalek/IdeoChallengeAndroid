package com.ideochallenge.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.ideochallenge.R;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;
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
        CustomListAdapter customListAdapter = new CustomListAdapter(this, routeList);
        listView.setAdapter(customListAdapter);
    }

    private List<Route> getRouteList(){
        try {
            routeList = dbHelper.getAllRoute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routeList;
    }
}
