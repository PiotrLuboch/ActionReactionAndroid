package com.project.pluboch.actionreaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
        final ArrayList<ActionReaction> actionReactions = actionReactionController.getActionReactionList();
        final ActionReactionAdapter actionReactionAdapter = new ActionReactionAdapter(this, actionReactions);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(actionReactionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(context, ListItemDetailsActivity.class);
                actionReactionController.setSelectedActionReaction(position);
                startActivity(detailIntent);
            }
        });
    }

    public void openEditActivity(View view){
        Intent editIntent = new Intent(context, EditActionReactionActivity.class);
        startActivity(editIntent);
    }
}
