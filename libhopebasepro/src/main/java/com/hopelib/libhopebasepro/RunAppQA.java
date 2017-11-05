package com.hopelib.libhopebasepro;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.KeyEvent;

import com.hopelib.libhopebasepro.dialog.DialogCheckOutPermiss;
import com.hopelib.libhopebasepro.service.ServiceAdMob;
import com.hopelib.libhopebasepro.utilmethor.DataCM;
import com.hopelib.libhopebasepro.utilmethor.Util;

public class RunAppQA {

    public static DialogCheckOutPermiss dialogCheckOutPermiss;

    public static void Adsshow(Context mContext, int number) {
        Intent mIntent = new Intent(mContext, ServiceAdMob.class);
        mIntent.putExtra(DataCM.INT_QA, number);
        mContext.startService(mIntent);
    }

    public static void stopQAData(Context mContext) {
        if (isMyServiceRunning(ServiceAdMob.class, mContext)) {
            Intent mIntent = new Intent(mContext, ServiceAdMob.class);
            mContext.stopService(mIntent);
        }
    }

    public static void startADSMode(final Activity mActivity) {
        showAdsMode(mActivity, -1);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !Util.hasUsageStatsPermission(mActivity)) {
            if (dialogCheckOutPermiss != null) {
                dialogCheckOutPermiss.dismiss();
                dialogCheckOutPermiss = null;
            }
            dialogCheckOutPermiss = new DialogCheckOutPermiss(mActivity, new DialogCheckOutPermiss.onClickDialogPermissAds() {
                @Override
                public void OnClickDialog(boolean isClickPermiss) {
                    dialogCheckOutPermiss = null;
                    if (isClickPermiss) {
                        mActivity.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                    } else {
                        mActivity.finish();
                    }
                }
            });
            dialogCheckOutPermiss.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mActivity.finish();
                        dialogCheckOutPermiss.dismiss();
                        dialogCheckOutPermiss = null;
                        return true;
                    }
                    return false;
                }
            });
            dialogCheckOutPermiss.show();
        } else {
            Adsshow(mActivity, -1);
        }
    }

    public static void showAdsMode(final Activity mActivity, int numberAds) {
        if (Util.hasUsageStatsPermission(mActivity)) {
            Adsshow(mActivity, numberAds);
        }
    }

    //check on/off service
    public static boolean isMyServiceRunning(Class<?> serviceClass, Context mContext) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
