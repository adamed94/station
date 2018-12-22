package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class Cuve {

    @SerializedName("id")
    private int id ;
    @SerializedName("capacity")
    private  int capacity ;
    @SerializedName("diameter")
    private  int diameter ;
    @SerializedName("carburan_type_id")
    private  String carburan_type_id ;
    @SerializedName("station_id")
    private  String station_id ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;
    @SerializedName("station")
    Station station ;
    @SerializedName("carburan_type")
    Station carburan_type ;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCarburan_type_id() {
        return carburan_type_id;
    }

    public void setCarburan_type_id(String carburan_type_id) {
        this.carburan_type_id = carburan_type_id;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
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

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getCarburan_type() {
        return carburan_type;
    }

    public void setCarburan_type(Station carburan_type) {
        this.carburan_type = carburan_type;
    }

    public Cuve(int id, int capacity, String carburan_type_id, String station_id, String created_at, String updated_at, Station station, Station carburan_type) {

        this.id = id;
        this.capacity = capacity;
        this.carburan_type_id = carburan_type_id;
        this.station_id = station_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.station = station;
        this.carburan_type = carburan_type;
    }

    @Override
    public String toString() {
        return "Cuve{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", carburan_type_id='" + carburan_type_id + '\'' +
                ", station_id='" + station_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", station=" + station +
                ", carburan_type=" + carburan_type +
                '}';
    }
}
