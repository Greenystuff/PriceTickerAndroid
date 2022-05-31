package com.greedythings.priceticker.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefsHelper {

    private static final String KEY_IP_SERVER = "ip_server";
    private static final String KEY_PORT_SERVER = "port_server";

    private SharedPreferences preferences;

    public SharedPrefsHelper(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveIpServer(String ipServer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IP_SERVER, ipServer);
        editor.commit();
    }
    public String getIpServer() {
        return preferences.getString(KEY_IP_SERVER, "");
    }

    public void savePortServer(String portServer) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PORT_SERVER, portServer);
        editor.commit();
    }
    public String getPortServer() {
        return preferences.getString(KEY_PORT_SERVER, "");
    }

}
