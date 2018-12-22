package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class Stock {


    @SerializedName("id")
    private int id;
    @SerializedName("carburanTypeId")
    private long carburanTypeId;
    @SerializedName("station_id")
    private int station_id;
    @SerializedName("stock_theor")
    private int stock_theor;
    @SerializedName("stock_reel")
    private int stock_reel;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("station")
    private Station station;
    @SerializedName("carburan_type")
    private CarburanType carburan_type;


    public Stock(int id, long carburanTypeId, int station_id, int stock_theor, int stock_reel, String created_at, String updated_at) {
        this.id = id;
        this.carburanTypeId = carburanTypeId;
        this.station_id = station_id;
        this.stock_theor = stock_theor;
        this.stock_reel = stock_reel;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Stock(int id, long carburanTypeId, int station_id, int stock_theor, int stock_reel, String created_at, String updated_at, Station station) {
        this.id = id;
        this.carburanTypeId = carburanTypeId;
        this.station_id = station_id;
        this.stock_theor = stock_theor;
        this.stock_reel = stock_reel;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.station = station;
    }

    public Stock(int id, long carburanTypeId, int station_id, int stock_theor, int stock_reel, String created_at, String updated_at, Station station, CarburanType carburan_type) {
        this.id = id;
        this.carburanTypeId = carburanTypeId;
        this.station_id = station_id;
        this.stock_theor = stock_theor;
        this.stock_reel = stock_reel;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.station = station;
        this.carburan_type = carburan_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCarburanTypeId() {
        return carburanTypeId;
    }

    public void setCarburanTypeId(long carburanTypeId) {
        this.carburanTypeId = carburanTypeId;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getStock_theor() {
        return stock_theor;
    }

    public void setStock_theor(int stock_theor) {
        this.stock_theor = stock_theor;
    }

    public int getStock_reel() {
        return stock_reel;
    }

    public void setStock_reel(int stock_reel) {
        this.stock_reel = stock_reel;
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

    public CarburanType getCarburan_type() {
        return carburan_type;
    }

    public void setCarburan_type(CarburanType carburan_type) {
        this.carburan_type = carburan_type;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", carburanTypeId=" + carburanTypeId +
                ", station_id=" + station_id +
                ", stock_theor=" + stock_theor +
                ", stock_reel=" + stock_reel +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", station=" + station +
                ", carburan_type=" + carburan_type +
                '}';
    }
}
