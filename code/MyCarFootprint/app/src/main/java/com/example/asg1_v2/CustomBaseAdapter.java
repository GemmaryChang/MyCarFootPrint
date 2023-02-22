package com.example.asg1_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomBaseAdapter extends ArrayAdapter<Item> {
    //A custom listview that shows the items in the footprint list
    //It display brief information about the items in the list

    public CustomBaseAdapter(@NonNull Context context,  ArrayList<Item> items) {
        super(context,0, items);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_item,parent, false);
        } else {
            view = convertView;
        }
        Item item = getItem(position);
        TextView footprintname=view.findViewById(R.id.FootPrintName);
        TextView fuelType=view.findViewById(R.id.FuelType);
        TextView carbonAmount=view.findViewById(R.id.CarbonAmount);
        TextView costAmount=view.findViewById(R.id.CostAmount);
        TextView dateDate=view.findViewById(R.id.Date);

        String type = item.getFuel_type();
        footprintname.setText(item.getGas_station_name());
        fuelType.setText(item.getFuel_type());

        double carbon = item.getCarbon();
        double total = item.getCost()*carbon;
        if(type.equals("Gasoline")){
            carbon*=2.32;
        }else{
            carbon*=2.69;
        }

        carbonAmount.setText(String.valueOf((int) Math.round(carbon)));
        costAmount.setText(String.format("%.2f", total));
        dateDate.setText(item.getDate());
        return view;
    }
}
