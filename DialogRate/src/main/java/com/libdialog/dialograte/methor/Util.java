package com.libdialog.dialograte.methor;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;


public class Util {

    public static final String APPS_DATA = "https://play.google.com/store/apps/details?id=";
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public Util(Context mContext) {
        this.mContext = mContext;
        sharedPreferences = mContext.getSharedPreferences("DATA_DIALOG", Context.MODE_PRIVATE);

    }

    public static void launchMarket(Activity mActivity) {
        Uri uri = Uri.parse("market://details?id=" + mActivity.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            mActivity.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mActivity, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    public void setShowDialog(boolean setdialog){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("SHARED_SHOW_DIALOG",setdialog);
        editor.commit();
    }

    public boolean getShowDialog(){
        return sharedPreferences.getBoolean("SHARED_SHOW_DIALOG",false);
    }

}
