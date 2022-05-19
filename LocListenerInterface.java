package com.example.myapplication;

import android.location.Location;

import androidx.annotation.NonNull;

public interface LocListenerInterface {

    void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    public void OnLocationChanged(Location loc);
}
