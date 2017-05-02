package com.project.pluboch.actionreaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.pluboch.actionreaction.dbpersistence.DbManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActionReactionController actionReactionController = new ActionReactionController();
    private Context context;
    private ActionReactionAdapter actionReactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        final ArrayList<ActionReaction> actionReactions = actionReactionController.getActionReactionList();
        actionReactionAdapter = new ActionReactionAdapter(this, actionReactions);
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

        DbManager dbManager = new DbManager(getApplicationContext());
        List<ActionReaction> allActionReactions = dbManager.getAllActionReactions(getApplicationContext(), this);
    }

    public void openEditActivity(View view) {
        Intent editIntent = new Intent(context, EditActionReactionActivity.class);
        startActivityForResult(editIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case 1000:
                actionReactionAdapter.notifyDataSetChanged();
                break;
        }
    }


}
