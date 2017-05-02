package com.project.pluboch.actionreaction.reactions;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Piotr on 2017-05-02.
 */

public class SmsSendUserReaction extends AbstractUserReaction {

    private String number;
    private String message;

    public SmsSendUserReaction(String number, String message) {
        super(UserReactionType.SEND_SMS);
        this.number = number;
        this.message = message;
        this.paramNumber = 2;
    }

    @Override
    public void performReaction() {
        //code from http://stackoverflow.com/questions/8578689/sending-text-messages-programmatically-in-android
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage(number, null, parts, null, null);
    }

    @Override
    public String dbParamsRepresentation() {
        return number + DELIMETER + message;
    }
}
