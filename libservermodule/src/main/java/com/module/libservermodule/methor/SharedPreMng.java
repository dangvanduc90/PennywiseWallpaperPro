package com.module.libservermodule.methor;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreMng {

    private SharedPreferences sharedPreferences;

    public SharedPreMng(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(CommonData.NAME_DATA_TABLE, Context.MODE_PRIVATE);
    }

    public String getLinkDataA(Context context, String keyData) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CommonData.NAME_DATA_TABLE, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(keyData, null);
    }

    public void setLinkDataA(Context mContext, String keyData, String link) {
        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(CommonData.NAME_DATA_TABLE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyData, link);
        editor.commit();
    }

}
