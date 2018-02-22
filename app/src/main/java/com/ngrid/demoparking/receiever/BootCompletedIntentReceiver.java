package com.ngrid.demoparking.receiever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ngrid.demoparking.service.MqttService;


public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent pushIntent = new Intent(context, MqttService.class);
            context.startService(pushIntent);
        }
    }
}
