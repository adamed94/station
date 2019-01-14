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
import com.station.bangoura.stationnew.adapter.AdapterDepense;
import com.station.bangoura.stationnew.models.CaisseSolde;
import com.station.bangoura.stationnew.models.Depense;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CaisseService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDepenseActivity extends AppCompatActivity  implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvListDep;
    CaisseService caisseService  ;
    SwipeRefreshLayout mSwipeRefreshLayout  ;
    AVLoadingIndicatorView loader ;
    public static final String station = "stationKey";
    public static final String user = "userKey";
    SharedPreferences sharedPreference ;
    List<Depense> depenseList ;
    AdapterDepense adapterDepense ;
    TextView tvSoldeCaisse ;
    Integer solde = 0 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_depense);
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

        tvSoldeCaisse = (TextView)findViewById(R.id.tvSoldeCaisse) ;
        sharedPreference = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);





        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;

        Call<CaisseSolde> soldeCaisse = caisseService.getSoldeCaisse(sharedPreference.getInt(station, 1 )) ;
        soldeCaisse.enqueue(new Callback<CaisseSolde>() {
            @Override
            public void onResponse(Call<CaisseSolde> call, Response<CaisseSolde> response) {
                if(response.isSuccessful()){
                    solde = response.body().getAmount() ;
                    tvSoldeCaisse.setText(solde +  " GNF");

                }
                else
                    tvSoldeCaisse.setText(0 +  " GNF");

            }

            @Override
            public void onFailure(Call<CaisseSolde> call, Throwable t) {
                tvSoldeCaisse.setText(0 +  " GNF");
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survrnue .", Toast.LENGTH_LONG).show();

            }
        });





        rvListDep = (RecyclerView)findViewById(R.id.rvListDep) ;
        loader = (AVLoadingIndicatorView)findViewById(R.id.loader) ;
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipRefreshLayout) ;

        mSwipeRefreshLayout.setOnRefreshListener(this);
        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;
        Call <List<Depense>> depenses = caisseService.getDepenses( sharedPreference.getInt(user, 1) ,  sharedPreference.getInt(station, 1) )  ;
        depenses.enqueue(new Callback<List<Depense>>() {
            @Override
            public void onResponse(Call<List<Depense>> call, Response<List<Depense>> response) {

                if (response.isSuccessful())
                {
                    loader.show();
                   // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    depenseList = response.body() ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    adapterDepense = new AdapterDepense(depenseList) ;

                    rvListDep.setAdapter(adapterDepense);
                    rvListDep.setLayoutManager(mLayoutManager);
                    rvListDep.setHasFixedSize(true);
                    loader.hide();

                }
                else  {
                    loader.hide();
                    Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<List<Depense>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();


            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), CaisseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {



        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;
        Call <List<Depense>> depenses = caisseService.getDepenses( sharedPreference.getInt(user, 1) ,  sharedPreference.getInt(station, 1) )  ;
        depenses.enqueue(new Callback<List<Depense>>() {
            @Override
            public void onResponse(Call<List<Depense>> call, Response<List<Depense>> response) {

                if (response.isSuccessful())
                {
                    loader.show();
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    depenseList = response.body() ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext()) ;
                    adapterDepense = new AdapterDepense(depenseList) ;

                    rvListDep.setAdapter(adapterDepense);
                    rvListDep.setLayoutManager(mLayoutManager);
                    rvListDep.setHasFixedSize(true);
                    mSwipeRefreshLayout.setRefreshing(false);

                }
                else  {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<List<Depense>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(), "Ooops ! Une erreur est survenue .", Toast.LENGTH_LONG).show();


            }
        });







    }
}
