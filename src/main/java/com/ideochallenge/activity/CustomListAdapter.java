package com.ideochallenge.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ideochallenge.R;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-28.
 */

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Route> arrayList;

    public CustomListAdapter(Context context,
                             List<Route> routeArrayList){
        this.context = context;
        this.arrayList = routeArrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_item, null, true);
        }

        TextView title = view.findViewById(R.id.custom_list_title);
        TextView visitors = view.findViewById(R.id.custom_list_visitors);
        TextView rating = view.findViewById(R.id.custom_list_rating);

        title.setText(arrayList.get(position).getCategory());
        visitors.setText(String.valueOf(arrayList.get(position).getVisitors()));
        double rat = (arrayList.get(position).getPoints());
        rating.setText(String.valueOf(rat));
        return view;
    }
}
