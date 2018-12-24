package com.station.bangoura.stationnew.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.Stock;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.StockService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.VHStock> {


    private List<Station> stationList ;

    TextView ess , gaz ,dat ;
    ApiClient api;
    StockService stockService ;
    int r = 0;
    private AdapterStation.OnStationClickLister onStationClickLister;



    public AdapterStock(List<Station> stationList) {

        this.stationList = stationList;
        api = new ApiClient() ;
        stockService = ApiClient.getRetrofit().create(StockService.class);



    }

    public List<Station> getStationList() {
        return stationList;
    }

    @NonNull
    @Override
    public AdapterStock.VHStock onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock,parent,false) ;
        AdapterStock.VHStock VH = new AdapterStock.VHStock(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStock.VHStock holder, int position) {
        holder.bind(stationList.get(position));





    }

    @Override
    public int getItemCount() {
        return stationList == null ? 0 : stationList.size();
    }

    public class VHStock extends RecyclerView.ViewHolder {
        private TextView essTheo , gazTheo , essPhy , gazPhy ,dat ,tvNameStation , tvCoulageGaz ,tvCoulageEss ;

        public VHStock(@NonNull View itemView) {
            super(itemView);

            essTheo = itemView.findViewById(R.id.tvTheoEss) ;
            essPhy = itemView.findViewById(R.id.tvPhyEss) ;
            gazTheo = itemView.findViewById(R.id.tvTheoGaz) ;
            gazPhy = itemView.findViewById(R.id.tvPhyGaz) ;
            tvCoulageGaz = itemView.findViewById(R.id.tvCoulageGaz) ;
            tvCoulageEss = itemView.findViewById(R.id.tvCoulageEss) ;

            tvNameStation = itemView.findViewById(R.id.tvNameStation) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onStationClickLister != null)
                        onStationClickLister.onStationlick(stationList.get(getLayoutPosition()));
                }
            });
        }

        public void bind(final Station stat)
        {

            List<Stock> stockList ;


            List<Stock> stocskList ;
            final Call<List<Stock>> stocks = stockService.getStock(stat.getId()) ;
            stocks.enqueue(new Callback<List<Stock>>() {
                @Override
                public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                    int theoEss = 0;
                    int phyEss= 0;
                    int theoGaz = 0;
                    int phyGaz= 0;
                    Stock es ;
                    Stock ga ;
                    if (response.isSuccessful())
                    {
                       for (int i = 0 ; i < response.body().size() ; i ++)
                       {
                           if (stat.getId() == response.body().get(i).getStation_id())
                           {
                               System.out.println("Egal");

                               theoEss = response.body().get(0).getStock_theor() ;
                               phyEss =  response.body().get(0).getStock_reel() ;

                               theoGaz = response.body().get(1).getStock_theor() ;
                               phyGaz =  response.body().get(1).getStock_reel() ;



                           }
                           else
                               System.out.println("Not Egal");

                       }





                          }

                    tvNameStation.setText(stat.getName().toUpperCase());
                    essTheo.setText(theoEss+" L");

                    essPhy.setText(phyEss+" L");
                    gazTheo.setText(theoGaz+" L");
                    gazPhy.setText(phyGaz+"L");
                    tvCoulageEss.setText((theoEss-phyEss)+" L");
                    tvCoulageGaz.setText((theoGaz-phyGaz)+" L");


                }





                @Override
                public void onFailure(Call<List<Stock>> call, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });








        }
    }

    public interface OnStationClickLister
    {
        void onStationlick(Station station);
    }





}
