package com.project.pluboch.actionreaction;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.project.pluboch.actionreaction.actions.AbstractUserAction;
import com.project.pluboch.actionreaction.actions.TimeUserAction;
import com.project.pluboch.actionreaction.actions.UserActionType;
import com.project.pluboch.actionreaction.dbpersistence.DbManager;
import com.project.pluboch.actionreaction.reactions.AbstractUserReaction;
import com.project.pluboch.actionreaction.reactions.UserReactionType;
import com.project.pluboch.actionreaction.reactions.VolumeUserReaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by piotr on 20.04.17.
 */

public class ActionReactionController {
    private ArrayList<ActionReaction> actionReactions = new ArrayList<>();
    private static ActionReaction selectedActionReaction;

    private ActionReaction actionReaction;

    public ActionReactionController() {
    }

    public ActionReactionController(ActionReaction actionReaction) {
        this.actionReaction = actionReaction;
    }

    public ArrayList<ActionReaction> getActionReactionList() {
        return actionReactions;
    }

    public void addActionReaction(ActionReaction actionReaction) {
        actionReactions.add(actionReaction);
    }

    public void addActionReaction(List<ActionReaction> actionReactions){
        actionReactions.addAll(actionReactions);
    }

    public ActionReaction getSelectedActionReaction() {
        return selectedActionReaction;
    }

    public void setSelectedActionReaction(int position) {
        selectedActionReaction = actionReactions.get(position);
    }

    public void registerBroadcastReceiver(Context context, final String... intentActions) {
        IntentFilter filter = new IntentFilter();
        for (String s : intentActions) {
            filter.addAction(s);
        }

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            private ActionReactionController actionReactionController = new ActionReactionController(actionReaction);
            private List<String> actions = Arrays.asList(intentActions);
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (actions.contains(action) && actionReactionController.isConditionTrue()) {
                    actionReactionController.performReaction();
                    Log.i("ARController",actionReactionController.actionReaction.getUserAction().getUserActionType().toString());
                }
            }
        };

        actionReaction.setBroadcastReceiver(broadcastReceiver);
        context.registerReceiver(broadcastReceiver, filter);
    }

    public boolean isConditionTrue() {
        return actionReaction.getUserAction().isConditionTrue();
    }

    public void performReaction() {
        actionReaction.getUserReaction().performReaction();
    }
}
