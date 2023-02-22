package com.example.asg1_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddItemFragment.AddItemDialogListener {
    //This class is the main menu, involves with add an item to the list, total CO2, total cost
    //Collab with Item and AddItemFragment
    private static final String TAG = "Footprintlist App";
    Button btnAdd;
    public static final ArrayList<Item> footprintList = new ArrayList<>();
    TextView tvTotalFootprint;
    TextView tvTotalCost;
    ListView listView;
    CustomBaseAdapter customBaseAdapter;


    @Override
    public void addItem(Item item) {
        footprintList.add(item);
        customBaseAdapter.notifyDataSetChanged();
        updateBottom();
    }

    private void updateBottom() {
        double carbon = 0;
        double total = 0.0;
        for (Item item : footprintList) {
            double tmp = item.getCarbon();
            total += (item.getCost() * tmp);
            if (item.getFuel_type().equals("Gasoline")) {
                tmp *= 2.32;
            } else {
                tmp *= 2.69;
            }
            carbon += tmp;
        }

        tvTotalFootprint.setText(String.valueOf((int) Math.round(carbon)));
        tvTotalCost.setText(String.format("%.2f", total));
    }

    @Override
    public void updateDetail(Item item) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalFootprint = findViewById(R.id.tv_total_footprint);
        tvTotalCost = findViewById(R.id.tv_total_cost);

        listView = findViewById(R.id.customListView);
        customBaseAdapter = new CustomBaseAdapter(this, footprintList);
        listView.setAdapter(customBaseAdapter);
        //new clicked on item

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);

            }
        });
        //Here is a test
        //fillFootprintList();
        //Log.d(TAG,"onCreate"+footprintList.toString());
        //Toast.makeText(this,"Item count"+footprintList.size(),Toast.LENGTH_SHORT).show();
        btnAdd = findViewById(R.id.btn_add_item);

        //add item is to navigate to the activity_item, however not adding it
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddItemFragment addItemDialogFragment = new AddItemFragment(null);
                addItemDialogFragment.setListener(MainActivity.this);
                //addItemDialogFragment.setEditAddDialogListener(this);
                addItemDialogFragment.show(getSupportFragmentManager(), "Add_Edit");

            }
        });

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onResume() {
        super.onResume();
        customBaseAdapter.notifyDataSetChanged();
        updateBottom();
    }



}