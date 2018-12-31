package com.station.bangoura.stationnew.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterDetailAct;
import com.station.bangoura.stationnew.models.Activities;
import com.station.bangoura.stationnew.networkapi.ActivitiesService;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetListActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener{

    ActivitiesService activitiesService ;
    List<Activities> activitiesList ;
    RecyclerView rvDetList ;
    AdapterDetailAct adapterDetailAct ;
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    AVLoadingIndicatorView loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        rvDetList = (RecyclerView)findViewById(R.id.rvDetList);
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);
        loader = (AVLoadingIndicatorView) findViewById(R.id.loader);

        //Ceci pour remplir les differentes activites avec la

        activitiesService = ApiClient.getRetrofit().create(ActivitiesService.class);
        Call<List<Activities>> acts = activitiesService.getActivities(sharedPreferences.getInt(station, 0));
        acts.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                loader.show();
                if (response.isSuccessful()) {

                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    activitiesList = response.body();
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    adapterDetailAct = new AdapterDetailAct(activitiesList);
                    rvDetList.setAdapter(adapterDetailAct);
                    rvDetList.setLayoutManager(mLayoutManager);
                    rvDetList.setHasFixedSize(true);
                    loader.hide();


                } else {
                    loader.hide();
                    Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<Activities>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AtivitiesActivity.class);
                startActivity(intent);

            }
        });
    }



    public void remplissage()
    {











    }

    @Override
    public void onRefresh() {

        activitiesService = ApiClient.getRetrofit().create(ActivitiesService.class);
        Call<List<Activities>> acts = activitiesService.getActivities(sharedPreferences.getInt(station, 0));
        acts.enqueue(new Callback<List<Activities>>() {
            @Override
            public void onResponse(Call<List<Activities>> call, Response<List<Activities>> response) {
                  loader.show();
                if (response.isSuccessful()) {

                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    activitiesList = response.body();
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    adapterDetailAct = new AdapterDetailAct(activitiesList);
                    rvDetList.setAdapter(adapterDetailAct);
                    rvDetList.setLayoutManager(mLayoutManager);
                    rvDetList.setHasFixedSize(true);
                    loader.hide();


                } else {
                    loader.hide();
                    Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<Activities>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

            }
        });

        loader.hide();



    }
}
