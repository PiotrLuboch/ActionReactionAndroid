package com.project.pluboch.actionreaction.actions.activities;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.project.pluboch.actionreaction.R;

public class TimeAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_action);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
    }

    public void finishTimeAction(View view) {
        Intent intent = getIntent();
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        intent.putExtra("hour",timePicker.getHour());
        intent.putExtra("minute", timePicker.getMinute());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
