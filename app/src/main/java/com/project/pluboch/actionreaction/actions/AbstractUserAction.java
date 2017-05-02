package com.project.pluboch.actionreaction.actions;

/**
 * Created by piotr on 20.04.17.
 */

public abstract class AbstractUserAction {
    private UserActionType userActionType;
    protected int paramNumber = 0;
    public static final  String DELIMETER = "<<||>>";
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


    public abstract boolean isConditionTrue();

    public abstract String dbParamsRepresentation();

    public int getParamNumber() {
        return paramNumber;
    }
}
