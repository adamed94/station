package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class Depense {
    @SerializedName("id")
    private int id ;
    @SerializedName("amount")
    private int amount ;
    @SerializedName("from_id")
    private int from_id ;
    @SerializedName("station_id")
    private int station_id ;
    @SerializedName("state")
    private String state ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;

    public Depense(int id, int amount, int from_id, int station_id, String state, String created_at, String updated_at) {
        this.id = id;
        this.amount = amount;
        this.from_id = from_id;
        this.station_id = station_id;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", amount=" + amount +
                ", from_id=" + from_id +
                ", station_id=" + station_id +
                ", state='" + state + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
