package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class Station {

    @SerializedName("id")
    int id ;
    @SerializedName("name")
    String name ;
    @SerializedName("city")
    String city ;
    @SerializedName("state")
    String state ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;


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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Station(int id, String name, String city, String state, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
