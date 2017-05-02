package com.project.pluboch.actionreaction.actions;

/**
 * Created by Piotr on 2017-04-30.
 */

public enum UserActionType {
    LOCATION("Location", 1),
    WIFI_NAME("Wifi name", 2),
    TIME("Time", 3),
    //DATE("Date",4),
    //SMS_RECEIVED("SMS received",5),
    // BATERY_STATE("Batery state",6)
    ;
    private final String name;
    private final int id;

    UserActionType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static UserActionType getActionByName(String name){
        switch(name){
            case "Location":
                return UserActionType.LOCATION;
            case "Wifi name":
                return UserActionType.WIFI_NAME;
            case "Time":
                return UserActionType.TIME;
        }
        return null;
    }
}
