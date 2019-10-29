package com.ideochallenge.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ideochallenge.R;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewRouteActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText loadRouteCategory;
    ListView loadRouteListView;

    ArrayAdapter arrayAdapter;
    ArrayList<String> routeCategoryArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        loadRouteCategory = findViewById(R.id.load_route_name);
        loadRouteListView = findViewById(R.id.load_route_list);

        Toolbar toolbar = findViewById(R.id.new_route_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTxtColor));
        toolbar.setTitle("New Game");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dbHelper = new DBHelper(this);
        getRouteNameListDatabase();

        loadRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadRouteCategory.setText(routeCategoryArrayList.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.confirm_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if(i == R.id.confirm_ic){
            String text = loadRouteCategory.getText().toString();
            if(routeCategoryArrayList.contains(text)){
                goToMapActivityWithResult();
            }else{
                if(TextUtils.isEmpty(text)){
                    Toast.makeText(NewRouteActivity.this, "Click on list or write route name",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewRouteActivity.this, "Route doesn't exists",
                            Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getRouteNameListDatabase(){
        if(routeCategoryArrayList != null){
            routeCategoryArrayList.clear();
        }
        try {
            List<Route> routeNameList = dbHelper.getAllRoute();
            for (Route route: routeNameList){
                routeCategoryArrayList.add(route.getCategory());
            }
            Collections.sort(routeCategoryArrayList);
            arrayAdapter = new ArrayAdapter<>(this, R.layout.list_text,
                    routeCategoryArrayList);
            loadRouteListView.setAdapter(arrayAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goToMapActivityWithResult(){
        String text = loadRouteCategory.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("ROUTE_CATEGORY", text);
        setResult(RESULT_OK, intent);
        finish();
    }
}
