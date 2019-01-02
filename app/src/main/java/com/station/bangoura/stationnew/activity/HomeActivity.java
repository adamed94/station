package com.station.bangoura.stationnew.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.networkapi.ActivitiesService;
import com.station.bangoura.stationnew.networkapi.ApiClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ActivitiesService activitiesService ;
    public static final String userId = "userKey";
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    public static final String mypreference = "mypref";
    TextView tvCoulEss , tvCoulGaz ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        activitiesService = ApiClient.getRetrofit().create(ActivitiesService.class);
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);

        tvCoulEss = (TextView)findViewById(R.id.tvCoulEss) ;
        tvCoulGaz = (TextView)findViewById(R.id.tvCoulGaz) ;

        retrofit2.Call<List<Integer>> listCoulage = activitiesService.getCoulage(1,1) ;
        listCoulage.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful())
                {
                    tvCoulEss.setText(response.body().get(0)+" L ");
                    tvCoulGaz.setText(response.body().get(1)+" L ");

                }

            }

            @Override
            public void onFailure(retrofit2.Call<List<Integer>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage() , Toast.LENGTH_LONG ).show(); ;

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
