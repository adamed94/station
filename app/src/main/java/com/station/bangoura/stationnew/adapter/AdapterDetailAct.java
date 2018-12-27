package com.station.bangoura.stationnew.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Activities;
import com.station.bangoura.stationnew.models.Pomp;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDetailAct extends RecyclerView.Adapter<AdapterDetailAct.VHDet> {
    private List<Activities> activitiesList ;
    PompService pompService ;


    public AdapterDetailAct(List<Activities> activitiesList ) {
        this.activitiesList = activitiesList;
        this.pompService = ApiClient.getRetrofit().create(PompService.class);
    }

    public List<Activities> getActivitiesList() {
        return activitiesList;
    }

    public void setActivitiesList(List<Activities> activitiesList) {
        this.activitiesList = activitiesList;
    }

    @NonNull
    @Override
    public AdapterDetailAct.VHDet onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_details_act,viewGroup,false) ;
        AdapterDetailAct.VHDet VH = new AdapterDetailAct.VHDet(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetailAct.VHDet holder, int i) {
        holder.bind(activitiesList.get(i));

    }

    @Override
    public int getItemCount() {
        return activitiesList.size();
    }

    public class VHDet extends RecyclerView.ViewHolder {
        private TextView pompist , pompe , gaz , ess , montant ;
        public VHDet(@NonNull View itemView) {
            super(itemView);
           // pompist = (TextView)itemView.findViewById(R.id.tvPmps) ;
            pompe = (TextView)itemView.findViewById(R.id.tvPmp) ;
            gaz = (TextView)itemView.findViewById(R.id.tvGaz) ;
            ess = (TextView)itemView.findViewById(R.id.tvEss);
            montant = (TextView)itemView.findViewById(R.id.tvMont) ;

        }

        public void bind(final Activities activities) {
            Pomp p ;
            final List<String> s = new ArrayList<String>();
            retrofit2.Call<Pomp> po = pompService.getPomp(activities.getPomp_id());
            po.enqueue(new Callback<Pomp>() {

                @Override
                public void onResponse(retrofit2.Call<Pomp> call, Response<Pomp> response) {
                    if (response.isSuccessful()) {
                        s.add(response.body().getName()) ;
                        pompe.setText("Pompe " +activities.getPomp_id());

                        Log.d("msg","Good") ;
                    }
                    else
                        Log.d("msg","Retard") ;

                }

                @Override
                public void onFailure(retrofit2.Call<Pomp> call, Throwable t) {
                    Log.d("msg",t.getMessage()) ;

                }
            });



            gaz.setText((activities.getindex_gaz_end()-activities.getindex_gaz_start())+" L " );
            ess.setText((activities.getindex_ess_end()-activities.getindex_ess_start())+" L " ) ;
            montant.setText((activities.getAmount_ess()+activities.getAmount_gaz())+" GNF");



        }
    }
}
