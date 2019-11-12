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

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-28.
 */

class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Route> routeList;
    private List<Destination> destinationList;

    CustomListAdapter(Context context,
                             List<Route> routeArrayList,
                             List<Destination> destinationList){
        this.context = context;
        this.routeList = routeArrayList;
        this.destinationList = destinationList;
    }

    @Override
    public int getCount() {
        if(destinationList != null){
            return destinationList.size();
        }
        return routeList.size();
    }

    @Override
    public Object getItem(int position) {
        if(destinationList != null){
            destinationList.get(position);
        }
        return routeList.get(position);
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

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        if(routeList != null){
            title.setText(routeList.get(position).getCategory());
            visitors.setText(String.valueOf(routeList.get(position).getVisitors()));
            double dRating = (routeList.get(position).getPoints()
                / routeList.get(position).getVisitors());
            String strRating = decimalFormat.format(dRating);
            rating.setText(strRating);
        }

        if(destinationList != null){
            title.setText(destinationList.get(position).getName());
            visitors.setText(String.valueOf(destinationList.get(position).getVisitors()));
            double dRating = (destinationList.get(position).getPoints()
                    / destinationList.get(position).getVisitors());
            String strRating = decimalFormat.format(dRating);
            rating.setText(strRating);
        }
        return view;
    }
}
