package com.station.bangoura.stationnew.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Station;
import com.station.bangoura.stationnew.models.User;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.UserService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnConn ;
    AlertDialog.Builder alert ;
    EditText etPassword , etUsername , etRCEss , etRCGaz;
    UserService userService ;
    User user ;
    ApiClient api;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String station = "stationKey";
    public static final String userId = "userKey";

    CheckBox cbEss , cbGaz ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;

        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences(mypreference,getApplicationContext().MODE_PRIVATE);
        sharedpreferences.edit().remove(Name) ;
        sharedpreferences.edit().remove(Email) ;
        sharedpreferences.edit().remove(station) ;
        sharedpreferences.edit().remove(userId) ;
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        etPassword = (EditText)findViewById(R.id.etPassword);
        etUsername =(EditText)findViewById(R.id.etUser);
        etRCEss = (EditText)findViewById(R.id.etRCEss) ;
        etRCGaz = (EditText)findViewById(R.id.etRCGaz) ;
        cbEss = (CheckBox)findViewById(R.id.cbEss);
        cbGaz = (CheckBox)findViewById(R.id.cbGaz);

        //cbEss.setOnClickListener(this);


        alert = new AlertDialog.Builder(getApplicationContext());
        alert.setTitle("Info");
        alert.setMessage("Vos informations sont incorrectes !") ;
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Toast.makeText(getApplicationContext(),"Infos Incorrectes",Toast.LENGTH_LONG) ;
            }
        }) ;

        btnConn = (Button)findViewById(R.id.btnConn) ;
        btnConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u =  etUsername.getText().toString() ;
                String p =  etPassword.getText().toString() ;

               // Toast.makeText(getApplicationContext(),""+etUsername.getText().toString() + etPassword.getText().toString() ,Toast.LENGTH_LONG).show() ;

                userService = ApiClient.getRetrofit().create(UserService.class) ;
                retrofit2.Call<User> users = userService.getUser(u,p) ;
                users.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(), response.body().size()+"", Toast.LENGTH_LONG).show();
                            if (response.body()!=null) {
                                editor.putString(Name,response.body().getName()) ;
                                editor.putString(Email,response.body().getEmail()) ;
                                editor.putInt(station,response.body().getStation_id()) ;
                                editor.putInt(userId,response.body().getId()) ;
                                editor.commit();


                            }
                            Intent next  = new Intent(getApplicationContext(), MainActivity.class);
                            //Toast.makeText(getApplicationContext(),sharedpreferences.getString(Email, "Moi") ,Toast.LENGTH_LONG).show() ;
                            startActivity(next);
                            finish();


                        }
                        else {
                            etPassword.setText("");
                            Toast.makeText(getApplicationContext(), "Vos informations sont incorrectes." , Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(retrofit2.Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Ooops ! Une erreur est survenue. Veuillez Verifier l'état de votre connexion . " ,Toast.LENGTH_LONG).show() ;
                    }
                });




//
//                if ( u.equals("bangoura") && p.equals("madiou") ){
//                    Intent next  = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(next);
//
//                }
//                else
//                    Toast.makeText(getApplicationContext(),"Vos informations sont incorrectes.",Toast.LENGTH_LONG).show() ;
//
//

            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cbEss:
                if (cbEss.isChecked())
                    etRCEss.setVisibility(View.VISIBLE);
                else
                    etRCEss.setVisibility(View.INVISIBLE);
                break;
            case R.id.cbGaz:
                if (cbGaz.isChecked()) {
                    etRCGaz.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Vos informations sont incorrectes.", Toast.LENGTH_LONG).show();
                }
                else {
                    etRCGaz.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Vos informations sont incorrectes.",Toast.LENGTH_LONG).show() ;
                }
                break;

        }

    }
}
