package com.hopelib.libhopebasepro.utilmethor;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferMngService {

    SharedPreferences sharedPreferences;

    public SharedPreferMngService(Context mContext) {
        sharedPreferences = mContext.getSharedPreferences(DataCM.ADS_MODE, Context.MODE_PRIVATE);
    }

    public void setRadomAds(Context mContext, int adsMode){
        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(DataCM.ADS_MODE, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(DataCM.KEY_RAMDOM_ADS, adsMode);
        editor.commit();
    }

    public int getRadomAds(Context mContext) {
        if (sharedPreferences == null) {
            sharedPreferences = mContext.getSharedPreferences(DataCM.ADS_MODE, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(DataCM.KEY_RAMDOM_ADS, 0);
    }

}
