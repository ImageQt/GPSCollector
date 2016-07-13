package com.example.sxy.gpscollector;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PackageManager pm=getPackageManager();
        boolean p1=(PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION",this.getPackageName()));
        boolean p2=(PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.ACCESS_FINE_LOCATION",this.getPackageName()));
        if(!p1 || !p2){
            Toast.makeText(this,"Please Give GPS Permission to this App...",Toast.LENGTH_LONG).show();
        }

        registGPS();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonAbout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "GPS Points Collector v1.0 By Xiangyu SHI,WHU2014", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button buttonGet=(Button)findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                EditText elat=(EditText) findViewById(R.id.editTextLat);
                elat.setText(""+lat);
                EditText elng=(EditText) findViewById(R.id.editTextLng);
                elng.setText(""+lng);
                EditText ealt=(EditText) findViewById(R.id.editTextAlt);
                ealt.setText(""+alt);
                EditText etime=(EditText) findViewById(R.id.editTextTime);
                etime.setText(""+time);
            }
        });
        Button buttonExit=(Button)findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public LocationManager lm;
    public static final String TAG="GPS Service";
    public double lat=0,lng=0,alt=0,time=0;
    public boolean registGPS(){
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"Please Turn on Your GPS Service...",Toast.LENGTH_LONG).show();
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, ll);

        return true;
    }

    private LocationListener ll=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            lat=location.getLatitude();
            lng=location.getLongitude();
            alt=location.getAltitude();
            time=location.getTime();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(getParent(), "Your GPS is Out of Service...", Toast.LENGTH_LONG).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(getParent(), "Your GPS Service is Paused...", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
