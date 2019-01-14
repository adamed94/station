package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class CaisseSolde {

    @SerializedName("id")
    private int id ;
    @SerializedName("amount")
    private int amount ;
    @SerializedName("station_id")
    private int station_id ;

    public CaisseSolde(int id, int amount, int station_id) {
        this.id = id;
        this.amount = amount;
        this.station_id = station_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    @Override
    public String toString() {
        return "CaisseSolde{" +
                "id=" + id +
                ", amount=" + amount +
                ", station_id=" + station_id +
                '}';
    }
}
