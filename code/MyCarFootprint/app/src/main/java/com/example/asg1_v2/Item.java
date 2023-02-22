package com.example.asg1_v2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
    //This class represents each foot print in the listview as an object

    private String gas_station_name;
    private String date;
    private int carbon;
    private double cost;
    private String fuel_type;


    public Item(String gas_station_name, String date, int carbon, double cost, String fuel_type) {
        this.gas_station_name = gas_station_name;
        this.date = date;
        this.carbon = carbon;
        this.cost = cost;
        this.fuel_type = fuel_type;
    }

    @Override
    public String toString() {
        return "Item{" +
                "gas_station_name='" + gas_station_name + '\'' +
                ", date=" + date +
                ", carbon=" + carbon +
                ", cost=" + cost +
                ", fuel_type='" + fuel_type + '\'' +
                '}';
    }

    public String getGas_station_name() {
        return gas_station_name;
    }

    public void setGas_station_name(String gas_station_name) {
        this.gas_station_name = gas_station_name;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = String.valueOf(date);
    }

    public int getCarbon() {
        return carbon;
    }

    public void setCarbon(int carbon) {
        this.carbon = carbon;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }
}
