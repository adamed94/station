package com.station.bangoura.stationnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterStation;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.StationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPompistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ApiClient api;
    List<Station> stats;
    StationService stationService;
    PompistService pompistService;
    String[] stationNames;
    List<String> stationsName = new ArrayList<String>();
    Button btnSubmitPompist;

    EditText etNamePompist, etPhonePompist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pompist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        btnSubmitPompist = (Button) findViewById(R.id.btnSubmitPompist);
        etNamePompist = (EditText) findViewById(R.id.etNamePompist);
        etPhonePompist = (EditText) findViewById(R.id.etPhonePompist);

        // Recuperer la liste des stations
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
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddPompistActivity.this, android.R.layout.simple_spinner_item, stationsName);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);

            }
            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

            }
        });

        // Enregistrement du nouveau pompist
        pompistService = ApiClient.getRetrofit().create(PompistService.class);
        btnSubmitPompist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Declaration variable et recuperation de l'id de l'item selectionné
                int r;
                r = (int) spinner.getSelectedItemId();
                pompistService.addPompist(etNamePompist.getText().toString(), etPhonePompist.getText().toString() ,(int)stats.get(r).getId() )
                        .enqueue(new Callback<Pompist>() {
                            @Override
                            public void onResponse(Call<Pompist> call, Response<Pompist> response) {
                                if(response.isSuccessful())
                                    Toast.makeText(AddPompistActivity.this, "Enregistrement effectué", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(AddPompistActivity.this, "Error : "+response.message(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Pompist> call, Throwable t) {

                                Toast.makeText(AddPompistActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

        //Fin enregistrement pompist

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}






