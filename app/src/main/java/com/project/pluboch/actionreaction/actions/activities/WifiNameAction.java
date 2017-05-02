package com.project.pluboch.actionreaction.actions.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.project.pluboch.actionreaction.R;

public class WifiNameAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_name_action);
    }

    public void finishWifiNameAction(View view) {
        String wifiName = ((EditText) findViewById(R.id.edtWifiName)).getText().toString();
        Intent intent = getIntent();
        intent.putExtra("wifiName", wifiName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
