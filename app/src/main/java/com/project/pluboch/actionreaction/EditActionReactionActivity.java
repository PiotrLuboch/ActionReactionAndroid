package com.project.pluboch.actionreaction;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.project.pluboch.actionreaction.actions.LocationUserAction;
import com.project.pluboch.actionreaction.actions.TimeUserAction;
import com.project.pluboch.actionreaction.actions.UserActionType;
import com.project.pluboch.actionreaction.actions.WifiNameUserAction;
import com.project.pluboch.actionreaction.actions.activities.TimeAction;
import com.project.pluboch.actionreaction.actions.activities.WifiNameAction;
import com.project.pluboch.actionreaction.dbpersistence.DbManager;
import com.project.pluboch.actionreaction.reactions.SmsSendUserReaction;
import com.project.pluboch.actionreaction.reactions.UserReactionType;
import com.project.pluboch.actionreaction.reactions.VolumeUserReaction;
import com.project.pluboch.actionreaction.reactions.activities.SmsSendReaction;
import com.project.pluboch.actionreaction.reactions.activities.VolumeReaction;

import javax.crypto.ExemptionMechanismException;

public class EditActionReactionActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Spinner spinnerAction;
    private Spinner spinnerReaction;
    private static final int TIME_ACTION = 1000;
    private static final int WIFI_NAME_ACTION = 1001;
    private static final int LOCATION_ACTION = 1002;
    private static final int VOLUME_REACTION = 2000;
    private static final int SMS_SEND_REACTION = 2001;
    private ActionReaction actionReaction = new ActionReaction();
    private String[] actionIntents;

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action_reaction);

        spinnerAction = (Spinner) findViewById(R.id.spnAction);
        spinnerAction.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UserActionType.values()));

        spinnerReaction = (Spinner) findViewById(R.id.spnReaction);
        spinnerReaction.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UserReactionType.values()));

        dbManager = new DbManager(getApplicationContext());
    }

    public void selectActionProperties(View view) {
        int selectedActionId = spinnerAction.getSelectedItemPosition();
        Intent intent;
        final String itemAtPosition = spinnerAction.getItemAtPosition(selectedActionId).toString().toUpperCase().replace(' ', '_');

        switch (UserActionType.valueOf(itemAtPosition)) {
            case LOCATION:
                askForPermissions();
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(intentBuilder.build(this), LOCATION_ACTION);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case TIME:
                actionIntents = new String[]{Intent.ACTION_TIME_TICK, Intent.ACTION_TIME_CHANGED, Intent.ACTION_TIMEZONE_CHANGED};
                intent = new Intent(getApplicationContext(), TimeAction.class);
                startActivityForResult(intent, TIME_ACTION);
                break;
            case WIFI_NAME:
                actionIntents = new String[]{WifiManager.SCAN_RESULTS_AVAILABLE_ACTION};
                intent = new Intent(getApplicationContext(), WifiNameAction.class);
                startActivityForResult(intent, WIFI_NAME_ACTION);
                break;
            default:
                Toast.makeText(getApplicationContext(), itemAtPosition, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void selectReactionProperties(View view) {
        int selectedActionId = spinnerReaction.getSelectedItemPosition();
        final String itemAtPosition = spinnerReaction.getItemAtPosition(selectedActionId).toString().toUpperCase().replace(' ', '_');
        Intent intent;
        switch (UserReactionType.valueOf(itemAtPosition)) {
            case VOLUME:
                Toast.makeText(getApplicationContext(), itemAtPosition, Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), VolumeReaction.class);
                startActivityForResult(intent, VOLUME_REACTION);
                break;
            case SEND_SMS:
                intent = new Intent(getApplicationContext(), SmsSendReaction.class);
                startActivityForResult(intent, SMS_SEND_REACTION);
                break;
            default:
                Toast.makeText(getApplicationContext(), itemAtPosition, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case LOCATION_ACTION:
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                GoogleApiClient googleApiClient = new GoogleApiClient
                        .Builder(this)
                        .addApi(Places.GEO_DATA_API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .enableAutoManage(this, this)
                        .build();
                actionReaction.setUserAction(new LocationUserAction(place, getApplicationContext(), this));
                break;
            case TIME_ACTION:
                int hour = data.getIntExtra("hour", -1);
                int minute = data.getIntExtra("minute", -1);
                Toast.makeText(getApplicationContext(), hour + ":" + minute, Toast.LENGTH_SHORT).show();
                actionReaction.setUserAction(new TimeUserAction(hour, minute));
                break;
            case WIFI_NAME_ACTION:
                String wifiName = data.getStringExtra("wifiName");
                Toast.makeText(getApplicationContext(), wifiName, Toast.LENGTH_SHORT).show();
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifiManager.startScan();
                actionReaction.setUserAction(new WifiNameUserAction(wifiName, wifiManager));
                break;
            case VOLUME_REACTION:
                int volumeLevel = data.getIntExtra("volumeLevel", -1);
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                Toast.makeText(getApplicationContext(), volumeLevel + "", Toast.LENGTH_SHORT).show();
                actionReaction.setUserReaction(new VolumeUserReaction(volumeLevel, am));
                break;
            case SMS_SEND_REACTION:
                String number = data.getStringExtra("phoneNumber");
                String message = data.getStringExtra("smsMessage");
                Toast.makeText(getApplicationContext(), "Message will be send to " + number, Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                    actionReaction.setUserReaction(new SmsSendUserReaction(number, message));
                else {
                    Toast.makeText(this, "Cannot send SMS, lack of permission", Toast.LENGTH_SHORT).show();
                    actionReaction.setUserReaction(null);
                }
                break;
            default:
                break;
        }
    }


    public void finish(View view) {
        String name = ((EditText) findViewById(R.id.edtActionReactionName)).getText().toString();
        if (name.length() == 0 || actionReaction.getUserAction() == null || actionReaction.getUserReaction() == null) {
            Toast.makeText(getApplicationContext(), "Not all parameters are set!", Toast.LENGTH_SHORT).show();
            return;
        }
        actionReaction.setTitle(name);
        final ActionReactionController controller = new ActionReactionController(actionReaction);
        controller.addActionReaction(actionReaction);


        if (actionReaction.getUserAction().getUserActionType().equals(UserActionType.LOCATION)) {
            LocationManager manager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    if (controller.isConditionTrue())
                        controller.performReaction();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locationListener);
        } else {
            controller.registerBroadcastReceiver(getApplicationContext(), actionIntents);
        }
        setResult(Activity.RESULT_OK, getIntent());
        dbManager.insertActionReaction(actionReaction);
        Log.d("EditARActivity", "New BroadcastReceiver created");
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void askForPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == false) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == false) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }
}
