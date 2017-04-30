package com.project.pluboch.actionreaction.actions;

/**
 * Created by Piotr on 2017-04-30.
 */

public enum UserActionType {
    LOCATION("Location"), WIFI_NAME("WiFi name"), TIME("Time"), DATE("Date"), SMS_RECIEVED("SMS recieved"), BATERY_STATE("Batery state");
    private String name;

    UserActionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
