package com.project.pluboch.actionreaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionReactionController actionReactionController = new ActionReactionController();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        final ArrayList<ActionReaction> actionReactions = ActionReactionController.getMockObjects();
        ActionReactionAdapter actionReactionAdapter = new ActionReactionAdapter(this, actionReactions);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(actionReactionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(context, ListItemDetailsActivity.class);
                ActionReactionController.setSelectedActionReaction(position);
                startActivity(detailIntent);
            }
        });
    }
}
