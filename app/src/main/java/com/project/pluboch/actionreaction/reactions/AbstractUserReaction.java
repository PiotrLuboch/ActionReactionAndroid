package com.project.pluboch.actionreaction.reactions;

/**
 * Created by piotr on 20.04.17.
 */

public abstract class AbstractUserReaction {
    private UserReactionType userReactionType;

    public AbstractUserReaction(UserReactionType userActionType) {
        this.userReactionType = userActionType;
    }

    @Override
    public String toString() {
        return userReactionType.toString();
    }



    public UserReactionType getUserActionType() {
        return userReactionType;
    }

    public void setUserActionType(UserReactionType userActionType) {
        this.userReactionType = userActionType;
    }
}
