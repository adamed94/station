package com.station.bangoura.stationnew.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterCaisse;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CaisseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaisseActivity extends AppCompatActivity {
    Button btnSubmitDep ;
    CaisseService caisseService  ;
    EditText etAmountDep , etDescription;
    public static final String station = "stationKey";
    SharedPreferences sharedPreference ;
    public static final String user = "userKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caisse);
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
        sharedPreference = this.getSharedPreferences("mypref", getApplicationContext().MODE_PRIVATE);
        etAmountDep = (EditText)findViewById(R.id.tvAmountDep) ;
        etDescription =  (EditText)findViewById(R.id.etDescription) ;

        caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;



        btnSubmitDep = (Button)findViewById(R.id.btnSubmitDep) ;
        btnSubmitDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                 if(etAmountDep.getText().toString().equals("") || etDescription.getText().toString().equals("") ){

                     Toast.makeText(CaisseActivity.this, "Veuillez remplir tous les champs ."  , Toast.LENGTH_SHORT).show();

                 }
                 else{

                     caisseService.addDepense(Integer.valueOf(etAmountDep.getText().toString()),etDescription.getText().toString(), sharedPreference.getInt(user, 1) ,  sharedPreference.getInt(station, 1))
                             .enqueue(new Callback<Integer>() {
                                 @Override
                                 public void onResponse(Call<Integer> call, Response<Integer> response) {
                                     if(response.isSuccessful())
                                         Toast.makeText(CaisseActivity.this, "Les informations  ont été enregistrées" , Toast.LENGTH_SHORT).show();
                                     else
                                         Toast.makeText(CaisseActivity.this, "Les informations  ont été enregistrées . "  , Toast.LENGTH_SHORT).show();

                                     etAmountDep.setText("");
                                     etDescription.setText("");

                                 }

                                 @Override
                                 public void onFailure(Call<Integer> call, Throwable t) {
                                     Toast.makeText(CaisseActivity.this, "Les informations  ont été enregistrées .", Toast.LENGTH_SHORT).show();
                                     etAmountDep.setText("");
                                     etDescription.setText("");

                                 }
                             });

                 }



            }
        });


    }

}
