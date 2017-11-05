package com.hopelib.libhopebasepro.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hopelib.libhopebasepro.service.ServiceAdMob;
import com.hopelib.libhopebasepro.utilmethor.DataCM;

public class ScreenReceiver extends BroadcastReceiver {

    public static int screenOff;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            screenOff = -1;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            screenOff = 1;
        }
        if (isMyServiceRunning(ServiceAdMob.class, context)) {
            Intent i = new Intent(context, ServiceAdMob.class);
            i.putExtra(DataCM.INT_QA, screenOff);
            context.startService(i);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
