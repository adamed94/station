package com.station.bangoura.stationnew.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterCuve;
import com.station.bangoura.stationnew.models.Cuve;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CuveService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListCuveFragment extends Fragment {

    ApiClient api ;
    CuveService cuveService ;
    List<Cuve> cuves;
    AVLoadingIndicatorView loader ;
    RecyclerView rv ;
    AdapterCuve adapterCuve ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_list_cuve, container, false);
        loader = (AVLoadingIndicatorView)view.findViewById(R.id.loader) ;
        rv = (RecyclerView)view.findViewById(R.id.rvCuve) ;
        api = new ApiClient() ;
        cuveService = ApiClient.getRetrofit().create(CuveService.class);
        Call<List<Cuve>> listCuves =  cuveService.getCuves() ;
        listCuves.enqueue(new Callback<List<Cuve>>() {
            @Override
            public void onResponse(Call<List<Cuve>> call, Response<List<Cuve>> response) {
                if(response.isSuccessful())
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                   loader.show();
                    cuves = response.body() ;

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()) ;

                rv.setLayoutManager(mLayoutManager);
                rv.setHasFixedSize(true);
                adapterCuve = new AdapterCuve(cuves) ;
                rv.setAdapter(adapterCuve);
                for(Cuve c : response.body())
                {
                    System.out.println(c.toString());
                }
                //cacher le loader
                loader.hide();
            }

            @Override
            public void onFailure(Call<List<Cuve>> call, Throwable t) {
                Toast.makeText(getContext(),"Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();
                loader.hide();

            }
        });



        return view ;
    }


}
