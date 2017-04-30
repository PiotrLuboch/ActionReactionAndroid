package com.project.pluboch.actionreaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.project.pluboch.actionreaction.actions.UserActionType;
import com.project.pluboch.actionreaction.reactions.UserReactionType;

public class EditActionReactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_action_reaction);
        Spinner spinner = (Spinner) findViewById(R.id.spnAction);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UserActionType.values()));

        spinner = (Spinner) findViewById(R.id.spnReaction);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, UserReactionType.values()));
    }
}
