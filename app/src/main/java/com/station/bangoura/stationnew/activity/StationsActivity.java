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

public class StationsActivity extends AppCompatActivity {
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
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
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




        //Toast.makeText(getApplicationContext(),"Merci a tous .",Toast.LENGTH_LONG).show();
        loader = (AVLoadingIndicatorView)findViewById(R.id.loader) ;
        rv = (RecyclerView)findViewById(R.id.rv) ;
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
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    rv.setAdapter(adapterStation);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);

                    stationNames = new String[stats.size()] ;
                    for (int i = 0 ; i < stats.size() ; i++)
                    {
                        stationNames[i] = stats.get(i).getName() ;




                    }


                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();



                    for(Station s : stats)
                    {
                        System.out.println(s.toString());
                    }
                    loader.hide();
                }

                else
                    Toast.makeText(getApplicationContext(),"Not Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ooops ! Une erreur est survenue .",Toast.LENGTH_LONG).show();
                loader.hide();


            }
        });

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                Intent next  = new Intent(getActivity(), LoginActivity.class);
//
//
//                startActivity(next);


                dialog = new Dialog(context);
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
                                            Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "No " + response.message(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Station> call, Throwable t) {

                                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();

                                    }
                                });

                        adapterStation.notifyDataSetChanged();

                    } });

            }
        });

    }

}
