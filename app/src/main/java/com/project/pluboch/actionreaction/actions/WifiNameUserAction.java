package com.project.pluboch.actionreaction.actions;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Piotr on 2017-05-01.
 */

public class WifiNameUserAction extends AbstractUserAction {

    private String wifiName;
    private WifiManager wifiManager;


    public WifiNameUserAction(String wifiName, WifiManager wifiManager) {
        super(UserActionType.WIFI_NAME);
        this.wifiName = wifiName;
        this.wifiManager = wifiManager;
        this.paramNumber = 1;
    }

    @Override
    public boolean isConditionTrue() {

        List<ScanResult> scanResults = wifiManager.getScanResults();
        for (ScanResult scanResult : scanResults) {
            Log.d("WifiNameAction", scanResult.SSID);
            if (scanResult.SSID.equals(wifiName))
                return true;
        }
        return false;
    }

    @Override
    public String dbParamsRepresentation() {
        return wifiName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n").append("Selected Wifi name: ").append(wifiName);
        return sb.toString();
    }
}
