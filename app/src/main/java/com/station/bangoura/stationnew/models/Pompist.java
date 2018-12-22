package com.station.bangoura.stationnew.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Pompist {

    @SerializedName("id")
    private int id ;
    @SerializedName("name")
    private  String name ;
    @SerializedName("phone")
    private  String phone ;
    @SerializedName("station_id")
    private  String station_id ;
    @SerializedName("created_at")
    private String created_at ;
    @SerializedName("updated_at")
    private String updated_at ;


    public Pompist(int id, String name, String station_id, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.station_id = station_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
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

    @NonNull
    @Override
    public String toString() {
        return "Pompist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", station_id='" + station_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
