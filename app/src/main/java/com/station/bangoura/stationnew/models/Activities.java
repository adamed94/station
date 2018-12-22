package com.station.bangoura.stationnew.models;

import com.google.gson.annotations.SerializedName;

public class Activities {
    @SerializedName("id")
    private int id ;
    @SerializedName("start_at")
    String start_at ;
    @SerializedName("end_at")
    String end_at ;
    @SerializedName("index_ess_start")
    int index_ess_start ;
    @SerializedName("index_ess_end")
    int index_ess_end ;
    @SerializedName("index_gaz_start")
    int index_gaz_start ;
    @SerializedName("index_gaz_end")
    int index_gaz_end ;
    @SerializedName("amount_ess")
    int amount_ess ;
    @SerializedName("amount_gaz")
    int amount_gaz ;
    @SerializedName("remise_en_cuve_ess")
    int remise_en_cuve_ess ;
    @SerializedName("remise_en_cuve_gaz")
    int remise_en_cuve_gaz ;
    @SerializedName("pomp_id")
    private int pomp_id ;
    @SerializedName("pompist_id")
    private int pompist_id ;
    @SerializedName("created_at")
    String created_at ;
    @SerializedName("updated_at")
    String updated_at ;

    public Activities(int id, int pomp_id, int pompist_id, String created_at, String updated_at) {
        this.id = id;
        this.pomp_id = pomp_id;
        this.pompist_id = pompist_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Activities(int id, String start_at, String end_at, int index_ess_start, int index_ess_end, int index_gaz_start, int index_gaz_end, int amount_ess, int remise_en_cuve_ess, int remise_en_cuve_gaz, int pomp_id, int pompist_id, String created_at, String updated_at) {
        this.id = id;
        this.start_at = start_at;
        this.end_at = end_at;
        this.index_ess_start = index_ess_start;
        this.index_ess_end = index_ess_end;
        this.index_gaz_start = index_gaz_start;
        this.index_gaz_end = index_gaz_end;
        this.amount_ess = amount_ess;
        this.remise_en_cuve_ess = remise_en_cuve_ess;
        this.remise_en_cuve_gaz = remise_en_cuve_gaz;
        this.pomp_id = pomp_id;
        this.pompist_id = pompist_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPomp_id() {
        return pomp_id;
    }

    public void setPomp_id(int pomp_id) {
        this.pomp_id = pomp_id;
    }

    public int getPompist_id() {
        return pompist_id;
    }

    public int getAmount_ess() {
        return amount_ess;
    }

    public void setAmount_ess(int amount_ess) {
        this.amount_ess = amount_ess;
    }

    public int getAmount_gaz() {
        return amount_gaz;
    }

    public void setAmount_gaz(int amount_gaz) {
        this.amount_gaz = amount_gaz;
    }

    public void setPompist_id(int pompist_id) {
        this.pompist_id = pompist_id;
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


    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getindex_ess_start() {
        return index_ess_start;
    }

    public void setindex_ess_start(int index_ess_start) {
        this.index_ess_start = index_ess_start;
    }

    public int getindex_ess_end() {
        return index_ess_end;
    }

    public void setindex_ess_end(int index_ess_end) {
        this.index_ess_end = index_ess_end;
    }

    public int getindex_gaz_start() {
        return index_gaz_start;
    }

    public void setindex_gaz_start(int index_gaz_start) {
        this.index_gaz_start = index_gaz_start;
    }

    public int getindex_gaz_end() {
        return index_gaz_end;
    }

    public void setindex_gaz_end(int index_gaz_end) {
        this.index_gaz_end = index_gaz_end;
    }

    public int getamount_ess() {
        return amount_ess;
    }

    public void setamount_ess(int amount_ess) {
        this.amount_ess = amount_ess;
    }

    public int getRemise_en_cuve_ess() {
        return remise_en_cuve_ess;
    }

    public void setRemise_en_cuve_ess(int remise_en_cuve_ess) {
        this.remise_en_cuve_ess = remise_en_cuve_ess;
    }

    public int getRemise_en_cuve_gaz() {
        return remise_en_cuve_gaz;
    }

    public void setRemise_en_cuve_gaz(int remise_en_cuve_gaz) {
        this.remise_en_cuve_gaz = remise_en_cuve_gaz;
    }

    @Override
    public String toString() {
        return "Activities{" +
                "id=" + id +
                ", start_at='" + start_at + '\'' +
                ", end_at='" + end_at + '\'' +
                ", index_ess_start=" + index_ess_start +
                ", index_ess_end=" + index_ess_end +
                ", index_gaz_start=" + index_gaz_start +
                ", index_gaz_end=" + index_gaz_end +
                ", amount_ess=" + amount_ess +
                ", remise_en_cuve_ess=" + remise_en_cuve_ess +
                ", remise_en_cuve_gaz=" + remise_en_cuve_gaz +
                ", pomp_id=" + pomp_id +
                ", pompist_id=" + pompist_id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
