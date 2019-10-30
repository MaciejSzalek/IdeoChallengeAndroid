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
import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DestinationStatisticActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private List<Destination> destinationList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_statistic);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = findViewById(R.id.destination_statistic_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTxtColor));
        toolbar.setTitle("Destination Statistic");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = findViewById(R.id.destination_statistic_list);

        dbHelper = new DBHelper(this);
        if(destinationList.isEmpty()){
            getDestinationList();
        }
        CustomListAdapter customListAdapter = new CustomListAdapter(this, null, destinationList);
        listView.setAdapter(customListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_name:
                orderByDestination();
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

    private List<Destination> getDestinationList(){
        try {
            destinationList = dbHelper.getAllDestination();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return destinationList;
    }

    private void orderByDestination(){
        Collections.sort(destinationList, new Comparator<Destination>() {
            @Override
            public int compare(Destination destination1, Destination destination2) {
                return destination1.getCategory().compareTo(destination2.getCategory());
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, null, destinationList);
        listView.setAdapter(customListAdapter);
    }

    private void orderByVisitors(){
        Collections.sort(destinationList, new Comparator<Destination>() {
            @Override
            public int compare(Destination destination1, Destination destination2) {
                return Long.compare(destination2.getVisitors(), destination1.getVisitors());
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, null, destinationList);
        listView.setAdapter(customListAdapter);
    }

    private void orderByRating(){
        Collections.sort(destinationList, new Comparator<Destination>() {
            @Override
            public int compare(Destination destination1, Destination destination2) {
                double rating1 = destination1.getPoints() / destination1.getVisitors();
                double rating2 = destination2.getPoints() / destination2.getVisitors();
                return Double.compare(rating2, rating1);
            }
        });
        CustomListAdapter customListAdapter = new CustomListAdapter(this, null, destinationList);
        listView.setAdapter(customListAdapter);
    }
}
