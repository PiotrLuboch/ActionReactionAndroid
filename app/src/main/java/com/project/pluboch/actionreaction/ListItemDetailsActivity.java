package com.project.pluboch.actionreaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ListItemDetailsActivity extends AppCompatActivity {

    private ActionReactionController actionReactionController = new ActionReactionController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_details);

        ActionReaction actionReaction = actionReactionController.getSelectedActionReaction();
        TextView actionReactionName = (TextView)findViewById(R.id.txtActionReactionName);
        actionReactionName.setText(actionReaction.getTitle());
        TextView actionName = (TextView)findViewById(R.id.txtActionName);
        actionName.setText(actionReaction.getUserAction().toString());
        TextView reactionName = (TextView)findViewById(R.id.txtReactionName);
        reactionName.setText(actionReaction.getUserReaction().toString());

    }
}
