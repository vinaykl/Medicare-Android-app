package com.example.vinaykl.bs2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CVS_Search_Activity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cvs__search_);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, (android.location.LocationListener) locationListener);

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(33.424564, -111.928001));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        LatLng CVS1 = new LatLng( 33.378717, -111.951189);
        mMap.addMarker(new MarkerOptions().position(CVS1).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS1));

        LatLng CVS2 = new LatLng( 33.392897, -111.916513);
        mMap.addMarker(new MarkerOptions().position(CVS2).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS2));

        LatLng CVS3 = new LatLng( 33.407075, -111.903467);
        mMap.addMarker(new MarkerOptions().position(CVS3).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS3));

        LatLng CVS4 = new LatLng( 33.430563, -111.898317);
        mMap.addMarker(new MarkerOptions().position(CVS4).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS4));

        LatLng CVS5 = new LatLng( 33.394301, -111.863813);
        mMap.addMarker(new MarkerOptions().position(CVS5).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS5));

        LatLng CVS6 = new LatLng( 33.392141, -111.919947);
        mMap.addMarker(new MarkerOptions().position(CVS6).title("CVS STORE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CVS6));



    }
}
