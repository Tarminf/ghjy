package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocListenerInterface {
    private LocationManager locationManager;
    private TextView tvDistance, tvVelocity;
    private Location lastLocation;
    private MyLocListener myLocListener;
    private int distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void init(){

        tvVelocity = findViewById(R.id.tvVelocity);
        tvDistance = findViewById(R.id.tvDistans);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocListener = new MyLocListener();
        myLocListener.setLocListenerInterface(this);
        checkPermission();
    }

    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode == 100 && grantResults[0] == RESULT_OK)
        {
            checkPermission();
        }

  

    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},100);
        }
        else{
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,2,1,myLocListener);
        }
    }


    @Override
    public void OnLocationChanged(Location loc) {

        if (loc.hasSpeed() && lastLocation != null){
            distance += lastLocation.distanceTo(loc);
        }
        lastLocation = loc;
        tvDistance.setText(String.valueOf(distance));
        tvVelocity.setText(String.valueOf(loc.getSpeed()));
    }
}