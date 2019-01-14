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
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterCaisse;
import com.station.bangoura.stationnew.adapter.AdapterDetailAct;
import com.station.bangoura.stationnew.models.Caisse;
import com.station.bangoura.stationnew.models.CaisseSolde;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CaisseService;
import com.wang.avi.AVLoadingIndicatorView;

import java.net.Inet4Address;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListApproCaisseActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvListAppro ;
    CaisseService caisseService  ;
    SwipeRefreshLayout mSwipeRefreshLayout  ;
    AVLoadingIndicatorView loader ;
    public static final String station = "stationKey";
    public static final String user = "userKey";
    SharedPreferences sharedPreference ;
    List<Caisse> caisseList ;
    AdapterCaisse adapterCaisse ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_appro_caisse);
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



        rvListAppro = (RecyclerView)findViewById(R.id.rvListAppro);
        loader = (AVLoadingIndicatorView)findViewById(R.id.loader) ;
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipRefreshLayout)  ;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        sharedPreference = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);










        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;
        Call<List<Caisse>> caisses = caisseService.getApproCaisses(sharedPreference.getInt(station, 1 ) ) ;
        caisses.enqueue(new Callback<List<Caisse>>() {
            @Override
            public void onResponse(Call<List<Caisse>> call, Response<List<Caisse>> response) {
                if (response.isSuccessful())
                {
                   // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    caisseList = response.body() ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    adapterCaisse = new AdapterCaisse(caisseList) ;

                    rvListAppro.setAdapter(adapterCaisse);
                    rvListAppro.setLayoutManager(mLayoutManager);
                    rvListAppro.setHasFixedSize(true);
                    loader.hide();

                }


                else
            {

                caisseList = response.body() ;
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                adapterCaisse = new AdapterCaisse(caisseList) ;

                rvListAppro.setAdapter(adapterCaisse);
                rvListAppro.setLayoutManager(mLayoutManager);
                rvListAppro.setHasFixedSize(true);
                loader.hide();

            }



        }

            @Override
            public void onFailure(Call<List<Caisse>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(),CaisseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {



        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;
        Call<List<Caisse>> caisses = caisseService.getApproCaisses(sharedPreference.getInt(station, 1 ) ) ;
        caisses.enqueue(new Callback<List<Caisse>>() {
            @Override
            public void onResponse(Call<List<Caisse>> call, Response<List<Caisse>> response) {
                if (response.isSuccessful())
                {
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    caisseList = response.body() ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    adapterCaisse = new AdapterCaisse(caisseList) ;

                    rvListAppro.setAdapter(adapterCaisse);
                    rvListAppro.setLayoutManager(mLayoutManager);
                    rvListAppro.setHasFixedSize(true);

                    mSwipeRefreshLayout.setRefreshing(false);

                }


                else
                {

                    caisseList = response.body() ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    adapterCaisse = new AdapterCaisse(caisseList) ;

                    rvListAppro.setAdapter(adapterCaisse);
                    rvListAppro.setLayoutManager(mLayoutManager);
                    rvListAppro.setHasFixedSize(true);
                    mSwipeRefreshLayout.setRefreshing(false);

                }



            }

            @Override
            public void onFailure(Call<List<Caisse>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

            }
        });




    }
}
