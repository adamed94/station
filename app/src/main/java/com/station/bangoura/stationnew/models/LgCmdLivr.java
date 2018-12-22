package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LgCmdLivr {

    @SerializedName("id")
    private int id ;
    @SerializedName("quantity")
    private  int quantity ;
    @SerializedName("cmd_livr_id")
    private  int cmd_livr_id ;
    @SerializedName("carburan_type_id")
    private  int carburan_type_id ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;
    @SerializedName("carburanType")
    CarburanType carburanType ;


    public LgCmdLivr(int id, int quantity, int cmd_livr_id, int carburan_type_id, String created_at, String updated_at, CarburanType carburanType) {
        this.id = id;
        this.quantity = quantity;
        this.cmd_livr_id = cmd_livr_id;
        this.carburan_type_id = carburan_type_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.carburanType = carburanType;
    }

    public LgCmdLivr(int id, int quantity, int cmd_livr_id, int carburan_type_id, String created_at, String updated_at) {
        this.id = id;
        this.quantity = quantity;
        this.cmd_livr_id = cmd_livr_id;
        this.carburan_type_id = carburan_type_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public int getCmd_livr_id() {
        return cmd_livr_id;
    }

    public void setCmd_livr_id(int cmd_livr_id) {
        this.cmd_livr_id = cmd_livr_id;
    }

    public int getCarburan_type_id() {
        return carburan_type_id;
    }

    public void setCarburan_type_id(int carburan_type_id) {
        this.carburan_type_id = carburan_type_id;
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


    public CarburanType getCarburanType() {
        return carburanType;
    }

    public void setCarburanType(CarburanType carburanType) {
        this.carburanType = carburanType;
    }



    @Override
    public String toString() {
        return "LgCmdLivr{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", cmd_livr_id=" + cmd_livr_id +
                ", carburan_type_id=" + carburan_type_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", carburanType=" + carburanType +
                '}';
    }
}
