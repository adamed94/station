package com.station.bangoura.stationnew.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.station.bangoura.stationnew.adapter.AdapterCmdPending;
import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.ProvisionningService;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class ComandActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    Button btn ;
    RecyclerView rv ;
    AdapterCmdPending adapterCmdPending;
    AVLoadingIndicatorView loader ;
    EditText etQtyEss , etQtyGaz ;
    List<Station> stats;
    ApiClient api;
    Button btnSubmitCommand ;
    String[] stationNames;
    List<CmdLivrs> cmdLivrs;
    ProvisionningService provisionningService ;
    List<String> stationsName = new ArrayList<String>();
    StationService stationService ;
    Dialog dialog ;
    final Context context = this;
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(this);


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

        rv = (RecyclerView) findViewById(R.id.rvCmd);
        loader = (AVLoadingIndicatorView) findViewById(R.id.loader);


        btn = (Button) findViewById(R.id.button);


        //Remplissage de la RecyclerView a l'ouverture de l'activité
        provisionningService = ApiClient.getRetrofit().create(ProvisionningService.class);

        final retrofit2.Call<List<CmdLivrs>> cmdLvrs = provisionningService.getCmdLivrs(sharedPreferences.getInt(station, 1));
        cmdLvrs.enqueue(new Callback<List<CmdLivrs>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CmdLivrs>> call, Response<List<CmdLivrs>> response) {
                if (response.isSuccessful()) {


                    // Afficher le loader pour materialiser le chargement
                    loader.show();
                   // Toast.makeText(getApplicationContext(), "Well Done !", Toast.LENGTH_LONG).show();
                    cmdLivrs = response.body();

                    adapterCmdPending = new AdapterCmdPending(cmdLivrs);

                    for (int i = 0; i < cmdLivrs.size(); i++) {
                        //System.out.println(cmdLivrs.get(i).toString());
                        //Toast.makeText(getApplicationContext(), cmdLivrs.get(i).toString() , Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Well Done ." + i, Toast.LENGTH_LONG).show();
                    }

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setAdapter(adapterCmdPending);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);
                    loader.hide();


                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<CmdLivrs>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                loader.hide();

            }
        });

        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_new_cmd);
                dialog.setTitle("Nouvelle Commande ");
                btnSubmitCommand = (Button) dialog.findViewById(R.id.btnSubmitCommand);
                etQtyEss = (EditText) dialog.findViewById(R.id.etQtyEss);
                etQtyGaz = (EditText) dialog.findViewById(R.id.etQtyGaz);
                dialog.show();


                final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
                // spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this.getActivity());
                stationService = ApiClient.getRetrofit().create(StationService.class);
                retrofit2.Call<List<Station>> stations = stationService.getStations();
                stations.enqueue(new Callback<List<Station>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<Station>> call, Response<List<Station>> response) {
                        if (response.isSuccessful())

                        {
                            stats = response.body();
                            stationNames = new String[stats.size()];
                            for (int i = 0; i < stats.size(); i++) {
                                System.out.println(stats.get(i).getName());
                                stationsName.add(stats.get(i).getName());

                            }
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, stationsName);

                        // Drop down layout style - list view with radio button
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spinner.setAdapter(dataAdapter);

                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<Station>> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

                    }
                });


                btnSubmitCommand.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // Declaration variable et recuperation de l'id de l'item selectionné
                        int r;
                        int qtyEss;
                        int qtyGaz;

                        String qteEss = etQtyEss.getText().toString();
                        String qteGaz = etQtyGaz.getText().toString();
                        r = (int) spinner.getSelectedItemId();
                        int idStation = stats.get(r).getId();
                        if (qteEss != "") {
                            qtyEss = Integer.valueOf(etQtyEss.getText().toString());

                        } else {
                            qtyEss = 0;
                        }

                        if (qteGaz != "") {
                            qtyGaz = Integer.valueOf(etQtyGaz.getText().toString());

                        } else {
                            qtyGaz = 0;
                        }


                        int qtyTotal;
                        qtyTotal = Integer.valueOf(qteEss) + Integer.valueOf(qteGaz);
                        System.out.println(qtyTotal);
                        provisionningService.addCmdLivrs(qtyTotal, qtyEss, qtyGaz, "", idStation, 1)
                                .enqueue(new Callback<CmdLivrs>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<CmdLivrs> call, Response<CmdLivrs> response) {
                                        if (response.isSuccessful())
                                            Toast.makeText(getApplicationContext(), "Enregistrement effectué", Toast.LENGTH_SHORT).show();

                                        else
                                            Toast.makeText(getApplicationContext(), "Error : " + response.message(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<CmdLivrs> call, Throwable t) {

                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                });


            }
        });


    }

    @Override
    public void onRefresh() {


        // Raffraichir la RecyclerView des commandes
        final retrofit2.Call<List<CmdLivrs>> cmdLvrs = provisionningService.getCmdLivrs(sharedPreferences.getInt(station, 1));

        cmdLvrs.enqueue(new Callback<List<CmdLivrs>>() {
            @Override
            public void onResponse(retrofit2.Call<List<CmdLivrs>> call, Response<List<CmdLivrs>> response) {
                if (response.isSuccessful()) {


                    // Afficher le loader pour materialiser le chargement
                    loader.show();
                    // Toast.makeText(getApplicationContext(), "Well Done !", Toast.LENGTH_LONG).show();
                    cmdLivrs = response.body();

                    adapterCmdPending = new AdapterCmdPending(cmdLivrs);

                    for (int i = 0; i < cmdLivrs.size(); i++) {
                        System.out.println(cmdLivrs.get(i).toString());
                        //Toast.makeText(getApplicationContext(), cmdLivrs.get(i).toString() , Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(), "Well Done ." + i, Toast.LENGTH_LONG).show();
                    }

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setAdapter(adapterCmdPending);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);
                    loader.hide();


                }


            }

            @Override
            public void onFailure(retrofit2.Call<List<CmdLivrs>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                loader.hide();

            }
        });


        mSwipeRefreshLayout.setRefreshing(false);
    }
}
