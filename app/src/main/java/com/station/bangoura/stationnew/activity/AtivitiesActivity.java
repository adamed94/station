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
import android.widget.CheckBox;
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
import com.station.bangoura.stationnew.models.Stock;
import com.station.bangoura.stationnew.networkapi.ActivitiesService;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.PompService;
import com.station.bangoura.stationnew.networkapi.PompistService;
import com.station.bangoura.stationnew.networkapi.StationService;
import com.station.bangoura.stationnew.networkapi.StockService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtivitiesActivity extends AppCompatActivity {
    Spinner spinPomp , spinPompist , spinCarburant;
    EditText etDepEss , etFinEss , etDepGaz , etFinGaz , etRCEss , etRCGaz;
    ApiClient api;
    List<Station> stats;
    StationService stationService;
    String[] pompistNames;
    PompistService pompistService;
    List<Pompist>  pompistList ;
    List<String> p = new ArrayList<String>();
    List<String>  pompsName = new ArrayList<String>();
    List<Integer> lstIndx ;
    PompService pompService ;
    List<String> typeCarburan = new ArrayList<String>();
    Button btnSubmitActiv ;
    ActivitiesService activitiesService ;
    List<Pomp> pomps ;
    public static final String Email = "emailKey";
    SharedPreferences sharedPreferences ;
    public static final String station = "stationKey";
    public static final String mypreference = "mypref";
    CheckBox cbEss , cbGaz ;
    public Integer last_ind_ess , last_ind_gaz ;
    int idItemSelected ;
    StockService stockService;
    int theoEss = 0;
    int phyEss= 0;
    int theoGaz = 0;
    int phyGaz= 0;
    int rce , rcg ;
    int idPomp ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ativities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmitActiv = (Button)findViewById(R.id.btnSubmitActiv) ;
        sharedPreferences = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);
        stockService = ApiClient.getRetrofit().create(StockService.class);

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
        activitiesService =  ApiClient.getRetrofit().create(ActivitiesService.class);
        etRCEss = (EditText)findViewById(R.id.etRCEss) ;
        etRCGaz =   (EditText)findViewById(R.id.etRCGaz) ;
        cbEss= (CheckBox)findViewById(R.id.cbEss) ;
        cbGaz= (CheckBox)findViewById(R.id.cbGaz) ;

        cbGaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbGaz.isChecked()) {

                    etRCGaz.setVisibility(View.VISIBLE);

                }

                else {

                    etRCGaz.setVisibility(View.INVISIBLE);
                    rcg = 0 ;

                }


            }
        });
        cbEss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbEss.isChecked())
                    etRCEss.setVisibility(View.VISIBLE);
                else{
                    etRCEss.setVisibility(View.INVISIBLE);
                    rce = 0 ;

                }



            }
        });
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
                      // Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                       int n  = response.body().size() ;

                       for (int t = 0; t < n; t++) {
                          // System.out.println(response.body().get(t).getName());
                           pompsName.add(response.body().get(t).getName());

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
                    //Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < t; i++) {
                         //System.out.println(response.body().get(i).getName());
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
            int code ;
            String codeS ;
            @Override
            public void onClick(View v) {
                //verifier si les checkboxs sont coches et affecter leurs valeurs a travers les champs sinon la valeur par defaut 0 est maintenue
                if (cbEss.isChecked())
                    rce = Integer.valueOf(etRCEss.getText().toString()) ;
                 if (cbGaz.isChecked())
                     rcg = Integer.valueOf(etRCGaz.getText().toString()) ;

                //Ici pour verifier si apres lindexage il y a le stock disponible .
                List<Stock> stockList ;

                List<Stock> stocskList ;
                final Call<List<Stock>> stocks = stockService.getStock(sharedPreferences.getInt(station, 1)) ;
                stocks.enqueue(new Callback<List<Stock>>() {
                    @Override
                    public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {

                        Stock es ;
                        Stock ga ;
                        if (response.isSuccessful())
                        {
                            for (int i = 0 ; i < response.body().size() ; i ++)
                            {
                                if (sharedPreferences.getInt(station, 1) == response.body().get(i).getStation_id())
                                {
                                    System.out.println("Egal");

                                    theoEss = response.body().get(0).getStock_theor() ;
                                    phyEss =  response.body().get(0).getStock_reel() ;

                                    theoGaz = response.body().get(1).getStock_theor() ;
                                    phyGaz =  response.body().get(1).getStock_reel() ;



                                }
                                else
                                    System.out.println("Not Egal");

                            }





                        }



                    }





                    @Override
                    public void onFailure(Call<List<Stock>> call, Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });




                //Recuperation de l'id de la pompe selectionnee  en recuperant dabord 'id de l'item selectionné .
                // Et recuperer l'id de la pompe dans la liste des pompes

                idItemSelected = (int) spinPomp.getSelectedItemId();
                idPomp = pomps.get(idItemSelected).getId() ;

              Call<List<Integer>> lastIndex =   activitiesService.getLastIndex(sharedPreferences.getInt(station, 1),idPomp);

              lastIndex.enqueue(new Callback<List<Integer>>() {
                  @Override
                  public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                      code = response.code() ;
                      codeS = response.message() ;
                      if (response.isSuccessful()) {
                          lstIndx = response.body();
                          codeS = response.message() ;
                          last_ind_ess = lstIndx.get(0)  ;
                          last_ind_gaz = lstIndx.get(1);

                          int r;
                          r = (int) spinPompist.getSelectedItemId();
                          int u;
                          u = (int) spinPomp.getSelectedItemId();


                         if (etDepEss.getText().toString().equals("") || etFinEss.getText().toString().equals("") || etDepGaz.getText().toString().equals("") || etDepGaz.getText().toString().equals("")  )
                          {
                              Toast.makeText(AtivitiesActivity.this, "Veuillez bien renseigner les index !", Toast.LENGTH_SHORT).show();

                          }
                          else if (Integer.valueOf(etDepEss.getText().toString()) > Integer.valueOf(etFinEss.getText().toString())  || Integer.valueOf(etDepGaz.getText().toString()) > Integer.valueOf(etFinGaz.getText().toString()) )
                          {

                              Toast.makeText(AtivitiesActivity.this, "Vos index ne sont pas corrects !", Toast.LENGTH_SHORT).show();

                          }


                          else if ((last_ind_ess.toString().equals(etDepEss.getText().toString()) && last_ind_gaz.toString().equals(etDepGaz.getText().toString())) )
                          {
                              if ((Integer.valueOf(etFinEss.getText().toString()) - Integer.valueOf(etDepEss.getText().toString()) ) > theoEss   || (Integer.valueOf(etFinGaz.getText().toString()) - Integer.valueOf(etDepGaz.getText().toString()) ) > theoGaz )
                            {

                              Toast.makeText(AtivitiesActivity.this, "Un de vos stocks est  Insuffisant !", Toast.LENGTH_SHORT).show();

                             }
                             else
                              {
                                  activitiesService.addActivity(idPomp,Integer.valueOf(etDepEss.getText().toString()),Integer.valueOf(etFinEss.getText().toString()),Integer.valueOf(etDepGaz.getText().toString()),Integer.valueOf(etFinGaz.getText().toString()) ,5 ,5 , sharedPreferences.getInt(station, 1) )
                                          .enqueue(new Callback<Activities>() {
                                              @Override
                                              public void onResponse(Call<Activities> call, Response<Activities> response) {
                                                  if(response.isSuccessful())
                                                      Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées", Toast.LENGTH_SHORT).show();
                                                  else
                                                      Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées" , Toast.LENGTH_SHORT).show();


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

                          else
                              Toast.makeText(AtivitiesActivity.this, "Vos index ne sont pas corrects !", Toast.LENGTH_SHORT).show();








                      }
                      else
                      {

                          if (code==422)
                          {
                              int r;
                              r = (int) spinPompist.getSelectedItemId();
                              int u;
                              u = (int) spinPomp.getSelectedItemId();
                              if (Integer.valueOf(etDepEss.getText().toString())==0  || Integer.valueOf(etDepGaz.getText().toString()) ==0)
                              {
                                  activitiesService.addActivity(pomps.get(u).getId(),Integer.valueOf(etDepEss.getText().toString()),Integer.valueOf(etFinEss.getText().toString()),Integer.valueOf(etDepGaz.getText().toString()),Integer.valueOf(etFinGaz.getText().toString()) ,5 ,5 , sharedPreferences.getInt(station, 1) )
                                          .enqueue(new Callback<Activities>() {
                                              @Override
                                              public void onResponse(Call<Activities> call, Response<Activities> response) {
                                                  if(response.isSuccessful())
                                                      Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées", Toast.LENGTH_SHORT).show();
                                                  else
                                                      Toast.makeText(AtivitiesActivity.this, "Les informations  ont été enregistrées " , Toast.LENGTH_SHORT).show();


                                              }

                                              @Override
                                              public void onFailure(Call<Activities> call, Throwable t) {
                                                  Toast.makeText(AtivitiesActivity.this, "Ooops ! une erreur est survenue .", Toast.LENGTH_SHORT).show();

                                              }
                                          });
                                  etFinGaz.setText("");
                                  etDepGaz.setText("");
                                  etDepEss.setText("");
                                  etFinEss.setText("");

                              }
                              else
                                  Toast.makeText(AtivitiesActivity.this, "Les index doivent demarrer a partir de 0 !", Toast.LENGTH_SHORT).show();




                          }
                      }





                  }

                  @Override
                  public void onFailure(Call<List<Integer>> call, Throwable t) {
                      Toast.makeText(AtivitiesActivity.this,"Ooops une erreur est survenue !", Toast.LENGTH_SHORT).show();


                  }
              });














            }
        });









    }

}
