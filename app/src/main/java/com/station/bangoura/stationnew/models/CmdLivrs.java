package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CmdLivrs {

    @SerializedName("id")
    private int id ;
    @SerializedName("quantity")
    private  int quantity ;
    @SerializedName("state")
    private  String state ;
    @SerializedName("motif")
    private  String motif ;
    @SerializedName("station_id")
    private  int station_id ;
    @SerializedName("user_id")
    private  int user_id ;
    @SerializedName("provisionning_at")
    String provisionning_at ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;
    @SerializedName("station")
    Station station ;
    @SerializedName("lg_cmdlivrs")
    List<LgCmdLivr> lg_cmdlivrs ;

    public CmdLivrs(int id, int quantity, String state, String motif, int station_id, int user_id, String created_at, String updated_at, Station station, List<LgCmdLivr> lg_cmdlivrs) {
        this.id = id;
        this.quantity = quantity;
        this.state = state;
        this.motif = motif;
        this.station_id = station_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.station = station;
        this.lg_cmdlivrs = lg_cmdlivrs;
    }






    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public List<LgCmdLivr> getlg_cmdlivrs() {
        return lg_cmdlivrs;
    }

    public void setlg_cmdlivrs(List<LgCmdLivr> lg_cmdlivrs) {
        this.lg_cmdlivrs = lg_cmdlivrs;
    }

    @Override
    public String toString() {
        return "CmdLivrs{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", state='" + state + '\'' +
                ", motif='" + motif + '\'' +
                ", station_id=" + station_id +
                ", user_id=" + user_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", station=" + station +
                ", lg_cmdlivrs=" + lg_cmdlivrs +
                '}';
    }
}
