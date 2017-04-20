package com.project.pluboch.actionreaction;

/**
 * Created by piotr on 20.04.17.
 */

public class ActionReaction {
    private String title;

    private AbstractUserAction userAction;
    private AbstractUserReaction userReaction;

    public ActionReaction(String title) {
        this.title = title;
    }
    public ActionReaction(String title, AbstractUserAction userAction, AbstractUserReaction userReaction) {
        this.title = title;
        this.userAction = userAction;
        this.userReaction = userReaction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AbstractUserReaction getUserReaction() {
        return userReaction;
    }

    public void setUserReaction(AbstractUserReaction userReaction) {
        this.userReaction = userReaction;
    }

    public AbstractUserAction getUserAction() {
        return userAction;
    }

    public void setUserAction(AbstractUserAction userAction) {
        this.userAction = userAction;
    }
}
