package com.project.pluboch.actionreaction.actions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.places.Place;

/**
 * Created by Piotr on 2017-05-01.
 */

public class LocationUserAction extends AbstractUserAction {
    private static final String TAG = "LOCATION_USER_ACTION";
    private static final float RADIUS = 50.0f;
    private String locationName;
    private double latitude;
    private double longitude;
    private Context context;
    private Activity activity;

    public LocationUserAction(Place place, Context context, Activity activity) {
        super(UserActionType.LOCATION);
        this.context = context;
        this.activity = activity;
        this.paramNumber = 3;
        this.locationName = place.getAddress().toString();
        latitude = place.getLatLng().latitude;
        longitude = place.getLatLng().longitude;
    }

    public LocationUserAction(String locationName, double longitude, double latitude, Context context, Activity activity) {
        super(UserActionType.LOCATION);
        this.context = context;
        this.activity = activity;
        this.paramNumber = 3;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    @Override
    public boolean isConditionTrue() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }

        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Location current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "Current location is: " + current.toString());


        Location locationPlace = new Location("");
        locationPlace.setLatitude(latitude);
        locationPlace.setLongitude(longitude);
        float distance = locationPlace.distanceTo(current);
        Log.i("LUAction", distance + "");
        return distance < RADIUS;
    }

    @Override
    public String dbParamsRepresentation() {

        return locationName + DELIMETER + latitude + DELIMETER + longitude;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n").append("Selected place: ").append(locationName);
        return sb.toString();
    }
}
