package com.project.pluboch.actionreaction;

import android.app.Notification;

import com.project.pluboch.actionreaction.actions.AbstractUserAction;
import com.project.pluboch.actionreaction.actions.TimeUserAction;
import com.project.pluboch.actionreaction.actions.UserActionType;
import com.project.pluboch.actionreaction.reactions.AbstractUserReaction;
import com.project.pluboch.actionreaction.reactions.UserReactionType;
import com.project.pluboch.actionreaction.reactions.VolumeUserReaction;

import java.util.ArrayList;

/**
 * Created by piotr on 20.04.17.
 */

public class ActionReactionController {
    private static ArrayList<ActionReaction> actionReactions = new ArrayList<ActionReaction>() {{
        add(new ActionReaction("Job arrival", new TimeUserAction(UserActionType.LOCATION), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Battery low", new TimeUserAction(UserActionType.BATERY_STATE), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Home WiFi detected", new TimeUserAction(UserActionType.WIFI_NAME), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Work WiFi detected", new TimeUserAction(UserActionType.WIFI_NAME), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Home arrival", new TimeUserAction(UserActionType.LOCATION), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Max volume after sms", new TimeUserAction(UserActionType.SMS_RECIEVED), new VolumeUserReaction(UserReactionType.VOLUME)));
        add(new ActionReaction("Unmute at 7 am", new TimeUserAction(UserActionType.TIME), new VolumeUserReaction(UserReactionType.VOLUME)));
    }};
    private static ActionReaction selectedActionReaction;

    public ArrayList<ActionReaction> getActionReactionList() {
        return actionReactions;
    }

    public void addActionReaction(ActionReaction actionReaction) {
        actionReactions.add(actionReaction);
    }

    public ActionReaction getSelectedActionReaction() {
        return selectedActionReaction;
    }

    public void setSelectedActionReaction(int position) {
        selectedActionReaction = actionReactions.get(position);
    }

    public boolean isConditionMeet(){
        return false;
    }
}
