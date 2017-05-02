package com.project.pluboch.actionreaction.reactions;

/**
 * Created by piotr on 20.04.17.
 */

public abstract class AbstractUserReaction {
    private UserReactionType userReactionType;
    public static final String DELIMETER = "<<||>>";
    protected int paramNumber = 0;

    public AbstractUserReaction(UserReactionType userActionType) {
        this.userReactionType = userActionType;
    }

    @Override
    public String toString() {
        return userReactionType.toString();
    }


    public UserReactionType getUserReactionType() {
        return userReactionType;
    }

    public void setUserActionType(UserReactionType userActionType) {
        this.userReactionType = userActionType;
    }

    public abstract void performReaction();

    public abstract String dbParamsRepresentation();

    public int getParamNumber() {
        return paramNumber;
    }
}
