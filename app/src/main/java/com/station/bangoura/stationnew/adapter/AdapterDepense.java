package com.station.bangoura.stationnew.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Caisse;
import com.station.bangoura.stationnew.models.Depense;

import java.util.List;

public class AdapterDepense extends RecyclerView.Adapter<AdapterDepense.VHDepense> {
    private List<Depense> depenseList ;

    public AdapterDepense(List<Depense> depenseList) {
        this.depenseList = depenseList;
    }

    @NonNull
    @Override
    public AdapterDepense.VHDepense onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_depense,viewGroup,false) ;
        AdapterDepense.VHDepense VH = new AdapterDepense.VHDepense(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDepense.VHDepense vhDepense, int postition) {
        vhDepense.bind(depenseList.get(postition));

    }

    @Override
    public int getItemCount() {
        return depenseList.size();
    }

    public class VHDepense extends  RecyclerView.ViewHolder {
         TextView tvMontDep ,  tvDateDep ;
        Button btnStateDep  ;
        public VHDepense(@NonNull View itemView) {
            super(itemView);
            tvMontDep = (TextView)itemView.findViewById(R.id.tvMontDep) ;
            btnStateDep = (Button)itemView.findViewById(R.id.btnStateDep) ;
            tvDateDep = (TextView)itemView.findViewById(R.id.tvDateDep) ;

        }


        public void bind(Depense depense){

            tvMontDep.setText(depense.getAmount()+" GNF");

            if (depense.getState().equals("pending"))
            {
                btnStateDep.setText("En attente");
                btnStateDep.setBackgroundColor(Color.YELLOW);

            }

            else if (depense.getState().equals("receipt")){

                btnStateDep.setText("Validé");
                btnStateDep.setBackgroundColor(Color.GREEN);

            }
            else
            {
                btnStateDep.setText("Annulé");
                btnStateDep.setBackgroundColor(Color.RED);

            }

            String y , d , m  ;
            y = depense.getCreated_at().toString().substring(0,4) ;
            m = depense.getCreated_at().toString().substring(5,7) ;
            d = depense.getCreated_at().toString().substring(8,10) ;

            tvDateDep.setText(d+"/"+m+"/"+y );




        }
    }
}
