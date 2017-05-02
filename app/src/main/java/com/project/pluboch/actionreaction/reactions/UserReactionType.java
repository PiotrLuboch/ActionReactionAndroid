package com.project.pluboch.actionreaction.reactions;

/**
 * Created by Piotr on 2017-04-30.
 */

public enum UserReactionType {
    VOLUME("Volume", 1),
    SEND_SMS("Send SMS", 2),
    //NOTIFICATION("Notification", 3)
    ;

    private final String name;
    private final int id;

    UserReactionType(String name, int id) {
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

    public static UserReactionType getReactionByName(String name){
        switch(name){
            case "Volume":
                return UserReactionType.VOLUME;
            case "Send SMS":
                return UserReactionType.SEND_SMS;
        }
        return null;
    }
}
