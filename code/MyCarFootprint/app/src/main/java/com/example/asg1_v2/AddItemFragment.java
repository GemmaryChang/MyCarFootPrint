package com.example.asg1_v2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class AddItemFragment extends DialogFragment {
    //This Fragment will be showed when an new footprint item need to be added
    // or an existed footprint item needs to be edited
    Item item;

    public AddItemFragment(Item item) {
        this.item = item;
    }

    //TextView new_gas_station_name;
    //AddDialogListener listener;
    public interface AddItemDialogListener {
        void addItem(Item item);

        void updateDetail(Item item);
    }

    private AddItemDialogListener listener;

    public void setListener(AddItemDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_edit, null);
        EditText gasStationName = view.findViewById(R.id.GasStationName);
        EditText litreAmount = view.findViewById(R.id.LitreAmount);
        EditText litrePrice = view.findViewById(R.id.LitrePrice);
        litrePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        litrePrice.setText(s.subSequence(0, 1));
                        litrePrice.setSelection(1);
                        return;
                    }
                }

                if (s.toString().startsWith(".")) {
                    litrePrice.setText("0.");
                    litrePrice.setSelection(2);
                    return;
                }

                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 2 + 1);
                        litrePrice.setText(s);
                        litrePrice.setSelection(s.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        TextView footPrintDate = view.findViewById(R.id.FootPrintDate);
        footPrintDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar instance = Calendar.getInstance();

                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        footPrintDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth + "");
                    }
                }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.MONTH)).show();
            }
        });
        //EditText newdate=view.findViewById(R.id.FootPrintDate);


        String[] choices = {"Gasoline", "Diesel"};
        Spinner spinner = view.findViewById(R.id.spinner_fueltype);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (item != null) {
            gasStationName.setText(item.getGas_station_name());
            footPrintDate.setText(item.getDate());
            litreAmount.setText(String.valueOf(item.getCarbon()));
            litrePrice.setText(String.format("%.2f", item.getCost()));
            String type = item.getFuel_type();
            if (type.equals("Gasoline")) {
                spinner.setSelection(0);
            } else {
                spinner.setSelection(1);
            }
        }



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setView(view)
                .setTitle("Add or edit the Fuel Footprint")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String gas_station_name = gasStationName.getText().toString();
                    String litre_Amount = litreAmount.getText().toString();
                    String litre_Price = litrePrice.getText().toString();
                    String foot_PrintDate = footPrintDate.getText().toString();
                    String spinner_FuelType = spinner.getSelectedItem().toString();
                    if (item == null) {
                        Item item = new Item(gas_station_name, foot_PrintDate, Integer.parseInt(litre_Amount), Double.parseDouble(litre_Price), spinner_FuelType);
                        listener.addItem(item);
                    } else {
                        item.setGas_station_name(gas_station_name);
                        item.setDate(foot_PrintDate);
                        item.setCarbon(Integer.parseInt(litre_Amount));
                        item.setCost(Double.parseDouble(litre_Price));
                        item.setFuel_type(spinner_FuelType);
                        listener.updateDetail(item);
                    }

                })
                .setNegativeButton("Cancel", null)
                .create();


    }
}
