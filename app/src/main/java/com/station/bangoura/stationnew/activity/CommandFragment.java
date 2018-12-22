package com.station.bangoura.stationnew.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.ProvisionningService;
import com.station.bangoura.stationnew.networkapi.StationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommandFragment extends Fragment {

    List<String> stationsName = new ArrayList<String>();
    ProvisionningService provisionningService;
    List<Station> stats;
    String[] stationNames;
    Button btnSubmitCommand ;
    StationService stationService ;
    ApiClient api ;
    EditText etQtyEss , etQtyGaz ;





    public CommandFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_command, container, false) ;
        final Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
       // spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this.getActivity());
        stationService = ApiClient.getRetrofit().create(StationService.class);
        Call<List<Station>> stations = stationService.getStations();
        stations.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful())

                {
                    stats = response.body();
                    stationNames = new String[stats.size()];
                    for (int i = 0; i < stats.size(); i++) {
                        System.out.println(stats.get(i).getName());
                        stationsName.add(stats.get(i).getName()) ;

                    }
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stationsName);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);

            }
            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

                Toast.makeText(getContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

            }
        });

        etQtyEss =(EditText)view.findViewById(R.id.etQtyEss) ;
        etQtyGaz = (EditText)view.findViewById(R.id.etQtyGaz) ;

        btnSubmitCommand = (Button)view.findViewById(R.id.btnSubmitCommand);


        // Enregistrement du nouveau pompist


        provisionningService = ApiClient.getRetrofit().create(ProvisionningService.class);
        btnSubmitCommand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Declaration variable et recuperation de l'id de l'item selectionné
                int r;
                r = (int) spinner.getSelectedItemId();
                int idStation = stats.get(r).getId() ;
                int qtyEss = Integer.valueOf(etQtyEss.getText().toString()) ;
                int qtyGaz = Integer.valueOf(etQtyGaz.getText().toString());



                        String qteEss = etQtyEss.getText().toString() ;
                String qteGaz = etQtyGaz.getText().toString() ;

                int qtyTotal;
                qtyTotal = Integer.valueOf(qteEss) + Integer.valueOf(qteGaz);
                System.out.println(qtyTotal);
                provisionningService.addCmdLivrs(qtyTotal,qtyEss,qtyGaz,"",idStation,1)
                        .enqueue(new Callback<CmdLivrs>() {
                            @Override
                            public void onResponse(Call<CmdLivrs> call, Response<CmdLivrs> response) {
                                if(response.isSuccessful())
                                    Toast.makeText(getContext(), "Enregistrement effectué", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(getContext(), "Error : "+response.message(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<CmdLivrs> call, Throwable t) {

                                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

        //Fin enregistrement pompist




        return view;
    }

}
