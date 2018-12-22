package com.station.bangoura.stationnew.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;

import java.util.List;

public class AdapterPompist extends RecyclerView.Adapter<AdapterPompist.VHPompist> {
    private List<Pompist> pomistList ;
    private AdapterPompist.OnPompistClickLister onPompistClickLister;

    public AdapterPompist(List<Pompist> pomistList) {
        this.pomistList = pomistList;
    }



    public List<Pompist> getPomistList() {
        return pomistList;
    }

    public void setPomistList(List<Pompist> pomistList) {
        this.pomistList = pomistList;
    }

    @NonNull
    @Override
    public VHPompist onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_pompist,viewGroup,false) ;
        AdapterPompist.VHPompist VH = new AdapterPompist.VHPompist(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull VHPompist holder, int i) {
        holder.bind(pomistList.get(i));
    }

    @Override
    public int getItemCount() {
        return pomistList.size();
    }

    public class VHPompist extends RecyclerView.ViewHolder {
        private TextView name , number , dat ;
        public VHPompist(@NonNull View itemView) {


            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvNamePompist);
            number = (TextView)itemView.findViewById(R.id.tvNumberPompist);
            dat = (TextView)itemView.findViewById(R.id.tvDate) ;
        }


        public void bind(Pompist pompist)
        {
            name.setText(pompist.getName());
            number.setText(pompist.getPhone());
            dat.setText(pompist.getCreated_at());


        }
    }

    public interface OnPompistClickLister
    {
        void onPompistlick(Pompist station);
    }
}
