package com.project.pluboch.actionreaction.reactions;

/**
 * Created by Piotr on 2017-04-30.
 */

public enum UserReactionType {
    VOLUME("Volume"), SEND_SMS("Send SMS"), NOTIFICATION("Notification");
    private String name;

    UserReactionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
