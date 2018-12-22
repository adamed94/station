package com.station.bangoura.stationnew.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.adapter.AdapterCmdPending;
import com.station.bangoura.stationnew.models.CmdLivrs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        {
    private Fragment flFrament ;
    private DrawerLayout mDrawer ;
    private Toolbar toolbar ;
    private NavigationView nvDrawer ;
    private FloatingActionButton fab ;
            public static final String Name = "nameKey";
            public static final String Email = "emailKey";
            public static final String station = "stationKey";
            public static final String userId = "userKey";
            public static final String mypreference = "mypref";

            SharedPreferences sharedpreferences;


            ImageView imCmd ,imStock ,imPompist , imStation , imActivities , imHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        //imCmd = (ImageView)findViewById(R.id.imCmd) ;
        sharedpreferences = getSharedPreferences(mypreference,getApplicationContext().MODE_PRIVATE);

        imCmd = (ImageView) findViewById(R.id.imCmd);

        imCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplication(), ComandActivity.class);


                startActivity(next);

            }
        });

        imStock = (ImageView) findViewById(R.id.imStock);
        imStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplication(), StockActivity.class);
                startActivity(next);
            }
        });
//        imPompist = (ImageView)findViewById(R.id.imPompist) ;
//        imPompist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent next  = new Intent(getApplication(), PompistActivity.class);
//                startActivity(next);
//
//            }
//        });
//        imStation = (ImageView)findViewById(R.id.imStation) ;
//        imStation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent next  = new Intent(getApplication(), StationsActivity.class);
//                startActivity(next);
//
//            }
//        });

        imActivities = (ImageView) findViewById(R.id.imActivities);
        imActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplication(), DetailsListActivity.class);
                startActivity(next);
            }
        });
//        imHome = (ImageView)findViewById(R.id.imHome) ;
//        imHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent next  = new Intent(getApplication(), HomeActivity.class);
//                startActivity(next);
//              //  Toast.makeText(getApplicationContext(),sharedPreferences.getString(Email, "Moi") ,Toast.LENGTH_LONG).show() ;
//
//
//            }
//        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        mDrawer =(DrawerLayout)findViewById(R.id.drawer_layout);
//        nvDrawer = (NavigationView)findViewById(R.id.nav_view) ;


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
    }
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this) ;
            builder.setMessage("Voulez vous vraiment quitter cette application ?") ;
            builder.setTitle("Confirmation") ;
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    sharedpreferences.edit().remove(Name) ;
                    sharedpreferences.edit().remove(Email) ;
                    sharedpreferences.edit().remove(station) ;
                    sharedpreferences.edit().remove(userId) ;
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class) ;
                    startActivity(intent);
                    finish();



                }
            }) ;
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            }) ;

            AlertDialog dialog = builder.create() ;
            dialog.show();











        }

        return super.onOptionsItemSelected(item);
    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        Fragment fragment = null ;
//        Class fragmentClass = null;
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//            fragmentClass = ListStationFragment.class ;
//        } else if (id == R.id.nav_gallery) {
//            fragmentClass = ListPompistFragment.class ;
//
//        } else if (id == R.id.nav_sen) {
//
//            fragmentClass = ListCmdLivrsPendingFragment.class ;
//
//        }
//
//        else if (id == R.id.nav_stock) {
//
//            fragmentClass = StockFragment.class ;
//
//        }
//        else if (id == R.id.nav_cuve) {
//
//            fragmentClass = ListCuveFragment.class ;
//
//        }
//
//
//
//
//        if(fragmentClass!=null) {
//
//            try {
//                fragment = (Fragment) fragmentClass.newInstance();
//            } catch (Exception e) {
//
//            }
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//            item.setChecked(true);
//
//            setTitle(item.getTitle());
//
//
//
//        }
//
//        mDrawer.closeDrawer(GravityCompat.START);
//        return true;
    }

//    @Override
//    public void onCmdLivrsClick(CmdLivrs cmdLivrs) {
//
//    }




