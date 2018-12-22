package com.station.bangoura.stationnew.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterStation;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStationFragment extends Fragment {

    ApiClient api ;
    StationService stationService ;
    RecyclerView rv ;
    List<Station> stats ;
    AdapterStation adapterStation ;
    String[] stationNames ;
    String str ;

    Dialog dialog ;
    AVLoadingIndicatorView loader ;

    Button btn ;
    Button btnSubmitStation ;
    EditText etNameStation ;
    EditText etCityStation ;

    public ListStationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListStationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListStationFragment newInstance(String param1, String param2) {
        ListStationFragment fragment = new ListStationFragment();
        Bundle args = new Bundle();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                // Inflate the layout for this fragment
                View view = inflater.inflate(R.layout.fragment_list_station, container, false);

                //Toast.makeText(getContext(),"Merci a tous .",Toast.LENGTH_LONG).show();
        loader = (AVLoadingIndicatorView)view.findViewById(R.id.loader) ;
        rv = (RecyclerView)view.findViewById(R.id.rv) ;
        api = new ApiClient() ;
        stationService =  ApiClient.getRetrofit().create(StationService.class);
        Call<List<Station>> stations = stationService.getStations() ;
        stations.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful())

                {
                    loader.show();
                    stats = response.body() ;
                    adapterStation = new AdapterStation(stats) ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()) ;
                    rv.setAdapter(adapterStation);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);

                    stationNames = new String[stats.size()] ;
                    for (int i = 0 ; i < stats.size() ; i++)
                    {
                        stationNames[i] = stats.get(i).getName() ;




                    }


                    //Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();



                    for(Station s : stats)
                    {
                        System.out.println(s.toString());
                    }
                    loader.hide();
                }

                else
                    Toast.makeText(getContext(),"Not Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(getContext(),"Ooops ! Une erreur est survenue .",Toast.LENGTH_LONG).show();
                loader.hide();


            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                Intent next  = new Intent(getActivity(), LoginActivity.class);
//
//
//                startActivity(next);


                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_new_station);
                dialog.setTitle("Nouvelle Station ");
                dialog.show();

                btnSubmitStation =(Button)dialog.findViewById(R.id.btnSubmitStation) ;
                etCityStation = (EditText)dialog.findViewById(R.id.etNameCity) ;
                etNameStation = (EditText)dialog.findViewById(R.id.etNameStation) ;
                btnSubmitStation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        api = new ApiClient();
                        stationService = ApiClient.getRetrofit().create(StationService.class);

                        stationService.addStation(etNameStation.getText().toString(), etCityStation.getText().toString())
                                .enqueue(new Callback<Station>() {
                                    @Override
                                    public void onResponse(Call<Station> call, Response<Station> response) {
                                        if(response.isSuccessful()) {
                                            Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(getContext(), "No " + response.message(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Station> call, Throwable t) {

                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                });

                        adapterStation.notifyDataSetChanged();

                    } });

          }
        });



             return view ;
    }


}
