package com.station.bangoura.stationnew.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterPompist;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PompistActivity extends AppCompatActivity {
    List<String> stationsName = new ArrayList<String>();
    String[] stationNames;
    StationService stationService;
    ApiClient api ;
    PompistService pompistService ;
    RecyclerView rv ;
    List<Station> stats;
    List<Pompist> pompis ;
    AdapterPompist adapterPompist ;
    AVLoadingIndicatorView loader ;
    Button btnSubmitPompist ;
    EditText etNamePompist;
    EditText etPhonePompist ;
    Dialog dialog ;
    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pompist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }


        rv = (RecyclerView)findViewById(R.id.rvPompist) ;
        loader = (AVLoadingIndicatorView)findViewById(R.id.loader) ;

        // Pour recuperer les les pompists et les injecter le recyclerview
        api = new ApiClient() ;
        pompistService =  ApiClient.getRetrofit().create(PompistService.class);

        Call<List<Pompist>> pompists = pompistService.getPompists() ;
        pompists.enqueue(new Callback<List<Pompist>>() {
            @Override
            public void onResponse(Call<List<Pompist>> call, Response<List<Pompist>> response) {
                if(response.isSuccessful())
                {
                    // Afficher le loader pour materialiser le chargement
                    loader.show();
                    pompis = response.body() ;
                    adapterPompist = new AdapterPompist(pompis) ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    rv.setAdapter(adapterPompist);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);
//                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    for(Pompist p : response.body())
                    {
                        System.out.println(p.toString());
                    }
                    //cacher le loader
                    loader.hide();

                }
                else
                    Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();



            }

            @Override
            public void onFailure(Call<List<Pompist>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();
                loader.hide();

            }
        });
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent next  = new Intent(getActivity(), LoginActivity.class);
//                startActivity(next);

                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_new_pompist);
                dialog.setTitle("Nouveau Pompist");
                dialog.show();


                final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
                // spinner.setOnItemSelectedListener(getActivit);
                btnSubmitPompist = (Button)dialog.findViewById(R.id.btnSubmitPompist);
                etNamePompist = (EditText)dialog.findViewById(R.id.etNamePompist);
                etPhonePompist = (EditText)dialog.findViewById(R.id.etPhonePompist);
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
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext() , android.R.layout.simple_spinner_item, stationsName);

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
                                        if(response.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Enregistrement effectué", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                        else {
                                            Toast.makeText(getApplicationContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Pompist> call, Throwable t) {

                                        Toast.makeText(getApplicationContext(), "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });


                    }
                });

                //Fin enregistrement pompist







            }
        });

        //Fin de recuperation et injection





    }

}
