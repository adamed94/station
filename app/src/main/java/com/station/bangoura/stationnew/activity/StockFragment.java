package com.station.bangoura.stationnew.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterStation;
import com.station.bangoura.stationnew.adapter.AdapterStock;
import com.station.bangoura.stationnew.models.CarburanType;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.Stock;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CarburanTypeService;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.station.bangoura.stationnew.networkapi.StockService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockFragment extends Fragment {


    ApiClient api;
    StockService stockService ;
    List<Stock> stockList ;
    AVLoadingIndicatorView loader ;
    StationService stationService ;
    RecyclerView rv ;
    List<Station> stats ;
    AdapterStock adapterStock ;
    AdapterStation adapterStation ;
    String[] stationNames ;
    Dialog dialog ;
    Button btnSubmitJauge ;
    EditText etRuleNumber ;
    CarburanTypeService carburanTypeService ;
    String[] carburs ;
    List<CarburanType> carbs ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view =  inflater.inflate(R.layout.fragment_stock, container, false);

        rv = (RecyclerView)view.findViewById(R.id.rv) ;

        api = new ApiClient() ;
        stockService =  ApiClient.getRetrofit().create(StockService.class);
        stationService =  ApiClient.getRetrofit().create(StationService.class);
        Call<List<Station>> stations = stationService.getStations() ;
        stations.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful())

                {

                    stats = response.body() ;
                    adapterStock = new AdapterStock(stats) ;
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()) ;
                    rv.setAdapter(adapterStock);
                    rv.setLayoutManager(mLayoutManager);
                    rv.setHasFixedSize(true);

                    stationNames = new String[stats.size()] ;
                    for (int i = 0 ; i < stats.size() ; i++)
                    {
                        stationNames[i] = stats.get(i).getName() ;




                    }


                    //Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();



                    for(Station s : stats)
                    {
                        System.out.println(s.toString());
                    }

                }

                else
                    Toast.makeText(getContext(),"Not Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();



            }
        });



        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setTitle("Jaugeage");

                dialog.setContentView(R.layout.custom_jauge);
                dialog.show();
                final Spinner spinner = (Spinner)dialog.findViewById(R.id.spinner);
                btnSubmitJauge = (Button)dialog.findViewById(R.id.btnSubmitPompist);
                etRuleNumber  =  (EditText) dialog.findViewById(R.id.etRuleNumber);
                carburanTypeService = ApiClient.getRetrofit().create(CarburanTypeService.class);
                Call<List<CarburanType>> carburants = carburanTypeService.getCarburanType() ;
                carburants.enqueue(new Callback<List<CarburanType>>() {
                    @Override
                    public void onResponse(Call<List<CarburanType>> call, Response<List<CarburanType>> response) {
                        if (response.isSuccessful()) {
                          carbs = response.body() ;
                           carburs = new String[carbs.size()] ;
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
                            carburs[0]  ="Essence" ;
                            carburs[1]  ="Gazoil" ;


                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, carburs);

                            // Drop down layout style - list view with radio button
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            spinner.setAdapter(dataAdapter);
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












        return view;
    }




}
