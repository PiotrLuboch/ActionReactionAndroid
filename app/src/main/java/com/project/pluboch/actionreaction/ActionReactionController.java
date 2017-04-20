package com.project.pluboch.actionreaction;

import android.app.Notification;

import java.util.ArrayList;

/**
 * Created by piotr on 20.04.17.
 */

public class ActionReactionController {
    private static ArrayList<ActionReaction> actionReactions = new ArrayList<>();
    private static ActionReaction selectedActionReaction;

    public static ArrayList<ActionReaction> getMockObjects(){

        actionReactions.add(new ActionReaction("Job arrival"));
        actionReactions.add(new ActionReaction("Battery low"));
        actionReactions.add(new ActionReaction("Home WiFi detected"));
        actionReactions.add(new ActionReaction("Work WiFi detected"));
        actionReactions.add(new ActionReaction("Home arrival"));
        actionReactions.add(new ActionReaction("Max volume after sms"));
        actionReactions.add(new ActionReaction("Unmute at 7 am"));
        return actionReactions;
    }

    public static void addMockObject(ActionReaction actionReaction) {
        actionReactions.add(actionReaction);
    }

    public static ActionReaction getSelectedActionReaction() {
        return selectedActionReaction;
    }

    public static void setSelectedActionReaction(int position) {
        selectedActionReaction = actionReactions.get(position);
    }
}
