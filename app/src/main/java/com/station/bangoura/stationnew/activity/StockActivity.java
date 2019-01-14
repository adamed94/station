package com.station.bangoura.stationnew.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterStation;
import com.station.bangoura.stationnew.adapter.AdapterStock;
import com.station.bangoura.stationnew.models.CarburanType;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.Stock;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CarburanTypeService;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.station.bangoura.stationnew.networkapi.StockService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    ApiClient api;
    StockService stockService;
    List<Stock> stockList;
    AVLoadingIndicatorView loader;
    StationService stationService;
    RecyclerView rv;
    List<Station> stats;
    AdapterStock adapterStock;
    AdapterStation adapterStation;

    String[] stationNames;
    Dialog dialog;
    Button btnSubmitJauge;
    EditText etRuleNumber;
    CarburanTypeService carburanTypeService;
    String[] carburs;
    List<CarburanType> carbs;
    final Context context = this;
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    public static final String userId = "userKey";
    SwipeRefreshLayout mSwipeRefreshLayout ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        loader = (AVLoadingIndicatorView) findViewById(R.id.loader);

        setSupportActionBar(toolbar);
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);
       // Toast.makeText(getApplicationContext(),"Volume : " + sharedPreferences.getInt(station, 1) ,Toast.LENGTH_LONG).show();

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

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        rv = (RecyclerView) findViewById(R.id.rv);

        api = new ApiClient();



        stockService = ApiClient.getRetrofit().create(StockService.class);
        stationService = ApiClient.getRetrofit().create(StationService.class);
        Call<List<Station>> stations = stationService.getStationById( sharedPreferences.getInt(station, 1));
        stations.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful() && response.body()!=null && response.body().size() !=0 )

                {
                    loader.show();

                    stats = response.body();
                    adapterStock = new AdapterStock(stats);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setAdapter(adapterStock);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);

                    stationNames = new String[stats.size()];
                    for (int i = 0; i < stats.size(); i++) {
                        stationNames[i] = stats.get(i).getName();

                        }


                } else
                    Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();


                loader.hide();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(),"Ooops ! Une erreur est survenue." ,Toast.LENGTH_LONG).show() ;


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.setTitle("Jaugeage");

                dialog.setContentView(R.layout.custom_jauge);
                dialog.show();
                final TextView tvVol = (TextView) dialog.findViewById(R.id.tvVol) ;
                final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
                btnSubmitJauge = (Button) dialog.findViewById(R.id.btnSubmitPompist);
                etRuleNumber = (EditText)dialog.findViewById(R.id.etRuleNumber) ;
                //etRuleNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                btnSubmitJauge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if(etRuleNumber.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext()," Veuillez saisir le numéro de la règle de barremage . " ,Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            AlertDialog.Builder builder = new AlertDialog.Builder(StockActivity.this) ;
                            builder.setMessage("Voulez vous vraiment appliquer ce jaugeage ?") ;
                            builder.setTitle("Confirmation") ;
                            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {



                                    Call<Float> volune = stockService.getVolume(sharedPreferences.getInt(station, 1) , spinner.getSelectedItemPosition()+1 , Double.valueOf(etRuleNumber.getText().toString()));
                                    volune.enqueue(new Callback<Float>() {
                                        @Override
                                        public void onResponse(Call<Float> call, Response<Float> response) {
                                            if (response.isSuccessful())
                                            {
                                                Toast.makeText(getApplicationContext(),"Volume : " + response.body().toString() ,Toast.LENGTH_LONG).show();
                                                tvVol.setText(response.body().toString() + " L ");
                                            }
                                            else
                                                Toast.makeText(getApplicationContext(),"Not Success" ,Toast.LENGTH_LONG).show();

                                        }

                                        @Override
                                        public void onFailure(Call<Float> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    });
                                    etRuleNumber.setText("");
                                    dialog.dismiss();

                                }
                            });
                            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            }) ;
                            AlertDialog dialog = builder.create() ;
                            dialog.show();




                        }




                    }
                        }) ;














                carburs = new String[2];
                carburs[0] = "Essence";
                carburs[1] = "Gazoil";


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, carburs);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinner.setAdapter(dataAdapter);



                carburanTypeService = ApiClient.getRetrofit().create(CarburanTypeService.class);
                Call<List<CarburanType>> carburants = carburanTypeService.getCarburanType();
                carburants.enqueue(new Callback<List<CarburanType>>() {
                    @Override
                    public void onResponse(Call<List<CarburanType>> call, Response<List<CarburanType>> response) {
                        if (response.isSuccessful()) {
                            carbs = response.body();

//                            for (int i = 0 ; i < response.body().size() ; i++)
//                            {
//                                System.out.println(carbs.get(i).getName());
//                                if ( carbs.get(i).getName()=="gaz")
//                                  carburs[i] = "Gazoil";
//                                else
//                                    carburs[i] = "Essence";
//
//
//
//
//                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<List<CarburanType>> call, Throwable t) {

                    }
                });


            }
        });


//        api = new ApiClient() ;
//        stockService = ApiClient.getRetrofit().create(StockService.class);
//        final Call<List<Stock>> stocks = stockService.getStock() ;
//        stocks.enqueue(new Callback<List<Stock>>() {
//            @Override
//            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
//                if (response.isSuccessful())
//                {
//
//
//                    stockList = response.body() ;
//                    for( int i = 0 ; i < stockList.size() ; i++) {
//                        if(stockList.get(i)!=null)
//                           System.out.println(stockList.get(i).toString()+"\n");
//                    }
//                }
//                else
//                    System.out.println("Not Success");
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Stock>> call, Throwable t) {
//                System.out.println(t.getMessage());
//
//            }
//        });
//


    }

    @Override
    public void onRefresh() {

        stockService = ApiClient.getRetrofit().create(StockService.class);
        stationService = ApiClient.getRetrofit().create(StationService.class);
        Call<List<Station>> stations = stationService.getStationById( sharedPreferences.getInt(station, 1));
        stations.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful())

                {
                    loader.show();

                    stats = response.body();
                    adapterStock = new AdapterStock(stats);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    rv.setAdapter(adapterStock);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);

                    stationNames = new String[stats.size()];
                    for (int i = 0; i < stats.size(); i++) {
                        stationNames[i] = stats.get(i).getName();

                    }


                } else
                    Toast.makeText(getApplicationContext(), "Not Success", Toast.LENGTH_LONG).show();


                loader.hide();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                loader.hide();
                Toast.makeText(getApplicationContext(),"Ooops ! Une erreur est survenue." ,Toast.LENGTH_LONG).show() ;


            }

        });
        mSwipeRefreshLayout.setRefreshing(false);mSwipeRefreshLayout.setRefreshing(false);

    }
}
