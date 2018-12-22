package com.station.bangoura.stationnew.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Cuve;
import com.station.bangoura.stationnew.models.Pompist;

import java.util.List;

public class AdapterCuve  extends RecyclerView.Adapter<AdapterCuve.VHCuve>  {
    private List<Cuve> cuveList ;

    public AdapterCuve(List<Cuve> cuveList) {
        this.cuveList = cuveList;
    }

    public List<Cuve> getCuveList() {
        return cuveList;
    }

    public void setCuveList(List<Cuve> cuveList) {
        this.cuveList = cuveList;
    }

    @NonNull
    @Override
    public AdapterCuve.VHCuve onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cuve,viewGroup,false) ;
        AdapterCuve.VHCuve VH = new AdapterCuve.VHCuve(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCuve.VHCuve holder, int position) {
        holder.bind(cuveList.get(position));

    }

    @Override
    public int getItemCount() {


        return cuveList == null ? 0 : cuveList.size();
    }

    public class VHCuve extends RecyclerView.ViewHolder {
        private TextView capactity , station , carburanType,diameter ;
        public VHCuve(@NonNull View itemView) {

            super(itemView);
            capactity = (TextView)itemView.findViewById(R.id.tvCapacity) ;
            station = (TextView)itemView.findViewById(R.id.tvStation) ;
            carburanType = (TextView)itemView.findViewById(R.id.tvCarburantype) ;
            diameter = (TextView)itemView.findViewById(R.id.tvDiameter) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }
        public void bind(Cuve cuve)
        {
            capactity.setText(cuve.getCapacity()+"");
            carburanType.setText(cuve.getCarburan_type().getName()+"");
            diameter.setText(cuve.getDiameter()+"mm");
            station.setText(cuve.getStation().getName()+"");

        }
    }
}
