package com.station.bangoura.stationnew.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStationActivity extends AppCompatActivity {

    ApiClient api ;
    StationService stationService ;
    RecyclerView rv ;
    List<Station> stats ;
    AdapterStation adapterStation ;
    String[] stationNames ;
    String str ;
    Button btn ;
    Button btnSubmitStation ;
    EditText etNameStation ;
    EditText etCityStation ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmitStation =(Button)findViewById(R.id.btnSubmitStation) ;
        etCityStation = (EditText)findViewById(R.id.etNameCity) ;
        etNameStation = (EditText)findViewById(R.id.etNameStation) ;

        btnSubmitStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api = new ApiClient();
                stationService = ApiClient.getRetrofit().create(StationService.class);

                stationService.addStation(etNameStation.getText().toString(), etCityStation.getText().toString())
                        .enqueue(new Callback<Station>() {
                            @Override
                            public void onResponse(Call<Station> call, Response<Station> response) {
                                if(response.isSuccessful())
                                Toast.makeText(AddStationActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(AddStationActivity.this, "No "+response.message(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Station> call, Throwable t) {

                                Toast.makeText(AddStationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            } });



    }

}
