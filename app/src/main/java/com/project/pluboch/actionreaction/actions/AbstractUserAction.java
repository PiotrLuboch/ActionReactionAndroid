package com.project.pluboch.actionreaction.actions;

/**
 * Created by piotr on 20.04.17.
 */

public abstract class AbstractUserAction {
    private UserActionType userActionType;

    public AbstractUserAction(UserActionType userActionType) {
        this.userActionType = userActionType;
    }

    @Override
    public String toString() {
        return userActionType.toString();
    }



    public UserActionType getUserActionType() {
        return userActionType;
    }

    public void setUserActionType(UserActionType userActionType) {
        this.userActionType = userActionType;
    }

}
