package com.hopelib.libhopebasepro.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hopelib.libhopebasepro.service.ServiceAdMob;

public class BroadCastBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mIntent = new Intent(context, ServiceAdMob.class);
        context.startService(mIntent);
    }

}
