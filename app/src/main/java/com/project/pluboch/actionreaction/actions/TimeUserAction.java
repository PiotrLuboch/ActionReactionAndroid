package com.project.pluboch.actionreaction.actions;


import java.util.Calendar;

/**
 * Created by Piotr on 2017-04-30.
 */

public class TimeUserAction extends AbstractUserAction {
    private int hour;
    private int minute;


    public TimeUserAction(int hour, int minute) {
        super(UserActionType.TIME);
        this.hour = hour;
        this.minute = minute;
        this.paramNumber = 2;
    }

    @Override
    public boolean isConditionTrue() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        return (hour == currentHour && minute == currentMinute);
    }

    @Override
    public String dbParamsRepresentation() {
        return hour + DELIMETER + minute;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n").append("Selected time: ").append(hour).append(":").append(minute);
        return sb.toString();
    }
}
