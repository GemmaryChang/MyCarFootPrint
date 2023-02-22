package com.example.asg1_v2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity implements AddItemFragment.AddItemDialogListener {
    //This class includes single item with detailed information
    //includes button to edit the detailed info or delete the item
    //collab with AddItemFragment
    int index = 0;
    private Item item = null;
    private TextView gasTtl;
    private TextView dateTtl;
    private TextView fuelTtl;
    private TextView litreAmountTtl;
    private TextView litrePricetTtl;
    private TextView totalPricetTtl;
    private TextView totalFootPrintTtl;
    private Button btnEdit;
    private Button btnDel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail");
        index = getIntent().getIntExtra("index", 0);
        item = MainActivity.footprintList.get(index);

        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemFragment addItemDialogFragment = new AddItemFragment(item);
                addItemDialogFragment.setListener(DetailActivity.this);
                addItemDialogFragment.show(getSupportFragmentManager(), "Add_Edit");
            }
        });
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setMessage("Confirm to delete this visit?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.footprintList.remove(item);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        updateView();
    }

    public void updateView() {


        gasTtl = findViewById(R.id.GasTtl);
        gasTtl.setText(item.getGas_station_name());
        dateTtl = findViewById(R.id.DateTtl);
        dateTtl.setText(item.getDate());
        fuelTtl = findViewById(R.id.FuelTtl);
        String type = item.getFuel_type();
        fuelTtl.setText(type);
        litreAmountTtl = findViewById(R.id.LitreAmountTtl);
        litrePricetTtl = findViewById(R.id.LitrePricetTtl);

        litreAmountTtl.setText(String.valueOf(item.getCarbon()));
        litrePricetTtl.setText(String.format("%.2f", item.getCost()));

        double carbon = item.getCarbon();
        double total = item.getCost() * carbon;
        if (type.equals("Gasoline")) {
            carbon *= 2.32;
        } else {
            carbon *= 2.69;
        }

        totalPricetTtl = findViewById(R.id.TotalPricetTtl);
        totalFootPrintTtl = findViewById(R.id.TotalFootPrintTtl);
        totalFootPrintTtl.setText(String.valueOf((int)Math.round(carbon)));
        totalPricetTtl.setText(String.format("%.2f", total));
    }

    @Override
    public void addItem(Item item) {

    }

    @Override
    public void updateDetail(Item item) {
        this.item = item;
        updateView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
