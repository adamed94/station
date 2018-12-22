package com.station.bangoura.stationnew.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Activities;
import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.models.Pomp;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.networkapi.ActivitiesService;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompService;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.StationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtivitiesActivity extends AppCompatActivity {
    Spinner spinPomp , spinPompist , spinCarburant;
    EditText etDepEss , etFinEss , etDepGaz , etFinGaz ;
    ApiClient api;
    List<Station> stats;
    StationService stationService;
    String[] pompistNames;
    PompistService pompistService;
    List<Pompist>  pompistList ;
    List<String> p = new ArrayList<String>();
    List<String>  pompsName = new ArrayList<String>();

    PompService pompService ;
    List<String> typeCarburan = new ArrayList<String>();
    Button btnSubmitActiv ;
    ActivitiesService activitiesService ;
    List<Pomp> pomps ;
    public static final String Email = "emailKey";
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    public static final String mypreference = "mypref";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmitActiv = (Button)findViewById(R.id.btnSubmitActiv) ;
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);

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
        etDepEss =(EditText) findViewById(R.id.etIndDepEss) ;
        etFinEss = (EditText)findViewById(R.id.etIndFinEss) ;
        etDepGaz = (EditText)findViewById(R.id.etIndDepGaz) ;
        etFinGaz = (EditText)findViewById(R.id.etIndFinGaz) ;

        spinPomp = (Spinner)findViewById(R.id.spinPomp) ;
        //spinCarburant = (Spinner)findViewById(R.id.spinCarburant) ;
        spinPompist = (Spinner)findViewById(R.id.spinPompist) ;
        typeCarburan.add("Essence") ;
        typeCarburan.add("Gazoil") ;
        ArrayAdapter<String> dataAdapt = new ArrayAdapter<String>(getBaseContext() , android.R.layout.simple_spinner_item, typeCarburan);
        dataAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
//        spinCarburant.setAdapter(dataAdapt);

        pompService = ApiClient.getRetrofit().create(PompService.class);
       final retrofit2.Call<List<Pomp>> pmpst = pompService.getPompsByStation(1) ;
       pmpst.enqueue(new Callback<List<Pomp>>() {
           @Override
           public void onResponse(Call<List<Pomp>> call, Response<List<Pomp>> response) {



                   if (response.isSuccessful())
                   {
                       pomps = response.body() ;
                       // = new String[pompistList.size()];
                       Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                       int n  = response.body().size() ;
                       for (int t = 0; t < n; t++) {
                           System.out.println(response.body().get(t).getName());
                           pompsName.add(response.body().get(t).getName());
                           Log.d("msg",t+"") ;
                             pomps.add(response.body().get(t)) ;
                            //Toast.makeText(getApplicationContext(),""+pompsName.get(i),Toast.LENGTH_LONG).show();


                       }

                   ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext() , android.R.layout.simple_spinner_item, pompsName);
                   dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                   // attaching data adapter to spinner
                   spinPomp.setAdapter(dataAdapter);


               }
           }

           @Override
           public void onFailure(Call<List<Pomp>> call, Throwable t) {

           }
       });


        pompistService = ApiClient.getRetrofit().create(PompistService.class);
        final retrofit2.Call<List<Pompist>> pompist = pompistService.getPompists() ;
        pompist.enqueue(new Callback<List<Pompist>>() {
            @Override
            public void onResponse(Call<List<Pompist>> call, Response<List<Pompist>> response) {
                if (response.isSuccessful())
                {
                    pompistList = response.body();
                    int t = response.body().size() ;
                    // = new String[pompistList.size()];
                    Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < t; i++) {
                        System.out.println(response.body().get(i).getName());
                         p.add(response.body().get(i).getName());


                         pompistList.add(response.body().get(i));

                         //Toast.makeText(getApplicationContext(),""+p.get(i),Toast.LENGTH_LONG).show();


                    }


                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getBaseContext() , android.R.layout.simple_spinner_item, p);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spinPompist.setAdapter(dataAdapter);

            }

            @Override
            public void onFailure(Call<List<Pompist>> call, Throwable t) {
                Toast.makeText(getApplication(),t.getMessage(),Toast.LENGTH_LONG) ;

            }
        });

        // Enregistrement des activites
        btnSubmitActiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etDepEss.getText().toString().equals("") || etFinEss.getText().toString().equals("") || etDepGaz.getText().toString().equals("") || etDepGaz.getText().toString().equals("")  )
                {
                    Toast.makeText(AtivitiesActivity.this, "Veuillez bien renseigner les index !", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    int r;
                    r = (int) spinPompist.getSelectedItemId();
                    int u;
                    u = (int) spinPomp.getSelectedItemId();
                    if (Integer.valueOf(etDepEss.getText().toString()) > Integer.valueOf(etFinEss.getText().toString())  || Integer.valueOf(etDepGaz.getText().toString()) > Integer.valueOf(etFinGaz.getText().toString()) )
                    {

                        Toast.makeText(AtivitiesActivity.this, "Vos index ne sont pas corrects !", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        activitiesService =  ApiClient.getRetrofit().create(ActivitiesService.class);
                        activitiesService.addActivity(pomps.get(u).getId(),Integer.valueOf(etDepEss.getText().toString()),Integer.valueOf(etFinEss.getText().toString()),Integer.valueOf(etDepGaz.getText().toString()),Integer.valueOf(etFinGaz.getText().toString()) , sharedPreferences.getInt(station, 1) )
                                .enqueue(new Callback<Activities>() {
                                    @Override
                                    public void onResponse(Call<Activities> call, Response<Activities> response) {
                                        if(response.isSuccessful())
                                            Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(AtivitiesActivity.this, "Error : " + response.message(), Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onFailure(Call<Activities> call, Throwable t) {
                                        Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées", Toast.LENGTH_SHORT).show();

                                    }
                                });
                        etFinGaz.setText("");
                        etDepGaz.setText("");
                        etDepEss.setText("");
                        etFinEss.setText("");


                    }





                }





            }
        });









    }

}
