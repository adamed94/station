package com.station.bangoura.stationnew.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Station;

import java.util.List;

public class AdapterStation extends RecyclerView.Adapter<AdapterStation.VHStation> {

    private List<Station> stationList ;
    private OnStationClickLister onStationClickLister;



    public AdapterStation(List<Station> stationList) {

        this.stationList = stationList;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    @NonNull
    @Override
    public VHStation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_station,parent,false) ;
        VHStation VH = new VHStation(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull VHStation holder, int position) {
        holder.bind(stationList.get(position));


    }

    @Override
    public int getItemCount() {
        return stationList == null ? 0 : stationList.size();
    }

    public class VHStation extends RecyclerView.ViewHolder {
        private TextView name , city ,create ,state ;

        public VHStation(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name) ;
            city = itemView.findViewById(R.id.tv_city) ;
            state = itemView.findViewById(R.id.tv_state) ;
            create = itemView.findViewById(R.id.tv_create) ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onStationClickLister != null)
                        onStationClickLister.onStationlick(stationList.get(getLayoutPosition()));
                }
            });
        }

        public void bind(Station stat)
        {
            name.setText(stat.getName());
            city.setText(stat.getCity());
            state.setText(stat.getState());
            create.setText(stat.getCreated_at());

        }
    }

    public interface OnStationClickLister
    {
        void onStationlick(Station station);
    }


}
